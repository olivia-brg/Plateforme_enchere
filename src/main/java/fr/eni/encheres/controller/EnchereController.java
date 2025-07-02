package fr.eni.encheres.controller;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.User;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@SessionAttributes({"connectedUser"})
public class EnchereController {


    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String accueil(@ModelAttribute("connectedUser") User connectedUser) {
        return "index";
    }

    @GetMapping("/newProduct")
    public String newArticle(Model model){
        Article article = new Article();
        model.addAttribute(article);
        return "new-product";
    }

    @ModelAttribute("connectedUser")
    public User AddUser() {
        System.out.println("Add Attribut User to Session");
        return new User();
    }

}
