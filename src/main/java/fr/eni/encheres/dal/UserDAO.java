package fr.eni.encheres.dal;

import fr.eni.encheres.bo.User;
import fr.eni.encheres.dto.UserDTO;
import fr.eni.encheres.exception.BusinessException;

public interface UserDAO {

    User login(String username, String password);
    boolean findId(String userName);
    boolean updateProfile(UserDTO user, int id);
    String findPasswordById(int id);
    void insertNewUser(User user);
    User findByUsername(String username);
    boolean isUsernameAvailable(String username, int id);
    User findUserById(int id);
    boolean deleteUserById(int id);
    int findUserCreditByUserId(int id);
    boolean updatePassword(String newPassword, int id) throws BusinessException;


}
