package fr.eni.encheres.dal;

import fr.eni.encheres.bo.User;

public interface UserDAO {

    User login(String username, String password);
    boolean findId(String userName);
    boolean update(User user, int id);
    boolean isPasswordCorrect(String username, String password);
    void insertNewUser(User user);
    User findByUsername(String username);
    boolean isUsernameAvailable(String username, int id);
    User findUserById(int id);
    boolean deleteUserById(int id);
    int findUserCreditByUserId(int id);


}
