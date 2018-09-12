package com.zr.rail.controller;


import com.alibaba.fastjson.JSONObject;
import com.zr.rail.entity.Student;
import com.zr.rail.dao.StudentDao;
import com.zr.rail.service.ScoreService;
import com.zr.rail.utils.ResultMsg;
import com.zr.rail.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.*;

/**
 * @description: 测试Controller
 * @author: KaiZhang
 * @create: 2018-09-10 13:34
 **/
@RestController
public class ScoreController {
    private final StudentDao studentDao;
    private final ScoreService scoreService;

    @Autowired
    public ScoreController(StudentDao studentDao, ScoreService scoreService) {
        this.studentDao = studentDao;
        this.scoreService = scoreService;
    }

    /**
     * 测试方法
     * @return HelloWorld
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String index(){
        List<Student> list = studentDao.getAll();
        System.out.println(list);
        return "Hello World!";
    }

    /**
     * 用户登录方法
     * @param stuNo 学号
     * @param passWord 密码
     * @return 返回
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public Map login(@RequestParam(value = "stuNo") String stuNo,
                     @RequestParam(value = "passWord") String passWord){
        Student student = studentDao.getOneFromSno(stuNo);
        if (student==null){
            return ResultUtils.error(ResultMsg.USER_NOT_EXIST.msg());
        }
        String pass = student.getStuPass();
        if(pass.isEmpty()){
            return ResultUtils.error(ResultMsg.USER_PASS_BLANK.msg());
        }
        if(pass.equals(passWord)){
            return ResultUtils.success(ResultMsg.SUCCESS.msg());
        }
        return ResultUtils.error(ResultMsg.USER_PASS_WRONG.msg());
    }

    /**
     * 获取成绩接口
     * @param jsonObject 各项参数
     * @return 得分结果
     */
    @RequestMapping(value = "/score",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public Map getScore(@RequestBody JSONObject jsonObject){
        return scoreService.getScore(jsonObject);
    }
}
