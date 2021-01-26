/*
SQLyog Ultimate v12.5.1 (32 bit)
MySQL - 8.0.20-cluster : Database - didi
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`didi` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `didi`;

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `ownerid` varchar(50) DEFAULT NULL,
  `driverid` varchar(50) DEFAULT NULL,
  `price` varchar(50) DEFAULT NULL,
  `start` varchar(50) DEFAULT NULL,
  `end` varchar(50) DEFAULT NULL,
  `idorder` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `orders` */

insert  into `orders`(`ownerid`,`driverid`,`price`,`start`,`end`,`idorder`) values 
('1','2','100.0','changsha','shanghai',1);

/*Table structure for table `path` */

DROP TABLE IF EXISTS `path`;

CREATE TABLE `path` (
  `location` varchar(50) DEFAULT NULL,
  `carriage` varchar(50) DEFAULT NULL,
  `iduser` varchar(50) DEFAULT NULL,
  `ordernum` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `path` */

insert  into `path`(`location`,`carriage`,`iduser`,`ordernum`) values 
('changsha','10','002',1),
('tianjin','50','002',2),
('wuhan','20','002',3),
('shanghai','35','002',4),
('guangzhou','18','003',5),
('shanghai','30','003',6),
('changsha','10','003',7);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `iduser` int DEFAULT NULL,
  `pwd` varchar(50) DEFAULT NULL,
  `type` int DEFAULT NULL,
  `nickname` varchar(50) DEFAULT NULL,
  `balance` varchar(50) DEFAULT NULL,
  `sex` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`iduser`,`pwd`,`type`,`nickname`,`balance`,`sex`,`phone`) values 
(1,'shaolilongcf614',0,'sll','200','男','17694808614'),
(2,'shaolilongcf614',1,'dzc','900','女','13752318520'),
(3,'shaolilongcf614',1,'yk','100','男','18222230770');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
