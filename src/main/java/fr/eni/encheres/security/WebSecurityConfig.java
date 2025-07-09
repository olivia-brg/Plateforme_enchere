package fr.eni.encheres.security;
import fr.eni.encheres.controller.LoginController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /**
     * Récupération des utilisateurs de l'application via la base de données
     */

    protected final Log logger = LogFactory.getLog(getClass());

    @Bean
    UserDetailsManager userDetailsManager(DataSource datasource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(datasource);


        //todo remplacer 1 par isActive
        users.setUsersByUsernameQuery("select username, password, 1 from auctionUsers where username=?");
        System.out.println("user database has been loaded");
        users.setAuthoritiesByUsernameQuery("select username, Role from auctionUsers where username =?");
        return users;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/encheres").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/js/**","/flipflop.mp3").permitAll()
                        .requestMatchers("/signIn").permitAll()
                        .requestMatchers("/profile").permitAll()
                        .requestMatchers("/profile/update").authenticated()
                        .requestMatchers("/sell").authenticated()
                        .requestMatchers("/bid").authenticated()
                        .requestMatchers("/register").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/loginSucess")
                )
                .logout(logout -> logout
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll()
        );

        return http.build();
    }

}
