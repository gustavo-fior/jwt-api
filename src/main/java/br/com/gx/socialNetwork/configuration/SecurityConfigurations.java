package br.com.gx.socialNetwork.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.gx.socialNetwork.repository.UserRepository;
import br.com.gx.socialNetwork.service.TokenService;
import br.com.gx.socialNetwork.service.UserAuthService;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserAuthService userAuthService;
	
	@Autowired
	private RestAuthenticationEntryPoint entryPoint;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	// Configurando o acesso de requisicoes
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		.antMatchers(HttpMethod.POST, "/auth").permitAll()
		.antMatchers(HttpMethod.GET, "/").permitAll()
		.anyRequest().permitAll()
		.and().csrf()
		.disable().cors()
		.disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().exceptionHandling().authenticationEntryPoint(entryPoint)
		.and().addFilterBefore(new TokenFilter(tokenService, userRepository), UsernamePasswordAuthenticationFilter.class);
		
	}
	
	// Configurando o metodo de autenticacao de usuarios
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// Autenticacao no banco de dados 
		auth.userDetailsService(userAuthService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**", "/configuration/**",
				"/swagger-resources/**");
	}
	
	
}
