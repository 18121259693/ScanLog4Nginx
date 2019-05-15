package com.zzzmh.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JdbcUtils {

	public static Connection getConn() {
		Connection conn = null;
		try {
			Class.forName(SystemUtils.get("JdbcDriver"));
			conn = DriverManager.getConnection(SystemUtils.get("JdbcUrl"), SystemUtils.get("JdbcUserName"), SystemUtils.get("JdbcPassWord"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static List<Map<String, String>> query(Connection conn, String sql) {
		System.out.println(sql);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			ResultSetMetaData md = rs.getMetaData();
			int columnCount = md.getColumnCount();
			while (rs.next()) {
				Map<String, String> map = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					map.put(md.getColumnName(i), rs.getString(i));
				}
				list.add(map);
			}
			rs.close();
			psmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static int update(Connection conn, String sql) {
		System.out.println(sql);
		int i = -1;
		PreparedStatement psmt = null;
		try {
			psmt = conn.prepareStatement(sql);
			i = psmt.executeUpdate();

			psmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("success update line : " + i);
		return i;
	}

}
