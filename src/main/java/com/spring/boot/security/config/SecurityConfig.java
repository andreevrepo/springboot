package com.spring.boot.security.config;

import com.spring.boot.security.service.SuccessAuthHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.sql.DataSource;

/*
 * Конфигурационный класс Spring Security
 * Мы расширяем класс WebSecurityConfigurerAdapter и реализуем методы конфигурации
 * @Configuration аннотация Spring, обозначающая что в классе присутствуют один или более компонентов Spring
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //Класс javax.sql в котором содержатся данные о бд
    private DataSource dataSource;
    //Кастомный класс, который реализует поведение при успешной аутентификации
    private SuccessAuthHandler successAuthHandler;

    /*
        Автоматическое внедрение через конструктор
        @Qualifier - квалификатор, уточняющий, какой именно компонент должен быть внедрен в DataSource
        Используется тут, потому что в DataSource есть более одного кандидата на авто внедрение
     */
    @Autowired
    public SecurityConfig(@Qualifier("dataSource")DataSource dataSource, SuccessAuthHandler successAuthHandler) {
        this.successAuthHandler = successAuthHandler;
        this.dataSource = dataSource;
    }

    /*
        @Value предоставляет возможность взять из application.properties стринговые ресурсы и внедрить их в поля.
        В данном случае внедрены sql запросы, которые используются для  AuthenticationManagerBuilder
        чтобы аутентифицировать пользователя из базы данных
     */
    @Value("${spring.query.user-query}")
    private String userQuery;
    @Value("${spring.query.role-query}")
    private String roleQuery;


    /*
        Переопределенная конфигурация позволяет переопределить фильтры Spring Security
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Отключаем защиту от csrf атак
        http.csrf().disable()
                //Фильтрация по привилегиям
        .authorizeRequests()
                //Запросы на /admin и глубже разрешены только пользователям с приоритетом ADMIN
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                //Запросы на /user и грубже разрешены пользователям с приоритетами USER или ADMIN
                .antMatchers("/user/**").hasAnyAuthority("USER","ADMIN")
                //Все остальные запросы разрешены любым пользователям
                // .authenticated() - разрешит только аутентифицированным пользователям заходить по остальным запросам
                .anyRequest().permitAll()
                //and возвращает HttpSecurity чтобы продолжить настройку
                .and()
                //Создаем кастомную форму
                .formLogin()
                //Которая будет по запросу /
                .loginPage("/")
                //В случае успешной аутентификации будет вызван кастомный объект класса см SuccessAuthHandler
                .successHandler(successAuthHandler)
                //В случае неудачной аутентификации будет перенаправление на /access
                .failureUrl("/access")
                //Имена логина и пароля параметров в форме
                .usernameParameter("login").passwordParameter("password");
    }

    /*
        AuthenticationManagerBuilder настраивает аутентификацию
        В данном случае аутентификация будет производиться с помощью запросов из базы данных.

        Также существует альтернатива, метод .userDetailsService() которому нужно передать класс реализующий интерфейс
        UserDetailsService и в этом классе аутентифицировать пользователя
     */
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //JdbcAuthentication означает, что аутентификация будет с помощью запросов через jdbc
        auth.jdbcAuthentication()
                //Запрос берущий логин и пароль юзера
                .usersByUsernameQuery(userQuery)
                //Запрос возвращающий привелегии
                .authoritiesByUsernameQuery(roleQuery)
                //Данные подключения к бд
                .dataSource(dataSource)
                //Шифрователь паролей
                .passwordEncoder(passwordEncoder());
    }

    //Объявление бина Password Encoder , Spring 5 требует предоставить Password Encoder
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
