package fr.eni.encheres.bll.user;

import fr.eni.encheres.bo.User;
import fr.eni.encheres.dal.PasswordDTO;
import fr.eni.encheres.dal.UserDTO;
import fr.eni.encheres.exception.BusinessException;

public interface UserService {

    User findById(int id);
    User load(String username, String password) throws BusinessException;
    boolean updateProfile(UserDTO user, int id) throws BusinessException;
    boolean updatePassword(PasswordDTO passwordModif, int id) throws BusinessException;
    boolean isPasswordCorrect(String username, String password, BusinessException be);
    boolean isPasswordCorrect(int id, String password, BusinessException be);
    User findByUsername(String username);
    boolean isUserExisting(String userName, BusinessException be);
    void createNewUser (User user)throws BusinessException;
    boolean deleteUserById(int id);
    boolean isUsernameAvailable(String username, int id, BusinessException be);
    boolean checkPasswordConfirmation(String firstPassword, String secondPassword) throws BusinessException;
    int getUserCredit(int userId);

    int findIdByUsername(String username);
    void deactivateUser(int userId);
    void activateUser(int userId);

    public boolean isCreditValid(int bidAmount,int userId) throws BusinessException;


}
