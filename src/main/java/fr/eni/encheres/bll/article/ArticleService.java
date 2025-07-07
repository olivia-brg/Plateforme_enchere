package fr.eni.encheres.bll.article;

import java.util.List;

import fr.eni.encheres.bo.Address;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Category;
import fr.eni.encheres.exception.BusinessException;

public interface ArticleService {
	
	Article consultArticleById(int id);
	
	List<Article> consultArticles() throws BusinessException;

	List<Category> consultCategories();

	Category consultCategoryById(int id);

	List<Article> getFilteredArticles(int categoryId, String search, String selectedOptions) throws BusinessException;
	List<Article> getCurrentSale(int categoryId, String search) throws BusinessException;
	List<Article> getFutureSale(int categoryId, String search) throws BusinessException;
	List<Article> getPastSale(int categoryId, String search) throws BusinessException;
	Address consultAddressById(int id);

	void createArticle(Article article,int userId);

}
