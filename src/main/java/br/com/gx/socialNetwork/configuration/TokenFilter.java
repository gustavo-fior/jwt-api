package br.com.gx.socialNetwork.configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.gx.socialNetwork.model.User;
import br.com.gx.socialNetwork.repository.UserRepository;
import br.com.gx.socialNetwork.service.TokenService;

public class TokenFilter extends OncePerRequestFilter{

	private TokenService tokenService;
	private UserRepository userRepository;

	public TokenFilter(TokenService tokenService, UserRepository userRepository) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = getToken(request);
		boolean valid = tokenService.isTokenValid(token);
		if (valid) {
			authenticateUser(token, request);
		}
		
		filterChain.doFilter(request, response);
	}

	private void authenticateUser(String token, HttpServletRequest req) {
		
		Long userId= tokenService.getUserId(token);
		User user = userRepository.findById(userId).get();
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		authentication.setDetails(req);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	public String getToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7, token.length());
	}

}
