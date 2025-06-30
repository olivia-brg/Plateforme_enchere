package fr.eni.encheres.bll.login;

import fr.eni.encheres.bo.User;

public class LoginServiceImpl implements LoginService {

    private UserDAO userDAO;

    public LoginServiceImpl(User userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User load(String username, String password) {
        return this.userDAO.login(username, password);
    }
}
