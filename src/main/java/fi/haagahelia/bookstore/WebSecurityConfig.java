package fi.haagahelia.bookstore;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import fi.haagahelia.bookstore.web.UserDetailServiceImpl;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailServiceImpl userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/css/*").permitAll() // Enable css when logged out
            .and()
            .authorizeRequests().antMatchers("/signup", "/saveuser").permitAll()
            .and()
            .authorizeRequests()
              .anyRequest().authenticated()
              .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
            	.permitAll()
            	.invalidateHttpSession(true); // Invalidate session
        		
    }
	
	/* Not used after User entity
	@Bean
    @Override
    public UserDetailsService userDetailsService() {
        List<UserDetails> users = new ArrayList();
    	UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("user")
                .roles("USER")
                .build();

    	users.add(user);
    	
    	user = User.withDefaultPasswordEncoder()
                   .username("admin")
                   .password("admin")
                   .roles("USER", "ADMIN")
                   .build();
    	
    	users.add(user);
    	
        return new InMemoryUserDetailsManager(users);
    }
	*/
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		/*
		auth
        .inMemoryAuthentication()
            .withUser("user").password("user").roles("USER");
		
		auth
    	.inMemoryAuthentication()
        	.withUser("admin").password("user").roles("ADMIN");
		*/
        auth.userDetailsService(userDetailsService).passwordEncoder(new 
        		BCryptPasswordEncoder());
    }
	

}
