package com.example.web.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.web.domain.UserVo;
import com.example.web.login.LoginService;

@RestController
public class UserController {
    @Autowired
    private LoginService loginService;
    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
	public List<UserVo> getUserVoList() {
    	List<UserVo> userList = new ArrayList<UserVo>();
    	userList = loginService.getUserList();
		return userList;
	}
}
