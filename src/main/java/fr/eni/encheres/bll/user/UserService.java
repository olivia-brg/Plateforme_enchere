package fr.eni.encheres.bll.user;

import fr.eni.encheres.bo.User;
import fr.eni.encheres.exception.BusinessException;

public interface UserService {

    User readById(int id);
    User load(String username, String password)throws BusinessException;
    void update(User user);
    boolean isPasswordCorrect(String username, String password);
    User findByUsername(String username);
    boolean isUserExisting(String userName, BusinessException be);
    boolean deleteUserById(String username);

}
