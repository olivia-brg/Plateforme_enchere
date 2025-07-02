package fr.eni.encheres.controller;

import fr.eni.encheres.bll.article.ArticleService;
import fr.eni.encheres.bll.article.ArticleServiceImpl;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.User;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"connectedUser"})
public class EnchereController {
	
	private ArticleService articleService;

    @RequestMapping(path={"/","/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String accueil(@ModelAttribute("membreEnSession") User connectedUser){
        return "index";
    }


	@GetMapping("/detailArticle")
	public String afficherUnArticle(@RequestParam(name = "id") int id, Model model) {
		
			Article current = articleService.consultArticleById(id);
			if (current != null) {
				System.out.println(current);
				model.addAttribute("article", current);
			}else
				System.out.println("Article inconnu!!");

		
		return "detail-vente";
	}




}
