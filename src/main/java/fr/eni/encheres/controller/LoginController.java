package fr.eni.encheres.controller;

import fr.eni.encheres.bll.user.UserService;
import fr.eni.encheres.bo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes({"connectedUser"})
public class LoginController {

    private final UserService loginService;

    public LoginController(UserService loginService) {
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
        User user = this.loginService.load(userName, password);
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
        System.out.println(connectedUser.getUserName());
        return "redirect:/index";
    }

    @GetMapping("/signIn")
    public String signIn(){
        return "signIn";
    }

    @GetMapping("/logout")
    public String finSession(SessionStatus status) {
        // Suppression des attributs de @SessionAttributs
        status.setComplete();
        return "redirect:/index";
    }
}
