package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Bid;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BidDAOImpl implements BidDAO{

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    BidDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public int createBid() {
        return 0;
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
