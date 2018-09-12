package com.zr.rail.dao;

import com.zr.rail.entity.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 学生Mapper
 * @author: KaiZhang
 * @create: 2018-09-10 17:23
 **/
@Repository
public interface StudentDao {

    /**
     * 查询所有学生
     * @return 学生list
     */
    @Select("SELECT * FROM tbl_student")
    @Results({
            @Result(property = "stuId",   column = "stu_id"),
            @Result(property = "stuName", column = "stu_name"),
            @Result(property = "stuPass", column = "stu_pass"),
            @Result(property = "stuNo",   column = "stu_no"),
            @Result(property = "stuSex",  column = "stu_sex")
    })
    List<Student> getAll();

    /**
     * 通过学生id查询学生
     * @param stuId id
     * @return 学生
     */
    @Select("SELECT * FROM tbl_student WHERE stu_id = #{stuId}")
    @Results({
            @Result(property = "stuId",   column = "stu_id"),
            @Result(property = "stuName", column = "stu_name"),
            @Result(property = "stuPass", column = "stu_pass"),
            @Result(property = "stuNo",   column = "stu_no"),
            @Result(property = "stuSex",  column = "stu_sex")
    })
    Student getOneFromId(Long stuId);

    /**
     * 使用学号查询学生
     * @param stuNo 学号
     * @return 学生
     */
    @Select("SELECT * FROM tbl_student WHERE stu_no = #{stuNo}")
    @Results({
            @Result(property = "stuId",   column = "stu_id"),
            @Result(property = "stuName", column = "stu_name"),
            @Result(property = "stuPass", column = "stu_pass"),
            @Result(property = "stuNo",   column = "stu_no"),
            @Result(property = "stuSex",  column = "stu_sex")
    })
    Student getOneFromSno(String stuNo);

    /**
     * 插入学生信息
     * @param student 学生对象
     */
    @Insert("INSERT INTO tbl_student(stu_name,stu_pass,stu_no,stu_sex) VALUES(#{stuName}, #{stuPass}, #{stuNo}, #{stuSex})")
    void insert(Student student);

    /**
     * 更新学生信息
     * @param student 学生
     */
    @Update("UPDATE tbl_student SET stu_name=#{stuName},stu_pass=#{stuPass},stu_no=#{stuNo},stu_sex=#{stuSex} WHERE stu_id =#{stuId}")
    void update(Student student);

    /**
     * 删除学生信息
     * @param stuId 学生id
     */
    @Delete("DELETE FROM tbl_student WHERE stu_id =#{stuId}")
    void delete(Long stuId);

}
