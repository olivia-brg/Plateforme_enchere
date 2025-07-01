package fr.eni.encheres.bll.user;

import fr.eni.encheres.bo.User;
import fr.eni.encheres.dal.UserDAO;
import fr.eni.encheres.dal.UserDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User load(String username, String password) {
        User user = this.userDAO.login(username, password);
        logger.error("load : " + user.toString());
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
