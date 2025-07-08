package fr.eni.encheres.bll.article;

import fr.eni.encheres.bo.Address;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Category;
import fr.eni.encheres.dto.ArticleSearchCriteria;
import fr.eni.encheres.exception.BusinessException;

import java.util.List;

public interface ArticleService {

    Article consultArticleById(int id);

    List<Article> consultArticles() throws BusinessException;

    List<Category> consultCategories();

    Category consultCategoryById(int id);

    List<Article> getFilteredArticles(ArticleSearchCriteria criteria, int currentUserId, int page, int size);

    Address consultAddressById(int id);

    void createArticle(Article article, int userId);

}
