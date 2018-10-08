package com.zr.rail.entity;

/**
 * @description: 观速实体类
 * @author: KaiZhang
 * @create: 2018-09-11 15:12
 **/
public class Speed {
    /**
     * 本次观速id
     */
    private Long speedId;
    /**
     * 实速
     */
    private Double speedReal;
    /**
     * 观速
     */
    private Double speedView;
    /**
     * 本次观速成绩
     */
    private Integer speedScore;
    /**
     * 本次观速对应成绩id
     */
    private Long speedScoreId;

    public Long getSpeedId() {
        return speedId;
    }

    public void setSpeedId(Long speedId) {
        this.speedId = speedId;
    }

    public Double getSpeedReal() {
        return speedReal;
    }

    public void setSpeedReal(Double speedReal) {
        this.speedReal = speedReal;
    }

    public Double getSpeedView() {
        return speedView;
    }

    public void setSpeedView(Double speedView) {
        this.speedView = speedView;
    }

    public Integer getSpeedScore() {
        return speedScore;
    }

    public void setSpeedScore(Integer speedScore) {
        this.speedScore = speedScore;
    }

    public Long getSpeedScoreId() {
        return speedScoreId;
    }

    public void setSpeedScoreId(Long speedScoreId) {
        this.speedScoreId = speedScoreId;
    }

    public Speed(Long speedId, Double speedReal, Double speedView, Integer speedScore, Long speedScoreId) {
        this.speedId = speedId;
        this.speedReal = speedReal;
        this.speedView = speedView;
        this.speedScore = speedScore;
        this.speedScoreId = speedScoreId;
    }

    public Speed() {
    }
}
