package br.com.gx.socialNetwork.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/")
public class IndexController {

	@GetMapping
	public ResponseEntity<String> index() {
		return ResponseEntity.ok("oi");
	}
	
}
