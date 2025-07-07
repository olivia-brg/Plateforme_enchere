package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Bid;

import java.util.List;

public interface BidDAO {
    public int create(Bid bid, int userId, int  articleId);
    Bid read(long id);
    List<Bid> readAllFromArticleId(long articleId);
    List<Bid> readAllFromArticleIdByUserId(long articleId,long userId);

}
