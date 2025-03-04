package com.example.app.service;

import java.time.LocalTime;
import java.util.ArrayList;
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

	@Override
	public boolean validateStartAndEndTime(Meeting meet) {

		LocalTime startTime = LocalTime.parse(meet.getStartTime());
		LocalTime endTime = LocalTime.parse(meet.getEndTime());

		if (startTime.isAfter(endTime)) {
			return false;
		}

		return true;
	}

	@Override
	public List<String> getMeetingSchedule(Meeting meet) {

		LocalTime startTime = LocalTime.parse(meet.getStartTime());
		LocalTime endTime = LocalTime.parse(meet.getEndTime());
		int meetingTime = Integer.parseInt(meet.getTimePerMeeting());

		List<String> schdule = new ArrayList<>();

		LocalTime currentTime = startTime;

		while (currentTime.isBefore(endTime)) {
			int hour = currentTime.getHour();
			int minute = currentTime.getMinute();

			minute += meetingTime;
			if (minute >= 60) {
				minute -= 60;
				++hour;
			}
			LocalTime nextTime =  LocalTime.of(padZero(hour), padZero(minute));
			schdule.add(currentTime +" ～ " + nextTime);
			currentTime = nextTime;
		}

		return schdule;
	}

	private int padZero(int num) {
		if (num < 10) {
			return Integer.parseInt("0" + num);
		} else {
			return num;
		}
	}

}
