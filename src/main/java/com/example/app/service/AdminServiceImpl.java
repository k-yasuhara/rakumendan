package com.example.app.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.example.app.domain.Admin;
import com.example.app.mapper.AdminMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

	private final AdminMapper mapper;

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
	public Admin selectByIdAndPass(String loginId) {
		return mapper.selectById(loginId);

	}
}
