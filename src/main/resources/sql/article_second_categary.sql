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

 Date: 15/12/2019 22:52:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article_second_categary
-- ----------------------------
DROP TABLE IF EXISTS `article_second_categary`;
CREATE TABLE `article_second_categary`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `mainId` int(10) NULL DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_secondary_category`(`mainId`) USING BTREE,
  CONSTRAINT `fk_secondary_category` FOREIGN KEY (`mainId`) REFERENCES `article_main_categary` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of article_second_categary
-- ----------------------------
INSERT INTO `article_second_categary` VALUES (1, 1, 'Java基础');
INSERT INTO `article_second_categary` VALUES (2, 1, 'Python基础');
INSERT INTO `article_second_categary` VALUES (3, 1, 'Java项目');
INSERT INTO `article_second_categary` VALUES (4, 1, 'Python项目');
INSERT INTO `article_second_categary` VALUES (5, 1, 'Spring');
INSERT INTO `article_second_categary` VALUES (6, 1, 'JVM');
INSERT INTO `article_second_categary` VALUES (7, 1, 'Socket');
INSERT INTO `article_second_categary` VALUES (8, 1, '多线程');
INSERT INTO `article_second_categary` VALUES (9, 1, '数据分析可视化');
INSERT INTO `article_second_categary` VALUES (10, 1, '爬虫');
INSERT INTO `article_second_categary` VALUES (11, 1, 'web前端');
INSERT INTO `article_second_categary` VALUES (12, 1, 'C/C++');
INSERT INTO `article_second_categary` VALUES (13, 2, 'MySQL');
INSERT INTO `article_second_categary` VALUES (14, 2, 'MongoDB');
INSERT INTO `article_second_categary` VALUES (15, 3, 'Tomcat');
INSERT INTO `article_second_categary` VALUES (16, 3, 'Netty');
INSERT INTO `article_second_categary` VALUES (17, 3, 'Redis');
INSERT INTO `article_second_categary` VALUES (18, 3, 'Elasticsearch');
INSERT INTO `article_second_categary` VALUES (19, 3, 'Docker');
INSERT INTO `article_second_categary` VALUES (20, 3, 'Nginx');
INSERT INTO `article_second_categary` VALUES (21, 4, 'Ubuntu');
INSERT INTO `article_second_categary` VALUES (22, 4, 'Linux');
INSERT INTO `article_second_categary` VALUES (23, 4, 'Windows');
INSERT INTO `article_second_categary` VALUES (24, 5, '计算机网络');
INSERT INTO `article_second_categary` VALUES (25, 5, '数据结构和算法');
INSERT INTO `article_second_categary` VALUES (26, 5, '计算机组成原理');
INSERT INTO `article_second_categary` VALUES (27, 5, '计算机操作系统');
INSERT INTO `article_second_categary` VALUES (28, 6, '基础概念');
INSERT INTO `article_second_categary` VALUES (29, 6, '目标检测');
INSERT INTO `article_second_categary` VALUES (30, 6, 'GAN');
INSERT INTO `article_second_categary` VALUES (31, 6, 'OpenCV');
INSERT INTO `article_second_categary` VALUES (32, 6, 'AI平台');
INSERT INTO `article_second_categary` VALUES (33, 7, '在线工具');
INSERT INTO `article_second_categary` VALUES (34, 8, '软件');
INSERT INTO `article_second_categary` VALUES (35, 8, '电影');
INSERT INTO `article_second_categary` VALUES (36, 8, '音乐');
INSERT INTO `article_second_categary` VALUES (37, 8, 'PDF书');
INSERT INTO `article_second_categary` VALUES (38, 8, '公开课');
INSERT INTO `article_second_categary` VALUES (39, 9, '个人感悟');
INSERT INTO `article_second_categary` VALUES (40, 9, '文章转载');
INSERT INTO `article_second_categary` VALUES (41, 9, '朝花夕拾');
INSERT INTO `article_second_categary` VALUES (42, 9, '寻常巷陌');
INSERT INTO `article_second_categary` VALUES (43, 9, '吃喝玩乐');

SET FOREIGN_KEY_CHECKS = 1;
