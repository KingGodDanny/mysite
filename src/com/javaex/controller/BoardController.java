package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;


@WebServlet("/board")
public class BoardController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("[BoardController]");
		
		//텍스트 인코딩
		request.setCharacterEncoding("UTF-8");
		
		//업무에 필요한 파라미터 만들기
		String action = request.getParameter("action");
		
		
		if("list".equals(action)) {
			
			//리스트 출력확인
			System.out.println("BoardController.list");
			
			//리스트위해 다오열기
			BoardDao boardDao = new BoardDao();
			List<BoardVo> boardList = boardDao.getBoardList();
			
			
			System.out.println(boardList);
			
			//Request 어트리뷰트에 넣어주기
			request.setAttribute("bList", boardList);
			
			//포워드해주기
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			
		} else if("read".equals(action)) {
			
			//Read 공간 출력확인
			System.out.println("Read");
			
			//다오에 메소드 하나 더만들예정
			
		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
