package com.example.app.service;

import com.example.app.domain.Admin;

public interface AdminService {
	boolean loginByIdAndPass(String loginId, String pass);

	Admin selectByIdAndPass(String loginId);
}
