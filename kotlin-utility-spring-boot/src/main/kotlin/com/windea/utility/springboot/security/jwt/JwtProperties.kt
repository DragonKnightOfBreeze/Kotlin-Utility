package com.windea.utility.springboot.security.jwt

import org.springframework.boot.context.properties.*

/**Jwt的属性类。*/
@ConfigurationProperties("com.windea.security.jwt")
class JwtProperties {
	/**jwt口令的请求头。*/
	var tokenHeader: String = "Authorization"
	
	/**jwt口令的开头。*/
	var tokenHead: String = "Bearer "
	
	/**jwt密钥。*/
	lateinit var secret: String
	
	/**jwt过期时间。*/
	var expiration: Int = 86400
}
