package fr.eni.encheres.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import fr.eni.encheres.bo.Category;



public class CategoryDAOImpl implements CategoryDAO{
	
	private final String FIND_ALL = "SELECT ID, NAME FROM CATEGORIES";

	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public Category read(long id) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", id);
		return jdbcTemplate.queryForObject(FIND_ALL, mapSqlParameterSource, new BeanPropertyRowMapper<>(Category.class));
	}

}
