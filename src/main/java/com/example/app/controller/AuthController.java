package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Admin;
import com.example.app.service.AdminService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class AuthController {

	private final AdminService service;
	private final HttpSession session;

	@GetMapping("/login")
	public String getLogin(Model m) {
		m.addAttribute("admin", new Admin());
		return "login";
	}

	@PostMapping("/login")
	public String postLogin(
			@Valid @ModelAttribute Admin inputAdmin,
			Errors errors,
			Model m) {

		if (errors.hasErrors()) {
			m.addAttribute("admin", inputAdmin);
			return "login";
		}

		if (!service.loginByIdAndPass(inputAdmin.getLoginId(), inputAdmin.getPass())) {
			m.addAttribute("errorMsg", "不正なログインです");
			m.addAttribute("admin", inputAdmin);
			return "login";
		}

		Admin admin = service.selectByIdAndPass(inputAdmin.getLoginId());
		session.setAttribute("loginId", admin.getLoginId());
		session.setAttribute("loginName", admin.getName());
		
		//先生用画面に遷移
		if (admin.getStatus().equals("admin")) {
			return "redirect:/admin/mypage";
		}
		//保護者用画面に遷移
		return "redirect:/student";
	}

	@GetMapping("/logout")
	public String getLogout(RedirectAttributes ra) {
		session.invalidate();
		ra.addFlashAttribute("errorMsg", "ログアウトしました");
		return "redirect:/login";
	}
	
	@GetMapping("")
	public String getRedirectLogin(Model m) {
		m.addAttribute("admin", new Admin());
		return "redirect:/login";
	}
	

}
