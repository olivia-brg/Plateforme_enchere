package fr.eni.encheres.bll.article;

import java.util.List;

import fr.eni.encheres.bo.Adress;
import fr.eni.encheres.bo.Bid;
import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Category;
import fr.eni.encheres.bo.User;
import fr.eni.encheres.dal.AdresseDAO;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.BidDAO;
import fr.eni.encheres.dal.CategoryDAO;
import fr.eni.encheres.dal.UserDAO;
import fr.eni.encheres.exception.BusinessException;

@Service
public class ArticleServiceImpl implements ArticleService{

	private ArticleDAO articleDAO;
	private AdresseDAO adressDAO;
	private BidDAO bidDAO;
	private CategoryDAO categoryDAO;
	private UserDAO userDAO;
	
	
	public ArticleServiceImpl(ArticleDAO articleDAO, AdresseDAO adressDAO, BidDAO bidDAO, CategoryDAO categoryDAO,
			UserDAO userDAO) {
		
		this.articleDAO = articleDAO;
		this.adressDAO = adressDAO;
		this.bidDAO = bidDAO;
		this.categoryDAO = categoryDAO;
		this.userDAO = userDAO;
	}
	
	public Article consultArticleById(int id) {
		return this.articleDAO.read(id);
	}

	@Override
	public List<Article> consultArticles() throws BusinessException {
		List<Article> articles = this.articleDAO.findAll();
		
		for (Article article : articles) {
			User user = userDAO.findUserById(article.getUser().getId());
//			Adress adress = adressDAO.findAddressById(article.getWithdrawalAdress().getDeliveryAdressId());
			article.setUser(user);
//			article.setWithdrawalAdress(adress);
		}
		
		
		return articles;
	}

	@Override
	public List<Category> consultCategories() {
		return categoryDAO.readAll();
	}

	@Override
	public Category consultCategoryById(int id) {
		return categoryDAO.read(id);
	}

	@Override
	public Bid consultBidById(int id) {
		return bidDAO.read(id);
	}

	@Override
	public List<Bid> consultBidsByArticleId(int id) {
		return bidDAO.readAllFromArticleId(id);
	}

	@Override
	public Adress consultAdressById(int id){
		return adressDAO.findAddressById(id);
	}

	@Override
	public void createArticle(Article article, int userId) {
		//On extrait l'adresse pour la partie vérification
		Adress adress = article.getWithdrawalAdress();
		//Ola méthode suivante vérifie l'existence dans la BDD sur la base des trois attributs
		Boolean adressExists=adressDAO.findIfExists(adress);
		//Si l'adresse existe on lui attribue l'id existante
		if(adressExists){adress.setDeliveryAddressId(adressDAO.findIdByAdress(adress));
			System.out.println("id de l\'adresse existante"+adress.getDeliveryAddressId());}
		//sinon on crée l'adresse
		else{adressDAO.create(adress);
			System.out.println("id de l\'adresse"+adress.getDeliveryAddressId());}
		//enfin on crée l'article
		articleDAO.create(article, userId, adress.getDeliveryAddressId());
	}
}
