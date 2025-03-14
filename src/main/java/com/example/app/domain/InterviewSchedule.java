package com.example.app.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;

@Data
public class InterviewSchedule {
	private Integer id;
	private String teacherId;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private Integer durationMinutes;
	private String status; // "open：面談可", "closed：面談不可", "confirmed：確定済み"
	private String reserved_student_id;
	private LocalDateTime created;
	private String meetingId; //いつ作成した面談枠なのか、面談枠のグループを明記
}
