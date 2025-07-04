package fr.eni.encheres.controller.converter;

import fr.eni.encheres.bll.user.UserService;
import fr.eni.encheres.bo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToUserConverter implements Converter<String, User> {


    private UserService userService;

    @Autowired
    public void setArticleService(UserService userService){
        this.userService = userService;
    }

    @Override
    public User convert(String id) {
        Integer TheId = Integer.parseInt(id);
        return userService.findById(TheId) ;
    }
}
