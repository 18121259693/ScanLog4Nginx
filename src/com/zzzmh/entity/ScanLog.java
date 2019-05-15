package com.zzzmh.entity;

public class ScanLog {

    private String filename;
    /** 0 成功 1 失败 */
    private Integer code;
    /** 运行情况信息 */
    private String msg;

    private Long success;
    private Long error;
    private Long cost;

    public ScanLog() {
    }

    public ScanLog(String filename, Integer code, String msg, Long success, Long error, Long cost) {
        this.filename = filename;
        this.code = code;
        this.msg = msg;
        this.success = success;
        this.error = error;
        this.cost = cost;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public Long getSuccess() {
        return success;
    }

    public void setSuccess(Long success) {
        this.success = success;
    }

    public Long getError() {
        return error;
    }

    public void setError(Long error) {
        this.error = error;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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
