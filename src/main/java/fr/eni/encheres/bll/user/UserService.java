package fr.eni.encheres.bll.user;

import fr.eni.encheres.bo.User;

public interface UserService {
    User load(String username, String password);
    void update(User user);
}
