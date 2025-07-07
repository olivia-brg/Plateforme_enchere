package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Bid;
import fr.eni.encheres.bo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BidDAOImpl implements BidDAO{

    private final String INSERT_NEW_BID = """
				INSERT INTO bids(bidDate, bidAmount, userId, articleId)
			             VALUES(:bidDate,:bidAmount,:userId,:articleId);
			""";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    BidDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public int create(Bid bid, int userId, int  articleId) {
    MapSqlParameterSource namedParameters = new MapSqlParameterSource();
    namedParameters.addValue("bidDate", bid.getAuctionDate());
    namedParameters.addValue("bidAmount", bid.getAuctionAmount());
    namedParameters.addValue("userId", userId);
    namedParameters.addValue("articleId", articleId);
        return namedParameterJdbcTemplate.update(INSERT_NEW_BID, namedParameters);
    }

    @Override
    public Bid read(long id) {
        String sql = "SELECT (bidDate, bidAmount, userId, articleId) FROM bids WHERE bidId = :id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters ,new BeanPropertyRowMapper<>(Bid.class));

    }

    @Override
    public List<Bid> readAllFromArticleId(long artId) {
        String SQL = "SELECT (bidDate, bidAmount, userId, articleId) FROM bids WHERE articleId = :artId";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("artId", artId);
        return namedParameterJdbcTemplate.queryForList(SQL, namedParameters, Bid.class);
    }

    @Override
    public List<Bid> readAllFromArticleIdByUserId(long articleId, long userId) {
        String SQL = "SELECT (bidDate, bidAmount, userId, articleId) FROM bids WHERE (articleId = :artId AND userId = :userId)"   ;
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("artId", articleId);
        namedParameters.addValue("userId", userId);
        return namedParameterJdbcTemplate.queryForList(SQL, namedParameters, Bid.class);
    }
}
