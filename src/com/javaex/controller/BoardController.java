package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;


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
			
			String keyword = request.getParameter("keyword");
			
			//검색어 잘 넘어왔는지 확인
			System.out.println(keyword);
			
			List<BoardVo> boardList;
			
			if(keyword != null) { // 검색어가 있을때
				
				boardList = boardDao.getBoardList(keyword);
				
			} else { //검색어가 없을때
				
				boardList = boardDao.getBoardList(null);
				
			}
			
			//Request 어트리뷰트에 넣어주기
			request.setAttribute("bList", boardList);
			
			//포워드해주기
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			
		} else if("read".equals(action)) {
			
			//Read 공간 출력확인
			System.out.println("Read");
			
			//read 메소드 사용위해 다오 열어주기
			BoardDao boardDao = new BoardDao();
			
			//파라미터 꺼내오기
			int no = Integer.parseInt(request.getParameter("no"));
			
			//조회수 메소드 사용하기
			boardDao.readHit(no);
			
			//read메소드 사용후 Vo 
			BoardVo boardVo = boardDao.getRead(no);
			
			
			//Request 어트리뷰트에 넣어주기
			request.setAttribute("read", boardVo);
			System.out.println(boardVo);
			
			//포워드 해주기
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
			
		} else if("wForm".equals(action)) {
			
			//WriteForm 공간 출력확인
			System.out.println("WriteForm");
			
			//포워드
			WebUtil.forward(request, response, "WEB-INF/views/board/writeForm.jsp");
			
		} else if ("write".equals(action)) {
			
			//Write 공간 출력확인
			System.out.println("Write");
			
			//파라미터값 꺼내기
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			//userNo(no)값은 로그인 세션에서 가져오기
			HttpSession session = request.getSession();
			int no = ((UserVo)session.getAttribute("authUser")).getNo();
			
			//받아온 파라미터들 Vo에 담기
			BoardVo boardVo = new BoardVo(title, content, no);
			System.out.println(boardVo);
			
			
			//다오 열고 write메소드 사용
			BoardDao boardDao = new BoardDao();
			boardDao.boardWrite(boardVo);
			
			//리다이렉트
			WebUtil.redirect(request, response, "/mysite/board?action=list");
			
		} else if("delete".equals(action)) {
			
			//Delete 공간 출력확인
			System.out.println("Delete");
			
			//no 파라미터 꺼내오기
			int no = Integer.parseInt(request.getParameter("no"));
			
			//Delete 메소드 사용위해 다오열기
			BoardDao boardDao = new BoardDao();
			boardDao.boardDelete(no);
			
			//삭제후 list.jsp로 리다이렉트
			WebUtil.redirect(request, response, "/mysite/board?action=list");
			
			
		} else if("mForm".equals(action)) {
			
			//ModifyForm 공간 출력확인
			System.out.println("ModifyForm");
			
			//read.jsp에서 보낸 파라미터 꺼내기
			int no = Integer.parseInt(request.getParameter("no"));
			
			//no 값으로 getRead 불러오기
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getRead(no);
			
			//request 어트리뷰터 업뎃
			request.setAttribute("read", boardVo);
			
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
			
			
		} else if("modify".equals(action)) {
			
			//Modify 공간 출력확인
			System.out.println("Modify");
			
			//ModifyForm에서 넘어온 파라미터값 꺼내기
			int no = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			//받아온 파라미터들 Vo에 담기
			BoardVo boardVo = new BoardVo(no, title, content);
			
			System.out.println(boardVo);
			
			//다오 열어주기
			BoardDao boardDao = new BoardDao();
			boardDao.boardModify(boardVo);
			
			
			//리다이렉트
			WebUtil.redirect(request, response, "/mysite/board?action=list");
			
		}
	
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
