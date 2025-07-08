package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Address;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Category;
import fr.eni.encheres.bo.User;
import fr.eni.encheres.dto.ArticleSearchCriteria;
import fr.eni.encheres.dto.FilterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class ArticleDAOImpl implements ArticleDAO {

    private final String FIND_ALL = "SELECT ID, NAME, DESCRIPTION, AUCTIONSTARTDATE, AUCTIONENDDATE, STARTINGPRICE, SOLDPRICE, ISONSALE, CATEGORYID, DELIVERYADDRESSID, USERID, ImageURL FROM ARTICLES";

    private final String INSERT_NEW_ARTICLE = """
            	INSERT INTO articles(userID,categoryId,deliveryAddressId,name,description,auctionStartDate,auctionEndDate,startingPrice,isOnSale,IMAGEURL)
                         VALUES(:userId,:categoryId,:deliveryAddressId,:name,:description,:auctionStartDate,:auctionEndDate,:startingPrice,1,:imageURL);
            """;

    private final String FIND_BY_ID = """
            SELECT ID,
                   NAME,
                   DESCRIPTION,
                   AUCTIONSTARTDATE,
                   AUCTIONENDDATE,
                   STARTINGPRICE,
                   SOLDPRICE,
                   ISONSALE,
                   CATEGORYID,
                   DELIVERYADDRESSID,
                   USERID,
                   IMAGEURL 
            FROM ARTICLES WHERE ID = :id""";


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Article> findAll() {
        return namedParameterJdbcTemplate.query(FIND_ALL, new ArticleRowMapper());
    }

    @Override
    public Article findArticleById(long id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(FIND_BY_ID, mapSqlParameterSource, new ArticleRowMapper());
    }

    @Override
    public int create(Article article, int userId, int deliveryAddressId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        //Pas d'utilisation du keyholder pour le moment a voir si on en a besoin. Faire un prepared statement comme pour l'adresse.
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("deliveryAddressId", deliveryAddressId);
        namedParameters.addValue("userId", userId);
        namedParameters.addValue("name", article.getName());
        namedParameters.addValue("categoryId", article.getCategory().getId());
        namedParameters.addValue("description", article.getDescription());
        namedParameters.addValue("auctionStartDate", article.getAuctionStartDate());
        namedParameters.addValue("auctionEndDate", article.getAuctionEndDate());
        namedParameters.addValue("startingPrice", article.getStartingPrice());
        namedParameters.addValue("imageURL", article.getImageURL());
        System.out.println("L'article ajouté : " + article);
        // les dates sont nulles pour le moment
        return namedParameterJdbcTemplate.update(INSERT_NEW_ARTICLE, namedParameters);
    }


    public static class ArticleRowMapper implements RowMapper<Article> {

        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
            Article a = new Article();
            a.setId(rs.getInt("ID"));
            a.setName(rs.getString("NAME"));
            a.setDescription(rs.getString("DESCRIPTION"));

            a.setAuctionStartDate(rs.getTimestamp("AUCTIONSTARTDATE").toLocalDateTime());
            a.setAuctionEndDate(rs.getTimestamp("AUCTIONENDDATE").toLocalDateTime());
            a.setStartingPrice(rs.getFloat("STARTINGPRICE"));
            a.setSoldPrice(rs.getFloat("SOLDPRICE"));
            a.setOnSale(rs.getBoolean("ISONSALE"));
            a.setImageURL(rs.getString("ImageURL"));


            // Catgory's Association
            Category category = new Category();
            category.setId(rs.getInt("CATEGORYID"));
            a.setCategory(category);

            // Address's Association
            Address address = new Address();
            address.setDeliveryAddressId(rs.getInt("DELIVERYADDRESSID"));
            a.setWithdrawalAddress(address);

            User user = new User();
            user.setId(rs.getInt("USERID"));
            a.setUser(user);

            return a;
        }
    }
}

