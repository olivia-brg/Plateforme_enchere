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
    public boolean deleteUserById(int id) {
        logger.info("deleteUserById : " + id);
        return this.userDAO.deleteUserById(id);
    }

    @Override
    public User findById(int id) {
        return userDAO.findUserById(id);
    }


    @Override
    public boolean update(User user) throws BusinessException {
        logger.info("update : " + user.toString());
        BusinessException be = new BusinessException();
        boolean isValid = !doesUsernameExist(user.getUserName(), be);
//        isValid &= validerGenre(film.getGenre(), be);
//        isValid &= validerActeurs(film.getActeurs(), be);
//        isValid &= validerRealisateur(film.getRealisateur(), be);
        return this.userDAO.update(user);
    }

    @Override
    public boolean isPasswordCorrect(String username, String password, BusinessException be) {
        return this.userDAO.isPasswordCorrect(username, password);
    }

	@Override
	@Transactional(rollbackFor = BusinessException.class)
	public void createNewUser(User user)throws BusinessException {
		BusinessException be = new BusinessException();
		boolean userExists = isUserExisting(user.getUserName(), be);

		if (userExists) throw be;
		else userDAO.insertNewUser(user);

	}

    @Override
    public User findByUsername(String username) {
        return this.userDAO.findByUsername(username);
    }

    private boolean doesUsernameExist(String username, BusinessException be) {

        boolean usernameExist = userDAO.doesUsernameExist(username);
        if (usernameExist) {
            be.add("L'username existe déjà");
            return false;
        }

        return true;
    }


}
