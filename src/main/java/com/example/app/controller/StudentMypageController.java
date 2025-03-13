package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("student")
public class StudentMypageController {
	
	@GetMapping("{studentNumber}/mypage")
	public String getMypage(@PathVariable("studentNumber") String studentNumber,
			Model m) {
		
		
		return "student/mypage";
	}
	
}
