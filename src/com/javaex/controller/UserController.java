package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;


@WebServlet("/user")
public class UserController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("[UserController]");
		
		//텍스트 인코딩
		request.setCharacterEncoding("UTF-8");
		
		//파라미터 가져오기(업무에 해당하는 액션)
		String action = request.getParameter("action");
		
		if("joinForm".equals(action)) { //회원가입 폼
			System.out.println("[UserController.joinForm]");
			
			//회원가입폼 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
			
			
		} else if("join".equals(action)) { //회원가입
			System.out.println("[UserController.join]");
			
			//*******회원가입********
			
			//파라미터 꺼내기
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			System.out.println(id + ", " + pw + ", " + name + ", " + gender);

			//VO 만들기
			UserVo userVo = new UserVo(id, pw, name, gender);
			System.out.println(userVo);
			
			
			//dao.insert(vo) --> db저장
			UserDao userDao = new UserDao();
			userDao.userInsert(userVo);
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
			
		} else if("loginForm".equals(action)) {
			System.out.println("[UserController.loginForm]");
			
			//로그인 폼 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
			
		} else if("login".equals(action)) {
			System.out.println("[UserController.login]");
			
			//파라미터 값 꺼내오기
			String id = request.getParameter("id");
			String password = request.getParameter("password");
				
			//Dao 회원정보 조회하기(세션 저장용)
			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getUser(id, password);

			if(userVo !=null) {
				
				System.out.println("로그인 성공");
				System.out.println(userVo);
				
				//성공일때(아디디, 비번 일치했을때) 세션에 저장!!
				HttpSession session = request.getSession();
				session.setAttribute("authUser", userVo);	//jsp에 데이터전달할때 비교 request.setAttribute(); 
				
				System.out.println("성공일때" + userVo);
				
				//리다이렉트 -- 메인페이지
				WebUtil.redirect(request, response, "/mysite/main");
				
			} else {
				System.out.println("로그인 실패");
				
				//리다이렉트 - 로그인폼 페이지
				WebUtil.redirect(request, response, "/mysite/user?action=loginForm&result=fail");
			}
			
			
			
		} else if("logout".equals(action)) {
			System.out.println("[UserController.logout]");
			
			
			//세션에 있는 "authUser"의 정보삭제
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			WebUtil.redirect(request, response, "/mysite/main");
			
			
		} else if("modifyForm".equals(action)) {
			
			
			
			/* 내가 생각했던 방법 분석결과
			System.out.println("[회원정보수정]");
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
			
			Dao에 모든 정보를 불러오는 메소드를 하나만 만들어서 그것을 세션에 담고   
			회원정보 수정과 메인화면에서 다시 회원정보수정을 눌렀을때 "아이디"란의 null의 이유는
			modifyForm.jsp에서 아이디는 고칠수 없는 고정값으로 세션의 id만 불러왔고 파라미터로 보내주지
			않았기때문에 그랬던 것 같다. 그래서 코드가 지저분해지지만 아래처럼 바꿔주어 해결할 수 있었다.
			<span class="text-large bold"><input type="hidden" name="id" value=<%=authUser.getId() %>><%=authUser.getId() %></span>
			input 태그처럼 파라미터값을 보낼수 있으면서 수정할 수 없도록 고정하는 태그를 알아야 깔끔하게 코딩할 수 있을것같다.
			*/
		} else if("modify".equals(action)) {
			
			System.out.println("[회원정보 파라미터]");
			HttpSession session = request.getSession();
			
			//파라미터 꺼내오기
			int no = ((UserVo)session.getAttribute("authUser")).getNo();
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			
			//Modify해주기위해 다오 열어주기
			UserVo userVo = new UserVo(no, id, password, name, gender);
			
			
			UserDao userDao = new UserDao();
			
			userDao.userModify(userVo);
			
			
			session.setAttribute("authUser", userVo);
			
			
			WebUtil.redirect(request, response, "/mysite/main");
			
		}
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
