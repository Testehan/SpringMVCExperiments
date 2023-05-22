package com.testehan.SpringMVCExperiments.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/login","/register/**","/clubs/**","/events/**","/css/**","/js/**")// to access these patterns a user can be NOT authenticated
                .permitAll()
                .and()
                .formLogin(form -> form.loginPage("/login")
                                        .defaultSuccessUrl("/clubs")
                                        .loginProcessingUrl("/login")
                                        .failureUrl("/login?error=true")
                                        .permitAll()
                 ).logout(
                         logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                 .permitAll()
                );

        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public void configure(AuthenticationManagerBuilder builder) throws Exception{
        builder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
}
