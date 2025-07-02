package fr.eni.encheres.bll.user;

import fr.eni.encheres.bo.User;

public interface UserService {
    User load(String username, String password);
    User readById(int id);

    void update(User user);
    boolean isPasswordCorrect(String username, String password);
}
