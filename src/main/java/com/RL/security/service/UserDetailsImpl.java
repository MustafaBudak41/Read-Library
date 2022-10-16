package com.RL.security.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.RL.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails  {//security UserDetails istiryor

	private Long id;
	
	private String email;
	
	@JsonIgnore//bu class i disari cikarmak istersen
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;//GrantedAuthority i extends eden herseyi buraya veriyoruz
	
	
	public static UserDetailsImpl build(User user) {//security benden SimpleGrantedAuthority tipinden istiyor
		List<GrantedAuthority> authorities=user.getRoles().stream().map(role->new SimpleGrantedAuthority(role.getName().name()))
		   .collect(Collectors.toList());//role bilgileriniz GrantedAuthority tipine cevirdik

		return new UserDetailsImpl(user.getId(),user.getEmail(),user.getPassword(),authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}