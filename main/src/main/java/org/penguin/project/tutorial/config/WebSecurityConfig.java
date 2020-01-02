package org.penguin.project.tutorial.config;

import org.penguin.project.tutorial.security.filter.AuthenticationFilter;
import org.penguin.project.tutorial.security.filter.JwtAuthenticationFilter;
import org.penguin.project.tutorial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userDetailsService;

    @Autowired
    PasswordEncoder bcryptPasswordEncoder;

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bcryptPasswordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .anonymous().principal("guest").authorities("ROLE_GUEST")
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/api/auth/**",
                        "/album/**",
                        "/graphiql/**",
                        "/graphql/**",
                        "/api/auth/**")
                .permitAll()
                .antMatchers("/api/business/**","/user/**")
                .authenticated();
         http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // 实现
//        http.addFilterBefore(restAuthenticationFilter(), AnonymousAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationFilter restAuthenticationFilter() throws Exception {
        return new AuthenticationFilter(authenticationManager(), userDetailsService);
    }
}
