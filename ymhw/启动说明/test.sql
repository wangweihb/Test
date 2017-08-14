/*
Navicat MySQL Data Transfer

Source Server         : ymhwConn
Source Server Version : 50622
Source Host           : ymhw2016.gotoip2.com:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50622
File Encoding         : 65001

Date: 2016-05-11 00:22:31
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

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `xj` varchar(40) DEFAULT '' COMMENT '学籍号',
  `name` varchar(40) DEFAULT '' COMMENT '学生姓名',
  `sex` int(1) DEFAULT '0' COMMENT '0-男生 1-女生',
  `school` varchar(40) DEFAULT '' COMMENT '毕业学校',
  `district` varchar(40) DEFAULT '' COMMENT '户口所在地（区或县）',
  `address` varchar(40) DEFAULT '' COMMENT '家庭住址（居住地址）',
  `fname` varchar(30) DEFAULT '' COMMENT '父亲姓名',
  `fwork` varchar(40) DEFAULT '' COMMENT '父亲工作单位及职务',
  `ftel` varchar(13) DEFAULT '' COMMENT '父亲联系电话',
  `mname` varchar(40) DEFAULT '' COMMENT '母亲姓名',
  `mwork` varchar(40) DEFAULT '' COMMENT '母亲工作单位及职务',
  `mtel` varchar(13) DEFAULT '' COMMENT '母亲联系电话',
  `jname` varchar(40) DEFAULT '' COMMENT '监护人姓名',
  `jwork` varchar(40) DEFAULT '' COMMENT '监护人工作单位及职务',
  `jtel` varchar(13) DEFAULT '' COMMENT '监护人联系电话',
  `xqtc` varchar(40) DEFAULT '' COMMENT '兴趣特长',
  `hjqk` varchar(40) DEFAULT '' COMMENT '学生在校期间获奖情况',
  `tyzp` varchar(40) DEFAULT '' COMMENT '五六年级体育总评成绩',
  `zwpj` varchar(40) DEFAULT '' COMMENT '学生自我评价',
  `bcsm` varchar(50) DEFAULT '' COMMENT '补充说明（50个字内）',
  `other` varchar(50) DEFAULT '' COMMENT '其他信息',
  `zmcl` varchar(40) DEFAULT '' COMMENT '证明材料文件路径',
  `isValid` int(2) DEFAULT '0' COMMENT '有效状态 0-无效 1-有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
