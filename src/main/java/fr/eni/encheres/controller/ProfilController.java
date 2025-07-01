package fr.eni.encheres.controller;

import fr.eni.encheres.bll.user.UserService;
import fr.eni.encheres.bo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
@SessionAttributes({"connectedUser", "updateUserInfo"})
public class ProfilController {

    private UserService userService;

    public ProfilController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("updateUserInfo")
    public User AddUser(){
        return new User();
    }


    @GetMapping("")
    public String showProfil(@ModelAttribute("connectedUser") User connectedUser) {
        return "profile";
    }

    @PostMapping("/update")
    public String updateProfil(@ModelAttribute("updateUserInfo") User updateUserInfo,
                               @ModelAttribute("connectedUser") User connectedUser,
                               @RequestParam(name = "password", required = true) String password,
                               @RequestParam(name = "confirmPassword", required = true) String confirmPassword) {
        System.out.println("updateUserInfo = " + updateUserInfo);
        if (password.equals(confirmPassword)) {
            if (this.userService.isPasswordCorrect(updateUserInfo.getUserName(), password)) {
                this.userService.update(updateUserInfo);
                connectedUser = updateUserInfo;
            } else {
                System.out.println("Mot de passe erroné");
            }
        } else {
            System.out.println("Veuillez saisir le même mot de passe");
        }
        return "redirect:/profile";

    }


}
