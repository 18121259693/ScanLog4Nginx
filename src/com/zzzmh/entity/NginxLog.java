package com.zzzmh.entity;

import java.util.Date;

public class NginxLog {
    /** ip地址 */
    private String ip;
    /** 请求时间 */
    private Date time;
    /** GET POST */
    private String method;
    /** 请求目标路径 */
    private String target;
    /** HTTP1.1/2.0 */
    private String protocol;
    /** 请求HTTP状态码 */
    private Integer status;
    /** 响应消耗时间(毫秒) */
    private Integer cost;
    /** 请求来源 */
    private String referrer;
    /** User Agent */
    private String ua;



    public NginxLog() {

    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    @Override
    public String toString() {
        return "NginxLog{" +
                "ip='" + ip + '\'' +
                ", time=" + time +
                ", target='" + target + '\'' +
                ", status=" + status +
                ", cost=" + cost +
                ", referrer='" + referrer + '\'' +
                ", ua='" + ua + '\'' +
                '}';
    }
}
