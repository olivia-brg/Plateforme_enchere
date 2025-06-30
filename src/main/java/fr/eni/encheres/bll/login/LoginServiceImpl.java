package fr.eni.encheres.bll.login;

import fr.eni.encheres.bo.User;
import fr.eni.encheres.dal.UserDAO;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private UserDAO userDAO;

    public LoginServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User load(String username, String password) {
        return this.userDAO.login(username, password);
    }
}
