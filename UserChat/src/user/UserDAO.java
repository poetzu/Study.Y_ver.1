package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class UserDAO {

	DataSource dataSource;
	
	public UserDAO(){
		try {
			InitialContext initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/UserChat");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public int login(String userID, String userPassword){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL = "select * from user where userID = ?";
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()){
				if(rs.getString("userPassword").equals("userPassowrd")){
					return 1; //로그인ㅇ 성공 
				}
				return 2; //비밀번호가 틀림 
			}else{
				return 0; //사용자가 존재하지 않음 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
		//	데이터베이스 오류 
		
	}
	
	
	public int registerCheck(String userID){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL = "select * from user where userID = ?";
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next() || userID.equals("")){
					return 0;//이미 존재하는 회원 
			}else{
				return 1; //가입 가능한회원 아이디 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
		//	데이터베이스 오류 
		
	}
	
	
	
	
	
	public int register(String userID, String userPassword, String userName, String userAge, String userEmail, String userProfile){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String SQL = "insert into user values (?, ?, ?, ?, ?, ?)";
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			pstmt.setString(2, userPassword);
			pstmt.setString(3, userName);
			pstmt.setInt(4, Integer.parseInt(userAge));
			pstmt.setString(5, userEmail);
			pstmt.setString(6, userProfile);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
		//	데이터베이스 오류 
		
	}
	
	
	
	
	
}
