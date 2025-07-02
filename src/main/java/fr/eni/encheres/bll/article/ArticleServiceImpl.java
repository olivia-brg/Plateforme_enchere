package fr.eni.encheres.bll.article;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Category;
import fr.eni.encheres.dal.AdresseDAO;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.BidDAO;
import fr.eni.encheres.dal.CategoryDAO;
import fr.eni.encheres.dal.UserDAO;

@Service
public class ArticleServiceImpl implements ArticleService{

	private ArticleDAO articleDAO;
	private AdresseDAO adressDAO;
	private BidDAO bidDAO;
	private CategoryDAO categoryDAO;
	private UserDAO user;
	
	
	public ArticleServiceImpl(ArticleDAO articleDAO, AdresseDAO adressDAO, BidDAO bidDAO, CategoryDAO categoryDAO,
			UserDAO user) {
		
		this.articleDAO = articleDAO;
		this.adressDAO = adressDAO;
		this.bidDAO = bidDAO;
		this.categoryDAO = categoryDAO;
		this.user = user;
	}
	
	public Article consultArticleById(int id) {
		return this.articleDAO.read(id);
	}

	@Override
	public List<Article> consultArticles() {
		List<Article> articles = this.articleDAO.findAll();
		return articles;
	}
	
}
