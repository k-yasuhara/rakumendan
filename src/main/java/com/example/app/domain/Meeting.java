package com.example.app.domain;

import lombok.Data;

@Data
public class Meeting {
	private String parentMeetingDate;
	private String startTime;
	private String endTime;
	private String timePerMeeting;
}
