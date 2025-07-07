package fr.eni.encheres.bll.bid;


import fr.eni.encheres.bo.Bid;

import java.util.Collections;
import java.util.Comparator;

public interface BidService {
    void createBid(Bid bid, int userId, int articleId);
    Bid getHighestBid(int articleId);
}
