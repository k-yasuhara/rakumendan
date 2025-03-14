package com.example.app.service;

import java.util.List;
import java.util.Map;

import com.example.app.domain.InterviewSchedule;

public interface InterviewSchedulesService {
	
	void add (InterviewSchedule i);
	
	List<InterviewSchedule> findByLoginIdAndTeacherId(String teacherId);
	
	boolean existsByLoginIdAndTeacherId(String teacherId);
	
	List<Integer>getDistinctMeetingId(String teacherId);
	
	Map<Integer, String>getMeetingStatus(List<Integer> meetingList);
}
