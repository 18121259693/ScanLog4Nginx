/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-05-15 10:06:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_nginx_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_nginx_log`;
CREATE TABLE `sys_nginx_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(100) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `target` varchar(1000) DEFAULT NULL,
  `status` smallint(5) DEFAULT NULL,
  `cost` int(11) DEFAULT NULL,
  `referrer` varchar(200) DEFAULT NULL,
  `ua` varchar(1000) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
