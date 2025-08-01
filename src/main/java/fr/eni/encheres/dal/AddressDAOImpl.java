package fr.eni.encheres.dal;

import fr.eni.encheres.bll.user.UserServiceImpl;
import fr.eni.encheres.bo.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class AddressDAOImpl implements AddressDAO {

	private static final Logger logger = LoggerFactory.getLogger(AddressDAOImpl.class);

	private final String FIND_BY_ID = "SELECT ID, STREET, POSTALCODE, CITY FROM DELIVERYADDRESS WHERE ID = :id";
	private final String CREATE_ADDRESS = """
			INSERT INTO DELIVERYADDRESS(street, postalCode, city)
			VALUES(? , ?, ?)
	""";
	private final String FIND_IF_EXISTS = """
			SELECT COUNT(*) FROM DELIVERYADDRESS WHERE STREET = :street AND  POSTALCODE = :postalCode
			AND CITY = :city
			""";
	private final String FIND_ID_BY_ADDRESS = """
			SELECT ID FROM DELIVERYADDRESS WHERE STREET = :street AND  POSTALCODE = :postalCode
			AND CITY = :city
			""";

	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public Address findAddressById(long id){
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", id);
		return jdbcTemplate.queryForObject(FIND_BY_ID, mapSqlParameterSource, new BeanPropertyRowMapper<>(Address.class));
	}

	//idem ici on a une erreur dès qu'on ne trouve aucun résultat. A modifier
	@Override
	public int findIdByAddress(Address address) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("street", address.getStreet());
		namedParameters.addValue("postalCode", address.getPostalCode());
		namedParameters.addValue("city", address.getCity());
		int result = jdbcTemplate.queryForObject(FIND_ID_BY_ADDRESS, namedParameters, Integer.class);
		logger.info("id de l'adresse requêtée : {}", result);
		return result;
	}

	@Override
	public Boolean findIfExists(Address address) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("street", address.getStreet());
		namedParameters.addValue("postalCode", address.getPostalCode());
		namedParameters.addValue("city", address.getCity());
		int count=jdbcTemplate.queryForObject(FIND_IF_EXISTS, namedParameters, Integer.class);


		return count > 0;
	}


	@Override
	public int create(Address address) {

		KeyHolder keyHolder = new GeneratedKeyHolder();

		//int result = jdbcTemplate.update(CREATE_ADDRESS,namedParameters);
		jdbcTemplate.getJdbcTemplate().update(connection -> {
			PreparedStatement ps = connection.prepareStatement(CREATE_ADDRESS, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, address.getStreet());
			ps.setString(2, address.getCity());
			ps.setString(3, address.getPostalCode());
			return ps;
		}, keyHolder);
		address.setDeliveryAddressId(keyHolder.getKey().intValue());
		logger.info("L'adresse ajoutée : {}", address.toString());

		return keyHolder.getKey().intValue();
	}
}


