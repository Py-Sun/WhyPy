package com.example.whypyprojdect.config;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Configuration;

@Log
@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**").authenticated()
                 .anyRequest().permitAll()
                 .and()
                .formLogin()
                .loginPage("/auth/signin")
                .defaultSuccessUrl("/");



    }


}
