package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

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
	public void add(InterviewSchedule i) {
		interviewMapper.insert(i);
	}

	@Override
	public List<InterviewSchedule> findByLoginIdAndTeacherId(String teacherId) {
		return interviewMapper.findByTeacherId(teacherId);
	}

	@Override
	public boolean existsByLoginIdAndTeacherId(String teacherId) {
		//teacherIdと合致するデータを取得して戻り値がnullならfalse,nullじゃなければtrue
		if (findByLoginIdAndTeacherId(teacherId) != null) {
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
			String status = meetingsMapper.findByMeetingId(meetingId).getStatus();
			if (status != null) {
				return meetingsMapper.findByMeetingId(meetingId);
			}
		}
		return null;
	}

	@Override
	public MeetingsDomain findByMeetingId(Integer meetingId) {
		return meetingsMapper.findByMeetingId(meetingId);
	}

}
