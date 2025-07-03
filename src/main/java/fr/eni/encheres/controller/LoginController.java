package fr.eni.encheres.controller;

import fr.eni.encheres.bll.user.UserService;
import fr.eni.encheres.bll.user.UserServiceImpl;
import fr.eni.encheres.bo.User;
import fr.eni.encheres.exception.BusinessException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes({"connectedUser"})
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @ModelAttribute("connectedUser")
    public User AddUser(){
        return new User();
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "userName", required = true) String userName,
                        @RequestParam(name = "password", required = true) String password,
                        @ModelAttribute("connectedUser") User connectedUser, RedirectAttributes redirectAttributes) {
        User user = new User();
		try {
			user = this.userService.load(userName, password);
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
	            connectedUser.setAdmin(user.isAdmin());
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
	            connectedUser.setAdmin(false);
	        }
	        System.out.println(connectedUser);
	        return "redirect:/";
		} catch (BusinessException e) {
			redirectAttributes.addFlashAttribute("errorMessages", e.getMessages());
	        return "redirect:/login";

		}
    }

    @GetMapping(path="/signIn")
    public String signIn(Model model){
        model.addAttribute("user", new User());
        return "signIn";
    }

	@GetMapping(path="/home")
	public String returnToIndex(Model model){
		model.addAttribute("user", new User());

		return "redirect:/";

	}


    @PostMapping(path="/signIn")
    public String addUser(Model model){
        model.addAttribute("user", new User());
        //ajouter articleService
        return "redirect:/";
    }


    @GetMapping("/logout")
    public String finSession(SessionStatus status) {
        // Suppression des attributs de @SessionAttributs
        status.setComplete();
        return "redirect:/";
    }

    @PostMapping("/register")
    public String registred(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
    	System.out.println("méthode registred workin");


    	try {
			this.userService.createNewUser(user);
			return "redirect:/";
		} catch (BusinessException e) {

			redirectAttributes.addFlashAttribute("errorMessages", e.getMessages());
			logger.warn("exception username already used");
			return "redirect:/signIn";
		}


    }
}
