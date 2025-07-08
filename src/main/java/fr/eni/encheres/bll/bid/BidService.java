package fr.eni.encheres.bll.bid;


import fr.eni.encheres.bo.Bid;
import fr.eni.encheres.exception.BusinessException;

import java.util.List;

public interface BidService {
    void createBid(Bid bid, int userId, int articleId);
    Bid getHighestBid(int articleId);
    public Bid consultBidById(int id);
    public List<Bid> consultBidsByArticleId(int id);
    public boolean isBidValid(float bidAmount, int articleId ) throws BusinessException;

}
