package fr.eni.encheres.dal;

import fr.eni.encheres.bo.User;

public interface UserDAO {

    User login(String username, String password);
    public boolean findId(String userName);
    void update(User user);
    boolean isPasswordCorrect(String username, String password);
    void insertNewUser(User user);
    User findByUsername(String username);
    User findUserById(int id);
    boolean deleteUserById(String username);


}
