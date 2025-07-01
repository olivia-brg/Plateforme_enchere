package fr.eni.encheres.bll.login;

import fr.eni.encheres.bo.User;
import fr.eni.encheres.exception.BusinessException;

public interface LoginService {
    User load(String username, String password) throws BusinessException;
    boolean isUserExisting(String userName, BusinessException be) ;
}
