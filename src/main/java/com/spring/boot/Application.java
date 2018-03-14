package com.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/*
Главный класс Spring boot, который запускает приложение
@SpringBootApplication включает в себя несколько аннотаций:
 * @Configuration - обозначает класс комонентом spring, рассматривающийся как источник других компонентов Spring.
 * @EnableAutoConfiguration - эта аннотация позволяет автоматически конфигурировать приложение, исходя из зависимостей в pom.xml file Maven
Данные конфигурации будут отображаться в файле application.properties
 * @ComponentScan - сканирует пакет и грубже, в котором находится этот класс.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
