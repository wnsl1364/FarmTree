package com.farm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
	// Connection 생성

	// 1. 데이터베이스 연결을 위한 기본 정보 (Oracle 기준)
	private static final String url = "jdbc:oracle:thin:@192.168.0.40:1521:xe"; // 오라클 DB 접속 URL
	private static final String userId = "scott"; // 데이터베이스 사용자 계정
	private static final String userPw = "tiger"; // 데이터베이스 사용자 비밀번호

	public static Connection getConnect() {
		// 2. 데이터베이스 연결(Connection 객체 생성)
		try {
			return DriverManager.getConnection(url, userId, userPw);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
