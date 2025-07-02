package fr.eni.encheres.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import fr.eni.encheres.bo.Adress;
import fr.eni.encheres.bo.Category;
import fr.eni.encheres.exception.BusinessException;

import org.springframework.stereotype.Repository;

@Repository
public class AdresseDAOImpl implements AdresseDAO{

	private final String FIND_BY_ID = "SELECT ID, STREET, POSTALCODE, CITY FROM DELIVERYADDRESS WHERE ID = :id";
	private final String CREATE_ADRESS = """
	INSERT INTO DELIVERYADDRESS(street, postalCode, city)
	VALUES(:street, :postalCode, :city)
	""";

	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public Adress findAddressById(long id){
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", id);
		return jdbcTemplate.queryForObject(FIND_BY_ID, mapSqlParameterSource, new BeanPropertyRowMapper<>(Adress.class));
	}

	@Override
	public int create(Adress adress) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("street", adress.getStreet());
		namedParameters.addValue("postalCode", adress.getPostalCode());
		namedParameters.addValue("city", adress.getCity());
		return jdbcTemplate.update(CREATE_ADRESS,namedParameters);
	}

}
