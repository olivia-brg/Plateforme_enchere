package fr.eni.encheres.bll.article;

import java.util.List;

import fr.eni.encheres.bo.Article;

public interface ArticleService {
	
	public Article consultArticleById(int id);
	
	public List<Article> consultArticles();

}
