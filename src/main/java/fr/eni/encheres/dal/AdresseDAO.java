package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Adress;
import fr.eni.encheres.exception.BusinessException;


public interface AdresseDAO {

	Adress findAddressById(long id);
}
