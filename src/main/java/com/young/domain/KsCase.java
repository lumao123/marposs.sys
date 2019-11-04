package com.young.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ks_case")
public class KsCase {
    private long caseID;
    private String companyName;//公司名称
    private Long subUser;//员工号
    private String replyUser;//处理人
    private String name;//姓名
    private String dept;//部门
    private String mobile;//分机号
    private String problem;//问题描述
    private String filePath;//附件了路径
    private String reply;//回复
    private Date insertTime;
    private int status;

    @Id
    @Column(name = "caseID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getCaseID() {
        return caseID;
    }

    public void setCaseID(long caseID) {
        this.caseID = caseID;
    }

    @Basic
    @Column(name = "dept")
    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "problem")
    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    @Basic
    @Column(name = "reply")
    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    @Basic
    @Column(name = "insertTime")
    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "subUser")
    public Long getSubUser() {
        return subUser;
    }

    public void setSubUser(Long subUser) {
        this.subUser = subUser;
    }

    @Basic
    @Column(name = "company")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "filePath")
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Basic
    @Column(name = "replyUser")
    public String getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(String replyUser) {
        this.replyUser = replyUser;
    }
}
