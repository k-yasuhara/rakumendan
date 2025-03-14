package com.example.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public Map<Integer, String> getMeetingStatus(List<Integer> meetingIdList) {
		Map<Integer, String> meetingStatus = new HashMap<>();
		List<MeetingsDomain> list = meetingsMapper.

		for (Integer meetingId : meetingIdList) {
			meetingStatus.put(meetingId, )
		}

		return null;
	}

}
