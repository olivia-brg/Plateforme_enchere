package fr.eni.encheres.controller;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes({"connectedUser"})
public class EnchereController {


    @RequestMapping(path={"/","/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String accueil(@ModelAttribute("membreEnSession") User connectedUser){
        return "index";
    }

    @GetMapping("/newProduct")
    public String newArticle(Model model){
        Article article = new Article();
        model.addAttribute(article);
        return "new-product";
    };
}
