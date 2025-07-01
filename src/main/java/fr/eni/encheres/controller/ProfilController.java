package fr.eni.encheres.controller;

import fr.eni.encheres.bll.user.UserService;
import fr.eni.encheres.bo.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
@SessionAttributes({"connectedUser"})
public class ProfilController {

    private UserService userService;

    public ProfilController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String showProfil(@ModelAttribute("connectedUser") User connectedUser) {
        return "profil";
    }

    @PostMapping("/update")
    public String updateProfil(@ModelAttribute("connectedUser") User connectedUser
                               /*BindingResult bindingResult*/
    ) {
//        if (bindingResult.hasErrors()) {
//            return "profil";
//        } else {
//        }
            this.userService.update(connectedUser);
            return "redirect:/profile";

    }


}
