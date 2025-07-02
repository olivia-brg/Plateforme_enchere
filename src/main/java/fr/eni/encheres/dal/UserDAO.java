package fr.eni.encheres.dal;

import fr.eni.encheres.bo.User;

public interface UserDAO {

    User login(String username, String password);
    void update(User user);
    boolean isPasswordCorrect(String username, String password);
    User findByUsername(String username);

}
