package fr.eni.encheres.bll.bid;

import fr.eni.encheres.bll.article.ArticleService;
import fr.eni.encheres.bo.Bid;
import fr.eni.encheres.dal.BidDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class BidServiceImpl implements BidService {

    private final ArticleService articleService;

    private BidDAO bidDAO;

    public BidServiceImpl(ArticleService articleService,  BidDAO bidDAO) {
        this.articleService = articleService;
        this.bidDAO = bidDAO;
    }

    public void createBid(Bid bid, int userId, int articleId) {
        bidDAO.create(bid, userId, articleId);

    }

//    we are looking for the highest bid for an article
    public Bid getHighestBid(int articleId){
       List<Bid> listBid = bidDAO.readAllFromArticleId(articleId);

       return Collections.max(listBid, Comparator.comparing(Bid::getBidAmount));

    }


}
