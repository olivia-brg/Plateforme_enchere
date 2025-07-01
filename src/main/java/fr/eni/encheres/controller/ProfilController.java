package fr.eni.encheres.controller;

import fr.eni.encheres.bll.user.UserService;
import fr.eni.encheres.bo.User;
import fr.eni.encheres.dal.UserDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
@SessionAttributes({"connectedUser", "updateUserInfo"})
public class ProfilController {

    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(ProfilController.class);

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
        logger.info("updateUserInfo : " + updateUserInfo.toString());
        if (password.equals(confirmPassword)) {
            if (this.userService.isPasswordCorrect(updateUserInfo.getUserName(), password)) {
                this.userService.update(updateUserInfo);
                connectedUser = updateUserInfo;
            } else {
                logger.info("updateProfil : Mot de passe erroné");
            }
        } else {
            logger.info("updateProfil : Veuillez saisir le même mot de passe");
        }
        return "redirect:/profile";

    }


}
