package com.esc.skillmen.config;

import com.esc.skillmen.domain.Role;
import com.esc.skillmen.domain.User;
import com.esc.skillmen.domain.UserRoles;
import com.esc.skillmen.repo.RoleRepository;
import com.esc.skillmen.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RolesUtil rolesUtil;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(new AuthenticationProvider() {

                    @Override
                    public boolean supports(Class<?> authentication) {
                        return authentication.equals(
                                UsernamePasswordAuthenticationToken.class);
                    }

                    @Override
                    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                        String name = authentication.getName();
                        String password = authentication.getCredentials().toString();
                        Optional<User> user = userRepository.findByNumber(name);
                        if (!user.isPresent()) return null;
                        List<Role> roles = rolesUtil.getUserRoles(user.get());
                        return user.isPresent() && user.get().getPassword().equals(password) ? new UsernamePasswordAuthenticationToken(
                                name, password,
                                AuthorityUtils.commaSeparatedStringToAuthorityList(roles.stream()
                                                .map(r -> r.getName())
                                                .collect(Collectors.joining(",")))
                        ) : null;
                    }
                });  // option 1
        // .userDetailsService(userDetailsService()); // option 2

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/documentation/**","documentation/**","/v2/api-docs","/swagger-resources/**",
                "/documentation","/swagger-resources","swagger-resources/","swagger-resources/**")
        .and().debug(true);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.getHttp()
                .authorizeRequests()
                .antMatchers( "/",
                        "/favicon.ico",
                        "/assets/**",
                        "/*.woff",
                        "/*.woff2",
                        "/*.ttf",
                        "/*.svg",
                        "/*.eot",
                        "/*.css",
                        "/*.js",
                        "/*.map",
                        "/login").permitAll()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .csrf().disable().httpBasic()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}

