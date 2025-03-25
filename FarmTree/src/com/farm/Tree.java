package com.farm;

public class Tree {
	// 필드
	private int tree_id;
	private String tree_name;
	private int price;
	private String description;
	private String write_date;
	

	// 생성자
	public Tree() {
	}

	public Tree(int tree_id, String tree_name, int price, String description) {
		this.tree_id = tree_id;
		this.tree_name = tree_name;
		this.price = price;
		this.description = description;
		this.write_date = null;
	}

	// getter setter
	public int getTree_id() {
		return tree_id;
	}

	public void setTree_id(int tree_id) {
		this.tree_id = tree_id;
	}

	public String getTree_name() {
		return tree_name;
	}

	public void setTree_name(String tree_name) {
		this.tree_name = tree_name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWrite_date() {
		return write_date;
	}

	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}

	// toString
	@Override
	public String toString() {
		return "Tree [ID=" + tree_id + ", 이름=" + tree_name + ", 가격=" + price + "원, 설명=" + description + ", 등록일="
				+ write_date + "]";
	}
}
