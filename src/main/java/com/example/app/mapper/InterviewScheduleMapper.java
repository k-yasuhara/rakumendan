package com.example.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.InterviewSchedule;

@Mapper
public interface InterviewScheduleMapper {
	
	//面談日程をinsert
	void insert (InterviewSchedule i); 
	
	//teacherIdに紐づくデータを取得
	List<InterviewSchedule> findByTeacherId (String teacherId);
	
	//teacherIdに紐づくmeeting_idのデータを取得
	List<Integer> distinctMeetingId(String teacherId);
}
