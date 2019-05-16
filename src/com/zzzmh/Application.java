package com.zzzmh;

import com.zzzmh.entity.NginxLog;
import com.zzzmh.entity.ScanLog;
import com.zzzmh.utils.CommonUtils;
import com.zzzmh.utils.JdbcUtils;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 主方法入口
 * 实现Nginx日志扫描分析并存数据库
 *
 * @anthor zmh
 * @date 2019-5-15 12:29:37
 */
public class Application {
    /**
     * 正则
     */
    private static final String PATTERN = CommonUtils.get("pattern");
    /**
     * 基本文件名( 结尾不带 -yyyyMMdd )
     */
    private static final String FILENAME = CommonUtils.get("baseFileName");
    /**
     * 文件路径( 结尾带 斜杠 )
     */
    private static final String LOGPATH = CommonUtils.get("logpath");

    /**
     * 扫描日志文件存数据库
     *
     * @param startDate 起始日期 例如 20190101
     * @param endDate   结束日期 例如 20190430
     */
    public void ScannerAccessLog(String startDate, String endDate) {
        Date start = CommonUtils.dateParse(startDate, "yyyyMMdd");
        Date end = CommonUtils.dateParse(endDate, "yyyyMMdd");
        while (start.getTime() <= end.getTime()) {
            startDate = CommonUtils.dateFormat(start, "yyyyMMdd");
            ScannerAccessLog(startDate);
            start = CommonUtils.getNextDay(start);
        }

    }

    /**
     * 扫描日志文件存数据库
     *
     * @param date 指定单个日期 例如 20190101
     */
    public void ScannerAccessLog(String date) {
        String path = LOGPATH + FILENAME + "-" + date;
        File file = new File(path);
        if (file.exists()) {
            new Thread4Scaner(file).start();
        }
    }

    class Thread4Scaner extends Thread {
        private File file;

        public Thread4Scaner(File file) {
            this.file = file;
        }

        @Override
        public void run() {
            try {
                ScannerFile(file);
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }

        /**
         * 扫描日志文件 主方法
         * 在进此方法之前要先判断过file是非空且存在
         * <p>
         * 这里用Scanner 实现扫描 可以一行一行扫进内存
         * 扫完的不占用内存，可防止文件过大内存溢出
         *
         * @param file 传入File
         */

        public void ScannerFile(File file) {
            System.out.println("开始解析:" + file.getName());
            long success = 0, error = 0, cost = System.currentTimeMillis();
            try {
                // 这里是一个特殊的时间格式 所以单独声明到这里
                SimpleDateFormat sdf = new SimpleDateFormat("[dd/MMM/yyyy:HH:mm:ss Z]", Locale.ENGLISH);
                FileInputStream inputStream = new FileInputStream(file);
                Scanner sc = new Scanner(inputStream, "UTF-8");
                Pattern p = Pattern.compile(PATTERN);
                Matcher m = null;
                Connection conn = JdbcUtils.getConn();
                while (sc.hasNextLine()) {
                    try {
                        m = p.matcher(sc.nextLine());
                        if (m.find()) {
                            NginxLog nginxLog = new NginxLog();
                            nginxLog.setIp(m.group(1));
                            nginxLog.setTime(sdf.parse(m.group(2)));
                            // 正则有个坑 GET / HTTP/1.1 3个字段一起归纳进m.group(3)了，本人正则辣鸡，就用笨办法拆分了
                            String str = m.group(3);
                            String method = null, target = null, prorocol = null;
                            if (CommonUtils.isNotBlank(str)) {
                                try {
                                    if (str.contains(" ") && str.indexOf(" ") < 50) {
                                        method = str.substring(0, str.indexOf(" "));
                                        if (str.indexOf(" ") != str.lastIndexOf(" ")
                                                && str.length() - str.lastIndexOf(" ") < 50) {
                                            target = str.substring(str.indexOf(" "), str.lastIndexOf(" "));
                                            prorocol = str.substring(str.lastIndexOf(" "), str.length());
                                        } else {
                                            target = str.substring(str.indexOf(" "), str.length());
                                        }
                                    } else {
                                        target = str != null && str.length() > 10000 ? str.substring(0, 10000) : str;
                                    }
                                    if (method != null) {
                                        method = method.trim();
                                    }
                                    if (target != null) {
                                        target = target.trim();
                                    }
                                    if (prorocol != null) {
                                        prorocol = prorocol.trim();
                                    }
                                } catch (Exception e) {
                                    System.out.println("str:" + str);
                                    e.printStackTrace();
                                }
                            }
                            nginxLog.setMethod(method);
                            nginxLog.setTarget(target);
                            nginxLog.setProtocol(prorocol);
                            nginxLog.setStatus(m.group(4) == null ? null : Integer.valueOf(m.group(4)));
                            nginxLog.setCost(m.group(5) == null ? null : Integer.valueOf(m.group(5)));
                            nginxLog.setReferrer(m.group(6));
                            nginxLog.setUa(m.group(7));
                            String sql = JdbcUtils.getSqlByNginxLog(nginxLog);
                            int update = JdbcUtils.update(conn, sql);
                            if (update == -1) {
                                error++;
                            } else {
                                success++;
                            }
                        } else {
                            error++;
                        }
                    } catch (Exception e) {
                        error++;
//                    e.printStackTrace();
                    } catch (Throwable t) {
                        error++;
//                    t.printStackTrace();
                    }
                }
                cost = System.currentTimeMillis() - cost;
                JdbcUtils.saveScanLog(new ScanLog(file.getName(), 0, "success", success, error, cost));
                conn.close();
                inputStream.close();
                sc.close();
            } catch (Exception e) {
                cost = System.currentTimeMillis() - cost;
                JdbcUtils.saveScanLog(new ScanLog(file.getName(), 1, "error:" + e.getMessage(), success, error, cost));
                e.printStackTrace();
            }
            System.out.println("结束解析:" + file.getName());
        }
    }


    public static void main(String[] args) {
        long temp = System.currentTimeMillis();
        new Application().ScannerAccessLog("20190301", "20190515");
        System.out.println("运行结束 消耗时间:" + ((System.currentTimeMillis() - temp) / 1000.0) + "秒");
    }
}
