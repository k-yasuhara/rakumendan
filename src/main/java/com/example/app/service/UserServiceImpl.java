package com.example.app.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.example.app.domain.User;
import com.example.app.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserMapper mapper;

	@Override
	public boolean loginByIdAndPass(String loginId, String pass) {
		if (mapper.selectById(loginId) == null) {
			return false;
		} else if (!BCrypt.checkpw(pass, mapper.selectById(loginId).getPass())) {
			return false;
		}
		return true;
	}

	@Override
	public User selectByIdAndPass(String loginId) {
		return mapper.selectById(loginId);
	}
}
