package com.urzaizcoding.iusteimanserver.configuration.security;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.urzaizcoding.iusteimanserver.configuration.AppConfigurer;
import com.urzaizcoding.iusteimanserver.exception.InvalidTokenException;
import com.urzaizcoding.iusteimanserver.exception.TokenException;

public class JWTAuthenticationProvider {

	private static JWTAuthenticationProvider instance;

	public static JWTAuthenticationProvider getProvider(AppConfigurer configurer) {
		if (instance == null) {
			synchronized (JWTAuthenticationProvider.class) {
				instance = new JWTAuthenticationProvider(configurer);
			}
		}

		return instance;
	}

	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String PREFIX = "Bearer ";
	private static final String ROLE_CLAIM = "permissions";
	private static final Long ACCESS_TOKEN_VALIDITY = 15L * 60 * 1000; // 5 minits
	private static final Long REFRESH_TOKEN_VALIDITY = 30 * 24 * 3600 * 1000L; // 30 days

	private Algorithm algorithm;
	private String issuer;

	public JWTAuthenticationProvider(AppConfigurer configurer) {
		super();
		algorithm = Algorithm.HMAC512(configurer.getSecretKey().getBytes());
		issuer = configurer.getJwtIssuer();

	}

	public String createAccessToken(UserDetails user) {

		String accessToken = JWT.create().withSubject(user.getUsername()).withIssuer(issuer).withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
				.withClaim(ROLE_CLAIM,
						user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);

		return accessToken;
	}

	public Authentication authenticateAccessToken(String token) throws Exception {

		DecodedJWT decoded = decodeToken(token);

		String username = decoded.getSubject();

		String[] roles = decoded.getClaim(ROLE_CLAIM).asArray(String.class);

		List<GrantedAuthority> autorities = Arrays.stream(roles).map(r -> new SimpleGrantedAuthority(r))
				.collect(Collectors.toList());

		return new UsernamePasswordAuthenticationToken(username, null, autorities);

	}

	private DecodedJWT decodeToken(String token) throws TokenException {

		try {
			JWTVerifier verifier = JWT.require(algorithm).build();

			DecodedJWT decoded = verifier.verify(token);
			return decoded;
		} catch (Exception e) {
			throw new InvalidTokenException();
		}
	}

	public String createRefreshToken(String subject) throws TokenException {

		try {
			return JWT.create().withSubject(subject).withIssuer(issuer).withIssuedAt(new Date())
					.withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY)).sign(algorithm);
		} catch (Exception e) {
			throw new TokenException();
		}
	}

	public String getUsernameFromAccessToken(String refreshToken) throws TokenException {
		if (refreshToken != null) {
			DecodedJWT decodedToken;
			decodedToken = decodeToken(refreshToken);
			return decodedToken.getSubject();
		}

		return null;
	}

	public String getUsernameFromAccessToken(HttpServletRequest request) throws TokenException {
		return getUsernameFromAccessToken(parseTokenFromRequest(request));
	}

	public static String parseTokenFromRequest(HttpServletRequest request) {
		String jwtToken = request.getHeader(AUTHORIZATION_HEADER);

		if (jwtToken != null) {
			if (jwtToken.startsWith(PREFIX)) {
				return jwtToken.substring(PREFIX.length());
			}
		}

		return null;
	}
}
