package com.farm;

public class User {
	// 필드
	private String user_id;
	private String user_name;
	private String password;

	// 생성자
	public User() {
	}
	

	public User(String user_id, String user_name, String password) {
		this.user_id = user_id;
		this.user_name = user_name;
		this.password = password;
	}

	// getter setter
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// toString
	@Override
	public String toString() {
		return "User [id=" + user_id + "이름=" + user_name + "]";
	}
}
