package fr.eni.encheres.bll.article;

import java.util.List;

import fr.eni.encheres.bo.Address;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Bid;
import fr.eni.encheres.bo.Category;
import fr.eni.encheres.exception.BusinessException;

public interface ArticleService {
	
	public Article consultArticleById(int id);
	
	public List<Article> consultArticles() throws BusinessException;

	public List<Category> consultCategories();

	public Category consultCategoryById(int id);



	public Address consultAddressById(int id);

	public void createArticle(Article article,int userId);

}
