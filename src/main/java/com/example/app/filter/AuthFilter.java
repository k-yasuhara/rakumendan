package com.example.app.filter;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class AuthFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// ログイン画面や静的リソースには適用しない
		String uri = request.getRequestURI();
		if (uri.equals("/") || uri.equals("/login") || uri.equals("/logout") || uri.startsWith("/css/")
				|| uri.startsWith("/js/") || uri.startsWith("/images/")) {
			filterChain.doFilter(request, response);
			return;
		}

		if (request.getSession().getAttribute("loginId") == null) {
			response.sendRedirect("/login");
			return;
		}
		filterChain.doFilter(request, response);

	}

}
