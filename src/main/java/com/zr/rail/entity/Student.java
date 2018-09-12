package com.zr.rail.entity;

/**
 * @description: 学生表
 * @author: KaiZhang
 * @create: 2018-09-10 16:43
 **/
public class Student {
    /**
     * 学生id
     */
    private Long stuId;
    /**
     * 学生姓名
     */
    private String stuName;
    /**
     * 学生登录密码
     */
    private String stuPass;
    /**
     * 学号
     */
    private String stuNo;
    /**
     * 学生性别
     */
    private Integer stuSex;

    public Long getStuId() {
        return stuId;
    }

    public void setStuId(Long stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuPass() {
        return stuPass;
    }

    public void setStuPass(String stuPass) {
        this.stuPass = stuPass;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public Integer getStuSex() {
        return stuSex;
    }

    public void setStuSex(Integer stuSex) {
        this.stuSex = stuSex;
    }

    public Student(Long stuId, String stuName, String stuPass, String stuNo, Integer stuSex) {
        this.stuId = stuId;
        this.stuName = stuName;
        this.stuPass = stuPass;
        this.stuNo = stuNo;
        this.stuSex = stuSex;
    }
    public Student(){}

}
