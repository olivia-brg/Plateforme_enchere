package fr.eni.encheres.controller;


import fr.eni.encheres.bll.article.ArticleService;
import fr.eni.encheres.bll.bid.BidService;
import fr.eni.encheres.bll.user.UserService;
import fr.eni.encheres.bo.*;
import fr.eni.encheres.dal.AddressDAO;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.CategoryDAO;
import fr.eni.encheres.dto.ArticleSearchCriteria;
import fr.eni.encheres.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Controller
@SessionAttributes({"connectedUser"})
public class EnchereController {

    private static final Logger logger = LoggerFactory.getLogger(EnchereController.class);
    private final UserService userService;
    private final ArticleDAO articleDAO;
    private final ArticleService articleService;
    private final AddressDAO addressDAO;
    private final CategoryDAO categoryDAO;
    private final BidService bidService;


    EnchereController(ArticleService articleService, ArticleDAO articleDAO, AddressDAO addressDAO, CategoryDAO categoryDAO, UserService userService, BidService bidService) {
        this.articleService = articleService;
        this.articleDAO = articleDAO;
        this.addressDAO = addressDAO;
        this.categoryDAO = categoryDAO;
        this.userService = userService;
        this.bidService = bidService;
    }

    @RequestMapping(path = {"/", "/encheres"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String search(
            @ModelAttribute("connectedUser") User connectedUser,
            @ModelAttribute ArticleSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) throws BusinessException {

        List<Category> listeCategories = articleService.consultCategories();
        model.addAttribute("listeCategories", listeCategories);
        List<Article> articles = new ArrayList<>();

        // suppression du caratère de recherche fantôme
        if (criteria.getSearchText() != null && criteria.getSearchText().isEmpty()) criteria.setSearchText(null);

        if (criteria == null) {
            logger.warn("critères null");
            articles = articleService.consultArticles();
        }
        else {
            logger.warn(criteria.toString());
            int userId = connectedUser.getId();
            articles = articleService.getFilteredArticles(criteria, userId, page, size);
        }

        model.addAttribute("article", articles);
        return "encheres";
    }

    @GetMapping("/sell")
    public String newArticle(@ModelAttribute("connectedUser") User connectedUser, Model model) {
        List<Category> listeCategories = articleService.consultCategories();
        Article article = new Article();
        Address tempAdress = new Address();
        tempAdress.setCity(userService.findById(connectedUser.getId()).getCity());
        tempAdress.setStreet(userService.findById(connectedUser.getId()).getStreet());
        tempAdress.setPostalCode(userService.findById(connectedUser.getId()).getPostalCode());
        article.setWithdrawalAddress(tempAdress);
        model.addAttribute("article", article);
        model.addAttribute("listeCategories", listeCategories);
        return "new-product";
    }

    @PostMapping(path = "/sell")
    String insererArticle(@ModelAttribute("article") Article article, @ModelAttribute("connectedUser") User connectedUser) {
        article.setUser(connectedUser);
        article.setAuctionStartDate(LocalDateTime.now());
        //On appelle la méthode du service qui créera l'article
        articleService.createArticle(article, connectedUser.getId());


        //redirection vers l'accueil pour le moment je n'arrive pas a renvoyer sur la page article en conservant l'id.
        return "redirect:/";
        //return "redirect:/detailArticle(id=${article.id})";
        //"@{/detailArticle(id=${a.id})}" a essayer d'ajouter
    }

    @GetMapping("/detailArticle")
    public String afficherUnArticle(@RequestParam(name = "id") int id, Model model) {

        Article current = articleService.consultArticleById(id);
        if (current != null) {
            User vendeur = userService.findById(current.getUser().getId());
            Address address = articleService.consultAddressById(current.getWithdrawalAddress().getDeliveryAddressId());
            Category category = articleService.consultCategoryById(current.getCategory().getId());
            current.setUser(vendeur);
            current.setWithdrawalAddress(address);
            current.setCategory(category);

            model.addAttribute("article", current);
            Bid maxBid = bidService.getHighestBid(current.getId());
            model.addAttribute("maxBid", maxBid);
        }else
        {System.out.println("Article inconnu!!");}
        return "detail-vente";
    }

    @ModelAttribute("connectedUser")
    public User AddUser() {
        System.out.println("Add Attribut User to Session");
        return new User();
    }

    @PostMapping("/bid")
    public String newBid(@ModelAttribute("connectedUser") User connectedUser,
                         @RequestParam("user-bid") int bidAmount,
                         @RequestParam("articleId") int articleId,
                         Model model) throws BusinessException {

        Article currentArticle = articleService.consultArticleById(articleId);
        User vendeur = userService.findById(currentArticle.getUser().getId());
        Address address = articleService.consultAddressById(currentArticle.getWithdrawalAddress().getDeliveryAddressId());
        Category category = articleService.consultCategoryById(currentArticle.getCategory().getId());
        currentArticle.setUser(vendeur);
        currentArticle.setWithdrawalAddress(address);
        currentArticle.setCategory(category);

        Bid bid = new Bid();

        try {
            userService.isCreditValid(bidAmount, connectedUser.getId());
            bidService.isBidValid(bidAmount, currentArticle.getId());


            bid.setArticle(currentArticle);
            bid.setBidAmount(bidAmount);
            bid.setBidDate(LocalDate.now());

            bidService.createBid(bid, connectedUser.getId(), currentArticle.getId());
            Bid maxBid = bidService.getHighestBid(currentArticle.getId());

            model.addAttribute("bid", bid);
            model.addAttribute("maxBid", maxBid);
            model.addAttribute("article", currentArticle);



            return "detail-vente";

        }catch (BusinessException be){
            Bid maxBid = bidService.getHighestBid(currentArticle.getId());
            model.addAttribute("maxBid", maxBid);
            model.addAttribute("bid", bid);
            model.addAttribute("article", currentArticle);
            model.addAttribute("errorMessages", be.getMessages());
            return "detail-vente";
        }
    }
}