package com.example.app.domain;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class InterviewSchedule {
	private Integer id;
	private Integer teacherId;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private Integer durationMinutes;
	private String status; // "open：面談可", "closed：面談不可", "confirmed：確定済み"
}
