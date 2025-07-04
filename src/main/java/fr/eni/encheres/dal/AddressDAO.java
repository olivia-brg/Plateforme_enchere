package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Adress;

import fr.eni.encheres.bo.Article;

import fr.eni.encheres.exception.BusinessException;



public interface AdresseDAO {


	int create(Adress adress);
	Adress findAddressById(long id);
	int findIdByAdress(Adress adress);
	Boolean findIfExists(Adress adress);

}
