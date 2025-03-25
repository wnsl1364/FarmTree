package com.farm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TreeDao {
	private Connection conn;

	public TreeDao() {
		this.conn = DBConnect.getConnect();
	}
	

	// 나무 등록
	public boolean add(Tree tree) {
		String sql = "insert into Trees(tree_id, tree_name, price, description, write_date) "
				+ "values (?, ?, ?, ?, SYSDATE)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, tree.getTree_id());
			pstmt.setString(2, tree.getTree_name());
			pstmt.setInt(3, tree.getPrice());
			pstmt.setString(4, tree.getDescription());

			int r = pstmt.executeUpdate();
			return r > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 나무 수정
	public boolean edit(Tree tree) {
		String sql = "update Trees set tree_name = ?, price = ?, description = ?, write_date = SYSDATE where tree_id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, tree.getTree_name());
			pstmt.setInt(2, tree.getPrice());
			pstmt.setString(3, tree.getDescription());
			pstmt.setInt(4, tree.getTree_id());

			int r = pstmt.executeUpdate();
			return r > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 나무 삭제
	public boolean delete(int id) {
		String sql = "delete from Trees where tree_id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);

			int r = pstmt.executeUpdate();
			return r > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 나무 목록조회
	public List<Tree> list() {
		String sql = "select * from Trees";
		List<Tree> list = new ArrayList<>();

		try (PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				Tree tree = new Tree();
				tree.setTree_id(rs.getInt("tree_id"));
				tree.setTree_name(rs.getString("tree_name"));
				tree.setPrice(rs.getInt("price"));
				tree.setDescription(rs.getString("description"));
				tree.setWrite_date(rs.getString("write_date"));
				list.add(tree);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	// 나무 상세정보조회
	public Tree getTreeById(int id) {
		String sql = "select * from Trees where tree_id = ?";
		Tree tree = null;

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					tree = new Tree();
					tree.setTree_id(rs.getInt("tree_id"));
					tree.setTree_name(rs.getString("tree_name"));
					tree.setPrice(rs.getInt("price"));
					tree.setDescription(rs.getString("description"));
					tree.setWrite_date(rs.getString("write_date"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tree;
	}
}