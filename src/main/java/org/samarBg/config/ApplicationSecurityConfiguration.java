package org.samarBg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfiguration(UserDetailsService userDetailsService,
                                            PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // Разрешаване на статичните ресурси
                .antMatchers(
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/fonts/**",
                        "/impl/**",
                        "/video/**").permitAll()
                .antMatchers(
                        "/index",
                        "/allAccessories",
                        "/allHorses",
                        "/allads",
                        "/forgotten_password",
                        "/user/registration",
                        "/registration",
                        "/users/login",
                        "/login",
                        "/confirm").permitAll()
                // Забраняване на всичко останало за неаутентифицирани потребители
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login") // URL адрес на страницата за вход
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                .defaultSuccessUrl("/index")
                .failureForwardUrl("/login")
                .and()
                .logout()
                .logoutUrl("/users/logout")
                .logoutSuccessUrl("/index")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}
