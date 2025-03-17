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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.InterviewSchedule;
import com.example.app.domain.MeetingsDomain;
import com.example.app.dto.Meeting;
import com.example.app.service.InterviewSchedulesService;
import com.example.app.service.MeetingSettingService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teacher")
public class SettingController {

	private final MeetingSettingService meetingSettingService;
	private final HttpSession session;
	private final InterviewSchedulesService interviewService;

	@GetMapping("/setting")
	public String getSetting(Model m, RedirectAttributes ra) {
		//初期値入力
		Meeting meet = new Meeting();
		m.addAttribute("meet", meet);
		meet.setStartTime("09:00");
		meet.setEndTime("17:00");

		String loginId = (String) session.getAttribute("loginId");
		//teacherIdと合致するデータを取得して戻り値がnullならfalse,nullじゃなければtrue
		boolean isTeacherExist = interviewService.existsByLoginIdAndTeacherId(loginId);

		if (isTeacherExist) {
			//teacherIdと紐づくdataがinterview_schedulesテーブルにあった
			System.out.println("interviewDBにdataあり");
			List<Integer> meetingIdList = interviewService.getDistinctMeetingId(loginId);
//			System.out.println(meetingIdList);
			MeetingsDomain meetindStatusById = interviewService.getMeetingStatus(meetingIdList);
//			System.out.println(meetindStatusById);

			if (meetindStatusById.getStatus().equals("active")) {
				ra.addFlashAttribute("meetingStatus", "active");
				ra.addFlashAttribute("checkMsg", "登録済みの面談日程があります。どうしますか");
				ra.addFlashAttribute("meet", meetindStatusById);
				//mpdalのbutton「削除する」→/teacher/setting/closed→getmapping(/setting/closed)からDBをupdateする
				//modalのbutton「残す」→/mypageに戻る
				return "redirect:/teacher/check";

			} else if (meetindStatusById.getStatus().equals("accepting")) {
				System.out.println("予約募集中の面談日程があります。どうしますか");
				m.addAttribute("meetingStatus", "accepting");
				m.addAttribute("checkMsg", "予約募集中の面談日程があります。どうしますか");
				//mpdalのbutton「予約の募集を止める」→/teacher/setting/closed→getmapping(/setting/closed)からDBをupdateする
				//modalのbutton「止めない」→/mypageに戻る
				return "teacher/setting";
			} else {
				//statusがclosdである　mapperですでにclosedを除外済み
			}
		} else {
			//teacherIdと紐づくdataがinterview_schedulesテーブルがなかった
		}
		return "teacher/setting";
	}

	@PostMapping("/setting")
	public String postSetting(
			@ModelAttribute Meeting meet,
			@RequestParam(value = "unavailableDates", required = false) String unavailableDates,
			Model m) {

		//面談スケジュールをDBに格納
		List<String> meetDate = meetingSettingService.getMeetingDate(meet); //面談日をリストに格納
		List<String> schedule = meetingSettingService.getMeetingSchedule(meet);//面談時間枠をリストに格納

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

	@GetMapping("/check")
	public String getCheck(Model m,
			@ModelAttribute("checkMsg") String checkMsg,
			@ModelAttribute("meetingStatus") String meetingStatus,
			@ModelAttribute("meet") MeetingsDomain meet) {
		m.addAttribute("checkMsg", checkMsg);
		m.addAttribute("status", meetingStatus);
//		System.out.println(interviewService.findByMeetingId(meet.getMeetingId()));
		return "teacher/check";
	}

	@GetMapping("/setting/closed")
	public String getMeetingDatabaseClosed() {
		return "redirect:/teacher/mypage";
	}

}
