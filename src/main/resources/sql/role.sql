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

 Date: 15/12/2019 22:55:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `roleid` tinyint(20) NOT NULL,
  `role` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色描述',
  PRIMARY KEY (`roleid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'blogAdmin', '系统创建者');
INSERT INTO `role` VALUES (2, 'blogManager', '博客管理员');
INSERT INTO `role` VALUES (3, 'consumer', 'VIP会员');
INSERT INTO `role` VALUES (4, 'register', '注册用户');
INSERT INTO `role` VALUES (5, 'register_unchecked', '未激活注册用户');
INSERT INTO `role` VALUES (6, 'staff', '普通游客');

SET FOREIGN_KEY_CHECKS = 1;
