package com.example.app.dto;

import java.time.LocalTime;

import lombok.Data;

@Data
public class StartTimeEndTime {
	private LocalTime startTime;
	private LocalTime endTime;
}
