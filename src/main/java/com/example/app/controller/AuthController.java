package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.User;
import com.example.app.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthController {

	private final UserService service;
	private final HttpSession session;

	@GetMapping("/login")
	public String getLogin(Model m) {
		m.addAttribute("user", new User());
		return "login";
	}

	@PostMapping("/login")
	public String postLogin(
			@Valid @ModelAttribute User inputUser,
			Errors errors,
			Model m) {

		//バリデーション
		if (errors.hasErrors()) {
			m.addAttribute("user", inputUser);
			return "login";
		}

		//不正なログイン
		if (!service.loginByIdAndPass(inputUser.getLoginId(), inputUser.getPass())) {
			m.addAttribute("errorMsg", "不正なログインです");
			m.addAttribute("user", inputUser);
			return "login";
		}

		//セッションに格納
		User user = service.selectByIdAndPass(inputUser.getLoginId());
		session.setAttribute("loginId", user.getLoginId());
		session.setAttribute("loginName", user.getName());

		//userTypeによって画面遷移を変更
		//userType == 2(先生)
		//userType == 3(生徒)
		if (user.getUserType() == 2) {
			return "redirect:/teacher/mypage";
		}else if(user.getUserType() == 3) {
			return "redirect:/student";			
		}
		//管理者画面
		return "redirect:/admin";
	}

	@GetMapping("/logout")
	public String getLogout(RedirectAttributes ra) {
		session.invalidate();
		ra.addFlashAttribute("errorMsg", "ログアウトしました");
		return "redirect:/login";
	}

	@GetMapping("")
	public String getRedirectLogin(Model m) {
		m.addAttribute("user", new User());
		return "redirect:/login";
	}

}
