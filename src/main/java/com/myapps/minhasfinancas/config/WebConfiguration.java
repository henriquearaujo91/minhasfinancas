package com.myapps.minhasfinancas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")// LIBERA TODAS AS URLS DA APLICACAO
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");// LIBERA PARA OS SEGUINTES METODOS
	}
}
