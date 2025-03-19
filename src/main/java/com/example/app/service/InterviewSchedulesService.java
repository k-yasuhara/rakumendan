package com.example.app.service;

import java.time.LocalDate;
import java.util.List;

import com.example.app.domain.InterviewSchedule;
import com.example.app.domain.MeetingsDomain;

public interface InterviewSchedulesService {

	void addInterviewSchedules(InterviewSchedule i);
	
	Integer addMeetings(String teacherId);

	List<InterviewSchedule> findByLoginIdAndTeacherId(String teacherId);

	boolean existsByLoginIdAndTeacherId(String teacherId);

	List<Integer> getDistinctMeetingId(String teacherId);

	MeetingsDomain getMeetingStatus(List<Integer> meetingList);

	MeetingsDomain findByMeetingId(Integer meetingId);
	
	List<LocalDate> getMeetingDate(Integer meetingId);
	
	List<InterviewSchedule> getInterviewSchedule(Integer meetingId);
	
	List<String> getDistinctStartAndEndTime (Integer MeetingId);
	
	void closeStatus (Integer MeetingId);
}
