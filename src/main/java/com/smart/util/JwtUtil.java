package com.smart.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60*1000; // this is 5hr * 60min * 60 seconds
    private String secret = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";
    
    public String generateToken(UserDetails userDetails)
    {
    	return generateToken(new HashMap<>(), userDetails);
    }
	private String generateToken(Map<String, Object> claims ,UserDetails userDetails)
	   // private String createToken( Map<String, Object> claims ,String userName)
	    {
	    	System.out.println("create token");
	     
	    	return Jwts
	    			.builder()
	    			.setClaims(claims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
	    			.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
	    			.signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
	    }
    private Key getSignKey()
    {
    	System.out.println("get signinkey token");
    	byte[] keybytes=Decoders.BASE64.decode(secret);
    	return  Keys.hmacShaKeyFor(keybytes);
    }
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
    
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

	private Claims getAllClaimsFromToken(String token) {
		// TODO Auto-generated method stub
		return Jwts.parserBuilder().setSigningKey((getSignKey())).build().parseClaimsJws(token).getBody();
	}
	
	 private Boolean isTokenExpired(String token) {
	        final Date expiration = getExpirationDateFromToken(token);
	        System.out.println(expiration.before(new Date())==true?"true":"false");
	        return expiration.before(new Date());
	    }
	 
	  public Date getExpirationDateFromToken(String token) {
	        return getClaimFromToken(token, Claims::getExpiration);
	    }

	  public Boolean validateToken(String token, UserDetails userDetails) {
		    System.out.println(token);
		    System.out.println(userDetails.getUsername());
	        final String username = getUsernameFromToken(token);
	        System.out.println(username);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }
}
