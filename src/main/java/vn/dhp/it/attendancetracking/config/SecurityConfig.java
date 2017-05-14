/*
 * Copyright (c) 2017. KMS Technology, Inc.
 */

package vn.dhp.it.attendancetracking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import vn.dhp.it.attendancetracking.domain.user.UserRole;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Profile("!utest")
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String PARA_USERNAME = "username";
    public static final String PARA_CREDENTIAL = "password";
    private static final Integer REMEMBER_ME_TIME = 30 * 24 * 60 * 60;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**", "/images/**", "/css/**", "/js/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/user/register", "/signin*", "/user/activate", "/user/reset-password*", "/sidebar").permitAll()
            .antMatchers("/api/match/update-match-score/*", "/competitor/save", "/competitions/*/bettings/create").hasAuthority(UserRole.ADMIN.getAuthority())
            .antMatchers("/competitions/create-page", "/competitions/*/update", "/competitions/import", "/competitions/auto-import").hasAuthority(UserRole.ADMIN.getAuthority())
            .antMatchers("/competitor/save").hasAuthority(UserRole.ADMIN.getAuthority())
            .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/signin")
                .loginProcessingUrl("/signin")
                .usernameParameter(PARA_USERNAME)
                .passwordParameter(PARA_CREDENTIAL)
                .failureHandler(authenticationFailureHandler)
                .successHandler(authenticationSuccessHandler)
            .and()
            .logout()
                .logoutUrl("/signout")
                .deleteCookies("remember-me")
                .logoutSuccessUrl("/signin");
    }


}




