<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%//***jstl문을 써주기 위해서 반드시 아래의 임포트를 해줘야한다*** %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@page import="com.javaex.vo.UserVo" %>
<%@page import="com.javaex.dao.GuestBookDao" %>
<%@page import="com.javaex.vo.GuestBookVo" %>
<%@page import="java.util.List" %>

<%
	//로그인관련
	//UserVo authUser = (UserVo)session.getAttribute("authUser");

%>    



	<div id="header" class="clearfix">
		<h1>
			<a href="/mysite/main">MySite</a>
		</h1>
		<c:choose>
			<c:when test="${!empty sessionScope.authUser }">
			<% //if(authUser != null) { //로그인이 성공하면 %>
				<ul>
					<li> ${sessionScope.authUser.name } <%//=authUser.getName() %>님 안녕하세요^^</li>
					<li><a href="/mysite/user?action=logout" class="btn_s">로그아웃</a></li>
					<li><a href="/mysite/user?action=modifyForm" class="btn_s">회원정보수정</a></li>
				</ul>
			</c:when>
			
			<c:otherwise>
			<%//} else { %>
			
				<ul>
					<li><a href="/mysite/user?action=loginForm" class="btn_s">로그인</a></li>
					<li><a href="/mysite/user?action=joinForm" class="btn_s">회원가입</a></li>
				</ul>
			
			<%//} %>
			</c:otherwise>
		</c:choose>
	</div>
	
	<!-- //header -->

	<div id="nav">
		<ul class="clearfix">
			<li><a href="">입사지원서</a></li>
			<li><a href="">게시판</a></li>
			<li><a href="">갤러리</a></li>
			<li><a href="/mysite/guest?action=addList">방명록</a></li>
		</ul>
	</div>
	<!-- //nav -->

	