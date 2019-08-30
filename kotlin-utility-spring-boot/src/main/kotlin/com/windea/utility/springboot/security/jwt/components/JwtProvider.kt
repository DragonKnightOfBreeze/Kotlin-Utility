package com.windea.utility.springboot.security.jwt.components

import com.windea.utility.springboot.security.jwt.*
import io.jsonwebtoken.*
import mu.*
import org.springframework.security.core.*
import org.springframework.security.core.userdetails.*
import java.util.*
import javax.servlet.http.*

/**Jwt提供器。*/
open class JwtProvider(
	private val userDetailsService: UserDetailsService,
	private val jwtProperties: JwtProperties
) {
	/**从http请求中得到令牌。*/
	fun getToken(request: HttpServletRequest): String? {
		val header = request.getHeader(jwtProperties.tokenHeader)
		return if(header?.startsWith(jwtProperties.tokenHeader, true) == true) {
			header.substring(jwtProperties.tokenHead.length + 1).takeIf { it.isNotBlank() }
		} else null
	}
	
	/**从身份验证对象生成令牌。*/
	fun generateToken(authentication: Authentication): String {
		return Jwts.builder()
			.setSubject((authentication.principal as UserDetails).username)
			.setIssuedAt(Date())
			.setExpiration(generateExpiration())
			.signWith(SignatureAlgorithm.HS512, jwtProperties.secret)
			.compact()
	}
	
	/**刷新令牌。*/
	fun refreshToken(token: String): String {
		return Jwts.builder()
			.setClaims(getClaims(token))
			.setIssuedAt(Date())
			.setExpiration(generateExpiration())
			.signWith(SignatureAlgorithm.HS512, jwtProperties.secret)
			.compact()
	}
	
	/**验证令牌。*/
	fun validateToken(token: String): UserDetails? {
		val claims = getClaims(token)
		
		//判断令牌是否过期
		val expiration = claims.expiration ?: return null
		if(expiration < Date()) return null
		
		//判断用户是否合法
		val username = claims.subject ?: return null
		return userDetailsService.loadUserByUsername(username)
	}
	
	private fun generateExpiration(): Date {
		return Date(System.currentTimeMillis() + jwtProperties.expiration * 1000)
	}
	
	private fun getClaims(token: String): Claims {
		return try {
			Jwts.parser().setSigningKey(jwtProperties.secret).parseClaimsJws(token).body
		} catch(e: Exception) {
			logger.error("Invalid Jwt format.")
			Jwts.claims()
		}
	}
	
	
	companion object : KLogging()
}
