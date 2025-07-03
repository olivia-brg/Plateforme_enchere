package fr.eni.encheres.bll.user;

import fr.eni.encheres.bo.User;
import fr.eni.encheres.dal.UserDAO;

import fr.eni.encheres.dal.UserDAOImpl;
import fr.eni.encheres.exception.BusinessException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public User load(String username, String password) throws BusinessException{
    	BusinessException be = new BusinessException();
    	boolean userExists = isUserExisting(username, be);
    	if(userExists) {
    		return this.userDAO.login(username, password);
    	}
    	else {
    		
    		throw be;
    	}
    	
    }
    @Override
    public boolean isUserExisting(String userName, BusinessException be) {
		
    	if(!this.userDAO.findId(userName)) {
    		be.add("user unknown");
    	    return false;
    	}
    	be.add("user name already existing");
    	return true;
	}

    @Override
    public boolean deleteUserById(String username) {
        logger.info("deleteUserById : " + username);
        return this.userDAO.deleteUserById(username);
    }

    @Override
    public User readById(int id) {
        return userDAO.findUserById(id);
    }


    @Override
    public void update(User user) {
        this.userDAO.update(user);
    }

    @Override
    public boolean isPasswordCorrect(String username, String password) {
        return this.userDAO.isPasswordCorrect(username, password);
    }

	@Override
	@Transactional(rollbackFor = BusinessException.class)
	public void createNewUser(User user)throws BusinessException {
		BusinessException be = new BusinessException();
		boolean userExists = isUserExisting(user.getUserName(), be);

		if (userExists) {
			throw be;
		}
		else {

		userDAO.insertNewUser(user);}

	}

    @Override
    public User findByUsername(String username) {
        return this.userDAO.findByUsername(username);
    }


}
