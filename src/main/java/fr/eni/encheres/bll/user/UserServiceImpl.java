package fr.eni.encheres.bll.user;

import fr.eni.encheres.bo.User;
import fr.eni.encheres.dal.PasswordDTO;
import fr.eni.encheres.dal.UserDAO;

import fr.eni.encheres.dal.UserDTO;
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
    public void deactivateUser(int id){
    this.userDAO.deactivateUser(id);
    }
    @Override
    public void activateUser(int id){
        this.userDAO.activateUser(id);
    }
    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public User load(String username, String password) throws BusinessException{
    	BusinessException be = new BusinessException();
    	boolean userExists = isUserExisting(username, be);
        boolean isPasscorrect = isPasswordCorrect(username, password, be);
    	if(userExists && isPasscorrect) {

    		return this.userDAO.login(username, password);}


    	else {
    		be.add("wrong informations");
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
    @Transactional(rollbackFor = BusinessException.class)
    public boolean updateProfile(UserDTO user, int id) throws BusinessException {
        logger.info("update : " + user.toString());
        BusinessException be = new BusinessException();

        boolean isValid = isUsernameAvailable(user.getUserName(), id, be);
//      isValid &= -ajouter autant de parametres de validation que nécessaire-

        if (isValid) {
            logger.info("update : " + user.toString());
            return this.userDAO.updateProfile(user, id);
        } else  {
            logger.error("Error updating : " + user.toString());
            throw be;
        }
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public boolean updatePassword(PasswordDTO passwordModif, int id) throws BusinessException {
        BusinessException be = new BusinessException();
        boolean isValid = isPasswordCorrect(id, passwordModif.getOldPassword(), be);

        if (isValid) {
            userDAO.updatePassword(passwordModif.getNewPassword(), id);
        } else {
            throw be;
        }
        return false;
    }

    @Override
    public boolean isPasswordCorrect(String username, String password, BusinessException be) {
        return this.userDAO.isPasswordCorrect(username, password);
    }

    public boolean isPasswordCorrect(int id, String password, BusinessException be) {
        return this.userDAO.isPasswordCorrect(id, password);
    }

	@Override
	@Transactional(rollbackFor = BusinessException.class)
	public void createNewUser(User user)throws BusinessException {
		BusinessException be = new BusinessException();

        // id car utilisateur pas en base de donnée
        boolean isValid = isUsernameAvailable(user.getUserName(), 0, be);
//      isValid &= -ajouter autant de paramètres de validation que nécessaire-

        if (isValid) {
            logger.info("Creating : " + user.toString());
            userDAO.insertNewUser(user);
        } else {
            logger.error("Error creating : " + user.toString());
            throw be;
        }
	}

    @Override
    public User findByUsername(String username) {
        return this.userDAO.findByUsername(username);
    }
    @Override
    public int findIdByUsername(String username) {
        return this.userDAO.findIdByUsername(username);
    }



    public boolean isUsernameAvailable(String username, int id, BusinessException be) {

        boolean usernameAvailable = userDAO.isUsernameAvailable(username, id);
        if (!usernameAvailable) {
            be.add("L'username existe déjà");
            return false;
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public boolean checkPasswordConfirmation(String firstPassword, String secondPassword) throws BusinessException {
        BusinessException be = new BusinessException();
        if (firstPassword.equals(secondPassword)) return true;

        be.add("Les mots de passe ne correspondent pas.");
        throw be;
    }
  
  @Override
      public int getUserCredit(int userId) {
        return userDAO.findUserCreditByUserId(userId);

    }

    @Transactional(rollbackFor = BusinessException.class)
    public boolean isCreditValid(float bidAmount,int userId) throws BusinessException{
        BusinessException be = new BusinessException();
        User currentUser = userDAO.findUserById(userId);

        if (bidAmount > currentUser.getCredit()) {

            be.add("Crédit insuffisant");

            throw be;
        }
        return true;

    }

    public void substractCredit(float bidAmount, int userId) throws BusinessException {

        User currentUser = userDAO.findUserById(userId);
        userDAO.updateCredit(userId, (int) (currentUser.getCredit() - bidAmount));

    }

    public void addCredit(float bidAmount,int userId) throws BusinessException {

        User currentUser = userDAO.findUserById(userId);

        userDAO.updateCredit(userId, currentUser.getCredit() + bidAmount);

    }

}

