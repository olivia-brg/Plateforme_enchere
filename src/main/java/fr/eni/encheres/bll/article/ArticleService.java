package fr.eni.encheres.bll.article;

import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Category;

public interface ArticleService {
	
	public Article consultArticleById(int id);
	
	public List<Article> consultArticles();

	public List<Category> consultCategories();

	public Category consultCategoryById(int id);

}
