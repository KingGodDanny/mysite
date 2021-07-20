package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {

	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			// System.out.println("접속성공");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	
	//BoardList 가져오기
	public List<BoardVo> getBoardList(String keyword) {
		
		//게시판 정보 담을 리스트 생성해주기
		List<BoardVo> boardList = new ArrayList<BoardVo>();
		
		this.getConnection();
		
		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select bo.no, ";
			query += "        bo.title, ";
			query += "        us.name, ";
			query += "        bo.hit, ";
			query += "        to_char(bo.reg_date, 'YYYY-MM-DD HH24:MI') dateD, ";
			query += "        bo.user_no ";
			query += " from users us, board bo ";
			query += " where us.no = bo.user_no ";
			
			if(keyword == null || keyword.equals("")) { //검색이 없을때
				
				query += " order by bo.reg_date desc";
				
				pstmt = conn.prepareStatement(query);
				
			} else { //name이랑 title에서 검색
				
				query += " and us.name || bo.title like ? ";
				query += " order by bo.reg_date desc";
				
				pstmt = conn.prepareStatement(query);
				
				pstmt.setString(1, '%' + keyword + '%');
				
			}
			
			rs = pstmt.executeQuery();
			
			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String title = rs.getString("title");
				String name = rs.getString("name");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("dateD");
				int userNo = rs.getInt("user_no");
				
				//생성자에 담아주기
				BoardVo boardVo = new BoardVo(no, title, hit, regDate, userNo, name);
				
				//리스트에 담기
				boardList.add(boardVo);
				
			
			}
		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		this.close();
		
		return boardList;
	}
	
	
	//BoardList 가져오기
		public BoardVo getRead(int num) {
			
			
			BoardVo boardVo = null;
			
			this.getConnection();
			
			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " select bo.no, ";
				query += "        us.name, ";
				query += "        bo.hit, ";
				query += "        to_char(bo.reg_date, 'YYYY-MM-DD HH24:MI') dateD , ";
				query += "        bo.title, ";
				query += "        bo.content, ";
				query += "        bo.user_no ";
				query += " from users us, board bo ";
				query += " where us.no = bo.user_no ";
				query += " and bo.no = ? ";
				
				System.out.println(query);
				
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, num);
				
				rs = pstmt.executeQuery();
				
				// 4.결과처리
				while (rs.next()) {
					int no = rs.getInt("no");
					String name = rs.getString("name");
					int hit = rs.getInt("hit");
					String regDate = rs.getString("dateD");
					String title = rs.getString("title");
					String content = rs.getString("content");
					int userNo = rs.getInt("user_no");
					
					//생성자에 담아주기
					boardVo = new BoardVo(no, title, content, hit, regDate, userNo, name);
					
				
				}
			
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
			this.close();
			
			return boardVo;
		}
	
		
		//조회수 증가
		public int readHit(int no) {
			
			int count = 0;
			
			this.getConnection();
			
			try {
				
				// 3. SQL문 준비 / 바인딩 / 실행
			    String query = "";
			    query += " UPDATE board ";
			    query += " set hit = hit + 1 ";
			    query += " where no = ? ";
			    
			    pstmt = conn.prepareStatement(query);
			    
			    pstmt.setInt(1, no);
			    
			    count = pstmt.executeUpdate();
			    
			    
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			this.close();
			
			return count;
		}
		
		
		//Board 등록하기
		public int boardWrite(BoardVo boardVo) {
			
			int count = 0;
			
			this.getConnection();
			
			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " INSERT INTO board ";
				query += " VALUES (seq_board_no.nextval, ?, ?, 0 , sysdate, ?) ";
				
				pstmt = conn.prepareStatement(query);
				
				pstmt.setString(1, boardVo.getTitle());
				pstmt.setString(2, boardVo.getContent());
				pstmt.setInt(3, boardVo.getUserNo());
				
				count = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			this.close();
			
			return count;
			
		}
		
		
		//Board 삭제하기
		public int boardDelete(int no) {
			
			int count = 0;
			
			this.getConnection();

			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " Delete from board ";
				query += " where no = ? ";
				
				//쿼리만ㄷㄹ기
				pstmt = conn.prepareStatement(query);
				
				pstmt.setInt(1, no);
				
				count = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			this.close();
			
			return count;
			
		}
		
		
		//Board 수정하기
		public int boardModify(BoardVo boardVo) {
			
			int count = 0;
			
			this.getConnection();
			
			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " UPDATE board ";
				query += " SET title = ?, ";
				query += "     content = ? ";
				query += " where no = ? ";
				
				pstmt = conn.prepareStatement(query);
				
				pstmt.setString(1, boardVo.getTitle());
				pstmt.setString(2, boardVo.getContent());
				pstmt.setInt(3, boardVo.getNo());
				
				count = pstmt.executeUpdate();
				
				// 4.결과처리
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			this.close();
			
			return count;

		}
}
