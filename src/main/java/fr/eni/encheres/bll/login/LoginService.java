package fr.eni.encheres.bll.login;

import fr.eni.encheres.bo.User;

public interface LoginService {
    User load(String username, String password);
}
