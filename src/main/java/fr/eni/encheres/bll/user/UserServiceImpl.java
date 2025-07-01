package fr.eni.encheres.bll.user;

import fr.eni.encheres.bo.User;
import fr.eni.encheres.dal.UserDAO;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User load(String username, String password) {
        User user = this.userDAO.login(username, password);
        System.out.println(user);
        return user;
    }

    @Override
    public void update(User user) {
        this.userDAO.update(user);
    }

    @Override
    public boolean isPasswordCorrect(String username, String password) {
        return this.userDAO.isPasswordCorrect(username, password);
    }
}
