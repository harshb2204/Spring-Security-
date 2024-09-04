package com.example.SecurityApp.SecurityApplication.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
//@EnableJpaAuditing(auditorAwareRef = "getAuditorAwareImpl")
public class Appconfig {

    @Bean
    ModelMapper getModelMapper(){
        return new ModelMapper();
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    AuditorAware<String> getAuditorAwareImpl(){
//        return new AuditorAwareImpl();
//    }
}
