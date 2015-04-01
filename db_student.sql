/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.6.14-enterprise-commercial-advanced : Database - db_student
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_student` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db_student`;

/*Table structure for table `t_course` */

DROP TABLE IF EXISTS `t_course`;

CREATE TABLE `t_course` (
  `cid` char(3) NOT NULL COMMENT '课程号',
  `name` varchar(30) DEFAULT NULL COMMENT '课程名',
  `credit` float DEFAULT NULL COMMENT '学分',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_course` */

insert  into `t_course`(`cid`,`name`,`credit`) values ('1','数据库',3),('2','PS基础',2),('3','讨厌的毛概',3),('4','计算机网络',3),('9','Dota2基础',4);

/*Table structure for table `t_sc` */

DROP TABLE IF EXISTS `t_sc`;

CREATE TABLE `t_sc` (
  `sid` char(5) NOT NULL COMMENT '学号',
  `cid` char(3) NOT NULL COMMENT '课程号',
  `grade` float DEFAULT NULL COMMENT '成绩',
  PRIMARY KEY (`sid`,`cid`),
  KEY `FK_t_sc2` (`cid`),
  CONSTRAINT `FK_t_sc` FOREIGN KEY (`sid`) REFERENCES `t_student` (`sid`) ON UPDATE CASCADE,
  CONSTRAINT `FK_t_sc2` FOREIGN KEY (`cid`) REFERENCES `t_course` (`cid`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_sc` */

insert  into `t_sc`(`sid`,`cid`,`grade`) values ('11001','1',87),('11002','1',22),('11005','3',56),('11005','4',89),('11010','3',67),('11045','1',100),('11051','2',89);

/*Table structure for table `t_student` */

DROP TABLE IF EXISTS `t_student`;

CREATE TABLE `t_student` (
  `sid` char(5) NOT NULL COMMENT '学号',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `sex` char(2) DEFAULT NULL COMMENT '性别',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_student` */

insert  into `t_student`(`sid`,`name`,`sex`,`age`) values ('0001','nobody','男',4),('11001','张三','男',17),('11002','赵飞燕','女',18),('11003','灰原哀','女',19),('11005','佳噜噜兽','男',5),('11007','wolfer','男',21),('11010','小白杨','男',21),('11012','小冰人','男',11),('11021','小白杨','女',21),('11022','小白杨','女',21),('11023','小白杨','女',21),('11024','小白杨','女',21),('11025','小火人','女',6),('11027','小白杨','女',21),('11029','小白杨','女',21),('11031','小白杨','女',21),('11042','小珏','女',18),('11044','小珏','女',18),('11045','小珏','女',18),('11049','小珏','女',18),('11051','小珏','女',18),('11052','小珏','女',18),('11053','小珏','女',18),('11056','小2辉','男',9),('11057','小珏','女',18),('11111','小蜗牛','男',21),('11112','小坦克','男',21),('11113','小飞机','男',21),('11120','小桌子','男',21),('12001','野比大雄','男',10),('12002','野比大雄','男',10),('12003','野比大雄','男',10),('12004','野比大雄','男',10),('12005','野比大雄','男',10),('12006','野比大雄','男',10),('12007','野比大雄','男',10),('12008','野比大雄','男',10),('12009','野比大雄','男',10),('12010','野比大雄','男',10),('12011','野比大雄','男',10),('12012','野比大雄','男',10),('12013','野比大雄','男',10),('12014','野比大雄','男',10),('12015','野比大雄','男',10),('12016','野比大雄','男',10),('12017','野比大雄','男',10),('12018','野比大雄','男',10),('12019','野比大雄','男',10),('12020','野比大雄','男',10);

/*Table structure for table `t_userinfo` */

DROP TABLE IF EXISTS `t_userinfo`;

CREATE TABLE `t_userinfo` (
  `uid` char(3) NOT NULL COMMENT '用户号 ',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `pwd` varchar(20) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_userinfo` */

insert  into `t_userinfo`(`uid`,`name`,`pwd`) values ('1','java','123456'),('2','wolfer','1234'),('3','yor','123'),('4','yours','123'),('5','abc','123');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
