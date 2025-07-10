package fr.eni.encheres.bll.article;

import fr.eni.encheres.bo.Address;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Bid;
import fr.eni.encheres.bo.Category;
import fr.eni.encheres.dto.ArticleSearchCriteria;
import fr.eni.encheres.exception.BusinessException;

import java.util.List;

public interface ArticleService {

    Article consultArticleById(int id);

    List<Article> consultArticles() throws BusinessException;

    List<Article> getFilteredArticles(ArticleSearchCriteria criteria, int currentUserId, int page, int size);

    List<Category> consultCategories();

    Category consultCategoryById(int id);


  	void createArticle(Article article, int userId);

	void updateArticle(Article article,int id);

    Address consultAddressById(int id);



    boolean isOnSaleArticle(int articleId) throws BusinessException;

    void closeSale(int articleId);

    int countFilteredArticles(ArticleSearchCriteria criteria, int currentUserId);

    boolean deleteArticle(int articleId);

	List<Bid> topFiveBids(int articleId);
}
