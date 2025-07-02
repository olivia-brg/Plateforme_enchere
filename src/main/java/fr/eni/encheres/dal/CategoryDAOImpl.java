package fr.eni.encheres.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import fr.eni.encheres.bo.Category;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CategoryDAOImpl implements CategoryDAO{
	
	private final String FIND_ALL = "SELECT ID, NAME FROM CATEGORIES";
	private final String FIND_BY_ID = "SELECT ID, NAME FROM CATEGORIES WHERE ID = :id";


	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public Category read(long id) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", id);
		return jdbcTemplate.queryForObject(FIND_BY_ID, mapSqlParameterSource, new BeanPropertyRowMapper<>(Category.class));
	}

	@Override
	public List<Category> readAll() {
		return jdbcTemplate.getJdbcOperations().query(FIND_ALL, new BeanPropertyRowMapper<>(Category.class));
	}


}
