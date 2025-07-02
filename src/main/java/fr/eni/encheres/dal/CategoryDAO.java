package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Category;

import java.util.List;

public interface CategoryDAO {

	Category read(long id);

	List<Category> readAll();
}
