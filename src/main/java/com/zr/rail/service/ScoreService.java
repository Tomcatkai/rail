package com.zr.rail.service;

import com.alibaba.fastjson.JSONObject;
import com.zr.rail.dao.DistanceDao;
import com.zr.rail.dao.ScoreDao;
import com.zr.rail.dao.SpeedDao;
import com.zr.rail.entity.Distance;
import com.zr.rail.entity.Score;
import com.zr.rail.entity.Speed;
import com.zr.rail.utils.ResultMsg;
import com.zr.rail.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class ScoreService {

    private final ScoreDao scoreDao;
    private final SpeedDao speedDao;
    private final DistanceDao distanceDao;

    @Autowired
    public ScoreService(ScoreDao scoreDao, SpeedDao speedDao, DistanceDao distanceDao) {
        this.scoreDao = scoreDao;
        this.speedDao = speedDao;
        this.distanceDao = distanceDao;
    }

    @Transactional
    public Map getScore(JSONObject jsonObject){
        //暂时没有userId,先写个1
        Long userId = 1L;
        //初始化结果
        HashMap result = new HashMap(5);
        //初始化观距,观速列表
        ArrayList distanceList = (ArrayList) jsonObject.get("distance");
        ArrayList speedList = (ArrayList) jsonObject.get("speed");
        //初始化观距,观速总分
        int distanceTotal = 0;
        int speedTotal = 0;
        //观距,观速次数限制为3次
        if(distanceList.size()!=3 ||speedList.size()!=3){
            return ResultUtils.error(ResultMsg.PARAM_IS_INVALID.msg());
        }
        //观距成绩计算
        for (Object distanceObj :distanceList){
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
        //观速成绩计算
        for (Object speedObj :speedList){
            //初始化分数
            int score = 0;
            LinkedHashMap map = (LinkedHashMap) speedObj;
            Integer viewSpeed = (Integer) map.get("viewSpeed");
            Integer realSpeed = (Integer) map.get("realSpeed");
            //观速,实速空校验
            if(viewSpeed==null||realSpeed==null){
                return ResultUtils.error(ResultMsg.PARAM_IS_BLANK.msg());
            }
            //实速0校验
            if(realSpeed==0){
                return ResultUtils.error(ResultMsg.PARAM_IS_INVALID.msg());
            }
            //观速实速差之绝对值
            int abs = Math.abs(viewSpeed-realSpeed);
            BigDecimal absDecimal = new BigDecimal(abs);
            BigDecimal realSpeedDecimal = new BigDecimal(realSpeed);
            //计算差与实距之比(误差)
            BigDecimal percent = absDecimal.divide(realSpeedDecimal,2,BigDecimal.ROUND_HALF_DOWN);
            if(percent.compareTo(new BigDecimal("0.05")) < 0){
                //误差小于5% 得满分
                score = 30;
                speedTotal += score;
            }else if (percent.compareTo(new BigDecimal("0.1")) < 0){
                //误差大于5%,小于10% 得15分
                score = 15;
                speedTotal += score;
            }else {
                //超过误差10% 不得分
                speedTotal += score;
            }
        }
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
            Integer viewDistance = (Integer) map.get("viewDistance");
            Integer realDistance = (Integer) map.get("realDistance");
            Distance distance = new Distance();
            distance.setDistanceView(viewDistance);
            distance.setDistanceReal(realDistance);
            distance.setDistanceScore(distanceTotal);
            distance.setDistanceScoreId(score.getScoreId());
            distanceDao.insert(distance);
        }
        for(Object speedObj :speedList){
            LinkedHashMap map = (LinkedHashMap) speedObj;
            Integer viewSpeed = (Integer) map.get("viewSpeed");
            Integer realSpeed = (Integer) map.get("realSpeed");
            Speed speed = new Speed();
            speed.setSpeedView(viewSpeed);
            speed.setSpeedReal(realSpeed);
            speed.setSpeedScore(speedTotal);
            speed.setSpeedScoreId(score.getScoreId());
            speedDao.insert(speed);
        }
        result.put("distanceTotal",distanceTotal);
        result.put("speedTotal",speedTotal);
        return ResultUtils.success(result);
    }


}
