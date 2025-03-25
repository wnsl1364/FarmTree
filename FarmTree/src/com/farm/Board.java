package com.farm;

public class Board {
	// 필드
	private int post_id;
	private String title;
	private String content;
	private String author;
	private String post_date;
	

	// 생성자
	public Board() {
	}

	public Board(int post_id, String title, String content, String author, String post_date) {
		this.post_id = post_id;
		this.title = title;
		this.content = content;
		this.author = author;
		this.post_date = post_date;
	}

	// getter setter
	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPost_date() {
		return post_date;
	}

	public void setPost_date(String post_date) {
		this.post_date = post_date;
	}

	// toString
	@Override
	public String toString() {
		return "Board [ID=" + post_id + ", 제목=" + title + ", 작성자=" + author + ", 등록일=" + post_date + "]";
	}
}
