package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Bid;
import fr.eni.encheres.bo.User;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDAOImpl implements UserDAO{
	private final String FIND_USER_NAME = "SELECT userName FROM auctionUsers WHERE  id = :id";

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
	public boolean findId(String userName) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("userName", userName);
		int val = jdbcTemplate.queryForObject(FIND_USER_NAME, mapSqlParameterSource, Integer.class);
		return val >= 1;
	}


    class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User m = new User();
            m.setId(rs.getInt("id"));
            m.setUserName(rs.getString("userName"));
            m.setFirstName(rs.getString("firstName"));
            m.setLastName(rs.getString("lastName"));
            m.setAdmin(rs.getBoolean("isAdmin"));
            // TODO: ajouter autant d'attribut que necessaire

            return m;
        }

    }
}
