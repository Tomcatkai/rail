package com.zr.rail.dao;

import com.zr.rail.entity.Speed;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SpeedDao {
    /**
     * 使用所属的成绩id查询speedList
     * @param speedScoreId 本次观速所属的成绩id
     * @return 观速list
     */
    @Select("SELECT * FROM tbl_speed WHERE speed_score_id = #{speedScoreId}")
    @Results({
            @Result(property = "speedId",   column = "speed_id"),
            @Result(property = "speedReal", column = "speed_real"),
            @Result(property = "speedView", column = "speed_view"),
            @Result(property = "speedScore",   column = "speed_score"),
            @Result(property = "speedScoreId",  column = "speed_score_id")
    })
    List<Speed> getSpeedList(String speedScoreId);

    /**
     * 插入观速记录
     * @param speed 本次观速实体对象
     */
    @Insert("INSERT INTO tbl_speed(speed_real,speed_view,speed_score,speed_score_id) VALUES(#{speedReal}, #{speedView}, #{speedScore}, #{speedScoreId})")
    void insert(Speed speed);

    /**
     * 删除观速信息
     * @param speedScoreId 观速所属的speedScoreId
     */
    @Delete("DELETE FROM tbl_speed WHERE speed_score_id =#{speedScoreId}")
    void delete(Long speedScoreId);
}
