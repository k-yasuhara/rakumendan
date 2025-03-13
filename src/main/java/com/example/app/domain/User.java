package com.example.app.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class User {
	private Integer id;
	private String name;
	@NotBlank
	private String loginId;
	private String pass;
	private String status;
	private String teacherId;
	private Integer userType;
}
