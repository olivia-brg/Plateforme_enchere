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




public class ArticleDAOImpl implements ArticleDAO{
	
	private final String FIND_ALL = "SELECT ID, NAME, DESCRIPTION, AUCTIONSTARTDATE, AUCTIONENDDATE, STARTINGPRICE, SOLDPRICE, ISONSALE, CATEGORYID, DELIVERYADRESSEID, USERID FROM ARTICLES";
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<Article> findAll() {
		return jdbcTemplate.query(FIND_ALL, new ArticleRowMapper());
	}
	
	public Article read(long id) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", id);
		return jdbcTemplate.queryForObject(FIND_ALL, mapSqlParameterSource, new ArticleRowMapper());
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
			adress.setDeliveryAdressId(rs.getInt("DELIVERYADRESSEID"));
			a.setWithdrawalAdress(adress);
			
			User user = new User();
			user.setId(rs.getInt("USERID"));
			a.setUser(user);

			return a;
		}
	}
}

