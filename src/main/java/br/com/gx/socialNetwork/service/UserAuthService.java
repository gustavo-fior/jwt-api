package br.com.gx.socialNetwork.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.gx.socialNetwork.model.User;
import br.com.gx.socialNetwork.repository.UserRepository;

// Classe para a gestao da logica de login 
@Service
public class UserAuthService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user = userRepository.findByEmail(username);
		
		if(user.isEmpty()) {
			throw new UsernameNotFoundException("Email ainda nao cadastrado " + username);
		}
		
		return user.get();
		
	}

}
