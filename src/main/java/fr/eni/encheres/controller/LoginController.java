package fr.eni.encheres.controller;

import fr.eni.encheres.bll.login.LoginService;
import fr.eni.encheres.bo.User;
import fr.eni.encheres.exception.BusinessException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes({"connectedUser"})
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @ModelAttribute("connectedUser")
    public User AddUser(){
        System.out.println("Add Attribut User to Session");
        return new User();
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "userName", required = true) String userName,
                        @RequestParam(name = "password", required = true) String password,
                        @ModelAttribute("connectedUser") User connectedUser) {

        User user = new User();
		try {
			user = this.loginService.load(userName, password);
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
	        return "redirect:/index";
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "redirect:/login";

		}
        

    }

    @GetMapping(path="/signIn")
    public String signIn(Model model){
        model.addAttribute("user", new User());
        return "signIn";
    }


    @PostMapping(path="/signIn")
    public String addUser(Model model){
        model.addAttribute("user", new User());
        //ajouter articleService
        return "redirect:/index";
    }


  
    @GetMapping("/logout")
    public String finSession(SessionStatus status) {
        // Suppression des attributs de @SessionAttributs
        status.setComplete();
        return "redirect:/index";

    }
}
