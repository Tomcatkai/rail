package com.zr.rail.controller;


import com.alibaba.fastjson.JSONObject;
import com.zr.rail.entity.Student;
import com.zr.rail.dao.StudentDao;
import com.zr.rail.service.ScoreService;
import com.zr.rail.utils.ResultMsg;
import com.zr.rail.utils.ResultUtils;
import com.zr.rail.utils.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @description: ScoreController
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
    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String index(){
        List<Student> list = studentDao.getAll();
        System.out.println(list);
        return "Hello World!";
    }

    /**
     * 用户注册
     * @param stuName 学生姓名
     * @param stuNo 学生学号
     * @param stuPass 学生密码
     * @param stuSex 学生性别
     * @return suc msg
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Map userRegister(@RequestParam(value = "stuName") String stuName,
                           @RequestParam(value = "stuNo") String stuNo,
                           @RequestParam(value = "stuPass") String stuPass,
                           @RequestParam(value = "stuSex") Integer stuSex){
        if(stuNo.isEmpty()){
            return ResultUtils.error(ResultMsg.STU_NO_BLANK.msg());
        }
        Student student = new Student();
        student.setStuName(stuName);
        student.setStuNo(stuNo);
        student.setStuPass(stuPass);
        student.setStuSex(stuSex);
        return scoreService.stuRegister(student);
    }

    /**
     * 用户登录方法
     * @param stuNo 学号
     * @param passWord 密码
     * @return 返回
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map login(@RequestParam(value = "stuNo") String stuNo,
                     @RequestParam(value = "passWord") String passWord,
                     HttpServletResponse response){
        return scoreService.stuLogin(stuNo,passWord,response);
    }

    /**
     * 获取成绩接口
     * @param jsonObject 各项参数
     * @return 得分结果
     */
    @RequestMapping(value = "/score",method = RequestMethod.POST)
    public Map getScore(@RequestBody JSONObject jsonObject){
        if(jsonObject.get("token")==null){
            return ResultUtils.error("token为空");
        }
        ArrayList list = (ArrayList) jsonObject.get("token");
        HashMap hashMap = (HashMap) list.get(0);
        if(hashMap==null){
            return ResultUtils.error("token为空");
        }
        String token = (String) hashMap.get("token");
        if(token.isEmpty()){
            return ResultUtils.error("token为空");
        }
        JwtUtil jwt = new JwtUtil();
        try {
            Claims claims = jwt.parseJWT(token);
            String studentStr = claims.getSubject();
            JSONObject student = JSONObject.parseObject(studentStr);
            Long stuId = student.getLong("stuId");
            if(stuId==null){
                return ResultUtils.error("身份校验失败,请重新登录");
            }
            return scoreService.getScore(jsonObject,stuId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtils.error("token为空,请登录后再访问");
        }
    }
}
