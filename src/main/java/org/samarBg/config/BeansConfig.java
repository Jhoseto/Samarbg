package org.samarBg.config;

import org.modelmapper.ModelMapper;
import org.samarBg.repository.UserRepository;
import org.samarBg.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;


@Configuration
@EnableWebSecurity
public class BeansConfig {

    @Bean
    public ModelMapper modelMapper (){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

}
