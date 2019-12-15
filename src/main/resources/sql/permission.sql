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

 Date: 15/12/2019 22:55:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `permissionid` tinyint(20) NOT NULL,
  `permission` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`permissionid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 'admin:delete', '删除文章操作');
INSERT INTO `permission` VALUES (2, 'admin:editBlog', '编辑文章内容');
INSERT INTO `permission` VALUES (3, 'admin:editTag', '修改文章标签');
INSERT INTO `permission` VALUES (4, 'article:changeCategory', '修改文章分类');
INSERT INTO `permission` VALUES (5, 'admin:home', '后台管理首页');
INSERT INTO `permission` VALUES (6, 'article:comment', '评论文章');
INSERT INTO `permission` VALUES (7, 'behavior:thank', '感谢文章');
INSERT INTO `permission` VALUES (8, 'article:collect', '收藏文章');
INSERT INTO `permission` VALUES (9, 'article:share', '分享文章');
INSERT INTO `permission` VALUES (10, 'article:single', '浏览某篇文章');
INSERT INTO `permission` VALUES (11, 'article:category', '浏览分类文章');
INSERT INTO `permission` VALUES (12, 'user:userInfo', '取用户登录信息');
INSERT INTO `permission` VALUES (13, 'behavior:uploadImage', '上传图片');
INSERT INTO `permission` VALUES (14, 'behavior:elasticSearch', '关键字文章搜索');

SET FOREIGN_KEY_CHECKS = 1;
