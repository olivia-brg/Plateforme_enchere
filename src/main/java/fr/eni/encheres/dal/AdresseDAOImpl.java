package fr.eni.encheres.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import fr.eni.encheres.bo.Adress;
import fr.eni.encheres.bo.Category;
import org.springframework.stereotype.Repository;

@Repository
public class AdresseDAOImpl implements AdresseDAO{

	private final String FIND_BY_ID = "SELECT ID, ARTICLEID, STREET, ZIPCODE, CITY FROM DELIVRYADDRESS";

	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public Adress read(long id) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", id);
		return jdbcTemplate.queryForObject(FIND_BY_ID, mapSqlParameterSource, new BeanPropertyRowMapper<>(Adress.class));
	
	}

	
	
	
}
