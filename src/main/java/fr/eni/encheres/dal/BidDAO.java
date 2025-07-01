package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Bid;

import java.util.List;

public interface BidDAO {
    int createBid();
    Bid read(long id);
    List<Bid> readAllFromArticleId(long articleId);
    List<Bid> readAllFromArticleIdByUserId(long articleId,long userId);

}
