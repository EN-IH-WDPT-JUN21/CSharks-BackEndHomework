package com.csharks.moviesbackend.security;

import com.csharks.moviesbackend.security.Service.CustomUserDetailService;
import com.csharks.moviesbackend.security.Service.UserAccessService;
import com.csharks.moviesbackend.security.filter.CustomAuthenticationFilter;
import com.csharks.moviesbackend.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailService customUserDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserAccessService userSecurityService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserAccessService userFilter() {
        return new UserAccessService();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic();
        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .mvcMatchers("/login", "/register", "/movie-app/users/validate/**").permitAll()
                .mvcMatchers(GET, "/movie-app/users/all").hasRole("ADMIN")

                // Get user details
                .mvcMatchers(GET, "/movie-app/users/username/current").hasAnyRole("ADMIN", "USER")

                // Get user details
                .mvcMatchers(GET, "/movie-app/users/{id}")
                .access("@userFilter.checkUsernameFromUserId(authentication,#id)")

                // Set user details
                .mvcMatchers(PUT, "/movie-app/users/{id}/set")
                .access("@userFilter.checkUsernameFromUserId(authentication,#id)")
                // Get playlist from user
                .mvcMatchers(GET, "/movie-app/playlists/user/{id}")
                .access("@userFilter.checkUsernameFromUserId(authentication,#id)")
                // Create playlist in user
                .mvcMatchers(PUT, "/movie-app/users/{id}/createPlaylist")
                .access("@userFilter.checkUsernameFromUserId(authentication,#id)")
                // Delete playlist in user
                .mvcMatchers(DELETE, "/movie-app/playlists/{playlistId}/delete")
                .access("@userFilter.checkUsernameFromPlaylistId(authentication,#playlistId)")
                // Add movie to playlist in user
                .mvcMatchers(PUT, "/movie-app/playlists/{playlistId}/add/**")
                .access("@userFilter.checkUsernameFromPlaylistId(authentication,#playlistId)")
                // Remover movie from playlist in user
                .mvcMatchers(PUT, "/movie-app/playlists/{playlistId}/remove/**")
                .access("@userFilter.checkUsernameFromPlaylistId(authentication,#playlistId)")
                .anyRequest().permitAll();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }


}
