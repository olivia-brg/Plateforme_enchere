package fr.eni.encheres.bll.bid;


import fr.eni.encheres.bo.Bid;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public interface BidService {
    void createBid(Bid bid, int userId, int articleId);
    Bid getHighestBid(int articleId);
    public Bid consultBidById(int id);
    public List<Bid> consultBidsByArticleId(int id);
}
