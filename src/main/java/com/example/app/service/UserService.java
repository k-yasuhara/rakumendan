package com.example.app.service;

import com.example.app.domain.User;

public interface UserService {
	
	boolean loginByIdAndPass(String loginId, String pass);
	User selectByIdAndPass(String loginId);
	
	
	
}
