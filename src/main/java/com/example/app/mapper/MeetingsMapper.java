package com.example.app.mapper;

import com.example.app.domain.MeetingsDomain;

public interface MeetingsMapper {
	
	//meetingIdに紐づくデータの取得
	MeetingsDomain findByMeetingId(Integer meetingId);

	//meetingIdに紐づくデータのstatusをclosedにする
	void updateStatusToClosed(Integer MeetingId);
	
	//insertした後、meetingIdを返す
	void insertByTeacherId (String teacherId);
	
	//insertした自動採番を取得
	Integer getLastInsertId();
}
