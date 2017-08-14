/*
Navicat MySQL Data Transfer

Source Server         : localconn
Source Server Version : 50172
Source Host           : localhost:3306
Source Database       : ymhw

Target Server Type    : MYSQL
Target Server Version : 50172
File Encoding         : 65001

Date: 2016-04-12 21:41:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(30) DEFAULT '' COMMENT '活动标题（控制在30字以内）',
  `property` int(11) DEFAULT NULL COMMENT '活动性质',
  `content` tinytext COMMENT '活动内容ID列表',
  `desc` varchar(150) DEFAULT '' COMMENT '简要描述（限制在150内）',
  `gatherPlace` varchar(100) DEFAULT '' COMMENT '集合地点',
  `departurePlace` varchar(100) DEFAULT '' COMMENT '出发地',
  `destination` varchar(100) DEFAULT '' COMMENT '目的地  ',
  `publishTime` datetime DEFAULT NULL COMMENT '活动发布时间',
  `startTime` datetime DEFAULT NULL COMMENT '活动开始时间  ',
  `endTime` datetime DEFAULT NULL COMMENT '活动结束时间',
  `deadline` datetime DEFAULT NULL COMMENT '活动截止时间  ',
  `gatherTime` datetime DEFAULT NULL COMMENT '活动集结时间  ',
  `intensityLevel` int(11) DEFAULT NULL COMMENT '活动强度级别  ',
  `scenicSpot` varchar(50) DEFAULT '' COMMENT '活动景点',
  `totolNum` int(11) DEFAULT NULL COMMENT '所需总人数',
  `enteredNum` int(11) DEFAULT NULL COMMENT '已经报名的人数 ',
  `confirmedNum` int(11) DEFAULT NULL COMMENT '已确认的人数 ',
  `contactnumber` char(12) DEFAULT '' COMMENT '联系电话',
  `qqGroup` varchar(20) DEFAULT '' COMMENT '联系QQ群 ',
  `cost` float DEFAULT NULL COMMENT '价格、费用   ',
  `discount` float DEFAULT NULL COMMENT '优惠后的价格 ',
  `routeDesc` longtext COMMENT '线路描述',
  `travelPlan` longtext COMMENT '行程安排',
  `requiredEquip` longtext COMMENT '装备要求   ',
  `notice` longtext COMMENT '报名须知  ',
  `pic` longtext COMMENT '活动的图片路径(base64的字符串)',
  `takers` tinytext COMMENT '报名的人员ID列表（以英文状态下的“,”分割）',
  `leaders` tinytext COMMENT '领队ID列表（多个时以“,”分割）',
  `top` int(11) DEFAULT NULL COMMENT '是否置顶 1-置顶',
  `password` varchar(32) DEFAULT '' COMMENT '密码',
  `isValid` int(2) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`),
  KEY `fk_pro` (`property`),
  CONSTRAINT `fk_pro` FOREIGN KEY (`property`) REFERENCES `activityproperty` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of activity
-- ----------------------------

-- ----------------------------
-- Table structure for activityitem
-- ----------------------------
DROP TABLE IF EXISTS `activityitem`;
CREATE TABLE `activityitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT '' COMMENT '活动内容名称',
  `isValid` int(1) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of activityitem
-- ----------------------------

-- ----------------------------
-- Table structure for activityproperty
-- ----------------------------
DROP TABLE IF EXISTS `activityproperty`;
CREATE TABLE `activityproperty` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '活动性质id',
  `name` varchar(50) DEFAULT '' COMMENT '性质名称',
  `isValid` int(1) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of activityproperty
-- ----------------------------
INSERT INTO `activityproperty` VALUES ('1', '休闲', '1');
INSERT INTO `activityproperty` VALUES ('2', '乡村游', '1');
INSERT INTO `activityproperty` VALUES ('3', '徒步', '1');
INSERT INTO `activityproperty` VALUES ('4', '露营', '1');
INSERT INTO `activityproperty` VALUES ('5', '烧烤', '1');
INSERT INTO `activityproperty` VALUES ('6', '摄影', '1');
INSERT INTO `activityproperty` VALUES ('7', '滑雪', '1');
INSERT INTO `activityproperty` VALUES ('8', '登山', '1');
INSERT INTO `activityproperty` VALUES ('9', '探险', '1');
INSERT INTO `activityproperty` VALUES ('10', '骑行', '1');
INSERT INTO `activityproperty` VALUES ('11', '攀岩', '1');
INSERT INTO `activityproperty` VALUES ('12', '背包', '1');
INSERT INTO `activityproperty` VALUES ('13', '其他', '1');

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
  CONSTRAINT `fk_user_role` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'ws7896', 'e10adc3949ba59abbe56e057f20f883e', '1502718072@qq.com', '18727721920', '2', '1');
