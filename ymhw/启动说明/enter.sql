/*
Navicat MySQL Data Transfer

Source Server         : ymhwConn
Source Server Version : 50622
Source Host           : ymhw2016.gotoip2.com:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50622
File Encoding         : 65001

Date: 2016-05-15 17:08:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for enter
-- ----------------------------
DROP TABLE IF EXISTS `enter`;
CREATE TABLE `enter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pass` varchar(20) DEFAULT '' COMMENT '密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of enter
-- ----------------------------
INSERT INTO `enter` VALUES ('1', '123456');
