package fr.eni.encheres.controller;


import fr.eni.encheres.bll.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import fr.eni.encheres.bo.User;
@Controller
@SessionAttributes({"connectedUser"})
public class AdminController {
    private final UserService userService;


    public AdminController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("deactivateUser")
    public String deactivateUser(@RequestParam (name = "userId") int id, @RequestParam (name= "userName") String userName){
        User user = userService.findById(id);
        if (user.getIsActive()) {
            userService.deactivateUser(id);
        } else {
            userService.activateUser(id);
        }



        return "redirect:/profile?id=" + id;
    }
}
