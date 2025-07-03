package fr.eni.encheres.controller;

import fr.eni.encheres.bll.user.UserService;
import fr.eni.encheres.bo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/profile")
@SessionAttributes({"connectedUser", "updateUserInfo"})
public class ProfilController {

    private static final Logger logger = LoggerFactory.getLogger(ProfilController.class);
    private final UserService userService;

    public ProfilController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("updateUserInfo")
    public User AddUser() {
        return new User();
    }


    @GetMapping("")
    public String showProfil(@RequestParam(name = "username", required = true) String username,
                             Model model) {
        User userFetched = this.userService.findByUsername(username);
        if (userFetched != null) {
            model.addAttribute("userFetched", userFetched);
            return "profile";
        } else logger.info("Unknown user");

        return "redirect:/encheres";
    }

    @GetMapping("/update")
    public String updateProfil(@ModelAttribute("connectedUser") User connectedUser) {
            return "edit-profile";
    }

    @PostMapping("/update")
    public String updateProfil(@ModelAttribute("updateUserInfo") User updateUserInfo,
                               @ModelAttribute("connectedUser") User connectedUser,
                               @RequestParam(name = "oldPassword", required = false) String oldPassword,
                               @RequestParam(name = "confirmPassword", required = false) String confirmPassword) {
        logger.info("updateUserInfo : " + updateUserInfo.toString());
        logger.info("connectedUser : " + connectedUser.toString());
        this.userService.update(updateUserInfo);
        // met à jour les données en session
        connectedUser = updateUserInfo;

//        if (updateUserInfo.getPassword().equals(confirmPassword)) {
//            if (this.userService.isPasswordCorrect(connectedUser.getUserName(), oldPassword)) {
//            } else {
//                logger.info("updateProfil : Mot de passe erroné");
//            }
//        } else {
//            logger.info("updateProfil : Veuillez saisir le même mot de passe");
//        }
        return "redirect:/profile?username=" + connectedUser.getUserName();

    }

    @GetMapping("/delete")
    public String deleteUser( @ModelAttribute("connectedUser") User connectedUser,
                              SessionStatus status){
        if (this.userService.deleteUserById(connectedUser.getUserName())){
            logger.info("deleteUser : {} deleted", connectedUser.getUserName());
            status.setComplete();
            return "redirect:/encheres";
        }
        logger.info("deleteUser : {} not deleted", connectedUser.getUserName());
        return "redirect:/profile?username=" + connectedUser.getUserName();
    }


}
