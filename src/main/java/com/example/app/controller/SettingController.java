package com.example.app.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.app.domain.InterviewSchedule;
import com.example.app.dto.Meeting;
import com.example.app.service.InterviewSchedulesService;
import com.example.app.service.MeetingSettingService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class SettingController {

	private final MeetingSettingService meetingService;
	private final HttpSession session;
	private final InterviewSchedulesService interviewService;

	@GetMapping("/setting")
	public String getSetting(Model m) {

		String loginId = (String) session.getAttribute("loginId");
		//teacherIdと合致するデータを取得して戻り値がnullならfalse,nullじゃなければtrue
		boolean isTeacherExist = interviewService.existsByLoginIdAndTeacherId(loginId); 
		
		
		if(isTeacherExist) {
			//teacherIdと紐づくdataがinterview_schedulesテーブルにあった
			System.out.println("interviewDBにdataあり");
			List<Integer> meetingIdList = interviewService.getDistinctMeetingId(loginId);
			System.out.println(meetingIdList);
		}
		
		
		Meeting meet = new Meeting();
		//デフォルト値設定
		meet.setStartTime("09:00");
		meet.setEndTime("17:00");
		m.addAttribute("meet", meet);
		return "teacher/setting2";
	}

	@PostMapping("/setting")
	public String postSetting(
			@ModelAttribute Meeting meet,
			@RequestParam(value = "unavailableDates", required = false) String unavailableDates,
			Model m) {

		//面談スケジュールをDBに格納
		List<String> meetDate = meetingService.getMeetingDate(meet); //面談日をリストに格納
		List<String> schedule = meetingService.getMeetingSchedule(meet);//面談時間枠をリストに格納

		for (String date : meetDate) {
			InterviewSchedule interview = new InterviewSchedule();
			for (String meetTime : schedule) {
				interview.setTeacherId((String) session.getAttribute("loginId"));
				interview.setDate(LocalDate.parse(date));
				interview.setStartTime(LocalTime.parse(meetTime.split("～")[0]));
				interview.setEndTime(LocalTime.parse(meetTime.split("～")[1]));
				interview.setDurationMinutes(Integer.parseInt(meet.getTimePerMeeting()));
				interviewService.add(interview);
			}
		}

		if (unavailableDates != null && !unavailableDates.isEmpty()) {
			String[] dates = unavailableDates.split(",");
			for (String date : dates) {

				// ここで、unavailableDatesを利用した処理を行います
				// 例えば、データベースに保存する、あるいはリストに追加するなど
				System.out.println("面談不可日: " + date);

				//リダイレクトする
			}
		}

		return "teacher/mypage";
	}

}
