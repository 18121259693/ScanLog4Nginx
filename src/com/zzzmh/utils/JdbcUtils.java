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

    public static final List<String> keywords = getMysql57Keyword();

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
            System.out.println(sql);
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

    public static List<String> getMysql57Keyword() {
        List<String> result = new ArrayList<>();
        List<Map<String, String>> query = query("select keyword from mysql57_keywords");
        for (Map<String, String> map : query) {
            if (map.get("keyword") != null) {
                result.add(map.get("keyword"));
            }
        }
        return result;
    }

    public static String getSqlByNginxLog(NginxLog nginxLog) {
        // 如果为空就插入null 允许所有字段为空 数据库会自动填充id和create_time
        StringBuffer sql = new StringBuffer();
        sql.append("insert into sys_nginx_log (");
        sql.append("ip,method,target,protocol,status,time,cost,referrer,ua");
        sql.append(") values (");
        sql.append(cleanFieldValue(nginxLog.getIp()) + ",");
        sql.append(cleanFieldValue(nginxLog.getMethod(), true) + ",");
        sql.append(cleanFieldValue(nginxLog.getTarget(), true) + ",");
        sql.append(cleanFieldValue(nginxLog.getProtocol(), true) + ",");
        sql.append(cleanFieldValue(nginxLog.getStatus()) + ",");
        sql.append(cleanFieldValue(nginxLog.getTime()) + ",");
        sql.append(cleanFieldValue(nginxLog.getCost()) + ",");
        sql.append(cleanFieldValue(nginxLog.getReferrer(), true) + ",");
        sql.append(cleanFieldValue(nginxLog.getUa(), true));
        sql.append(")");
        return sql.toString();
    }

    public static String cleanFieldValue(Integer value) {
        return value == null ? "null" : "'" + value.toString() + "'";
    }

    public static String cleanFieldValue(Date value) {
        return value == null ? "null" : "'" + CommonUtils.dateFormat(value) + "'";
    }

    public static String cleanFieldValue(String value) {
        return cleanFieldValue(value, false);
    }

    public static String cleanFieldValue(String value, boolean danger) {
        String result = "";
        if (value == null) {
            result = "null";
        } else {
            if (value.startsWith("\"")) {
                value = value.substring(1);
            }
            if (value.endsWith("\"")) {
                value = value.substring(0, value.length() - 1);
            }
            if (danger) {
                for (String k : keywords) {
                    value = value.replaceAll("(?i)" + k, "'" + k + "'");
                }
            }
            result = "'" + value.replaceAll("'", "\"").replaceAll("\"", "\\\"") + "'";
        }
        return result;
    }

    public static void saveScanLog(ScanLog scanLog) {
        try {
            String sql = "insert into sys_scan_log (filename,code,msg,success,error,cost) values (" +
                    "'" + scanLog.getFilename() + "'," +
                    "'" + scanLog.getCode() + "'," +
                    "'" + scanLog.getMsg() + "'," +
                    "'" + scanLog.getSuccess() + "'," +
                    "'" + scanLog.getError() + "'," +
                    "'" + scanLog.getCost() + "'" +
                    ")";
            int update = update(sql);
            if (update == -1) {
                throw new Exception("插入扫描日志失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
