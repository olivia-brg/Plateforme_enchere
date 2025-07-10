package fr.eni.encheres.controller;

import fr.eni.encheres.bll.user.UserService;
import fr.eni.encheres.bo.User;
import fr.eni.encheres.exception.BusinessException;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes({"connectedUser"})
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UserService userService;
    private final StandardServletMultipartResolver standardServletMultipartResolver;
    private final PasswordEncoder passwordEncoder;

    public LoginController(UserService userService, StandardServletMultipartResolver standardServletMultipartResolver, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.standardServletMultipartResolver = standardServletMultipartResolver;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage(
            @RequestParam(value = "error", required = false) String error,
            Model model
    ) {
        if (error != null) {
            model.addAttribute("errorMessage", "Nom d'utilisateur ou mot de passe incorrect.");
        }
        return "login";
    }

    @GetMapping("/login?error")
    public String loginError() {
        logger.info("Login unsuccessfull");
        return "login";
    }


//    @ModelAttribute("connectedUser")
//    public User AddUser(){
//        return new User();
//    }


    @GetMapping("/loginSucess")
    public String login(@AuthenticationPrincipal UserDetails userDetails,
                        @ModelAttribute("connectedUser") User connectedUser,
						RedirectAttributes redirectAttributes) {

		connectedUser.setUserName(userDetails.getUsername());
		connectedUser.setRole(userDetails.getAuthorities().iterator().next().getAuthority());
		connectedUser.setId(userService.findByUsername(connectedUser.getUserName()).getId());
		connectedUser.setIsActive(userService.findByUsername(connectedUser.getUserName()).getIsActive());

        logger.error(connectedUser.toString());
        logger.info(connectedUser.toString());
        logger.info("{} is connected", connectedUser.getUserName());
        logger.info("has role {}", connectedUser.getRole());
        logger.info("has id {}", connectedUser.getId());
        logger.info("is it active? {} ", connectedUser.getIsActive());

        return "redirect:/";
    }

    @GetMapping(path = "/signIn")
    public String signIn(Model model) {
        model.addAttribute("user", new User());
        return "signIn";
    }

    @GetMapping(path = "/home")
    public String returnToIndex(Model model) {
        model.addAttribute("user", new User());

        return "redirect:/";
    }


    @PostMapping(path = "/signIn")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        //ajouter articleService
        return "redirect:/";
    }


//    @GetMapping("/logout")
//    public String finSession(SessionStatus status) {
//		status.setComplete();
//        return "redirect:/";
//    }

    @PostMapping("/register")
    public String registred(@ModelAttribute User user, @ModelAttribute("connectedUser") User connectedUser, Model model, RedirectAttributes redirectAttributes) {
        logger.info("méthode registred workin");

        try {
            logger.info(user.getEmail());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            this.userService.createNewUser(user);

            user = this.userService.load(user.getUserName(), user.getId(), user.getPassword());
            if (user != null) {
                connectedUser.setId(user.getId());
                connectedUser.setUserName(user.getUserName());
                connectedUser.setFirstName(user.getFirstName());
                connectedUser.setLastName(user.getLastName());
                connectedUser.setEmail(user.getEmail());
                connectedUser.setPhoneNumber(user.getPhoneNumber());
                connectedUser.setStreet(user.getStreet());
                connectedUser.setCity(user.getCity());
                connectedUser.setPostalCode(user.getPostalCode());
                connectedUser.setCredit(user.getCredit());
                connectedUser.setRole(user.getRole());
            } else {
                connectedUser.setId(0);
                connectedUser.setUserName(null);
                connectedUser.setFirstName(null);
                connectedUser.setLastName(null);
                connectedUser.setEmail(null);
                connectedUser.setPhoneNumber(null);
                connectedUser.setStreet(null);
                connectedUser.setCity(null);
                connectedUser.setPostalCode(null);
                connectedUser.setCredit(0);
                connectedUser.setRole(null);
            }
            logger.info(connectedUser.toString());
            return "redirect:/";

        } catch (BusinessException e) {

            redirectAttributes.addFlashAttribute("errorMessages", e.getMessages());
            logger.info("exception username already used");
            return "redirect:/";
        }


    }
}
