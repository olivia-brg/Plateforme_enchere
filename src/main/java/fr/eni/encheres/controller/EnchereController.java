package fr.eni.encheres.controller;


import fr.eni.encheres.bll.article.ArticleService;
import fr.eni.encheres.bll.article.ArticleServiceImpl;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Category;
import fr.eni.encheres.bo.User;
import fr.eni.encheres.exception.BusinessException;

import java.util.List;

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

	
    private ArticleService articleService;
    
    EnchereController(ArticleService articleService) {
		this.articleService = articleService ;
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