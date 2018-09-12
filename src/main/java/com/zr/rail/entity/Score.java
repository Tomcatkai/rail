package com.zr.rail.entity;

/**
 * @description: 成绩实体类
 * @author: KaiZhang
 * @create: 2018-09-11 14:52
 **/
public class Score {
    /**
     * 分数id
     */
    private Long scoreId;
    /**
     * 分数所属用户id
     */
    private Long scoreUserId;
    /**
     * 分数_观距分数
     */
    private Integer scoreDistance;
    /**
     * 分数_观速分数
     */
    private Integer scoreSpeed;
    /**
     * 总分
     */
    private Integer scoreTotal;

    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public Long getScoreUserId() {
        return scoreUserId;
    }

    public void setScoreUserId(Long scoreUserId) {
        this.scoreUserId = scoreUserId;
    }

    public Integer getScoreDistance() {
        return scoreDistance;
    }

    public void setScoreDistance(Integer scoreDistance) {
        this.scoreDistance = scoreDistance;
    }

    public Integer getScoreSpeed() {
        return scoreSpeed;
    }

    public void setScoreSpeed(Integer scoreSpeed) {
        this.scoreSpeed = scoreSpeed;
    }

    public Integer getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(Integer scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public Score() {
    }

    public Score(Long scoreId, Long scoreUserId, Integer scoreDistance, Integer scoreSpeed, Integer scoreTotal) {
        this.scoreId = scoreId;
        this.scoreUserId = scoreUserId;
        this.scoreDistance = scoreDistance;
        this.scoreSpeed = scoreSpeed;
        this.scoreTotal = scoreTotal;
    }
}
