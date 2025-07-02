package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Article;

public interface ArticleDAO {

	List<Article> findAll();

	Article read(long id);

	int create(Article article, int userId, int deliveryAddressId);
}
