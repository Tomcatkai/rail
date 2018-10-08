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
    private Double distanceReal;
    /**
     * 观距
     */
    private Double distanceView;
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

    public Double getDistanceReal() {
        return distanceReal;
    }

    public void setDistanceReal(Double distanceReal) {
        this.distanceReal = distanceReal;
    }

    public Double getDistanceView() {
        return distanceView;
    }

    public void setDistanceView(Double distanceView) {
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

    public Distance(Long distanceId, Double distanceReal, Double distanceView, Integer distanceScore, Long distanceScoreId) {
        this.distanceId = distanceId;
        this.distanceReal = distanceReal;
        this.distanceView = distanceView;
        this.distanceScore = distanceScore;
        this.distanceScoreId = distanceScoreId;
    }

    public Distance() {
    }
}
