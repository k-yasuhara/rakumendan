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

		m.addAttribute("meet", meet);

		List<String> meetDate = service.getMeetingDate(meet); //面談日をリストに格納
		Integer countMeet = service.countMeetingDate(meet);//面談日数をカウント
		boolean isValidStartAndEndTime = service.validateStartAndEndTime(meet);//開始・終了時間の精査
		List<String> schedule = service.getMeetingSchedule(meet);
		
		//バリデーション
		if (countMeet == null) {
			m.addAttribute("errorMsg", "面談日を設定してください");
			return "setting";
		} else if (!isValidStartAndEndTime) {
			m.addAttribute("errorMsg", "開始時間・終了時間の入力が不正です");
			return "setting";
		} else if (meet.getTimePerMeeting().isEmpty() || meet.getTimePerMeeting().equals("0")) {
			m.addAttribute("errorMsg", "面談時間の入力が不正です");
			return "setting";
		}

		m.addAttribute("meetDate", meetDate);
		m.addAttribute("schedule", schedule);
//		System.out.println(schedule);
		
		return "setting";
	}

}
