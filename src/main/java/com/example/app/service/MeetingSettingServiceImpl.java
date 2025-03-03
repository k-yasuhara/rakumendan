package com.example.app.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.domain.Meeting;

@Service
public class MeetingSettingServiceImpl implements MeetingSettingService {

	@Override
	public List<String> getMeetingDate(Meeting meet) {
		String str = meet.getParentMeetingDate();
		List<String> meetDate = Arrays.asList(str.split(","));
		return meetDate;
	}

	@Override
	public Integer countMeetingDate(Meeting meet) {
		if (!meet.getParentMeetingDate().isEmpty()) {
			return getMeetingDate(meet).size();
		}
		return null;
	}

}
