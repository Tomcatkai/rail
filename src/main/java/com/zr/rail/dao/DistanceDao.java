package com.zr.rail.dao;

import com.zr.rail.entity.Distance;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 观距Dao
 * @author: KaiZhang
 **/
@Repository
public interface DistanceDao {
    /**
     * 使用所属的成绩id查询distanceList
     * @param distanceScoreId 本次观距所属的成绩id
     * @return 观距list
     */
    @Select("SELECT * FROM tbl_distance WHERE distance_score_id = #{distanceScoreId}")
    @Results({
            @Result(property = "distanceId",   column = "distance_id"),
            @Result(property = "distanceReal", column = "distance_real"),
            @Result(property = "distanceView", column = "distance_view"),
            @Result(property = "distanceScore",   column = "distance_score"),
            @Result(property = "distanceScoreId",  column = "distance_score_id")
    })
    List<Distance> getDistanceList(String distanceScoreId);

    /**
     * 插入观距记录
     * @param distance 本次观距实体对象
     */
    @Insert("INSERT INTO tbl_distance(distance_real,distance_view,distance_score,distance_score_id) VALUES(#{distanceReal}, #{distanceView}, #{distanceScore}, #{distanceScoreId})")
    void insert(Distance distance);

    /**
     * 删除观距信息
     * @param distanceScoreId 观距所属的scoreId
     */
    @Delete("DELETE FROM tbl_distance WHERE distance_score_id =#{distanceScoreId}")
    void delete(Long distanceScoreId);
}
