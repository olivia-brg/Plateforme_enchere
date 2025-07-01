package fr.eni.encheres.dal;

import fr.eni.encheres.bo.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDAOImpl implements UserDAO{

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
            WHERE userName = :userName
            AND password = :password
    """;

    private final String UPDATE_USER = """
        UPDATE auctionUsers SET userName = :userName,
                                firstName = :firstName,
                                lastName = :lastName,
                                email = :email,
                                phoneNumber = :phoneNumber,
                                street = :street,
                                city = :city,
                                postalCode = :postalCode,
                                password = :password
    """;

    private NamedParameterJdbcTemplate jdbcTemplate;

    public UserDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User login(String username, String password) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userName", username);
        mapSqlParameterSource.addValue("password", password);
        return jdbcTemplate.queryForObject(FIND_USER, mapSqlParameterSource, new UserRowMapper());
    }

    @Override
    public void update(User user) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userName", user.getUserName());
        mapSqlParameterSource.addValue("firstName", user.getFirsName());
        mapSqlParameterSource.addValue("lastName", user.getLastName());
        mapSqlParameterSource.addValue("email", user.getEmail());
        mapSqlParameterSource.addValue("phoneNumber", user.getPhoneNumber());
        mapSqlParameterSource.addValue("street", user.getStreet());
        mapSqlParameterSource.addValue("city", user.getCity());
        mapSqlParameterSource.addValue("postalCode", user.getPostalCode());
        mapSqlParameterSource.addValue("password", user.getPassword());
        jdbcTemplate.update(UPDATE_USER, mapSqlParameterSource);
    }

    class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User m = new User();
            m.setId(rs.getInt("id"));
            m.setUserName(rs.getString("userName"));
            m.setFirstName(rs.getString("firstName"));
            m.setLastName(rs.getString("lastName"));
            m.setEmail(rs.getString("email"));
            m.setPhoneNumber(rs.getString("phoneNumber"));
            m.setStreet(rs.getString("street"));
            m.setCity(rs.getString("city"));
            m.setPostalCode(rs.getString("postalCode"));
            m.setCredit(rs.getFloat("credit"));
            m.setAdmin(rs.getBoolean("isAdmin"));


            return m;
        }
    }
}
