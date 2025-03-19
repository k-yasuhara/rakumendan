package com.example.app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.InterviewSchedule;
import com.example.app.domain.MeetingsDomain;
import com.example.app.mapper.InterviewScheduleMapper;
import com.example.app.mapper.MeetingsMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterviewSchedulesServiceImpl implements InterviewSchedulesService {

	private final InterviewScheduleMapper interviewMapper;
	private final MeetingsMapper meetingsMapper;
	
	@Override
	public void addInterviewSchedules(InterviewSchedule i) {
		interviewMapper.insert(i);
	}

	@Override
	public Integer addMeetings(String teacherId) {
		meetingsMapper.insertByTeacherId(teacherId);
		return meetingsMapper.getLastInsertId();
	}

	@Override
	public List<InterviewSchedule> findByLoginIdAndTeacherId(String teacherId) {
		return interviewMapper.findByTeacherId(teacherId);
	}

	@Override
	public boolean existsByLoginIdAndTeacherId(String teacherId) {
		//teacherIdと合致するデータを取得して戻り値が空ならfalse,空じゃなければtrue
		if (!findByLoginIdAndTeacherId(teacherId).isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public List<Integer> getDistinctMeetingId(String teacherId) {
		return interviewMapper.distinctMeetingId(teacherId);
	}

	@Override
	public MeetingsDomain getMeetingStatus(List<Integer> meetingIdList) {
		for (Integer meetingId : meetingIdList) {
			MeetingsDomain meet = meetingsMapper.findByMeetingId(meetingId);
			//meetingIdに対してnullだった場合
			if (meet == null) {
				continue;
			}
			return meetingsMapper.findByMeetingId(meetingId);
		}
		return null;
	}

	@Override
	public MeetingsDomain findByMeetingId(Integer meetingId) {
		return meetingsMapper.findByMeetingId(meetingId);
	}

	@Override
	public List<LocalDate> getMeetingDate(Integer meetingId) {
		return interviewMapper.getMeetingDate(meetingId);
	}

	@Override
	public List<InterviewSchedule> getInterviewSchedule(Integer meetingId) {
		return interviewMapper.findByMeetingId(meetingId);
	}

	@Override
	public List<String> getDistinctStartAndEndTime(Integer MeetingId) {
		return interviewMapper.distinctStartAndEndTime(MeetingId);
	}
	
	@Transactional
	@Override
	public void closeStatus(Integer MeetingId) {
		interviewMapper.updateStatusToClosed(MeetingId);
		meetingsMapper.updateStatusToClosed(MeetingId);
	}


}
