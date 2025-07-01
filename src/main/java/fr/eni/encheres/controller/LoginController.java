package fr.eni.encheres.controller;

import fr.eni.encheres.bll.login.LoginService;
import fr.eni.encheres.bo.User;
import fr.eni.encheres.exception.BusinessException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes({"connectedUser"})
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "username", required = true) String username,
                        @RequestParam(name = "password", required = true) String password,
                        @ModelAttribute("connectedUser") User connectedUser) {
        User user;
		try {
			user = this.loginService.load(username, password);
			if (user != null) {
	            connectedUser.setId(user.getId());
	            connectedUser.setUserName(user.getUserName());
	            connectedUser.setFirsName(user.getFirsName());
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
	            connectedUser.setFirsName(null);
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
}
