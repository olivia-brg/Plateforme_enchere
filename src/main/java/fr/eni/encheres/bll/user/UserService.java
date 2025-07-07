package fr.eni.encheres.bll.user;

import fr.eni.encheres.bo.User;
import fr.eni.encheres.exception.BusinessException;

public interface UserService {

    User findById(int id);
    User load(String username, String password)throws BusinessException;
    boolean update(User user, int id) throws BusinessException;
    boolean isPasswordCorrect(String username, String password, BusinessException be);
    User findByUsername(String username);
    boolean isUserExisting(String userName, BusinessException be);
    void createNewUser (User user)throws BusinessException;
    boolean deleteUserById(int id);
    boolean isUsernameAvailable(String username, int id, BusinessException be);
    int getUserCredit(int userId);
}
