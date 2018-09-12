package com.zr.rail.dao;

import com.zr.rail.entity.Score;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreDao {

    /**
     * 插入成绩信息
     * @param score 成绩对象
     */
    @Insert("INSERT INTO tbl_score(score_user_id,score_distance,score_speed,score_total) VALUES(#{scoreUserId}, #{scoreDistance}, #{scoreSpeed}, #{scoreTotal})")
    @Options(useGeneratedKeys = true, keyProperty = "scoreId", keyColumn="score_id")
    void insert(Score score);

}
