/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.7.20-log : Database - bbf-api
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bbf-api` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;

USE `bbf-api`;

/*Table structure for table `appdemoinfo` */

DROP TABLE IF EXISTS `appdemoinfo`;

CREATE TABLE `appdemoinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `appId` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'app编号',
  `appName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名字',
  `url` text NOT NULL COMMENT 'url',
  `body` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT 'body',
  `contentType` text COMMENT 'contentType',
  `header` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT 'header',
  `formData` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT 'formData',
  `method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'method',
  `mockInfo` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT 'mock信息',
  `mocked` int(11) DEFAULT NULL COMMENT '是否mock调试',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `ext1` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展字段1',
  `ext2` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展字段2',
  `updateDate` datetime NOT NULL COMMENT '更新时间',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_appId` (`appId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='app展示信息';

/*Data for the table `appdemoinfo` */

insert  into `appdemoinfo`(`id`,`appId`,`appName`,`url`,`body`,`contentType`,`header`,`formData`,`method`,`mockInfo`,`mocked`,`status`,`ext1`,`ext2`,`updateDate`,`createdDate`) values (1,'bc23b10fe1804b4aa6e36b6b3d784cd0',NULL,'http://bbfapi.com:8080/api/rundApi/bc23b10fe1804b4aa6e36b6b3d784cd0?ssoToken=8a6723d4-9e8e-4991-b91e-e5f3a31b6c81','\"{\\\"name\\\":\\\"jim\\\",\\\"age\\\":100}\"','{\"key\":\"Content-Type\",\"value\":\"application/json\"}','[]','[]','POST','{\"scriptText\":\"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\",\"scriptType\":\"\",\"themeType\":\"\"}',0,NULL,NULL,NULL,'2019-12-16 19:14:23','2019-12-16 17:18:42');

/*Table structure for table `appinfo` */

DROP TABLE IF EXISTS `appinfo`;

CREATE TABLE `appinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `appId` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'app编号',
  `appName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'app名字',
  `appPath` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '路径',
  `checkPara` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '检查配置',
  `appPara` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '参数',
  `action` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'post|get等',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '备注',
  `version` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '版本',
  `userId` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '配置人id',
  `userName` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '配置人名字',
  `systemCode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '系统编码',
  `systemName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '系统名字',
  `status` int(10) DEFAULT NULL COMMENT '状态',
  `userIds` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '组员id',
  `lastModifyUser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '最后修改者',
  `ext1` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展字段1',
  `ext2` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展字段2',
  `updateDate` datetime NOT NULL COMMENT '更新时间',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_appId` (`appId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='app配置';

/*Data for the table `appinfo` */

insert  into `appinfo`(`id`,`appId`,`appName`,`appPath`,`checkPara`,`appPara`,`action`,`remark`,`version`,`userId`,`userName`,`systemCode`,`systemName`,`status`,`userIds`,`lastModifyUser`,`ext1`,`ext2`,`updateDate`,`createdDate`) values (1,'bc23b10fe1804b4aa6e36b6b3d784cd0','网关测试','','[]','','','网关测试','','test@163.com','test@163.com','student','学生网关',1,'网关测试','test@163.com',NULL,NULL,'2019-12-16 17:13:14','2019-12-16 00:00:00');

/*Table structure for table `jvmscriptinfo` */

DROP TABLE IF EXISTS `jvmscriptinfo`;

CREATE TABLE `jvmscriptinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `scriptId` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '脚本编号',
  `scriptName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '脚本作用名字',
  `scriptText` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '脚本内容',
  `scriptType` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'case脚本语言类型',
  `appId` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'apiId',
  `version` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '版本',
  `indexOrder` int(11) DEFAULT NULL COMMENT '排序',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `ext1` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展字段1',
  `ext2` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展字段2',
  `updateDate` datetime NOT NULL COMMENT '更新时间',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  `demoInputPara` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '演示脚本的输入参数',
  `themeType` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '脚本样式',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_scriptId` (`scriptId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='app脚本配置';

/*Data for the table `jvmscriptinfo` */

insert  into `jvmscriptinfo`(`id`,`scriptId`,`scriptName`,`scriptText`,`scriptType`,`appId`,`version`,`indexOrder`,`status`,`ext1`,`ext2`,`updateDate`,`createdDate`,`demoInputPara`,`themeType`,`remark`) values (1,'72297dc4cdea427f8ecbafa8c748e9c9','js-请求demo','$p.log.info(\'-----request,name={},age={}\', s.httpBody.name,s.httpBody.age)\r\nheader = {}\r\nheader[\'Content-Type\'] = \'application/x-www-form-urlencoded\'\r\nhttpParaMap = {}\r\nhttpParaMap.httpMethod = \'POST\'\r\nhttpParaMap.httpPara = s.httpBody\r\nhttpParaMap.httpHeader = header\r\nresponse = $p.http.call(\r\n            \"http://bbf-api/test/page/postFormData\",httpParaMap);\r\n$p.log.info(\'-----response={}\',response)\r\nreturn response','javascript','bc23b10fe1804b4aa6e36b6b3d784cd0','',NULL,NULL,NULL,NULL,'2019-12-16 18:19:22','2019-12-16 17:56:00','{\"name\":\"jim\",\"age\":100}','clouds_midnight','js-请求demo');

/*Table structure for table `jvmscriptinfohistory` */

DROP TABLE IF EXISTS `jvmscriptinfohistory`;

CREATE TABLE `jvmscriptinfohistory` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `scriptId` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '脚本编号',
  `scriptName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '脚本作用名字',
  `scriptText` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '脚本内容',
  `scriptType` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'case脚本语言类型',
  `appId` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'apiId',
  `version` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '版本',
  `indexOrder` int(11) DEFAULT NULL COMMENT '排序',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `ext1` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展字段1',
  `ext2` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展字段2',
  `updateDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `createdDate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='app脚本配置历史记录';

/*Data for the table `jvmscriptinfohistory` */

/*Table structure for table `jvmscriptinfohistory1` */

DROP TABLE IF EXISTS `jvmscriptinfohistory1`;

CREATE TABLE `jvmscriptinfohistory1` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `scriptId` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '脚本编号',
  `scriptName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '脚本作用名字',
  `scriptText` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '脚本内容',
  `scriptType` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'case脚本语言类型',
  `appId` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'apiId',
  `version` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '版本',
  `indexOrder` int(11) DEFAULT NULL COMMENT '排序',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `ext1` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展字段1',
  `ext2` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展字段2',
  `updateDate` datetime NOT NULL COMMENT '更新时间',
  `createdDate` datetime NOT NULL COMMENT '创建时间',
  `demoInputPara` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '演示脚本的输入参数',
  `themeType` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '脚本样式',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='app脚本配置';

/*Data for the table `jvmscriptinfohistory1` */

insert  into `jvmscriptinfohistory1`(`id`,`scriptId`,`scriptName`,`scriptText`,`scriptType`,`appId`,`version`,`indexOrder`,`status`,`ext1`,`ext2`,`updateDate`,`createdDate`,`demoInputPara`,`themeType`,`remark`) values (2,'72297dc4cdea427f8ecbafa8c748e9c9','js-请求demo','requestpara = {}\r\nrequestpara.age = \'10\'\r\nrequestpara.name = \'jim\'\r\nheader = {}\r\nheader[\'Content-Type\'] = \'application/x-www-form-urlencoded\'\r\nhttpParaMap = {}\r\nhttpParaMap.httpMethod = \'POST\'\r\nhttpParaMap.httpPara = requestpara\r\nhttpParaMap.httpHeader = header\r\nresponse = $p.http.call(\r\n            \"http://bbf-api/test/page/postFormData\",httpParaMap);\r\n$p.log.info(\'-----response={}\',response)\r\nreturn response','javascript','bc23b10fe1804b4aa6e36b6b3d784cd0','',NULL,NULL,NULL,NULL,'2019-12-16 18:05:40','2019-12-16 18:05:40','{\"name\":\"jim\",\"age\":10}','clouds_midnight','js-请求demo'),(3,'72297dc4cdea427f8ecbafa8c748e9c9','js-请求demo','$p.log.info(\'-----request,name={},age={}\', s.httpPara.name, s.httpPara.age)\r\nheader = {}\r\nheader[\'Content-Type\'] = \'application/x-www-form-urlencoded\'\r\nhttpParaMap = {}\r\nhttpParaMap.httpMethod = \'POST\'\r\nhttpParaMap.httpPara = s.httpPara\r\nhttpParaMap.httpHeader = header\r\nresponse = $p.http.call(\r\n            \"http://bbf-api/test/page/postFormData\",httpParaMap);\r\n$p.log.info(\'-----response={}\',response)\r\nreturn response','javascript','bc23b10fe1804b4aa6e36b6b3d784cd0','',NULL,NULL,NULL,NULL,'2019-12-16 18:16:40','2019-12-16 18:16:40','{\"name\":\"jim\",\"age\":10}','clouds_midnight','js-请求demo'),(4,'72297dc4cdea427f8ecbafa8c748e9c9','js-请求demo','$p.log.info(\'-----request,name={},age={}\', s.httpBody.name,s.httpBody.age)\r\nheader = {}\r\nheader[\'Content-Type\'] = \'application/x-www-form-urlencoded\'\r\nhttpParaMap = {}\r\nhttpParaMap.httpMethod = \'POST\'\r\nhttpParaMap.httpPara = s.httpPara\r\nhttpParaMap.httpHeader = header\r\nresponse = $p.http.call(\r\n            \"http://bbf-api/test/page/postFormData\",httpParaMap);\r\n$p.log.info(\'-----response={}\',response)\r\nreturn response','javascript','bc23b10fe1804b4aa6e36b6b3d784cd0','',NULL,NULL,NULL,NULL,'2019-12-16 18:19:22','2019-12-16 18:19:22','{\"name\":\"jim\",\"age\":\"10\"}','clouds_midnight','js-请求demo');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
