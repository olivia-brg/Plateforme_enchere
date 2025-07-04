package fr.eni.encheres.dal;

import fr.eni.encheres.bo.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository

public class UserDAOImpl implements UserDAO{

	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    private final String DELETE_USER_BY_USERNAME = "DELETE FROM auctionUsers WHERE id = :id";
    private final String FIND_USERNAME_BY_ID = "SELECT COUNT(*) FROM auctionUsers WHERE userName = :userName";
    private final String FIND_IF_USERNAME_EXIST = "SELECT COUNT(*) FROM auctionUsers WHERE userName = :userName AND id != :id";

    private final String INSERT_NEW_USER = """
            INSERT INTO auctionUsers(userName,
	                                    firstName,
	                                    LastName,
	                                    email,
	                                    phoneNumber,
	                                    street,
	                                    city,
	                                    postalCode,
	                                    password,
	                                    credit)
	        VALUES (:userName,
	                :firstName,
	                :LastName,
	                :email, 
	                :phoneNumber,
	                :street,
	                :city,
	                :postalCode,
	                :password,
	                100)""";

    private final String FIND_USER = """
                SELECT id,
                       userName,
                       firstName,
                       lastName,
                       email,
                       phoneNumber,
                       street,
                       city,
                       postalCode,
                       credit,
                       isAdmin
                from auctionUsers
                WHERE userName = :userName AND
                      password = :password
            """;

    private final String FIND_USER_BY_USERNAME = """
                SELECT id,
                       userName,
                       firstName,
                       lastName,
                       email,
                       phoneNumber,
                       street,
                       city,
                       postalCode,
                       credit
                from auctionUsers
                WHERE userName = :userName
            """;

    private final String UPDATE_USER = """
                UPDATE auctionUsers SET firstName = :firstName,
                                        lastName = :lastName,
                                        email = :email,
                                        phoneNumber = :phoneNumber,
                                        street = :street,
                                        city = :city,
                                        postalCode = :postalCode
                WHERE userName = :userName
            """;

    private final String IS_PASSWORD_CORRECT = """
                SELECT COUNT(*)
                FROM auctionUsers
                WHERE username = :userName AND
                      password = :password
            """;

    private final String FIND_USER_BY_ID = """
            SELECT id,
            userName,
            firstName,
            lastName,
            email,
            phoneNumber,
            street,
            city,
            postalCode,
            credit,
            isAdmin
            from auctionUsers
            WHERE id = ?
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User login(String username, String password) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userName", username);
        mapSqlParameterSource.addValue("password", password);
        User user = jdbcTemplate.queryForObject(FIND_USER, mapSqlParameterSource, new UserLoginRowMapper());
        assert user != null;
        logger.info(user.toString());
        return user;
    }

    @Override
    public boolean findId(String userName) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userName", userName);

        logger.info("findId : " + userName);

        int count = jdbcTemplate.queryForObject(FIND_USERNAME_BY_ID, mapSqlParameterSource, Integer.class);
        return count >= 1;
    }

    @Override
    public boolean update(User user) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        mapSqlParameterSource.addValue("userName", user.getUserName());
        mapSqlParameterSource.addValue("firstName", user.getFirstName());
        mapSqlParameterSource.addValue("lastName", user.getLastName());
        mapSqlParameterSource.addValue("email", user.getEmail());
        mapSqlParameterSource.addValue("phoneNumber", user.getPhoneNumber());
        mapSqlParameterSource.addValue("street", user.getStreet());
        mapSqlParameterSource.addValue("city", user.getCity());
        mapSqlParameterSource.addValue("postalCode", user.getPostalCode());
        mapSqlParameterSource.addValue("password", user.getPassword());
        return jdbcTemplate.update(UPDATE_USER, mapSqlParameterSource) == 1;

    }

    @Override
    public boolean isPasswordCorrect(String username, String password) {
        try {
            if (username == null || password == null) {
                return false;
            }

            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("userName", username);
            params.addValue("password", password);

            Integer count = jdbcTemplate.queryForObject(
                    IS_PASSWORD_CORRECT,
                    params,
                    Integer.class
            );
            logger.info("isPasswordCorrect : " + (count != null && count > 0));
            return count != null && count > 0;

        } catch (Exception e) {
            logger.error("Erreur lors de la vérification du mot de passe", e);
            return false;
        }
    }

    @Override
    public User findUserById(int id) {
        return jdbcTemplate.getJdbcTemplate().queryForObject(FIND_USER_BY_ID,new BeanPropertyRowMapper<>(User.class), id);
    }

    //si on change le username dans la page modifier profil cette méthode ne fonctionne plus. Remplacer par findByID?
    @Override
    public User findByUsername(String username) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userName", username);
        User user = jdbcTemplate.queryForObject(FIND_USER_BY_USERNAME, mapSqlParameterSource, new UserFetchRowMapper());
        assert user != null;
        logger.info(user.toString());
        return user;
    }

    @Override
    public boolean doesUsernameExist(String username) {
        //TODO: finir d'implémenter la méthode (la requete fonctionne bien, changer le 1 par un id dynamique)
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userName", username);
        params.addValue("id", 1);
        Integer count = jdbcTemplate.queryForObject(FIND_IF_USERNAME_EXIST, params, Integer.class);
        return count != null && count > 0;
    }

    @Override
    public boolean deleteUserById(int id) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        logger.info("Deleting {}", id);
        return jdbcTemplate.update(DELETE_USER_BY_USERNAME, mapSqlParameterSource) == 1;
    }

    @Override
    public void insertNewUser(User user) {
        logger.warn("call to insert methode");

        Map<String, Object> params = new HashMap<>();
        params.put("userName", user.getUserName());
        params.put("firstName", user.getFirstName());
        params.put("LastName", user.getLastName());
        params.put("email", user.getEmail());
        params.put("phoneNumber", user.getPhoneNumber());
        params.put("street", user.getStreet());
        params.put("city", user.getCity());
        params.put("postalCode", user.getPostalCode());
        params.put("password", user.getPassword());  // Make sure password is hashed in real app

        jdbcTemplate.update(INSERT_NEW_USER, params);
    }


    static class UserLoginRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUserName(rs.getString("userName"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setEmail(rs.getString("email"));
            user.setPhoneNumber(rs.getString("phoneNumber"));
            user.setStreet(rs.getString("street"));
            user.setCity(rs.getString("city"));
            user.setPostalCode(rs.getString("postalCode"));
            user.setCredit(rs.getFloat("credit"));
            user.setAdmin(rs.getBoolean("isAdmin"));
            return user;
        }

    }

    static class UserFetchRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUserName(rs.getString("userName"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setEmail(rs.getString("email"));
            user.setPhoneNumber(rs.getString("phoneNumber"));
            user.setStreet(rs.getString("street"));
            user.setCity(rs.getString("city"));
            user.setPostalCode(rs.getString("postalCode"));
            user.setCredit(rs.getFloat("credit"));
            return user;
        }
    }
}
