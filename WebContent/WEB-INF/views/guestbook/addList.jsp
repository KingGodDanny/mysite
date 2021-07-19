<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%-- <%@page import="com.javaex.vo.UserVo" %> --%>
<%-- <%@page import="com.javaex.dao.GuestBookDao" %> --%>
<%-- <%@page import="com.javaex.vo.GuestBookVo" %> --%>
<%-- <%@page import="java.util.List" %> --%>

	
<%
	//로그인관련
//	UserVo authUser = (UserVo)session.getAttribute("authUser");


	//리스트 출력관련
//	List<GuestBookVo> guestList = //(List<GuestBookVo>)request.getAttribute("gList");
	
	//이렇게 쓰는것은 모델1유형으로 쓰는것과 다름이없다(?)
// 	GuestBookDao guestBookDao = new GuestBookDao(); //게스트리스트를 불러오기위한 공간열기
// 	List<GuestBookVo> guestList = guestBookDao.getGuestList(); //게스트리스트를 배열에 담기

%>    


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link href="/mysite/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite/assets/css/guestbook.css" rel="stylesheet" type="text/css">

</head>

<body>
	<div id="wrap">
		
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
<%-- 		<jsp:include page="/WEB-INF/views/includes/header.jsp"></jsp:include> --%>

		<div id="container" class="clearfix">
			<div id="aside">
				<h2>방명록</h2>
				<ul>
					<li>일반방명록</li>
					<li>ajax방명록</li>
				</ul>
			</div>
			<!-- //aside -->

			<div id="content">

				<div id="content-head" class="clearfix">
					<h3>일반방명록</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>방명록</li>
							<li class="last">일반방명록</li>
						</ul>
					</div>
				</div>
				<!-- //content-head -->

				<div id="guestbook">
					<form action="/mysite/guest" method="get">
						<input type="hidden" name="action" value="add">
						<table id="guestAdd">
							<colgroup>
								<col style="width: 70px;">
								<col>
								<col style="width: 70px;">
								<col>
							</colgroup>
							<tbody>
								<tr>
									<th><label class="form-text" for="input-uname">이름</label></th>
									<td><input id="input-uname" type="text" name="name"></td>
									<th><label class="form-text" for="input-pass">패스워드</label></th>
									<td><input id="input-pass" type="password" name="password"></td>
								</tr>
								<tr>
									<td colspan="4"><textarea name="content" cols="72" rows="5"></textarea></td>
								</tr>
								<tr class="button-area">
									<td colspan="4" class="text-center"><button type="submit">등록</button></td>
								</tr>
							</tbody>

						</table>
						<!-- //guestWrite -->

					</form>


				<c:forEach items="${gList }" var="gBookVo">
				<%
				//for(int i=0; i<guestList.size(); i++) {
				%>
					<table class="guestRead">
						<colgroup>
							<col style="width: 10%;">
							<col style="width: 40%;">
							<col style="width: 40%;">
							<col style="width: 10%;">
						</colgroup>
						<tr>
							<td>${gBookVo.no }  <%//=guestList.get(i).getNo() %></td>
							<td>${gBookVo.name }  <%//=guestList.get(i).getName() %></td>
							<td>${gBookVo.regdate }  <%//=guestList.get(i).getRegdate() %></td>
							<td><a href="/mysite/guest?action=dForm&no=${gBookVo.no }  <%//=guestList.get(i).getNo() %>">삭제</a></td>
						</tr>
						<tr>
							<td colspan=4 class="text-left">방명록 내용<br>${gBookVo.content }  <%//=guestList.get(i).getContent() %></td>
						</tr>
					</table>

				<%
				//}
				%>
				</c:forEach>
				
					<!-- //guestRead -->

					
					<!-- //guestRead -->

				</div>
				<!-- //guestbook -->

			</div>
			<!-- //content  -->
		</div>
		<!-- //container  -->

		
		<!-- 푸터 -->
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
<%-- 		<jsp:include page="/WEB-INF/views/includes/footer.jsp"></jsp:include> --%>
		
	</div>
	<!-- //wrap -->

</body>

</html>