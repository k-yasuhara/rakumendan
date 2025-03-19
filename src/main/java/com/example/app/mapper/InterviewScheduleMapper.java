package com.example.app.mapper;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.InterviewSchedule;

@Mapper
public interface InterviewScheduleMapper {

	//面談日程をinsert
	void insert(InterviewSchedule i);

	//teacherIdに紐づくデータを取得
	List<InterviewSchedule> findByTeacherId(String teacherId);

	//teacherIdに紐づくmeeting_idのデータを取得
	List<Integer> distinctMeetingId(String teacherId);

	//meetingIdから開始時間と終了時間、１枠の面談時間を取得
	InterviewSchedule getMeetingTimeSettings(Integer meetingId);

	//meetingIdに紐づくデータを取得
	List<InterviewSchedule> findByMeetingId(Integer meetingId);
	
	//meetingIdに紐づく一意のdateを取得
	List<LocalDate> getMeetingDate(Integer MeetingId);
	
	//meetingIdに紐づく一意の開始時間と終了時間を取得
	List<String> distinctStartAndEndTime (Integer MeetingId);
	
	//meetingIdに紐づくデータのstatusをclosedにする
	void updateStatusToClosed(Integer MeetingId);

}
