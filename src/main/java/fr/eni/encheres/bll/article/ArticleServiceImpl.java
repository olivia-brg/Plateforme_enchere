package fr.eni.encheres.bll.article;

import java.util.List;

import fr.eni.encheres.bo.Address;
import fr.eni.encheres.bo.Bid;
import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Category;
import fr.eni.encheres.bo.User;
import fr.eni.encheres.dal.AddressDAO;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.BidDAO;
import fr.eni.encheres.dal.CategoryDAO;
import fr.eni.encheres.dal.UserDAO;
import fr.eni.encheres.exception.BusinessException;

@Service
public class ArticleServiceImpl implements ArticleService{


	private ArticleDAO articleDAO;
	private AddressDAO addressDAO;
	private BidDAO bidDAO;
	private CategoryDAO categoryDAO;
	private UserDAO userDAO;
	
	
	public ArticleServiceImpl(ArticleDAO articleDAO, AddressDAO addressDAO, BidDAO bidDAO, CategoryDAO categoryDAO,
							  UserDAO userDAO) {
		
		this.articleDAO = articleDAO;
		this.addressDAO = addressDAO;
		this.bidDAO = bidDAO;
		this.categoryDAO = categoryDAO;
		this.userDAO = userDAO;

	}
	
	public Article consultArticleById(int id) {
		Article article = this.articleDAO.findArticleById(id);
		User user = userDAO.findUserById(article.getUser().getId());
		article.setUser(user);

		return article;
	}

	@Override
	public List<Article> consultArticles() throws BusinessException {
		List<Article> articles = this.articleDAO.findAll();
		
		for (Article article : articles) {
			User user = userDAO.findUserById(article.getUser().getId());
//			Address address = addressDAO.findAddressById(article.getWithdrawalAddress().getDeliveryAddressId());
			article.setUser(user);
//			article.setWithdrawalAddress(address);
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
	public Address consultAddressById(int id){
		return addressDAO.findAddressById(id);
	}

	@Override
	public void createArticle(Article article, int userId) {
		//On extrait l'adresse pour la partie vérification
		Address address = article.getWithdrawalAddress();
		//Ola méthode suivante vérifie l'existence dans la BDD sur la base des trois attributs
		Boolean addressExists = addressDAO.findIfExists(address);
		//Si l'adresse existe on lui attribue l'id existante
		if(addressExists){
			address.setDeliveryAddressId(addressDAO.findIdByAddress(address));
			System.out.println("id de l\'adresse existante : "+address.getDeliveryAddressId());
		}
		//sinon on crée l'adresse (Fonctionne sur une première création d'article)
		else{
			int newAdressId=addressDAO.create(address);
			address.setDeliveryAddressId(newAdressId);
			System.out.println("id de l\'adresse : "+ address.getDeliveryAddressId());
		}
		//enfin on crée l'article
		articleDAO.create(article, userId, address.getDeliveryAddressId());
	}
}
