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
import java.util.List;


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


    EnchereController(ArticleService articleService,
                      ArticleDAO articleDAO,
                      AddressDAO addressDAO,
                      CategoryDAO categoryDAO,
                      UserService userService,
                      BidService bidService) {
        this.articleService = articleService;
        this.articleDAO = articleDAO;
        this.addressDAO = addressDAO;
        this.categoryDAO = categoryDAO;
        this.userService = userService;
        this.bidService = bidService;
    }

    @RequestMapping(path = {"/", "/encheres"}, method = {RequestMethod.GET, RequestMethod.POST})

    public String search(
            @ModelAttribute(value = "connectedUser", binding = false) User connectedUser,
            @ModelAttribute ArticleSearchCriteria criteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            Model model
    ) throws BusinessException {

        List<Category> listeCategories = articleService.consultCategories();
        model.addAttribute("listeCategories", listeCategories);
//        List<Bid> topFiveBids = new ArrayList<Bid>(5);
//        model.addAttribute("topFiveBids", topFiveBids);

        // suppression du caratère de recherche fantôme
        if (criteria.getSearchText() != null && criteria.getSearchText().isEmpty()) criteria.setSearchText(null);

        logger.warn(criteria.toString());
        int userId = connectedUser.getId();

        List<Article> articles = articleService.getFilteredArticles(criteria, userId, page, size);

        //seul l'idUser est renseigné, on boucle pour pouvoir update les autres info user
        for(Article article: articles){
            article.setUser(userService.findById(article.getUser().getId()));
            List<Bid> topFiveBids = articleService.topFiveBids(article.getId());
            article.setTopFiveBids(topFiveBids);
        }

        // Vérifiez que chaque article a bien son ID
//        articles.forEach(article -> {
//            System.out.println("Article ID: " + article.getId() + ", Name: " + article.getName());
//        });

        for (Article article : articles) {
            System.out.println("IS USER :" + article.getId());
            if (!articleService.isOnSaleArticle(article.getId())){
                articleService.closeSale(article.getId());

            }

        }

        int totalArticles = articleService.countFilteredArticles(criteria, userId);
        int totalPages = (int) Math.ceil((double) totalArticles / size);

        model.addAttribute("criteria", criteria);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
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
    String insererArticle(@ModelAttribute("article") Article article,
                          @ModelAttribute("connectedUser") User connectedUser,
                          Model model) {
        article.setUser(connectedUser);
        article.setAuctionStartDate(LocalDateTime.now());
        //On appelle la méthode du service qui créera l'article
        articleService.createArticle(article, connectedUser.getId());

        model.addAttribute("article", article);

        return "/detail-vente";
    }

    @GetMapping("/detailArticle")
    public String afficherUnArticle(@RequestParam("articleId") int id, Model model, @ModelAttribute("connectedUser") User connectedUser) {
        // On garde une référence à l'ID de l'utilisateur connecté
        int connectedUserId = connectedUser.getId();
        System.out.println("user ID is" + connectedUserId);

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
            System.out.println("vendeur is : " + vendeur);
            System.out.println("connected user is : " + connectedUser);
            // On s'assure que l'ID de l'utilisateur connecté n'a pas changé


        }else
        {System.out.println("Article inconnu!!");}
        connectedUser.setId(connectedUserId);

        return "detail-vente";
    }

    //doublon avec celle de login controller
    @ModelAttribute("connectedUser")
    public User initConnectedUser(@ModelAttribute(value = "connectedUser", binding = false) User connectedUser) {
        if (connectedUser.getId() == 0) {
            // Si l'utilisateur n'est pas initialisé, on retourne un nouvel utilisateur
            return new User();
        }
        // Sinon, on retourne l'utilisateur existant sans le modifier
        return connectedUser;
    }


    @PostMapping("/bid")
    public String newBid(@ModelAttribute("connectedUser") User connectedUser,
                         @RequestParam("user-bid") float bidAmount,
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
            articleService.isOnSaleArticle(currentArticle.getId());
            userService.substractCredit(bidAmount, connectedUser.getId());

            Bid maxBid = bidService.getHighestBid(currentArticle.getId());
            if (maxBid != null) {
                userService.addCredit(maxBid.getBidAmount(), maxBid.getUser().getId());
            }

            bid.setArticle(currentArticle);
            bid.setBidAmount(bidAmount);
            bid.setBidDate(LocalDate.now());

            bidService.createBid(bid, connectedUser.getId(), currentArticle.getId());
            articleDAO.updateSoldPrice(articleId, bidService.getHighestBid(articleId).getBidAmount());

            maxBid = bidService.getHighestBid(currentArticle.getId());

            model.addAttribute("bid", bid);
            model.addAttribute("maxBid", maxBid);
            model.addAttribute("article", currentArticle);

            return "detail-vente" ;




        } catch (BusinessException be) {
            Bid maxBid = bidService.getHighestBid(currentArticle.getId());
            model.addAttribute("maxBid", maxBid);
            model.addAttribute("bid", bid);
            model.addAttribute("article", currentArticle);
            model.addAttribute("errorMessages", be.getMessages());

            return "detail-vente" ;

        }
    }

    @GetMapping("/changeArticle")
    public String changeArticle(@RequestParam(name = "id") int id, Model model) {
        Article current = articleService.consultArticleById(id);
        Address address = articleService.consultAddressById(current.getWithdrawalAddress().getDeliveryAddressId());
        current.setWithdrawalAddress(address);
        model.addAttribute("article", current);
        return "change-product";
    }

    @GetMapping("/delete")
    public String deleteArticle(@RequestParam(name = "articleId") int id, Model model) {
        articleService.deleteArticle(id);
        return "redirect:/encheres";
    }
}