package com.farm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
	private Connection conn;
	
	public UserDao(){
		this.conn = DBConnect.getConnect();
	}
	
	public boolean login(String user_id, String password) {
		String sql = "select * from Users where user_id = ? AND password = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, user_id);
			pstmt.setString(2, password);

			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean register(String user_id, String user_name, String password) {
		String sql = "INSERT INTO Users(user_id, user_name, password) values (?,?,?)";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, user_id);
	        pstmt.setString(2, user_name);
	        pstmt.setString(3, password);
	        
	        int r = pstmt.executeUpdate();
			return r > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
