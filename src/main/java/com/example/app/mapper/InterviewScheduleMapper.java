package com.example.app.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.InterviewSchedule;

@Mapper
public interface InterviewScheduleMapper {
	
	void insert (InterviewSchedule i); 
	
}
