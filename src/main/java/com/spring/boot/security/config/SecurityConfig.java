package com.spring.boot.security.config;

import com.spring.boot.security.service.SuccessAuthHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

/**
 * By AndrewPC on 12.03.2018, in 16:04
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private DataSource dataSource;
    private SuccessAuthHandler successAuthHandler;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SecurityConfig(@Qualifier("dataSource")DataSource dataSource, SuccessAuthHandler successAuthHandler,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.successAuthHandler = successAuthHandler;
        this.dataSource = dataSource;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Value("${spring.query.user-query}")
    private String userQuery;
    @Value("${spring.query.role-query}")
    private String roleQuery;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/user/**").hasAnyAuthority("USER","ADMIN")
                .and()
                .formLogin()
                .loginPage("/")
                .successHandler(successAuthHandler)
                .failureUrl("/access")
                .usernameParameter("login").passwordParameter("password");
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery(userQuery)
                .authoritiesByUsernameQuery(roleQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    //TODO почему нельзя объявить bean encrypt

}
