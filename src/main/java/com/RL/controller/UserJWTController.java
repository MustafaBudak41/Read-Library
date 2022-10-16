package com.RL.controller;

import javax.validation.Valid;

import com.RL.domain.User;
import com.RL.dto.request.RegisterRequest;
import com.RL.dto.request.SignInRequest;
import com.RL.dto.response.SignInResponse;
import com.RL.security.jwt.JwtUtils;
import com.RL.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping
@AllArgsConstructor
public class UserJWTController {

    private IUserService userService;
	
	private AuthenticationManager authManager;
	
	private JwtUtils jwtUtils;

	@PostMapping("/register")
	public ResponseEntity<Map<String,String>>  register(@Valid @RequestBody RegisterRequest registerRequest){
		User newUser=userService.register(registerRequest);

		Map<String,String> map=new HashMap<>();
		map.put("id : ", newUser.getId().toString());
		map.put("name : ",newUser.getFirstName());

		return new ResponseEntity<>(map,HttpStatus.CREATED);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<SignInResponse> authenticate(@Valid  @RequestBody SignInRequest signInRequest){

		//bunu websec de AuthenticationManager da  bean olarak olusturugumuz icin burda direk kullanirizi
		Authentication authentication= authManager.authenticate(new
				UsernamePasswordAuthenticationToken(signInRequest.getEmail(),signInRequest.getPassword()));

		String token= jwtUtils.generateJwtToken(authentication);

		SignInResponse response=new SignInResponse();
		response.setToken(token);

		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
	
}
	

