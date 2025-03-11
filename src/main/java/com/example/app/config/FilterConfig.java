package com.example.app.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.app.filter.AuthFilter;

@Configuration
public class FilterConfig {
	
	@Bean
	public FilterRegistrationBean<AuthFilter> authFilter(){
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new AuthFilter());
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}
}
