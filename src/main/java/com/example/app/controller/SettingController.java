package com.example.app.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.app.domain.Meeting;
import com.example.app.service.MeetingSettingService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SettingController {
	
	private final MeetingSettingService service;

	@GetMapping
	public String getSetting(Model m) {
		Meeting meet = new Meeting();
		//デフォルト値設定
		meet.setStartTime("09:00");
		meet.setEndTime("17:00");
		m.addAttribute("meet", meet);
		return "setting";
	}

	@PostMapping
	public String postMethodName(
			@ModelAttribute Meeting meet,
			Model m) {

//		System.out.println(meet);
//		System.out.println(meet.getParentMeetingDate());
		m.addAttribute("meet", meet);
		
		List<String> meetDate = service.getMeetingDate(meet);
		Integer countMeet = service.countMeetingDate(meet);
		
		//面談日の入力なしmsg出力
		if(countMeet == null) {
			m.addAttribute("msg", "面談日を設定してください");
			return "setting";
		}
		
		m.addAttribute("meetDate", meetDate);
		System.out.println(service.getMeetingDate(meet));
		
		
		
		return "setting";
	}

}
