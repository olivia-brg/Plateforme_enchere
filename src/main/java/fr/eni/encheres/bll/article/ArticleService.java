package fr.eni.encheres.bll.article;

import java.util.List;

import fr.eni.encheres.bo.Adress;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Bid;
import fr.eni.encheres.bo.Category;
import fr.eni.encheres.exception.BusinessException;

public interface ArticleService {
	
	public Article consultArticleById(int id);
	
	public List<Article> consultArticles() throws BusinessException;

	public List<Category> consultCategories();

	public Category consultCategoryById(int id);

	public Bid consultBidById(int id);

	public List<Bid> consultBidsByArticleId(int id);

	public Adress consultAdressById(int id);

}
