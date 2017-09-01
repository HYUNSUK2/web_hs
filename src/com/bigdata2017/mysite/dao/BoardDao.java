package com.bigdata2017.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bigdata2017.mysite.vo.BoardVo;


public class BoardDao {
	
	public int delete( BoardVo vo ) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = 
				" delete" + 
				"   from guestbook" + 
				"  where no=?" +
				"    and password=?";
			pstmt = conn.prepareStatement( sql );

			pstmt.setLong( 1, vo.getNo() );
			pstmt.setString( 2, vo.getPassword() );

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return count;		
	}
	
	public int insert(GuestbookVo vo) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = 
				" insert" + 
				"   into guestbook" + 
				" values ( seq_guestbook.nextval, ?, ?, ?, sysdate )";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getContent());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return count;
	}

	public List<BoardVo> getList() {
		List<BoardVo> list = new ArrayList<BoardVo>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		

		try {
			System.out.println("다른곳");
			conn = getConnection();

			// Statement 객체 생성
			stmt = conn.createStatement();

			// SQL문 실행
			String sql =
					"select * " +
					" from (select no, title, hit, reg_date, depth,"+ 
						           " member_no, name, rownum as rn "+
					          " from( select a.no, a.title, a.hit,"+
							                  " to_char(reg_date, 'yyyy-mm-dd hh:mi:ss') as reg_date,"+
						                      " a.depth, a.member_no, b.name"+
					                     " from board a, member b"+
					                    " where a.member_no = b.no"+
					                 " order by g_no desc, o_no asc))" +
					  " where (1-1)*5+1 <= rn and rn <= 1*5";
			/*	"   select no," + 
				"          title," + 
				"	       content," + 
				"     	   to_char(reg_date, 'yyyy-mm-dd hh:mi:ss')" + 
				"     from guestbook" + 
				" order by reg_date desc";*/
			rs = stmt.executeQuery( sql );

			// 결과 가져오기(사용하기)
			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				//String content = rs.getString(3);
				String regDate = rs.getString(4);
				String hit = rs.getString(3);
				String name =rs.getString(7);
				

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				//vo.setContent( content );
				vo.setRegDate( regDate );
				vo.setHit(hit);
				vo.setName(name);
				
				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}
	public BoardVo getView(Long no){
		
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVo vo = null;
		
		try {
			conn = getConnection();
			
			// Statement 객체 생성
			

			// SQL문 실행
			String sql =
					"select title, content from board a where a.no=?";
			/*	"   select no," + 
				"          title," + 
				"	       content," + 
				"     	   to_char(reg_date, 'yyyy-mm-dd hh:mi:ss')" + 
				"     from guestbook" + 
				" order by reg_date desc";*/
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			rs = pstmt.executeQuery();
			
			// 결과 가져오기(사용하기)
			while (rs.next()) {
				
				//Long no = rs.getLong(1);
				String title = rs.getString(1);
			    String content = rs.getString(2);
				//String regDate = rs.getString(4);
				//String hit = rs.getString(3);
				//String name =rs.getString(7);
				

				vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContent( content );
				//vo.setRegDate( regDate );
				//vo.setHit(hit);
				//vo.setName(name);
				
				
			}
		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
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
				e.printStackTrace();
			}
		}

		return vo;
		
	}

	private Connection getConnection() throws SQLException {

		Connection conn = null;

		try {
			// JDBC 드라이버 로딩(JDBC 클래스 로딩)
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// Connection 가져오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}	
}
