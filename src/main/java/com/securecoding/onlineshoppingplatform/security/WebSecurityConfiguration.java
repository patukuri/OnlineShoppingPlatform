/*
 * package com.securecoding.onlineshoppingplatform.security;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.http.HttpMethod; import
 * org.springframework.security.authentication.dao.DaoAuthenticationProvider;
 * import
 * org.springframework.security.config.annotation.authentication.builders.
 * AuthenticationManagerBuilder; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * WebSecurityConfigurerAdapter; import
 * org.springframework.security.core.userdetails.UserDetailsService; import
 * org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 * 
 * import com.securecoding.onelineshoppingplatform.service.UserService;
 * 
 * @Configuration public class WebSecurityConfiguration extends
 * WebSecurityConfigurerAdapter {
 * 
 * @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
 * 
 * @Autowired private UserService userService;
 * 
 * @Override public void configure(HttpSecurity http) throws Exception {
 * http.csrf().disable().authorizeRequests() // .antMatchers("/").permitAll() //
 * .antMatchers(HttpMethod.POST, "/category/create/**").access("USER")
 * .antMatchers(HttpMethod.POST,
 * "/category/create*").permitAll().antMatchers(HttpMethod.GET, "/swagger*")
 * .permitAll().antMatchers(HttpMethod.POST,
 * "/product/*").permitAll().anyRequest().authenticated(); }
 * 
 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
 * Exception { super.authenticationManagerBean();
 * auth.authenticationProvider(daoAuthenticationProvider()); }
 * 
 * @Bean public DaoAuthenticationProvider daoAuthenticationProvider() {
 * DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
 * provider.setPasswordEncoder(bCryptPasswordEncoder);
 * provider.setUserDetailsService((UserDetailsService) userService); return
 * provider; } }
 */