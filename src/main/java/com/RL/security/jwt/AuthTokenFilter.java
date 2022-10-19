package com.RL.security.jwt;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.RL.domain.User;
import com.RL.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

//gelen request dispather servletta n once buaraya gfeliyor
public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsService userDetailsService;//securitide arik userlarim bu tipte

	@Autowired
	private UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String jwt = parseJwt(request);//aldigin jwt yi dogrulamak icin

			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				Long id = jwtUtils.getIdFromJwtToken(jwt);

				Optional<User> user = userRepository.findById(id);

				request.setAttribute("id", user.get().getId());

				UserDetails userDetails = userDetailsService.loadUserByUsername(user.get().getEmail());

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(authentication);
				//bu filtreyi  gectikten sonra
				//yetki gerektiren islemlerde current user in bilgilerini security contextten aliyrorz
			}

		} catch (Exception e) {
			logger.error("User Authentication error");
		}

		filterChain.doFilter(request, response);//son nolta en onemli yer
	}
	//gelen request authorization header isinde beraer token i almak lazim bu methodu yzadik
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7);//neden 7 cunku bearer token kismini almak istiyroum
		}

		return null;
	}

	@Override//filtrelemek istemedigin istekler
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		return antPathMatcher.match("/register", request.getServletPath())
				|| antPathMatcher.match("/signin", request.getServletPath());
	}

}