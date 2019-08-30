package com.windea.utility.springboot.security.jwt

import com.windea.utility.springboot.security.jwt.components.*
import io.jsonwebtoken.*
import org.springframework.boot.autoconfigure.*
import org.springframework.boot.autoconfigure.condition.*
import org.springframework.boot.autoconfigure.security.servlet.*
import org.springframework.boot.context.properties.*
import org.springframework.context.*
import org.springframework.context.annotation.*
import org.springframework.security.provisioning.*

@Configuration
@ConditionalOnClass(SecurityAutoConfiguration::class, Jwt::class)
@AutoConfigureAfter(SecurityAutoConfiguration::class)
@EnableConfigurationProperties(JwtProperties::class)
class JwtAutoConfiguration(
	private val applicationContext: ApplicationContext,
	private val jwtProperties: JwtProperties
) {
	//TODO 未测试当存在这个依赖时，返回的是那个依赖。
	@Bean
	@ConditionalOnMissingBean
	fun userDetailsService() = InMemoryUserDetailsManager()
	
	@Bean
	fun jwtEntryPoint() = JwtEntryPoint()
	
	@Bean
	fun jwtFilter() = JwtFilter(jwtProvider())
	
	@Bean
	fun jwtProvider() = JwtProvider(userDetailsService(), jwtProperties)
}
