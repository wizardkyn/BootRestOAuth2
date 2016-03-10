package com.example.web.sqlmappers.login;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.example.web.domain.UserVo;

@Repository
public interface LoginMapper {
	public UserVo getUser(String userId);
	public List<UserVo> getUserList();
}
