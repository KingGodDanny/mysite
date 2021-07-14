package com.javaex.dao;

import com.javaex.vo.UserVo;

public class DaoTest {

	public static void main(String[] args) {
		
		UserVo userVo = new UserVo("danny", "123", "조대근", "male");
		
		UserDao userDao = new UserDao();
		userDao.userInsert(userVo);
		

	}

}
