/*
 Navicat Premium Data Transfer

 Source Server         : 百度云服务器
 Source Server Type    : MySQL
 Source Server Version : 50646
 Source Host           : 106.13.189.205:3306
 Source Schema         : LyonBlog

 Target Server Type    : MySQL
 Target Server Version : 50646
 File Encoding         : 65001

 Date: 16/12/2019 13:33:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `user_id` bigint(20) NOT NULL,
  `role_id` tinyint(20) NOT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (2, 1);

SET FOREIGN_KEY_CHECKS = 1;
