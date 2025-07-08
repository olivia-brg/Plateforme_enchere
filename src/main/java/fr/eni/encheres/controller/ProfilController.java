package fr.eni.encheres.controller;

import fr.eni.encheres.bll.user.UserService;
import fr.eni.encheres.bo.User;
import fr.eni.encheres.dal.PasswordDTO;
import fr.eni.encheres.dal.ProfileFormDTO;
import fr.eni.encheres.dal.UserDTO;
import fr.eni.encheres.exception.BusinessException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

            logger.info("USER FOUND : {}", userFetched);
            System.out.println(userService.findById(id));
            return "profile";
        } else logger.info("Unknown user");

        return "redirect:/encheres";
    }

    @GetMapping("/update")
    public String updateProfil(@ModelAttribute("connectedUser") User connectedUser, Model model) {

        User user = userService.findById(connectedUser.getId());

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUserName());
        userDTO.setLastName(user.getLastName());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setEmail(user.getEmail());
        userDTO.setStreet(user.getStreet());
        userDTO.setPostalCode(user.getPostalCode());
        userDTO.setCity(user.getCity());
        PasswordDTO passwordDTO = new PasswordDTO();

        // Créer l'objet englobant
        ProfileFormDTO profileForm = new ProfileFormDTO();
        profileForm.setUser(userDTO);
        profileForm.setPasswordModification(passwordDTO);

        // Ajouter dans le modèle
        model.addAttribute("profileForm", profileForm);
        return "edit-profile";
    }

    @PostMapping("/update")
    public String updateProfile(@Valid @ModelAttribute("profileForm") ProfileFormDTO profileForm,
                                BindingResult bindingResult,
                                @ModelAttribute("connectedUser") User connectedUser,
                                Model model) throws BusinessException {

        // Gestion des erreurs de validation
        if (bindingResult.hasErrors()) {
            return "edit-profile";
        }

        try {
            // Mise à jour des infos utilisateur
            userService.updateProfile(profileForm.getUser(), connectedUser.getId());

            // Mise à jour mot de passe si champs remplis
//            PasswordDTO pwd = profileForm.getPasswordModification();
//            if (pwd.getNewPassword() != null && !pwd.getNewPassword().isBlank()) {
//;
//                userService.checkPasswordConfirmation(pwd.getNewPassword(), pwd.getConfirmPassword());
//                userService.updatePassword(pwd, connectedUser.getId());
//            }

            return "redirect:/profile?id=" + connectedUser.getId();

        } catch (BusinessException e) {
            model.addAttribute("errorMessages", e.getMessages());
            return "edit-profile";
        }
    }


    @GetMapping("/delete")
    public String deleteUser(@ModelAttribute("connectedUser") User connectedUser,
                             SessionStatus status) {
        if (this.userService.deleteUserById(connectedUser.getId())) {
            logger.info("deleteUser : {} deleted", connectedUser.getUserName());
            status.setComplete();
            return "redirect:/encheres";
        }
        logger.info("deleteUser : {} not deleted", connectedUser.getUserName());
        return "redirect:/profile?id=" + connectedUser.getId();
    }


}
