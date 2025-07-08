package fr.eni.encheres.bll.user;

import fr.eni.encheres.bo.User;
import fr.eni.encheres.dto.PasswordDTO;
import fr.eni.encheres.dto.UserDTO;
import fr.eni.encheres.exception.BusinessException;

public interface UserService {

    User load(String username, int id, String password) throws BusinessException;
    User findById(int id);
    User findByUsername(String username);
    void createNewUser(User user) throws BusinessException;
    boolean updateProfile(UserDTO user, int id) throws BusinessException;
    boolean updatePassword(PasswordDTO passwordModif, int id) throws BusinessException;
    boolean isPasswordCorrect(int id, String password, BusinessException be);
    boolean doesUserExist(String userName, BusinessException be);
    boolean deleteUserById(int id);
    boolean isUsernameAvailable(String username, int id, BusinessException be);
    boolean checkPasswordConfirmation(String firstPassword, String secondPassword) throws BusinessException;

    int getUserCredit(int userId);
    boolean isCreditValid(float bidAmount, int userId) throws BusinessException;
    void substractCredit(float bidAmount, int userId) throws BusinessException;
    void addCredit(float bidAmount, int userId) throws BusinessException;


    int findIdByUsername(String username);
    void deactivateUser(int userId);
    void activateUser(int userId);
}
