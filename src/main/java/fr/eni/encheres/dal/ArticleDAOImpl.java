package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Address;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Category;
import fr.eni.encheres.bo.User;
import fr.eni.encheres.controller.EnchereController;
import fr.eni.encheres.dto.ArticleSearchCriteria;
import fr.eni.encheres.dto.FilterType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.List;


@Repository
public class ArticleDAOImpl implements ArticleDAO {

    private final String FIND_ALL = "SELECT ID, NAME, DESCRIPTION, AUCTIONSTARTDATE, AUCTIONENDDATE, STARTINGPRICE, SOLDPRICE, ISONSALE, CATEGORYID, DELIVERYADDRESSID, USERID, ImageURL FROM ARTICLES";
    private final String DELETE_ARTICLE_BY_ID = "DELETE FROM articles WHERE id = :id";

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

    private final String UPDATE_ISONSALE = """
                UPDATE articles SET isOnSale = :isOnSale
                WHERE id = :id
            """;
    private final String UPDATE_SOLDPRICE = """
                UPDATE articles SET soldPrice = :soldPrice
                WHERE id = :id
            """;

    private static final Logger logger = LoggerFactory.getLogger(EnchereController.class);
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ArticleDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

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

    @Override

    public void updateIsOnSale(int articleId, boolean isOnSale) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("isOnSale", isOnSale);
        mapSqlParameterSource.addValue("id", articleId);
        namedParameterJdbcTemplate.update(UPDATE_ISONSALE, mapSqlParameterSource);
    }

    @Override
    public void updateSoldPrice(int articleId, float soldPrice) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("soldPrice", soldPrice);
        mapSqlParameterSource.addValue("id", articleId);
        namedParameterJdbcTemplate.update(UPDATE_SOLDPRICE, mapSqlParameterSource);
    }


    public List<Article> searchWithFilters(ArticleSearchCriteria criteria, int currentUserId, int page, int size, LocalDateTime dateNow) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        // Base requête SQL
        StringBuilder sql = new StringBuilder("SELECT DISTINCT a.* FROM articles a ");

        // jointure pour récupérer les enchères de l'utilisateur actif
        if (criteria.getSearchFilter() != null && criteria.getSearchFilter().contains(FilterType.ongoingAuctions)) {
            sql.append("JOIN bids b ON b.articleId = a.id ");
        }

        sql.append("WHERE 1=1 ");


        if (criteria.getSearchText() != null && !criteria.getSearchText().isBlank()) {
            sql.append("AND LOWER(a.name) LIKE LOWER(:searchText) ");
            namedParameters.addValue("searchText", "%" + criteria.getSearchText() + "%");
        }

        if (criteria.getCategoryId() != null) {
            sql.append("AND a.categoryId = :categoryId ");
            namedParameters.addValue("categoryId", criteria.getCategoryId());
        }

        if (criteria.getSearchFilter() != null) {
            for (FilterType filter : criteria.getSearchFilter()) {
                switch (filter) {
                    case openAuctions:
                        logger.warn("Filter openAuctions");
                        sql.append("AND a.auctionStartDate < :now AND a.auctionEndDate > :now ");
                        namedParameters.addValue("now", dateNow);
                        break;
                    case ongoingAuctions:
                        logger.warn("Filter ongoingAuctions");
                        sql.append("AND b.userId = :currentUserId ");
                        namedParameters.addValue("currentUserId", currentUserId);
                        break;
                    case CurrentSales:
                        logger.warn("Filter CurrentSales");
                        sql.append("AND a.userId = :currentUserId ");
                        sql.append("AND a.auctionStartDate < :now AND a.auctionEndDate > :now ");
                        namedParameters.addValue("currentUserId", currentUserId);
                        namedParameters.addValue("now", dateNow);
                        break;
                    case notStartedSales:
                        logger.warn("Filter notStartedSales");
                        sql.append("AND a.userId = :currentUserId ");
                        sql.append("AND a.auctionStartDate > :now ");
                        namedParameters.addValue("currentUserId", currentUserId);
                        namedParameters.addValue("now", dateNow);
                        break;
                    case finishedSales:
                        logger.warn("Filter finishedSales");
                        sql.append("AND a.userId = :currentUserId ");
                        sql.append("AND a.auctionEndDate < :now ");
                        namedParameters.addValue("currentUserId", currentUserId);
                        namedParameters.addValue("now", dateNow);
                        break;
                }
            }
        }

        namedParameters.addValue("limit", size);
        namedParameters.addValue("offset", page * size);
        sql.append("ORDER BY a.auctionEndDate ASC ");
        sql.append("OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY;");

        return namedParameterJdbcTemplate.query(sql.toString(), namedParameters, new ArticleRowMapper());
    }

    @Override
    public int countFilteredArticles(ArticleSearchCriteria criteria, int currentUserId, LocalDateTime dateNow) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        StringBuilder sql = new StringBuilder("SELECT COUNT(DISTINCT a.id) FROM articles a ");

        if (criteria.getSearchFilter() != null && criteria.getSearchFilter().contains(FilterType.ongoingAuctions)) {
            sql.append("JOIN bids b ON b.articleId = a.id ");
        }

        sql.append("WHERE 1=1 ");

        if (criteria.getSearchText() != null && !criteria.getSearchText().isBlank()) {
            sql.append("AND LOWER(a.name) LIKE LOWER(:searchText) ");
            namedParameters.addValue("searchText", "%" + criteria.getSearchText() + "%");
        }

        if (criteria.getCategoryId() != null) {
            sql.append("AND a.categoryId = :categoryId ");
            namedParameters.addValue("categoryId", criteria.getCategoryId());
        }

        if (criteria.getSearchFilter() != null) {
            for (FilterType filter : criteria.getSearchFilter()) {
                switch (filter) {
                    case openAuctions:
                        sql.append("AND a.auctionStartDate < :now AND a.auctionEndDate > :now ");
                        namedParameters.addValue("now", dateNow);
                        break;
                    case ongoingAuctions:
                        sql.append("AND b.userId = :currentUserId ");
                        namedParameters.addValue("currentUserId", currentUserId);
                        break;
                    case CurrentSales:
                        sql.append("AND a.userId = :currentUserId ");
                        sql.append("AND a.auctionStartDate < :now AND a.auctionEndDate > :now ");
                        namedParameters.addValue("currentUserId", currentUserId);
                        namedParameters.addValue("now", dateNow);
                        break;
                    case notStartedSales:
                        sql.append("AND a.userId = :currentUserId ");
                        sql.append("AND a.auctionStartDate > :now ");
                        namedParameters.addValue("currentUserId", currentUserId);
                        namedParameters.addValue("now", dateNow);
                        break;
                    case finishedSales:
                        sql.append("AND a.userId = :currentUserId ");
                        sql.append("AND a.auctionEndDate < :now ");
                        namedParameters.addValue("currentUserId", currentUserId);
                        namedParameters.addValue("now", dateNow);
                        break;
                }
            }
        }

        return namedParameterJdbcTemplate.queryForObject(sql.toString(), namedParameters, Integer.class);
    }

    @Override
    public boolean deleteArticle(int articleId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", articleId);
        logger.info("Deleting {}", articleId);
        return namedParameterJdbcTemplate.update(DELETE_ARTICLE_BY_ID, mapSqlParameterSource) == 1;
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

