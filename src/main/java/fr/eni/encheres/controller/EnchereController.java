package fr.eni.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EnchereController {


    @RequestMapping(path={"/","/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String accueil(){
        return "index";
    }







}
