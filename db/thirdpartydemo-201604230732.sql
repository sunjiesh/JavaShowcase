-- MySQL dump 10.13  Distrib 5.6.30, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: thirdpartydemo
-- ------------------------------------------------------
-- Server version	5.6.30-0ubuntu0.15.10.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `wechat_receive_message`
--

DROP TABLE IF EXISTS `wechat_receive_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_receive_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `from_user` varchar(45) NOT NULL,
  `message_content` varchar(45) NOT NULL,
  `message_type` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `create_user` varchar(45) NOT NULL,
  `create_time` datetime NOT NULL,
  `update_user` varchar(45) NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_receive_message`
--

LOCK TABLES `wechat_receive_message` WRITE;
/*!40000 ALTER TABLE `wechat_receive_message` DISABLE KEYS */;
INSERT INTO `wechat_receive_message` VALUES (1,'oiY-ExO09BRAH3kP80a_l438aBwQ','吧','text',1,'thirdpartydemo-web','2016-04-20 23:24:55','thirdpartydemo-web','2016-04-20 23:24:55'),(2,'oiY-ExO09BRAH3kP80a_l438aBwQ','GetImageMessage','click',1,'thirdpartydemo-web','2016-04-22 21:41:55','thirdpartydemo-web','2016-04-22 21:41:55');
/*!40000 ALTER TABLE `wechat_receive_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_user`
--

DROP TABLE IF EXISTS `wechat_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `country` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `create_user` varchar(255) COLLATE utf8_bin NOT NULL,
  `head_img_url` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `language` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `nickname` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `open_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `province` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `subscribe` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `subscribe_time` datetime NOT NULL,
  `union_id` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `update_time` datetime NOT NULL,
  `update_user` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_n5qy9tkbvf93bvullxvwbs5ye` (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_user`
--

LOCK TABLES `wechat_user` WRITE;
/*!40000 ALTER TABLE `wechat_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'thirdpartydemo'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-23  7:32:20
