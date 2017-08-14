/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50537
Source Host           : localhost:3306
Source Database       : ymhw

Target Server Type    : MYSQL
Target Server Version : 50537
File Encoding         : 65001

Date: 2016-02-15 14:44:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT '' COMMENT '活动名称',
  `time` datetime DEFAULT NULL COMMENT '活动时间',
  `location` varchar(100) DEFAULT '' COMMENT '集合地点',
  `qqGroup` varchar(20) DEFAULT '' COMMENT '联系QQ群',
  `cost` float DEFAULT NULL COMMENT '费用',
  `route` varchar(100) DEFAULT '' COMMENT '活动路线',
  `isValid` int(2) unsigned zerofill DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of activity
-- ----------------------------

-- ----------------------------
-- Table structure for contact
-- ----------------------------
DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `qq` varchar(15) DEFAULT '' COMMENT '公司QQ',
  `email` varchar(30) DEFAULT '' COMMENT '公司邮箱',
  `fixedphone` varchar(20) DEFAULT '' COMMENT '固定电话',
  `mobilephone` varchar(20) DEFAULT '' COMMENT '移动电话',
  `fax` varchar(15) DEFAULT '' COMMENT '公司传真',
  `isValid` int(2) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of contact
-- ----------------------------

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '反馈问题的内容',
  `title` varchar(50) DEFAULT '' COMMENT '问题标题',
  `content` longtext,
  `proposeTime` datetime DEFAULT NULL COMMENT '问题提出时间',
  `feedbackContent` longtext COMMENT '回答的内容',
  `feedbackTime` datetime DEFAULT NULL COMMENT '回答时间',
  `status` int(2) DEFAULT NULL COMMENT '处理状态 0-未处理 1-已处理',
  `isValid` int(2) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of feedback
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT '' COMMENT '角色名',
  `code` varchar(20) DEFAULT '' COMMENT '角色代码',
  `isValid` int(2) DEFAULT NULL COMMENT '是否有效： 0-无效 1-有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '管理员', 'admin', '1');
INSERT INTO `role` VALUES ('2', '客户', 'guest', '1');

-- ----------------------------
-- Table structure for strategy
-- ----------------------------
DROP TABLE IF EXISTS `strategy`;
CREATE TABLE `strategy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT '' COMMENT '攻略标题',
  `content` longtext COMMENT '攻略内容',
  `time` datetime DEFAULT NULL COMMENT '发布时间',
  `isValid` int(2) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of strategy
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(40) DEFAULT '' COMMENT '用户名、昵称',
  `password` varchar(32) DEFAULT '' COMMENT '密码',
  `email` varchar(30) DEFAULT '' COMMENT '邮箱',
  `telphone` varchar(20) DEFAULT '' COMMENT '电话号码',
  `roleId` int(11) DEFAULT NULL COMMENT '角色ID',
  `isValid` int(2) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`),
  KEY `fk_user_role` (`roleId`),
  CONSTRAINT `fk_user_role` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
