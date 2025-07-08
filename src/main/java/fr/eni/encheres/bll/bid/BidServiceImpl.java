package fr.eni.encheres.bll.bid;

import fr.eni.encheres.bll.article.ArticleService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Bid;
import fr.eni.encheres.bo.User;
import fr.eni.encheres.dal.BidDAO;
import fr.eni.encheres.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class BidServiceImpl implements BidService {

    private final ArticleService articleService;

    private BidDAO bidDAO;


    public BidServiceImpl(ArticleService articleService, BidDAO bidDAO) {
        this.articleService = articleService;
        this.bidDAO = bidDAO;
    }

    public void createBid(Bid bid, int userId, int articleId) {
        bidDAO.create(bid, userId, articleId);

    }


    @Transactional(rollbackFor = BusinessException.class)
    public boolean isBidValid(int bidAmount, int articleId ) throws BusinessException{
        BusinessException be = new BusinessException();
        Bid maxBid = getHighestBid(articleId);
        if (maxBid != null && bidAmount < maxBid.getBidAmount()) {
            be.add("L'enchère doit être suppérieur à l'enchère la plus haute");
            throw be;
        }
        return true;

    }



//    we are looking for the highest bid for an article
    public Bid getHighestBid(int articleId) {
        List<Bid> listBid = bidDAO.readAllFromArticleId(articleId);

        if (listBid.isEmpty() || listBid == null) {
            return null;
        }
        return Collections.max(listBid, Comparator.comparing(Bid::getBidAmount));
    }

    @Override
    public Bid consultBidById(int id) {
        return null;
    }

    @Override
    public List<Bid> consultBidsByArticleId(int id) {
        return bidDAO.readAllFromArticleId(id);
    }



}
