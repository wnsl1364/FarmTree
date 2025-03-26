package com.farm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class BoardDao {
	private Connection conn;

	public BoardDao() {
		this.conn = DBConnect.getConnect();
	}

	// 게시판 목록 조회
	public List<Board> list() {
		List<Board> list = new ArrayList<>();
		String sql = "SELECT * FROM Board ORDER BY post_id DESC";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				Board board = new Board();
				board.setAuthor(rs.getString("author"));
				board.setContent(rs.getString("content"));
				board.setPost_date(sdf.format(rs.getDate("post_date"))); // 날짜 포맷 변경
				board.setPost_id(rs.getInt("post_id"));
				board.setTitle(rs.getString("title"));
				board.setView_count(rs.getInt("view_count"));
				list.add(board);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	// 게시글 상세정보 조회
	public Board getBoardById(int postId) {
		String sql = "SELECT * FROM Board WHERE post_id = ?";
		Board board = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, postId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					board = new Board();
					board.setAuthor(rs.getString("author"));
					board.setContent(rs.getString("content"));
					board.setPost_date(sdf.format(rs.getDate("post_date"))); // 날짜 포맷 변경
					board.setPost_id(rs.getInt("post_id"));
					board.setTitle(rs.getString("title"));
					board.setView_count(rs.getInt("view_count"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return board;
	}

	// 게시글 등록
	public boolean add(Board board) {
		String sql = "INSERT INTO Board(post_id, title, content, author, post_date) "
				+ "VALUES (board_seq.NEXTVAL, ?, ?, ?, SYSDATE)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setString(3, board.getAuthor());

			int r = pstmt.executeUpdate();
			return r > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// 게시글 수정
	public boolean edit(Board board) {
		String sql = "UPDATE Board set title = ?, content = ?, author = ?, post_date = SYSDATE WHERE post_id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setString(3, board.getAuthor());
			pstmt.setInt(4, board.getPost_id());

			int r = pstmt.executeUpdate();
			return r > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// 게시글 삭제
	public boolean delete(int postId) {
		String sql = "DELETE Board WHERE post_id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, postId);

			int r = pstmt.executeUpdate();
			return r > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	// 조회수 증가
	public boolean viewCount(int postId) {
		String sql = "UPDATE Board SET view_count = view_count+1 WHERE post_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, postId);

			int r = pstmt.executeUpdate();
			return r > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}