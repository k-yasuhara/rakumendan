package com.example.app.service;

import java.util.List;

import com.example.app.domain.InterviewSchedule;
import com.example.app.domain.MeetingsDomain;

public interface InterviewSchedulesService {
	
	void add (InterviewSchedule i);
	
	List<InterviewSchedule> findByLoginIdAndTeacherId(String teacherId);
	
	boolean existsByLoginIdAndTeacherId(String teacherId);
	
	List<Integer>getDistinctMeetingId(String teacherId);
	
	MeetingsDomain getMeetingStatus(List<Integer> meetingList);
	
	MeetingsDomain findByMeetingId(Integer meetingId);
}
