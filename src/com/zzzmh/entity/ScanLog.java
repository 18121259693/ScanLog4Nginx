package com.zzzmh.entity;

public class ScanLog {
    /** 0 成功 1 失败 */
    private Integer code;
    /** 运行情况信息 */
    private String msg;

    public ScanLog() {
    }

    public ScanLog(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ScanLog{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
