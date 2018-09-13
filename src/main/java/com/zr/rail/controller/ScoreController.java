package com.zr.rail.controller;


import com.alibaba.fastjson.JSONObject;
import com.zr.rail.entity.Student;
import com.zr.rail.dao.StudentDao;
import com.zr.rail.service.ScoreService;
import com.zr.rail.utils.Constants;
import com.zr.rail.utils.ResultMsg;
import com.zr.rail.utils.ResultUtils;
import com.zr.rail.utils.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
    @RequestMapping(value = "/hello", method = RequestMethod.POST)
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
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map login(@RequestParam(value = "stuNo") String stuNo,
                     @RequestParam(value = "passWord") String passWord,
                     HttpServletResponse response){
        Student student = studentDao.getOneFromSno(stuNo);
        if (student==null){
            return ResultUtils.error(ResultMsg.USER_NOT_EXIST.msg());
        }
        String pass = student.getStuPass();
        if(pass.isEmpty()){
            return ResultUtils.error(ResultMsg.USER_PASS_BLANK.msg());
        }
        if(pass.equals(passWord)){
            //登录成功,将token设置到cookie中
            JwtUtil jwt = new JwtUtil();
            String subject = JwtUtil.generalSubject(student);
            String token = null;
            try {
                token = jwt.createJWT(Constants.JWT_ID,subject,Constants.JWT_REFRESH_TTL);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtils.error("创建token失败");
            }
            Cookie cookie = new Cookie("token",token);
            cookie.setPath("/");
            response.addCookie(cookie);
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
    public Map getScore(@RequestBody JSONObject jsonObject,@CookieValue("token") String token){
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
