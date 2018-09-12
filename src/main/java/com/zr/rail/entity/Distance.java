package com.zr.rail.entity;

/**
 * @description: 观距实体类
 * @author: KaiZhang
 * @create: 2018-09-11 14:59
 **/
public class Distance {
    /**
     * 本次观距id
     */
    private Long distanceId;
    /**
     * 实距
     */
    private Integer distanceReal;
    /**
     * 观距
     */
    private Integer distanceView;
    /**
     * 本次观距成绩
     */
    private Integer distanceScore;
    /**
     * 本次观距对应成绩id
     */
    private Long distanceScoreId;

    public Long getDistanceId() {
        return distanceId;
    }

    public void setDistanceId(Long distanceId) {
        this.distanceId = distanceId;
    }

    public Integer getDistanceReal() {
        return distanceReal;
    }

    public void setDistanceReal(Integer distanceReal) {
        this.distanceReal = distanceReal;
    }

    public Integer getDistanceView() {
        return distanceView;
    }

    public void setDistanceView(Integer distanceView) {
        this.distanceView = distanceView;
    }

    public Integer getDistanceScore() {
        return distanceScore;
    }

    public void setDistanceScore(Integer distanceScore) {
        this.distanceScore = distanceScore;
    }

    public Long getDistanceScoreId() {
        return distanceScoreId;
    }

    public void setDistanceScoreId(Long distanceScoreId) {
        this.distanceScoreId = distanceScoreId;
    }

    public Distance(Long distanceId, Integer distanceReal, Integer distanceView, Integer distanceScore, Long distanceScoreId) {
        this.distanceId = distanceId;
        this.distanceReal = distanceReal;
        this.distanceView = distanceView;
        this.distanceScore = distanceScore;
        this.distanceScoreId = distanceScoreId;
    }

    public Distance() {
    }
}
