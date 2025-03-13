package com.example.app.service;

import java.util.List;

import com.example.app.dto.Meeting;

public interface MeetingSettingService {

	//面談日をリストに格納
	List<String> getMeetingDate(Meeting meet);

	//面談日の日数
	Integer countMeetingDate(Meeting meet);

	//開始時間・終了時間
	boolean validateStartAndEndTime(Meeting meet);
	
	//面談時間枠をリストに格納
	List<String> getMeetingSchedule(Meeting meet);
}
