package com.zr.rail.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zr.rail.dao.DistanceDao;
import com.zr.rail.dao.ScoreDao;
import com.zr.rail.dao.SpeedDao;
import com.zr.rail.dao.StudentDao;
import com.zr.rail.entity.Distance;
import com.zr.rail.entity.Score;
import com.zr.rail.entity.Speed;
import com.zr.rail.entity.Student;
import com.zr.rail.service.ScoreService;
import com.zr.rail.utils.Constants;
import com.zr.rail.utils.ResultMsg;
import com.zr.rail.utils.ResultUtils;
import com.zr.rail.utils.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description: 成绩相关Service
 * @author: KaiZhang
 * @create: 2018-09-11 18:33
 **/
@Service
public class ScoreServiceImpl implements ScoreService {

    private final ScoreDao scoreDao;
    private final SpeedDao speedDao;
    private final DistanceDao distanceDao;
    private final StudentDao studentDao;

    @Autowired
    public ScoreServiceImpl(ScoreDao scoreDao, SpeedDao speedDao, DistanceDao distanceDao, StudentDao studentDao) {
        this.scoreDao = scoreDao;
        this.speedDao = speedDao;
        this.distanceDao = distanceDao;
        this.studentDao = studentDao;
    }


    /**
     * 学生注册方法
     * @param student 学生
     * @return 成功;失败
     */
    @Override
    public Map stuRegister(Student student){
        if(studentDao.getOneFromSno(student.getStuNo())!=null){
            return ResultUtils.error(ResultMsg.USER_HAS_EXISTED.msg());
        }
        studentDao.insert(student);
       if(student.getStuId()!=null){
           return ResultUtils.success(ResultMsg.SUCCESS.msg());
       }
       return ResultUtils.error(ResultMsg.FAILED.msg());
    }

    /**
     * 获取成绩接口
     * @param jsonObject 前台传入的Json对象
     * @return 处理结果
     */
    @Override
    public Map getScore(JSONObject jsonObject, Long stuId){
        //初始化结果
        HashMap<String, Integer> result = new HashMap<>(5);
        //初始化观距,观速列表
        ArrayList distanceList = (ArrayList) jsonObject.get("distance");
        ArrayList speedList = (ArrayList) jsonObject.get("speed");
        //计算观距成绩
        HashMap distanceScore = (HashMap) calculateScore(distanceList,"distance");
        if(Constants.FAILED.equals(distanceScore.get(Constants.FLAG))){
            return ResultUtils.error((String) distanceScore.get("msg"));
        }
        int distanceTotal = (int) distanceScore.get("total");
        ArrayList distanceScoreList = (ArrayList) distanceScore.get("scoreList");
        //计算观速成绩
        HashMap speedScore = (HashMap) calculateScore(speedList,"speed");
        if(Constants.FAILED.equals(speedScore.get(Constants.FLAG))){
            return ResultUtils.error((String) speedScore.get("msg"));
        }
        int speedTotal = (int) speedScore.get("total");
        ArrayList speedScoreList = (ArrayList) speedScore.get("scoreList");
        //存储成绩信息
        storeScore(distanceTotal,speedTotal, stuId,distanceList,speedList,distanceScoreList,speedScoreList);
        //构建返回结果
        result.put("distanceTotal",distanceTotal);
        result.put("speedTotal",speedTotal);
        return ResultUtils.success(result);
    }

    /**
     * 学生登录
     * @param stuNo 学号
     * @param passWord 密码
     * @return 结果
     */
    @Override
    public Map stuLogin(String stuNo, String passWord){
        Student student = studentDao.getOneFromSno(stuNo);
        HashMap result = new HashMap(3);
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
//            Cookie cookie = new Cookie("token",token);
//            cookie.setPath("/");
//            response.addCookie(cookie);
            result.put("token",token);
            return ResultUtils.success(result);
        }
        return ResultUtils.error(ResultMsg.USER_PASS_WRONG.msg());
    }

    /**
     * 成绩计算方法
     * @param list 观距或观速的list
     * @param type 观距还是观速    观距:distance   观速:speed
     * @return 正确: flag: suc    total 总成绩  scoreList 每次观测成绩
     *          错误: flag: failed msg:错误信息
     */
    private Map<String, java.io.Serializable> calculateScore(ArrayList list, String type){
        //初始化总分和结果集
        int total = 0;
        ArrayList<Integer> scoreList = new ArrayList<>();
        HashMap<String, java.io.Serializable> result = new HashMap<>(3);
        //成绩计算
        for (Object obj :list){
            //初始化分数
            int score = 0;
            LinkedHashMap map = (LinkedHashMap) obj;
            Integer view;
            Integer real;
            if("speed".equals(type)){
                view = Integer.parseInt((String) map.get("viewSpeed"));
                real = Integer.parseInt((String) map.get("realSpeed"));
            }else if("distance".equals(type)){
                view = Integer.parseInt((String) map.get("viewDistance"));
                real = Integer.parseInt((String) map.get("realDistance"));
            }else {
                result.put(Constants.FLAG, Constants.FAILED);
                result.put("msg",ResultMsg.PARAM_INSIDE_WRONG.msg());
                return result;
            }
///            //观测,实际空校验
//            if(view==null||real==null){
//                result.put(Constants.FLAG,Constants.FAILED);
//                result.put("msg",ResultMsg.PARAM_IS_BLANK.msg());
//                return result;
//            }
            //实际0校验
            if(real==0){
                result.put(Constants.FLAG,Constants.FAILED);
                result.put("msg",ResultMsg.PARAM_IS_INVALID.msg());
                return result;
            }
            //观测实际差之绝对值
            int abs = Math.abs(view-real);
            BigDecimal absDecimal = new BigDecimal(abs);
            BigDecimal realDecimal = new BigDecimal(real);
            //计算差与实际之比(误差)
            BigDecimal percent = absDecimal.divide(realDecimal,2,BigDecimal.ROUND_HALF_DOWN);
            if(percent.compareTo(new BigDecimal("0.05")) < 0){
                //误差小于5% 得满分
                score = 30;
                total += score;
            }else if (percent.compareTo(new BigDecimal("0.1")) < 0){
                //误差大于5%,小于10% 得15分
                score = 15;
                total += score;
            }else {
                //超过误差10% 不得分
                total += score;
            }
            scoreList.add(score);
        }
        result.put(Constants.FLAG,Constants.SUCCESS);
        result.put("total",total);
        result.put("scoreList",scoreList);
        return result;
    }

    /**
     * 存储成绩信息
     * @param distanceTotal 观距成绩
     * @param speedTotal  观速成绩
     * @param userId  用户id
     * @param distanceList  观距明细
     * @param speedList 观速明细
     */
    @Transactional(rollbackFor = Exception.class)
    public void storeScore(int distanceTotal,int speedTotal,Long userId,ArrayList distanceList,
                           ArrayList speedList,ArrayList distanceScoreList,ArrayList speedScoreList){
        //存储成绩信息,获得成绩id
        //存储各次观距,实距,得分情况,对应成绩id
        //存储各次观速,实速,得分情况,对应成绩id
        Score score = new Score();
        score.setScoreDistance(distanceTotal);
        score.setScoreSpeed(speedTotal);
        score.setScoreTotal(speedTotal+distanceTotal);
        score.setScoreUserId(userId);
        scoreDao.insert(score);
        for(Object distanceObj :distanceList){
            LinkedHashMap map = (LinkedHashMap) distanceObj;
            Integer viewDistance = Integer.parseInt((String) map.get("viewDistance"));
            Integer realDistance = Integer.parseInt((String) map.get("realDistance"));
            Distance distance = new Distance();
            distance.setDistanceView(viewDistance);
            distance.setDistanceReal(realDistance);
            int curIndex = distanceList.indexOf(distanceObj);
            distance.setDistanceScore((Integer) distanceScoreList.get(curIndex));
            distance.setDistanceScoreId(score.getScoreId());
            distanceDao.insert(distance);
        }
        for(Object speedObj :speedList){
            LinkedHashMap map = (LinkedHashMap) speedObj;
            Integer viewSpeed = Integer.parseInt((String) map.get("viewSpeed"));
            Integer realSpeed = Integer.parseInt((String) map.get("realSpeed"));
            Speed speed = new Speed();
            speed.setSpeedView(viewSpeed);
            speed.setSpeedReal(realSpeed);
            int curIndex = speedList.indexOf(speedObj);
            speed.setSpeedScore((Integer) speedScoreList.get(curIndex));
            speed.setSpeedScoreId(score.getScoreId());
            speedDao.insert(speed);
        }
    }

}
