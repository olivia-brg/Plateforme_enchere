package fr.eni.encheres.dal;

import fr.eni.encheres.bo.User;
import fr.eni.encheres.exception.BusinessException;

public interface UserDAO {

    User login(String username, String password);
    boolean findId(String userName);
    boolean updateProfile(UserDTO user, int id);
    boolean isPasswordCorrect(String username, String password);
    boolean isPasswordCorrect(int id, String password);
    void insertNewUser(User user);
    User findByUsername(String username);
    boolean isUsernameAvailable(String username, int id);
    User findUserById(int id);
    boolean deleteUserById(int id);
    int findUserCreditByUserId(int id);

    int findIdByUsername(String username);
    void deactivateUser(int id);
    void activateUser(int id);

    boolean updatePassword(String newPassword, int id) throws BusinessException;



}
