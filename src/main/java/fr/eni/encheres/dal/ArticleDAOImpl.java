package fr.eni.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import fr.eni.encheres.bo.Adress;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Category;
import fr.eni.encheres.bo.User;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


@Repository
public class ArticleDAOImpl implements ArticleDAO{
	
	private final String FIND_ALL = "SELECT ID, NAME, DESCRIPTION, AUCTIONSTARTDATE, AUCTIONENDDATE, STARTINGPRICE, SOLDPRICE, ISONSALE, CATEGORYID, DELIVERYADDRESSID, USERID FROM ARTICLES";

	private final String INSERT_NEW_ARTICLE = """
				INSERT INTO articles(userID,categoryId,deliveryAddressId,name,description,auctionStartDate,auctionEndDate,startingPrice,isOnSale)
			             VALUES(:userId,:categoryId,:deliveryAddressId,:name,:description,:auctionStartDate,:auctionEndDate,:startingPrice,1);			
			""";


	private final String FIND_BY_ID = "SELECT ID, NAME, DESCRIPTION, AUCTIONSTARTDATE, AUCTIONENDDATE, STARTINGPRICE, SOLDPRICE, ISONSALE, CATEGORYID, DELIVERYADDRESSID, USERID FROM ARTICLES WHERE ID = :id";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<Article> findAll() {
		return jdbcTemplate.query(FIND_ALL, new ArticleRowMapper());
	}
	
	@Override
	public Article findArticleById(long id) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", id);
		return jdbcTemplate.queryForObject(FIND_BY_ID, mapSqlParameterSource, new ArticleRowMapper());
	}

	@Override
	public int create(Article article, int userId, int deliveryAddressId) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("deliveryAddressId", deliveryAddressId);
		namedParameters.addValue("userId", userId);
		namedParameters.addValue("name", article.getName());
		namedParameters.addValue("categoryId", article.getCategory().getId());
		namedParameters.addValue("description", article.getDescription());
		namedParameters.addValue("auctionStartDate", article.getAuctionStartDate());
		namedParameters.addValue("auctionEndDate", article.getAuctionEndDate());
		namedParameters.addValue("startingPrice", article.getStartingPrice());


		return jdbcTemplate.update(INSERT_NEW_ARTICLE, namedParameters);
	}




	class ArticleRowMapper implements RowMapper<Article>{

		@Override
		public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
			Article a = new Article();
			a.setId(rs.getInt("ID"));
			a.setName(rs.getString("NAME"));
			a.setDescription(rs.getString("DESCRIPTION"));
			a.setAuctionStartDate(rs.getDate("AUCTIONSTARTDATE").toLocalDate());
			a.setAuctionEndDate(rs.getDate("AUCTIONENDDATE").toLocalDate());
			a.setStartingPrice(rs.getFloat("STARTINGPRICE"));
			a.setSoldPrice(rs.getFloat("SOLDPRICE"));
			a.setOnSale(rs.getBoolean("ISONSALE"));
		

			// Catgory's Association
			Category category = new Category();
			category.setId(rs.getInt("CATEGORYID"));
			a.setCategory(category);

			// Adress's Association
			Adress adress = new Adress();
			adress.setDeliveryAdressId(rs.getInt("DELIVERYADDRESSID"));
			a.setWithdrawalAdress(adress);
			
			User user = new User();
			user.setId(rs.getInt("USERID"));
			a.setUser(user);

			return a;
		}
	}
}

