package com.albraik.task.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	private Environment env;

	@Autowired
	public JwtRequestFilter(final Environment env) {
		super();
		this.env = env;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String authorizationHeader = request.getHeader(env.getProperty("auth.header"));

		if (authorizationHeader == null
				|| !authorizationHeader.startsWith(env.getProperty("token.key"))) {
			chain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String authorizationHeader = request.getHeader(env.getProperty("auth.header"));
		if (authorizationHeader == null)
			return null;
		String token = authorizationHeader.replaceAll(env.getProperty("token.key"), "");
		System.out.println(env.getProperty("secret.key"));
		System.out.println(token);
		String userID = Jwts.parser().setSigningKey(env.getProperty("secret.key")).parseClaimsJws(token).getBody()
				.getSubject();
		if (userID == null) {
			return null;
		}
		return new UsernamePasswordAuthenticationToken(userID, null, new ArrayList<>());
	}
}
