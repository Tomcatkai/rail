/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50640
 Source Host           : localhost:3306
 Source Schema         : db_rail

 Target Server Type    : MySQL
 Target Server Version : 50640
 File Encoding         : 65001

 Date: 13/09/2018 09:47:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_distance
-- ----------------------------
DROP TABLE IF EXISTS `tbl_distance`;
CREATE TABLE `tbl_distance`  (
  `distance_id` bigint(100) NOT NULL AUTO_INCREMENT COMMENT '观距id',
  `distance_real` int(100) NULL DEFAULT NULL COMMENT '实距',
  `distance_view` int(100) NULL DEFAULT NULL COMMENT '观距',
  `distance_score` int(100) NULL DEFAULT NULL COMMENT '本次观距成绩',
  `distance_score_id` bigint(100) NULL DEFAULT NULL COMMENT '观距对应成绩id',
  PRIMARY KEY (`distance_id`) USING BTREE,
  INDEX `FK_distance_score`(`distance_score_id`) USING BTREE,
  CONSTRAINT `FK_distance_score` FOREIGN KEY (`distance_score_id`) REFERENCES `tbl_score` (`score_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tbl_score
-- ----------------------------
DROP TABLE IF EXISTS `tbl_score`;
CREATE TABLE `tbl_score`  (
  `score_id` bigint(100) NOT NULL AUTO_INCREMENT COMMENT '成绩id',
  `score_user_id` bigint(100) NOT NULL COMMENT '成绩所属用户id',
  `score_distance` int(100) NULL DEFAULT NULL COMMENT '观距总成绩',
  `score_speed` int(100) NULL DEFAULT NULL COMMENT '观速总成绩',
  `score_total` int(100) NULL DEFAULT NULL COMMENT '总成绩',
  PRIMARY KEY (`score_id`) USING BTREE,
  INDEX `FK_score_user_id`(`score_user_id`) USING BTREE,
  CONSTRAINT `FK_score_user_id` FOREIGN KEY (`score_user_id`) REFERENCES `tbl_student` (`stu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tbl_speed
-- ----------------------------
DROP TABLE IF EXISTS `tbl_speed`;
CREATE TABLE `tbl_speed`  (
  `speed_id` bigint(100) NOT NULL AUTO_INCREMENT COMMENT '观速id',
  `speed_real` int(100) NULL DEFAULT NULL COMMENT '实速',
  `speed_view` int(100) NULL DEFAULT NULL COMMENT '观速',
  `speed_score` int(100) NULL DEFAULT NULL COMMENT '本次观速成绩',
  `speed_score_id` bigint(100) NULL DEFAULT NULL COMMENT '观速对应成绩id',
  PRIMARY KEY (`speed_id`) USING BTREE,
  INDEX `FK_speed_score_id`(`speed_score_id`) USING BTREE,
  CONSTRAINT `FK_speed_score_id` FOREIGN KEY (`speed_score_id`) REFERENCES `tbl_score` (`score_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for tbl_student
-- ----------------------------
DROP TABLE IF EXISTS `tbl_student`;
CREATE TABLE `tbl_student`  (
  `stu_id` bigint(100) NOT NULL AUTO_INCREMENT COMMENT '学生id',
  `stu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生姓名',
  `stu_pass` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生登录密码',
  `stu_no` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学生学号',
  `stu_sex` tinyint(10) NULL DEFAULT NULL COMMENT '学生性别：0： 女 ；1：男',
  PRIMARY KEY (`stu_id`) USING BTREE,
  UNIQUE INDEX `Unique_sno`(`stu_no`) USING BTREE COMMENT '学号唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
