package fr.eni.encheres.bll.login;

import fr.eni.encheres.bo.User;

public class LoginServiceImpl implements LoginService {

    //TODO: Remplacer par userDAO
    private User user;

    public LoginServiceImpl(User user) {
        this.user = user;
    }

    @Override
    public User load(String username, String password) {
        return null;
    }
}
