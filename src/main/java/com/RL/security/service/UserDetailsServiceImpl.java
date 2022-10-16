package com.RL.security.service;

import com.RL.domain.User;
import com.RL.exception.message.ErrorMessage;
import com.RL.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {//kendi user larimi bu service ile disari aciyorum

	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user=userRepository.findByEmail(email).orElseThrow(()->new
				UsernameNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, email)));
		
		return UserDetailsImpl.build(user);//kendi user imi  UserDetailsImpl classindaki build methodu ile security e verdim
	}

}