package com.javaex.dao;

import com.javaex.vo.UserVo;

public class DaoTest {

	public static void main(String[] args) {
		
//		UserVo userVo = new UserVo("danny", "123", "조대근", "male");
//		
//		UserDao userDao = new UserDao();
//		userDao.userInsert(userVo);
		
		
		UserDao userDao = new UserDao();
		UserVo userVo = userDao.getUser("ctct25", "123");
		System.out.println(userVo);
		
	}

}
