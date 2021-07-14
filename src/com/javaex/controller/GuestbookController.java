package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestBookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestBookVo;


@WebServlet("/guest")
public class GuestbookController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//텍스트 인코딩
		request.setCharacterEncoding("UTF-8");
		
		//파라미터 가져오기 (업무)
		String action = request.getParameter("action");
		
		
		if("addList".equals(action)) {
			
			//리스트업무 확인
			System.out.println("[GuestbookController.addList]");
			
			//리스트를 컨트롤러가 확인
			GuestBookDao gBookDao = new GuestBookDao();
			List<GuestBookVo> guestList = gBookDao.getGuestList();
			
			System.out.println(guestList);
			
			//데이터 어트리뷰터(속성)에 넣어주기
			request.setAttribute("gList", guestList);
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
			
		} else if("add".equals(action)) {
			
			//이 공간에 넘어오나 확인
			System.out.println("[GuestbookController.add]");
			
			//addList에서 받은 파라미터 꺼내오기
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
			
			//Vo로 묶어주기
			GuestBookVo gBookVo = new GuestBookVo(name, password, content);
			
			//등록을 위해 다오 열어주고 넣어주기
			GuestBookDao gBookDao = new GuestBookDao();
			gBookDao.guestInsert(gBookVo);
			
			//add를 통해 Insert하고나서 addList 링크를 보여주기 위해 아래 주소 확인 잘해야함!
			WebUtil.redirect(request, response, "/mysite/guest?action=addList");
			
		} else if("dForm".equals(action)) {
			
			//deleteForm 구간에 잘 넘어오는지 확인
			System.out.println("[GuestbookController.deleteForm]");
			
			//action = dForm 받았을때 deleteForm 화면에 보여주기
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
			
		} else if("delete".equals(action)) {
			
			//detele 구간에 잘 넘어오는지 확인
			System.out.println("[GuestbookController.delete]");
			
			//deleteForm에서 넘어온 파라미터 꺼내주기
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");
			
			//Delete 메소드를 사용하기위해 다오공간 열어주기
			GuestBookDao gBookDao = new GuestBookDao();
			gBookDao.goodDelete(no, password);
		
			//삭제 후 addList 열어주기위해 리다이렉트
			WebUtil.redirect(request, response, "/mysite/guest?action=addList");
			
		}
		 
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
