package com.example.app.mapper;

import com.example.app.domain.MeetingsDomain;

public interface MeetingsMapper {
	
	MeetingsDomain findByMeetingId(Integer meetingId);
}
