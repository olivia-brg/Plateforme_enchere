package fr.eni.encheres.bll.bid;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Bid;
import fr.eni.encheres.dal.BidDAO;
import fr.eni.encheres.dal.BidDAOImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidServiceImpl implements BidService {

    private BidDAO bidDAO;

    public void createBid(Bid bid, int userId, int articleId) {

        bidDAO.create(bid, userId, articleId);

    }

    public void getHighestBid(int articleId){
       List litsBid =
       bidDAO.readAllFromArticleId(articleId);


    }


}
