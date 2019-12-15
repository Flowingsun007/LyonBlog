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

 Date: 15/12/2019 22:52:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article_main_categary
-- ----------------------------
DROP TABLE IF EXISTS `article_main_categary`;
CREATE TABLE `article_main_categary`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of article_main_categary
-- ----------------------------
INSERT INTO `article_main_categary` VALUES (1, 'Code');
INSERT INTO `article_main_categary` VALUES (2, '数据库');
INSERT INTO `article_main_categary` VALUES (3, '服务器');
INSERT INTO `article_main_categary` VALUES (4, '操作系统');
INSERT INTO `article_main_categary` VALUES (5, '计算机科学');
INSERT INTO `article_main_categary` VALUES (6, '深度学习');
INSERT INTO `article_main_categary` VALUES (7, '工具');
INSERT INTO `article_main_categary` VALUES (8, '资源');
INSERT INTO `article_main_categary` VALUES (9, '生活');

SET FOREIGN_KEY_CHECKS = 1;
