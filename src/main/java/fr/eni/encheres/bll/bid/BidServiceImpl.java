package fr.eni.encheres.bll.bid;

import fr.eni.encheres.bll.article.ArticleService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Bid;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.ArticleDAOImpl;
import fr.eni.encheres.dal.BidDAO;
import fr.eni.encheres.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class BidServiceImpl implements BidService {


    private final BidDAO bidDAO;
    private final ArticleDAO articleDAO;

    public BidServiceImpl(BidDAO bidDAO, ArticleDAO articleDAO) {

        this.bidDAO = bidDAO;
        this.articleDAO = articleDAO;
    }

    public void createBid(Bid bid, int userId, int articleId) {
        bidDAO.create(bid, userId, articleId);

    }

    @Transactional(rollbackFor = BusinessException.class)
    public boolean isBidValid(float bidAmount, int articleId) throws BusinessException {
        BusinessException be = new BusinessException();
        Bid maxBid = getHighestBid(articleId);

        if (maxBid != null && bidAmount < maxBid.getBidAmount()) {
            be.add("La mise doit être suppérieur à l'enchère la plus haute !");
        }

        if (bidAmount < articleDAO.findArticleById(articleId).getStartingPrice()){
            be.add("La mise doit être supperieur au prix de départ !");
        }

        if (be.hasError()){
            throw be;
        }
        return true;
    }

    // we are looking for the highest bid for an article
    public Bid getHighestBid(int articleId) {
        List<Bid> listBid = bidDAO.readAllFromArticleId(articleId);

        if (listBid.isEmpty() || listBid == null) {
            return null;
        }

        Bid maxBid = Collections.max(listBid, Comparator.comparing(Bid::getBidAmount));
        Article article = articleDAO.findArticleById(articleId);
        maxBid.setArticle(article);
        return maxBid;
    }

    @Override
    public Bid consultBidById(int id) {
        return bidDAO.read(id);
    }

    @Override
    public List<Bid> consultBidsByArticleId(int id) {
        return bidDAO.readAllFromArticleId(id);
    }



}
