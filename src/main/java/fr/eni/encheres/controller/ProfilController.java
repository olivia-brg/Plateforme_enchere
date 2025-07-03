package fr.eni.encheres.controller;

import fr.eni.encheres.bll.user.UserService;
import fr.eni.encheres.bo.User;
import fr.eni.encheres.exception.BusinessException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/profile")
@SessionAttributes({"connectedUser"})
public class ProfilController {

    private static final Logger logger = LoggerFactory.getLogger(ProfilController.class);
    private final UserService userService;

    public ProfilController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("")
    public String showProfil(@RequestParam(name = "id", required = true) int id,
                             Model model) {
        User userFetched = this.userService.findById(id);
        if (userFetched != null) {
            model.addAttribute("userFetched", userFetched);
            return "profile";
        } else logger.info("Unknown user");

        return "redirect:/encheres";
    }

    @GetMapping("/update")
    public String updateProfil(@ModelAttribute("connectedUser") User connectedUser, Model model) {
        User user = userService.findById(connectedUser.getId());
        model.addAttribute("user", user);
        return "edit-profile";
    }

    @PostMapping("/update")
    public String updateProfil(@Valid @ModelAttribute("updateUserInfo") User updateUserInfo,
                               BindingResult bindingResult,
                               @ModelAttribute("connectedUser") User connectedUser,
                               Model model) {
        User user = userService.findById(connectedUser.getId());

//        if (bindingResult.hasErrors()) {
//            model.addAttribute("user", connectedUser);
//            return "edit-profile";
//        } else {
            try {
                if (this.userService.update(updateUserInfo)) {
                    connectedUser = updateUserInfo;
                    User userFetched = this.userService.findById(connectedUser.getId());
                    model.addAttribute("userFetched", userFetched);
                }
                return "redirect:/profile?id=" + connectedUser.getId();
            } catch (BusinessException e) {
                e.getMessages().forEach(m -> {
                    ObjectError error = new ObjectError("globalError", m);
                    bindingResult.addError(error);
                });
                model.addAttribute("user", connectedUser);
                return "edit-profile";
            }
//        }


//        @RequestParam(name = "oldPassword", required = false) String oldPassword,
//        @RequestParam(name = "confirmPassword", required = false) String confirmPassword
//
//        if (updateUserInfo.getPassword().equals(confirmPassword)) {
//            if (this.userService.isPasswordCorrect(connectedUser.getUserName(), oldPassword)) {
//            } else {
//                logger.info("updateProfil : Mot de passe erroné");
//            }
//        } else {
//            logger.info("updateProfil : Veuillez saisir le même mot de passe");
//        }

    }

    @GetMapping("/delete")
    public String deleteUser(@ModelAttribute("connectedUser") User connectedUser,
                             SessionStatus status) {
        if (this.userService.deleteUserById(connectedUser.getUserName())) {
            logger.info("deleteUser : {} deleted", connectedUser.getUserName());
            status.setComplete();
            return "redirect:/encheres";
        }
        logger.info("deleteUser : {} not deleted", connectedUser.getUserName());
        return "redirect:/profile?id=" + connectedUser.getId();
    }


}
