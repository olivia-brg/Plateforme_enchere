package fr.eni.encheres.bll.article;

import java.util.List;

import fr.eni.encheres.bo.*;
import fr.eni.encheres.dto.ArticleSearchCriteria;
import fr.eni.encheres.exception.BusinessException;

public interface ArticleService {

	public Article consultArticleById(int id);

	public List<Article> consultArticles() throws BusinessException;

	List<Article> getFilteredArticles(ArticleSearchCriteria criteria, int currentUserId, int page, int size);

	public List<Category> consultCategories();

	public Category consultCategoryById(int id);

	public Address consultAddressById(int id);

    void createArticle(Article article, int userId);

	public boolean isOnSaleArticle(int articleId) throws BusinessException;

//	public User articleClosed (int articleId);

	public void closeSale(int articleId);

}
