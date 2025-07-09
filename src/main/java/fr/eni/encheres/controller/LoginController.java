package fr.eni.encheres.controller;

import fr.eni.encheres.bll.user.UserService;
import fr.eni.encheres.bo.User;
import fr.eni.encheres.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
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
    public String login() {
        return "login";
    }

    @GetMapping("/login?error")
    public String loginError() {
        System.out.println("Login unsuccessfull");
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


        logger.info("{} is connected", connectedUser.getUserName());
        logger.info("has role {}", connectedUser.getRole());
        logger.info("has id {}", connectedUser.getId());
        logger.info("is it active? {} ", connectedUser.getIsActive());


        return "redirect:/";

//        User user;
//		try {
//			if (user != null) {
//	            connectedUser.setId(user.getId());
//	            connectedUser.setUserName(user.getUserName());
//	            connectedUser.setAdmin(user.isAdmin());
//	        } else {
//	            connectedUser.setId(0);
//	            connectedUser.setUserName(null);
//	            connectedUser.setAdmin(false);
//	        }
//	        logger.info("{} is connected", connectedUser.getUserName());
//	        return "redirect:/";
//		} catch (BusinessException e) {
//			redirectAttributes.addFlashAttribute("errorMessages", e.getMessages());
//	        return "redirect:/login";
//		}
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
        System.out.println("méthode registred workin");
        logger.info("méthode registred workin");

        try {
            System.out.println(user.getEmail());
            //todo: est ce que c'est là qu'on met l'encodage ?
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
            System.out.println(connectedUser);
            return "redirect:/";

        } catch (BusinessException e) {

            redirectAttributes.addFlashAttribute("errorMessages", e.getMessages());
            logger.warn("exception username already used");
            return "redirect:/";
        }


    }
}
