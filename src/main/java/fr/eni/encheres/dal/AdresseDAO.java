package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Adress;
import fr.eni.encheres.bo.Article;


public interface AdresseDAO {

	Adress read(long id);
	int create(Adress adress);
}
