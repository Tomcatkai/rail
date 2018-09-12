package com.zr.rail.controller;


import com.alibaba.fastjson.JSONObject;
import com.zr.rail.entity.Student;
import com.zr.rail.dao.StudentDao;
import com.zr.rail.utils.ResultMsg;
import com.zr.rail.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.util.*;

/**
 * @description: 测试Controller
 * @author: KaiZhang
 * @create: 2018-09-10 13:34
 **/
@RestController
public class HelloWorldController {
    private final StudentDao studentDao;

    @Autowired
    public HelloWorldController(StudentDao studentDao) {
        this.studentDao = studentDao;
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
        //初始化结果
        HashMap result = new HashMap(5);
        ArrayList distanceList = (ArrayList) jsonObject.get("distance");
        //观距次数限制为3次
        if(distanceList.size()!=3){
            return ResultUtils.error(ResultMsg.PARAM_IS_INVALID.msg());
        }
        //初始化观距总分
        int distanceTotal = 0;
        for (Object distanceObj :distanceList ){
            //初始化分数
            int score = 0;
            LinkedHashMap map = (LinkedHashMap) distanceObj;
            Integer viewDistance = (Integer) map.get("viewDistance");
            Integer realDistance = (Integer) map.get("realDistance");
            //观距,实距空校验
            if(viewDistance==null||realDistance==null){
                return ResultUtils.error(ResultMsg.PARAM_IS_BLANK.msg());
            }
            //实距0校验
            if(realDistance==0){
                return ResultUtils.error(ResultMsg.PARAM_IS_INVALID.msg());
            }
            //观距实距差之绝对值
            int abs = Math.abs(viewDistance-realDistance);
            BigDecimal absDecimal = new BigDecimal(abs);
            BigDecimal realDistanceDecimal = new BigDecimal(realDistance);
            //计算差与实距之比(误差)
            BigDecimal percent = absDecimal.divide(realDistanceDecimal,2,BigDecimal.ROUND_HALF_DOWN);
            if(percent.compareTo(new BigDecimal("0.05")) < 0){
                //误差小于5% 得满分
                score = 30;
                distanceTotal += score;
            }else if (percent.compareTo(new BigDecimal("0.1")) < 0){
                //误差大于5%,小于10% 得15分
                score = 15;
                distanceTotal += score;
            }else {
                //超过误差10% 不得分
                distanceTotal += score;
            }
        }
        result.put("distanceTotal",distanceTotal);
        ArrayList speedList = (ArrayList) jsonObject.get("speed");
        if(speedList.size()!=3){
            return ResultUtils.error(ResultMsg.PARAM_IS_INVALID.msg());
        }
        for (Object speedObj :speedList ){
            LinkedHashMap map = (LinkedHashMap) speedObj;
            System.out.println(map.get("viewSpeed"));
            System.out.println(map.get("realSpeed"));
        }




        return ResultUtils.success("suc");
    }
}
