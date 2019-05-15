package com.zzzmh.entity;

import java.util.Date;

public class NginxLog {
    private String ip;
    private Date time;
    private String target;
    private Integer status;
    private Integer cost;
    private String referrer;
    private String ua;

    public NginxLog(String ip, Date time, String target, Integer status, Integer cost, String referrer, String ua) {
        this.ip = ip;
        this.time = time;
        this.target = target;
        this.status = status;
        this.cost = cost;
        this.referrer = referrer;
        this.ua = ua;
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
