package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dto.ArticleSearchCriteria;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleDAO {

    List<Article> findAll();

    Article findArticleById(long id);

    int create(Article article, int userId, int deliveryAddressId);

    void updateIsOnSale(int articleId, boolean isOnSale);

    void updateSoldPrice(int articleId, float soldPrice);

//	List<Article> searchByFilters(int categoryId, String search, String purchasesOptions);

    List<Article> searchWithFilters(ArticleSearchCriteria criteria, int currentUserId, int page, int size, LocalDateTime dateNow);

    int countFilteredArticles(ArticleSearchCriteria criteria, int currentUserId, LocalDateTime dateNow);


	public void updateArticle(Article article,int id);

    boolean deleteArticle(int articleId);

}
