package fr.eni.encheres.bll.login;

import fr.eni.encheres.bo.User;
import fr.eni.encheres.dal.UserDAO;
import fr.eni.encheres.exception.BusinessException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginServiceImpl implements LoginService {

    private UserDAO userDAO;

    public LoginServiceImpl(UserDAO userDAO) {
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
    		be.add("user unknown");
    		throw be;
    	}
    	
    }
    @Override
    public boolean isUserExisting(String userName, BusinessException be) {
		
    	if(!this.userDAO.findId(userName)) {
    	    
    	    return false;
    	}
    	return true;
	}
}
