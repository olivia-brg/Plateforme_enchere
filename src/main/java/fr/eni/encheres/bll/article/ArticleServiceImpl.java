package fr.eni.encheres.bll.article;

import fr.eni.encheres.bll.bid.BidService;
import fr.eni.encheres.bll.user.UserService;
import fr.eni.encheres.bo.*;
import fr.eni.encheres.controller.EnchereController;
import fr.eni.encheres.dal.*;
import fr.eni.encheres.dto.ArticleSearchCriteria;
import fr.eni.encheres.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(EnchereController.class);
    private final ArticleDAO articleDAO;
    private final AddressDAO addressDAO;
    private final BidDAO bidDAO;
    private final CategoryDAO categoryDAO;
    private final UserDAO userDAO;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final BidService bidService;
    private final UserService userService;


    public ArticleServiceImpl(ArticleDAO articleDAO, AddressDAO addressDAO, BidDAO bidDAO, CategoryDAO categoryDAO,
                              UserDAO userDAO, NamedParameterJdbcTemplate namedParameterJdbcTemplate,BidService bidService, UserService userService) {

        this.articleDAO = articleDAO;
        this.addressDAO = addressDAO;
        this.bidDAO = bidDAO;
        this.categoryDAO = categoryDAO;
        this.userDAO = userDAO;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

        this.bidService = bidService;
        this.userService = userService;
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
    public List<Article> getFilteredArticles(ArticleSearchCriteria criteria, int currentUserId, int page, int size) {
        LocalDateTime dateNow = LocalDateTime.now();

        return articleDAO.searchWithFilters(criteria, currentUserId, page, size, dateNow);
    }

    @Override
    public Address consultAddressById(int id) {
        return addressDAO.findAddressById(id);
    }

    @Override
    public void createArticle(Article article, int userId) {
        //On extrait l'adresse pour la partie vérification
        Address address = article.getWithdrawalAddress();
        //la méthode suivante vérifie l'existence dans la BDD sur la base des trois attributs
        Boolean addressExists = addressDAO.findIfExists(address);
        //Si l'adresse existe on lui attribue l'id existante
        if (addressExists) {
            address.setDeliveryAddressId(addressDAO.findIdByAddress(address));
            System.out.println("id de l'adresse existante : " + address.getDeliveryAddressId());
        }
        //sinon on crée l'adresse (Fonctionne sur une première création d'article)
        else {
            int newAdressId = addressDAO.create(address);
            address.setDeliveryAddressId(newAdressId);
            System.out.println("id de l'adresse : " + address.getDeliveryAddressId());
        }
        //enfin on crée l'article
        articleDAO.create(article, userId, address.getDeliveryAddressId());
    }

    @Override
    public void updateArticle(Article article,int id) {
        articleDAO.updateArticle(article,id);
        logger.info("{} has been updated", article.getId());
    }


    public boolean isOnSaleArticle(int articleId) throws BusinessException {
        BusinessException be = new BusinessException();
        Article article = this.consultArticleById(articleId);

        LocalDateTime dateNow = LocalDateTime.now();
        LocalDateTime endDate = article.getAuctionEndDate();

        if (dateNow.isAfter(endDate)) {
            articleDAO.updateIsOnSale(articleId, false);
            be.add("La vente est finie !");
            return false;
        }
        else{
            return true;
       }
     }

    public void closeSale(int articleId) {

        articleDAO.updateIsOnSale(articleId, false);

        if (bidService.getHighestBid(articleId) != null) {
            articleDAO.updateSoldPrice(articleId, bidService.getHighestBid(articleId).getBidAmount());
        }

    }


    @Override
    public int countFilteredArticles(ArticleSearchCriteria criteria, int currentUserId) {
        LocalDateTime dateNow = LocalDateTime.now();
        return articleDAO.countFilteredArticles(criteria, currentUserId, dateNow);
    }

}
