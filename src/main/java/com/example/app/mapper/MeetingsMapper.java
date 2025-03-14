package com.example.app.mapper;

import java.util.List;

import com.example.app.domain.MeetingsDomain;

public interface MeetingsMapper {
	
	List<MeetingsDomain> findByTeacherId(String teacherId);
}
