package com.zr.rail.dao;

import com.zr.rail.entity.Score;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreDao {

//    /**
//     * 通过学生id查询该学生的所有成绩列表
//     * @param stuId id
//     * @return 学生
//     */
//    @Select("SELECT * FROM tbl_score WHERE score_user_id = #{scoreUserId}")
//    @Results({
//            @Result(property = "scoreId",   column = "score_id"),
//            @Result(property = "scoreUserId", column = "score_user_id"),
//            @Result(property = "scoreDistance", column = "score_distance"),
//            @Result(property = "scoreSpeed",   column = "score_speed"),
//            @Result(property = "scoreTotal",  column = "score_total")
//    })
//    Score getOneFromId(Long stuId);


    /**
     * 插入成绩信息
     * @param score 成绩对象
     */
    @Insert("INSERT INTO tbl_score(score_user_id,score_distance,score_speed,score_total) VALUES(#{scoreUserId}, #{scoreDistance}, #{scoreSpeed}, #{scoreTotal})")
    @Options(useGeneratedKeys = true, keyProperty = "scoreId", keyColumn="score_id")
    void insert(Score score);


//    /**
//     * 删除学生信息
//     * @param stuId 学生id
//     */
//    @Delete("DELETE FROM tbl_student WHERE stu_id =#{stuId}")
//    void delete(Long stuId);
}
