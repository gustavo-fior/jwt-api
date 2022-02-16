package br.com.gx.socialNetwork.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gx.socialNetwork.request.AuthenticationRequest;
import br.com.gx.socialNetwork.response.TokenResponse;
import br.com.gx.socialNetwork.service.TokenService;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<TokenResponse> login(@RequestBody @Valid AuthenticationRequest data) {

		System.out.println("oi");
		
		UsernamePasswordAuthenticationToken authenticationData = data.convert();

		try {
			Authentication authentication = authManager.authenticate(authenticationData);
			String token = tokenService.generateToken(authentication);
			return ResponseEntity.ok(new TokenResponse("Bearer ", token));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}

	}

}
