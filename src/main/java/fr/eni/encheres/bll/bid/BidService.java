package fr.eni.encheres.bll.bid;


import fr.eni.encheres.bo.Bid;

public interface BidService {
    void createBid(Bid bid, int userId, int articleId);
}
