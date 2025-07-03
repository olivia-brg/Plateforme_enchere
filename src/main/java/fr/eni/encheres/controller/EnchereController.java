package fr.eni.encheres.controller;


import fr.eni.encheres.bll.article.ArticleService;
import fr.eni.encheres.bll.article.ArticleServiceImpl;
import fr.eni.encheres.bo.Adress;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Category;
import fr.eni.encheres.bo.User;
import fr.eni.encheres.exception.BusinessException;

import java.util.List;

import fr.eni.encheres.dal.AdresseDAO;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.ArticleDAOImpl;
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

import java.util.List;


@Controller
@SessionAttributes({"connectedUser"})
public class EnchereController {


    private ArticleDAO articleDAO;
    private ArticleService articleService;
    private AdresseDAO adresseDAO;
    private CategoryDAO categoryDAO;
    
    EnchereController(ArticleService articleService, ArticleDAO articleDAO, AdresseDAO adresseDAO, CategoryDAO categoryDAO) {
		this.articleService = articleService ;
        this.articleDAO = articleDAO;
        this.adresseDAO = adresseDAO;
        this.categoryDAO = categoryDAO;
    }

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String accueil(@ModelAttribute("connectedUser") User connectedUser, Model model) throws BusinessException {
    	List<Article> articles = articleService.consultArticles();
		model.addAttribute("article", articles);
        return "index";
    }

    @GetMapping("/newProduct")
    public String newArticle(Model model){
        List<Category> listeCategories = articleService.consultCategories();
        Article article = new Article();
        model.addAttribute("article", article);
        model.addAttribute("listeCategories", listeCategories);
        return "new-product";
    }

    @PostMapping(path="/newProduct")
    String insererArticle(@ModelAttribute("article") Article article, @ModelAttribute("connectedUser") User connectedUser ){
        //On crée une adresse avec les attributs adresse de l'utilisateur
        Adress adress = new Adress();
        adress.setStreet(connectedUser.getStreet());
        adress.setCity(connectedUser.getCity());
        adress.setPostalCode(connectedUser.getPostalCode());
        //On implémente cette adresse à l'article.
        article.setWithdrawalAdress(adress);
        //On appelle la méthode du service qui créera l'article
        articleService.createArticle(article, connectedUser.getId());
        return "detail-vente";
    }
    
   

    @GetMapping("/detailArticle")
    public String afficherUnArticle(@RequestParam(name = "id") int id, Model model) {

        Article current = articleService.consultArticleById(id);
        if (current != null) {
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


}