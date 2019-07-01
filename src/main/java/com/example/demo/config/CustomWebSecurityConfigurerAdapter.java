package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {


    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER")
//                .and()
//                .withUser("admin").password("$2a$04$9XYtm7RMnsx9WYZoeezlaOhSW63LbnSgmhUQ1MwOmfn1sXP5y9y2e").roles("ADMIN");
        auth.userDetailsService(userDetailsService).passwordEncoder(encodePWD());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//
//       // http.authorizeRequests().antMatchers("/register") .permitAll();
//
//        http.authorizeRequests().antMatchers("/register") .permitAll().
//                and().
//                authorizeRequests().antMatchers("/api/file").authenticated().anyRequest().permitAll()
//                ;

//        http.csrf().disable();
//
//        http.authorizeRequests().antMatchers("/register").authenticated().anyRequest().permitAll().and()
//                .authorizeRequests().antMatchers("/api/**").authenticated().anyRequest().hasAnyRole("USER").and()
//                .formLogin().permitAll();

        http.csrf().disable();
        http.authorizeRequests().antMatchers("/register") .permitAll();
        http//.authorizeRequests().antMatchers("/api/**").authenticated().anyRequest().permitAll().and()
                .authorizeRequests().antMatchers("/all").authenticated().anyRequest().hasAnyRole("USER").and()
                .httpBasic();
        http.authorizeRequests().antMatchers("/api/*").authenticated().and()
                .httpBasic();

    }


    @Bean
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }
}
