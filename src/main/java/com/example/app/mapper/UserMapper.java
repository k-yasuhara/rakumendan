package com.example.app.mapper;

import com.example.app.domain.User;

public interface UserMapper {
	User selectById(String id);
}
