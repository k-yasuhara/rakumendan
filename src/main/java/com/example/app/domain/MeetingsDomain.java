package com.example.app.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MeetingsDomain {
	
	private Integer meetingId;
	private String teacherId;
	private String status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
