package fr.eni.encheres.bll.bid;


import fr.eni.encheres.bo.Bid;
import fr.eni.encheres.exception.BusinessException;

import java.util.List;

public interface BidService {
    void createBid(Bid bid, int userId, int articleId);
    Bid getHighestBid(int articleId);
    Bid consultBidById(int id);
    List<Bid> consultBidsByArticleId(int id);
    boolean isBidValid(float bidAmount, int articleId ) throws BusinessException;
}
