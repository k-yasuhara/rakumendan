package com.example.app.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.dto.Meeting;

@Service
public class MeetingSettingServiceImpl implements MeetingSettingService {

	@Override
	public List<String> getMeetingDate(Meeting meet) {
		String str = meet.getParentMeetingDate();
		// 文字列をカンマで分割
        String[] dates = str.split(",");
        
        // 日付部分だけを格納するリスト
        List<String> list = new ArrayList<>();
        
        // 各要素から曜日部分を削除して、日付だけを取得
        for (String date : dates) {
            String formattedDate = date.split("[(]")[0];  // (月)部分を除去
            list.add(formattedDate);
        }
		return list;
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
			schdule.add(currentTime + "～" + nextTime);
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
