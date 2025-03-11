package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminMypageController {
	
	@GetMapping("/mypage")
	public String getAdminMypage() {
		return "admin/mypage";
	}
	
	
}
