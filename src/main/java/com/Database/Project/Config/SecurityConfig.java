package com.Database.Project.Config;


import com.Database.Project.ExceptionHandler.JwtAuthEntryPoint;
import com.Database.Project.ExceptionHandler.CustomAccessDeniedHandler;
import com.Database.Project.Filter.UserPasswordAuthFilter;
import com.Database.Project.Provider.UserPassAuthProvider;
import com.Database.Project.Service.UserDetailsSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  extends  WebSecurityConfigurerAdapter {

    @Autowired
    UserPassAuthProvider userPassAuthProvider;
    @Autowired
    JwtAuthEntryPoint jwtAuthEntryPoint;
    @Autowired
    CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    public SecurityConfig() {
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(userPassAuthProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(userPasswordAuthFilterBean(), BasicAuthenticationFilter.class);
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint)
                .and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }


    //    @Bean
//    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests(authorizeRequests -> authorizeRequests
//                        .requestMatchers(
//                                new AntPathRequestMatcher("/roles/**")).permitAll()
//                        .requestMatchers(
//                                new AntPathRequestMatcher("/permissions")).permitAll()
//                        .requestMatchers(
//                                new AntPathRequestMatcher("/users")).permitAll()
//                        .anyRequest().authenticated())
//                .userDetailsService(userDetailsSer)
//                .csrf(csrf -> csrf.disable())
//                .httpBasic(withDefaults());
//
//        return http.build();
//    }

    @Bean
    public UserPasswordAuthFilter userPasswordAuthFilterBean(){
        return new UserPasswordAuthFilter();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}