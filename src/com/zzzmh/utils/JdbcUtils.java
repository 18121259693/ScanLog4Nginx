package com.zzzmh.utils;

import com.zzzmh.entity.NginxLog;
import com.zzzmh.entity.ScanLog;

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
            Class.forName(CommonUtils.get("JdbcDriver"));
            conn = DriverManager.getConnection(CommonUtils.get("JdbcUrl"),
                    CommonUtils.get("JdbcUserName"), CommonUtils.get("JdbcPassWord"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static List<Map<String, String>> query(Connection conn, String sql) {
        CommonUtils.print("sql:" + sql);
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
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
                result.add(map);
            }
            CommonUtils.print("result:" + result);
            rs.close();
            psmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Map<String, String>> query(String sql) {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        try {
            Connection conn = getConn();
            result = query(conn, sql);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int update(Connection conn, String sql) {
        CommonUtils.print("sql:" + sql);
        int result = -1;
        PreparedStatement psmt = null;
        try {
            psmt = conn.prepareStatement(sql);
            result = psmt.executeUpdate();
            CommonUtils.print("result:" + result);
            psmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int update(String sql) {
        int result = -1;
        try {
            Connection conn = getConn();
            result = update(conn, sql);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getSqlByNginxLog(NginxLog nginxLog) {
        // 如果为空就插入null 允许所有字段为空 数据库会自动填充id和create_time
        StringBuffer sql = new StringBuffer();
        sql.append("insert into sys_nginx_log (");
        sql.append("ip,target,status,time,cost,referrer,ua");
        sql.append(") values (");
        sql.append(CommonUtils.isNotNull(nginxLog.getIp()) ? "'" + nginxLog.getIp() + "'," : "null,");
        sql.append(CommonUtils.isNotNull(nginxLog.getTarget()) ? "'" + nginxLog.getTarget() + "'," : "null,");
        sql.append(CommonUtils.isNotNull(nginxLog.getStatus()) ? "'" + nginxLog.getStatus() + "'," : "null,");
        sql.append(CommonUtils.isNotNull(nginxLog.getTime()) ? "'" + CommonUtils.dateFormat(nginxLog.getTime()) + "'," : "null,");
        sql.append(CommonUtils.isNotNull(nginxLog.getCost()) ? "'" + nginxLog.getCost() + "'," : "null,");
        sql.append(CommonUtils.isNotNull(nginxLog.getReferrer()) ? "'" + nginxLog.getReferrer() + "'," : "null,");
        sql.append(CommonUtils.isNotNull(nginxLog.getUa()) ? "'" + nginxLog.getUa() + "'" : "null");
        sql.append(")");
        return sql.toString();
    }

    public static void saveScanLog(ScanLog scanLog) {
        String sql = "insert into sys_scan_log (code,msg) values (" + scanLog.getCode() + ",'" + scanLog.getMsg() + "')";
        update(sql);
    }

    public static void main(String[] args) {
        NginxLog nginxLog = new NginxLog();
        nginxLog.setIp("0.0.0.0");
        nginxLog.setTime(new Date());
        nginxLog.setCost(10000);
        nginxLog.setTarget("/js/index.js");
        nginxLog.setReferrer("https://zzzmh.cn");
        nginxLog.setStatus(200);
        nginxLog.setUa("XXXX/Window 10 - Chrome 73.xx.xx");
        String sql = getSqlByNginxLog(nginxLog);
        int update = update(sql);
    }
}
