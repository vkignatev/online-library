//package com.sber.java13spring.java13springproject.libraryproject.config.jwt;
//
//import com.sber.java13spring.java13springproject.libraryproject.config.CorsFilter;
//import com.sber.java13spring.java13springproject.libraryproject.config.RestAuthenticationEntryPoint;
//import com.sber.java13spring.java13springproject.libraryproject.constants.SecurityConstants;
//import com.sber.java13spring.java13springproject.libraryproject.service.userdetails.CustomUserDetailsService;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.firewall.HttpFirewall;
//import org.springframework.security.web.firewall.StrictHttpFirewall;
//import org.springframework.security.web.session.SessionManagementFilter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.Arrays;
//import java.util.Collections;
//
//import static com.sber.java13spring.java13springproject.libraryproject.constants.SecurityConstants.*;
//import static com.sber.java13spring.java13springproject.libraryproject.constants.UserRolesConstants.*;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//public class JWTSecurityConfig {
//    private final CustomUserDetailsService customUserDetailsService;
//    private final JWTTokenFilter jwtTokenFilter;
//
//    public JWTSecurityConfig(CustomUserDetailsService customUserDetailsService,
//                             JWTTokenFilter jwtTokenFilter) {
//        this.customUserDetailsService = customUserDetailsService;
//        this.jwtTokenFilter = jwtTokenFilter;
//    }
//
//    @Autowired
//    @Qualifier("restAuthenticationEntryPoint")
//    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
//
////    @Autowired
////    private CorsFilter corsFilter;
//
////    @Override
////    public void addCorsMappings(CorsRegistry registry) {
////        registry.addMapping("/**")
////              .allowedOrigins("*")
////              .allowedMethods("PUT", "DELETE", "POST", "GET")
////              .allowedHeaders("*")
////              .exposedHeaders("*")
////              .allowCredentials(false).maxAge(3600);
////    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
////              .addFilterBefore(corsFilter, SessionManagementFilter.class)
//              .csrf().disable()
//              //Настройка http запросов - кому куда можно/нельзя
//              .authorizeHttpRequests(auth -> auth
//                                           .requestMatchers(RESOURCES_WHITE_LIST.toArray(String[]::new)).permitAll()
//                                           .requestMatchers(REST.USERS_WHITE_LIST.toArray(String[]::new)).permitAll()
//                                           .requestMatchers(USERS_WHITE_LIST.toArray(String[]::new)).permitAll()
//                                           .requestMatchers(BOOKS_WHITE_LIST.toArray(String[]::new)).permitAll()
//                                           .requestMatchers(AUTHORS_WHITE_LIST.toArray(String[]::new)).permitAll()
//                                           .requestMatchers(REST.BOOKS_WHITE_LIST.toArray(String[]::new)).permitAll()
//                                           .requestMatchers(REST.AUTHORS_WHITE_LIST.toArray(String[]::new)).permitAll()
//                                           .requestMatchers(REST.AUTHORS_PERMISSION_LIST.toArray(String[]::new)).hasAnyRole(ADMIN, LIBRARIAN)
//                                           .requestMatchers(AUTHORS_PERMISSION_LIST.toArray(String[]::new)).hasAnyRole(ADMIN, LIBRARIAN)
//                                           .requestMatchers(BOOKS_PERMISSION_LIST.toArray(String[]::new)).hasAnyRole(ADMIN, LIBRARIAN)
//                                           .requestMatchers(REST.BOOKS_PERMISSION_LIST.toArray(String[]::new)).hasAnyRole(ADMIN, LIBRARIAN)
//                                           .requestMatchers(REST.USERS_PERMISSION_LIST.toArray(String[]::new)).hasRole(USER)
//                                           .anyRequest().authenticated()
//                                    )
//              .exceptionHandling()
////              .authenticationEntryPoint((request, response, authException) -> {
////                  response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
////                                     authException.getMessage());
////              })
//              .authenticationEntryPoint(restAuthenticationEntryPoint)
//              .and()
//              .sessionManagement(
//                    session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//              //JWT Token Filter VALID or NOT
//              .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
//              .userDetailsService(customUserDetailsService)
//              .build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(
//          AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//}
