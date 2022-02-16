package br.com.gx.socialNetwork;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.gx.socialNetwork.model.Authority;
import br.com.gx.socialNetwork.model.User;
import br.com.gx.socialNetwork.repository.AuthorityRepository;
import br.com.gx.socialNetwork.repository.UserRepository;

@SpringBootApplication 
public class SocialNetworkApplication {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(SocialNetworkApplication.class, args);
	}
	
	// Metodo para logo apos inicializar a aplicacao ja criarmos as roles e um user
	@PostConstruct
	private void init() {
		List<Authority> authorities = new ArrayList<Authority>();
		
		authorities.add(createAuthoriry("USER", "Common user"));
		authorities.add(createAuthoriry("ADMIN", "Administrator of the system"));
		
		User user = new User();
		user.setUsername("gpf");
		user.setEmail("gpf@email.com");
		user.setPassword(new BCryptPasswordEncoder().encode("12345"));
		user.setCreationDate(LocalDateTime.now());
		user.setAuthorities(authorities);
		
		userRepository.save(user);
	}
	
	// Metodo para criacao de uma autoridade (role)
	private Authority createAuthoriry(String code, String desc) {
		
		Authority authority = new Authority();
		authority.setRoleCode(code);
		authority.setRoleDesc(desc);
		
		authorityRepository.save(authority); 
		
		return authority;
		
	}

}
