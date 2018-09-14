package com.zr.rail.service;

import com.alibaba.fastjson.JSONObject;
import com.zr.rail.entity.Student;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @description: 成绩相关Service
 * @author: KaiZhang
 * @create: 2018-09-11 18:33
 **/
public interface ScoreService {
    /**
     * 学生注册方法
     * @param student 学生
     * @return 成功;失败
     */
    Map stuRegister(Student student);

    /**
     * 获取成绩接口
     * @param jsonObject 前台传入的Json对象
     * @return 处理结果
     */
    Map getScore(JSONObject jsonObject, Long stuId);

    /**
     * 学生登录
     * @param stuNo 学号
     * @param passWord 密码
     * @param response 响应
     * @return 处理结果
     */
    Map stuLogin(String stuNo, String passWord, HttpServletResponse response);
}
