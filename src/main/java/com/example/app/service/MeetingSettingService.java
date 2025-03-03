package com.example.app.service;

import java.util.List;

import com.example.app.domain.Meeting;

public interface MeetingSettingService {
	
	//面談日を配列に格納
	List<String> getMeetingDate(Meeting meet);
	
	//面談日の日数
	Integer countMeetingDate(Meeting meet);
}
