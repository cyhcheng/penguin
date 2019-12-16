package org.penguin.project.tutorial.config;

import lombok.AllArgsConstructor;
import org.penguin.project.tutorial.security.AuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password(encoder().encode("administrator")).roles("ADMIN")
                .and()
                .withUser("user").password(encoder().encode("12345678")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().authorizeRequests()
//                .antMatchers("/graphql").permitAll()
//                .antMatchers(HttpMethod.GET, "/user/list").hasRole("ADMIN")
//                .antMatchers(HttpMethod.POST, "/user/create").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PUT, "/user/**").hasRole("USER")
//                .antMatchers(HttpMethod.PATCH, "/user/**").hasRole("USER")
//                .antMatchers(HttpMethod.DELETE, "/user/**").hasRole("ADMIN")
//                .and().httpBasic()
//                .authenticationEntryPoint(authenticationEntryPoint);

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/user/list").hasRole("ADMIN")  // .anyRequest().authenticated() // any valid user can access == .antMatchers(HttpMethod.GET, "/user/list").hasRole("ADMIN") // only special role can access
                .and().httpBasic();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler getFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }
}
