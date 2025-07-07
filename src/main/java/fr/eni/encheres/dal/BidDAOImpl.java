package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Bid;
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

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;



    BidDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public int create(Bid bid, int userId, int  articleId) {
    MapSqlParameterSource namedParameters = new MapSqlParameterSource();
    namedParameters.addValue("bidDate", bid.getBidDate());
    namedParameters.addValue("bidAmount", bid.getBidAmount());
    namedParameters.addValue("userId", userId);
    namedParameters.addValue("articleId", articleId);
        return namedParameterJdbcTemplate.update(INSERT_NEW_BID, namedParameters);
    }

    @Override
    public Bid read(long id) {
        String sql = "SELECT bidDate, bidAmount, userId, articleId FROM bids WHERE bidId = :id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, namedParameters ,new BeanPropertyRowMapper<>(Bid.class));

    }

    @Override
    public List<Bid> readAllFromArticleId(long artId) {
        String SQL = "SELECT bidDate, bidAmount, userId, articleId FROM bids WHERE articleId = ?";
        return namedParameterJdbcTemplate.getJdbcOperations().query(SQL, new BeanPropertyRowMapper<>(Bid.class), artId);
    }

    @Override
    public List<Bid> readAllFromArticleIdByUserId(long articleId, long userId) {
        String SQL = "SELECT bidDate, bidAmount, userId, articleId FROM bids WHERE (articleId = ? AND userId = ?)"   ;
        return namedParameterJdbcTemplate.getJdbcOperations().query(SQL, new BeanPropertyRowMapper<>(Bid.class), articleId, userId);
    }
}
