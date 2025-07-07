package fr.eni.encheres.controller;


import fr.eni.encheres.bll.article.ArticleService;

import fr.eni.encheres.bll.bid.BidService;
import fr.eni.encheres.bll.user.UserService;
import fr.eni.encheres.bo.*;
import fr.eni.encheres.exception.BusinessException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import fr.eni.encheres.dal.AddressDAO;
import fr.eni.encheres.dal.ArticleDAO;

import fr.eni.encheres.dal.CategoryDAO;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;



@Controller
@SessionAttributes({"connectedUser"})
public class EnchereController {


    private final UserService userService;
    private ArticleDAO articleDAO;
    private ArticleService articleService;
    private AddressDAO addressDAO;
    private CategoryDAO categoryDAO;
    private BidService bidService;


    EnchereController(ArticleService articleService, ArticleDAO articleDAO, AddressDAO addressDAO, CategoryDAO categoryDAO, UserService userService, BidService bidService) {
		this.articleService = articleService ;
        this.articleDAO = articleDAO;
        this.addressDAO = addressDAO;
        this.categoryDAO = categoryDAO;
        this.userService = userService;
        this.bidService = bidService;
    }

    @RequestMapping(path = {"/", "/encheres"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String accueil(@ModelAttribute("connectedUser") User connectedUser,@RequestParam(required = false) Long category,@RequestParam(required = false) String search, Model model) throws BusinessException {
    	List<Article> articles = articleService.consultArticles();
        List<Category> listeCategories = articleService.consultCategories();

        model.addAttribute("listeCategories", listeCategories);
        model.addAttribute("category", category);

        if (search != null && !search.isBlank()) {
            articles = articles.stream().filter( article ->article.getName().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("article", articles);

        return "encheres";
    }

    @GetMapping("/sell")
    public String newArticle(@ModelAttribute("connectedUser") User connectedUser, Model model){
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

    @PostMapping(path="/sell")
    String insererArticle(@ModelAttribute("article") Article article, @ModelAttribute("connectedUser") User connectedUser, Model model ){
        article.setUser(connectedUser);
        article.setAuctionStartDate(LocalDateTime.now());
        //On appelle la méthode du service qui créera l'article
        articleService.createArticle(article, connectedUser.getId());

        model.addAttribute("article", article);


       
        return "/detail-vente";
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
            System.out.println(current);
            model.addAttribute("article", current);
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
    public String newBid(@ModelAttribute("connectedUser") User connectedUser,@RequestParam("user-bid") int bidAmount,@RequestParam("articleId") int articleId, Model model) {

        Article currentArticle = articleService.consultArticleById(articleId);
        Bid bid = new Bid();

        bid.setArticle(currentArticle);
        bid.setBidAmount(bidAmount);
        bid.setBidDate(LocalDate.now());

        System.out.println("l'utilisateur est " + connectedUser.toString());
        System.out.println("le current article est" + currentArticle.toString());

        bidService.createBid(bid, connectedUser.getId(),currentArticle.getId());
        Bid maxBid = bidService.getHighestBid(currentArticle.getId());

        model.addAttribute("bid", bid);
        model.addAttribute("maxBid", maxBid);
        model.addAttribute("article", currentArticle);

        return "detail-vente";
    }


}