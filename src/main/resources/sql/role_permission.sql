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

 Date: 15/12/2019 22:55:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `role_id` tinyint(20) NOT NULL,
  `permission_id` tinyint(20) NOT NULL,
  UNIQUE INDEX `role_permission_nuique`(`role_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 1);
INSERT INTO `role_permission` VALUES (1, 2);
INSERT INTO `role_permission` VALUES (1, 3);
INSERT INTO `role_permission` VALUES (1, 4);
INSERT INTO `role_permission` VALUES (1, 5);
INSERT INTO `role_permission` VALUES (1, 6);
INSERT INTO `role_permission` VALUES (1, 7);
INSERT INTO `role_permission` VALUES (1, 8);
INSERT INTO `role_permission` VALUES (1, 9);
INSERT INTO `role_permission` VALUES (1, 10);
INSERT INTO `role_permission` VALUES (1, 11);
INSERT INTO `role_permission` VALUES (1, 12);
INSERT INTO `role_permission` VALUES (1, 13);
INSERT INTO `role_permission` VALUES (1, 14);
INSERT INTO `role_permission` VALUES (2, 3);
INSERT INTO `role_permission` VALUES (2, 5);
INSERT INTO `role_permission` VALUES (2, 6);
INSERT INTO `role_permission` VALUES (2, 7);
INSERT INTO `role_permission` VALUES (2, 8);
INSERT INTO `role_permission` VALUES (2, 9);
INSERT INTO `role_permission` VALUES (2, 10);
INSERT INTO `role_permission` VALUES (2, 11);
INSERT INTO `role_permission` VALUES (2, 12);
INSERT INTO `role_permission` VALUES (2, 13);
INSERT INTO `role_permission` VALUES (2, 14);
INSERT INTO `role_permission` VALUES (3, 6);
INSERT INTO `role_permission` VALUES (3, 7);
INSERT INTO `role_permission` VALUES (3, 8);
INSERT INTO `role_permission` VALUES (3, 9);
INSERT INTO `role_permission` VALUES (3, 10);
INSERT INTO `role_permission` VALUES (3, 11);
INSERT INTO `role_permission` VALUES (3, 12);
INSERT INTO `role_permission` VALUES (3, 13);
INSERT INTO `role_permission` VALUES (3, 14);
INSERT INTO `role_permission` VALUES (4, 6);
INSERT INTO `role_permission` VALUES (4, 7);
INSERT INTO `role_permission` VALUES (4, 8);
INSERT INTO `role_permission` VALUES (4, 9);
INSERT INTO `role_permission` VALUES (4, 10);
INSERT INTO `role_permission` VALUES (4, 11);
INSERT INTO `role_permission` VALUES (4, 12);
INSERT INTO `role_permission` VALUES (4, 13);
INSERT INTO `role_permission` VALUES (4, 14);
INSERT INTO `role_permission` VALUES (5, 9);
INSERT INTO `role_permission` VALUES (5, 10);
INSERT INTO `role_permission` VALUES (5, 11);
INSERT INTO `role_permission` VALUES (5, 12);
INSERT INTO `role_permission` VALUES (5, 14);
INSERT INTO `role_permission` VALUES (6, 9);
INSERT INTO `role_permission` VALUES (6, 10);
INSERT INTO `role_permission` VALUES (6, 11);
INSERT INTO `role_permission` VALUES (6, 12);

SET FOREIGN_KEY_CHECKS = 1;
