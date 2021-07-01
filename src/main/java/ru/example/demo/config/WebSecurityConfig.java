package ru.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import ru.example.demo.domain.enumeration.Permission;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    
    @Autowired
    public WebSecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                    .antMatchers("/admin").hasAnyAuthority(Permission.MUNICIPALITY_ROLE.getPermission(), Permission.SUBJECT_ROLE.getPermission())
                .antMatchers("/admin/get-map-data").hasAnyAuthority(Permission.MUNICIPALITY_ROLE.getPermission(), Permission.SUBJECT_ROLE.getPermission())
                    .antMatchers("/admin/municipalities/**").hasAuthority(Permission.MUNICIPALITY_ROLE.getPermission())
                    .antMatchers("/admin/federal-subjects/**").hasAuthority(Permission.SUBJECT_ROLE.getPermission())
                    .antMatchers("/admin/**").hasAuthority(Permission.ADMIN.getPermission())
                    .antMatchers("/login").permitAll()
                    .antMatchers("/auth").permitAll()
                    .antMatchers("/webjars/**").permitAll()
                    .antMatchers("/resources/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin().loginPage("/login").permitAll()
                    .defaultSuccessUrl("/")
                    .failureUrl("/login?error=true")
                .and()
                    .exceptionHandling().accessDeniedPage("/403")
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/");
        
//        http.csrf().disable();
//        http.headers().frameOptions().disable();
    }

    
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) 
            throws Exception { 
        auth.userDetailsService(userDetailsService); 
    }
    
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
    
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    
}