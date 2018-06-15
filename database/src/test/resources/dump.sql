-- MySQL dump 10.15  Distrib 10.0.33-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: dbsnake
-- ------------------------------------------------------
-- Server version	10.0.33-MariaDB-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `Game`
--

DROP TABLE IF EXISTS `Game`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Game` (
  `id`         INT(10)      NOT NULL AUTO_INCREMENT,
  `startTime`  TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `endTime`    TIMESTAMP(6) NOT NULL DEFAULT '0000-00-00 00:00:00.000000',
  `idGameMode` INT(10)      NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Game_GameMode_id_fk` (`idGameMode`),
  CONSTRAINT `Game_GameMode_id_fk` FOREIGN KEY (`idGameMode`) REFERENCES `GameMode` (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 26
  DEFAULT CHARSET = utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Game`
--

LOCK TABLES `Game` WRITE;
/*!40000 ALTER TABLE `Game`
  DISABLE KEYS */;
INSERT INTO `Game` VALUES (1, '2018-02-21 11:11:10.000000', '2018-02-21 11:17:25.000000', 1),
  (2, '2018-02-16 09:14:29.000000', '2018-02-16 09:18:45.000000', 2),
  (3, '2018-02-20 10:19:21.000000', '2018-02-20 10:38:33.000000', 2);
/*!40000 ALTER TABLE `Game`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GameMode`
--

DROP TABLE IF EXISTS `GameMode`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GameMode` (
  `id`        INT(10)     NOT NULL AUTO_INCREMENT,
  `name`      VARCHAR(64) NOT NULL,
  `minPlayer` INT(10)     NOT NULL DEFAULT '0',
  `maxPlayer` INT(10)     NOT NULL DEFAULT '10',
  PRIMARY KEY (`id`),
  UNIQUE KEY `GameMode_name_uindex` (`name`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GameMode`
--

LOCK TABLES `GameMode` WRITE;
/*!40000 ALTER TABLE `GameMode`
  DISABLE KEYS */;
INSERT INTO `GameMode` VALUES (1, 'Single Player', 1, 1), (2, 'Classic Deathmatch', 2, 8);
/*!40000 ALTER TABLE `GameMode`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GameParticipation`
--

DROP TABLE IF EXISTS `GameParticipation`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GameParticipation` (
  `idGame`     INT(11) DEFAULT NULL,
  `idUser`     INT(11) DEFAULT NULL,
  `score`      INT(11) DEFAULT '0',
  `killCount`  INT(11) DEFAULT '0',
  `deathCount` INT(11) DEFAULT '0',
  UNIQUE KEY `GameParticipation_idGame_idUser_pk` (`idGame`, `idUser`),
  KEY `GameParticipation_User_id_fk` (`idUser`),
  CONSTRAINT `GameParticipation_Game_id_fk` FOREIGN KEY (`idGame`) REFERENCES `Game` (`id`),
  CONSTRAINT `GameParticipation_User_id_fk` FOREIGN KEY (`idUser`) REFERENCES `User` (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GameParticipation`
--

LOCK TABLES `GameParticipation` WRITE;
/*!40000 ALTER TABLE `GameParticipation`
  DISABLE KEYS */;
INSERT INTO `GameParticipation`
VALUES (1, 1, 468, 0, 1), (2, 1, 352, 2, 5), (2, 2, 896, 8, 1), (2, 3, 752, 2, 6), (3, 5, 1220, 10, 5),
  (3, 3, 750, 8, 13), (3, 2, 950, 9, 9);
/*!40000 ALTER TABLE `GameParticipation`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Snake`
--

DROP TABLE IF EXISTS `Snake`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Snake` (
  `id`           INT(10)     NOT NULL AUTO_INCREMENT,
  `userID`       INT(10)     NOT NULL,
  `name`         VARCHAR(64) NOT NULL,
  `exp`          INT(10)     NOT NULL DEFAULT '0',
  `info`         BLOB,
  `idSnakeClass` INT(10)     NOT NULL,
  PRIMARY KEY (`id`),
  KEY `Snake_SnakeClass_id_fk` (`idSnakeClass`),
  KEY `snake_User_id_fk` (`userID`),
  CONSTRAINT `Snake_SnakeClass_id_fk` FOREIGN KEY (`idSnakeClass`) REFERENCES `SnakeClass` (`id`),
  CONSTRAINT `snake_User_id_fk` FOREIGN KEY (`userID`) REFERENCES `User` (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 34
  DEFAULT CHARSET = utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Snake`
--

LOCK TABLES `Snake` WRITE;
/*!40000 ALTER TABLE `Snake`
  DISABLE KEYS */;
INSERT INTO `Snake` VALUES (1, 1, 'snake1', 10, NULL, 1), (2, 1, 'snake2', 20, NULL, 2), (3, 2, 'snake3', 30, NULL, 1),
  (4, 2, 'snake4', 40, NULL, 2), (5, 3, 'snake5', 50, NULL, 1), (6, 3, 'snake6', 60, NULL, 2),
  (7, 4, 'snake7', 70, NULL, 1), (8, 4, 'snake8', 80, NULL, 2), (9, 5, 'snake9', 90, NULL, 1),
  (10, 5, 'snake10', 100, NULL, 2), (14, 2, 'nameTestSnake', 200, NULL, 1), (15, 2, 'nameTestSnake', 200, NULL, 1),
  (16, 2, 'nameTestSnake', 200, NULL, 1), (17, 2, 'nameTestSnake', 200, NULL, 1),
  (18, 2, 'nameTestSnake', 200, NULL, 1), (19, 2, 'nameTestSnake', 200, NULL, 1),
  (20, 2, 'nameTestSnake', 200, NULL, 1), (21, 2, 'nameTestSnake', 200, NULL, 1),
  (22, 2, 'nameTestSnake', 200, NULL, 1), (23, 2, 'nameTestSnake', 200, NULL, 1),
  (24, 2, 'nameTestSnake', 200, NULL, 1), (25, 2, 'nameTestSnake', 200, NULL, 1),
  (26, 2, 'nameTestSnake', 200, NULL, 1), (27, 2, 'nameTestSnake', 200, NULL, 1),
  (28, 2, 'nameTestSnake', 200, NULL, 1), (29, 2, 'nameTestSnake', 200, NULL, 1),
  (30, 2, 'nameTestSnake', 200, NULL, 1), (31, 2, 'nameTestSnake', 200, NULL, 1),
  (32, 2, 'nameTestSnake', 200, NULL, 1), (33, 2, 'nameTestSnake', 200, NULL, 1);
/*!40000 ALTER TABLE `Snake`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SnakeClass`
--

DROP TABLE IF EXISTS `SnakeClass`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SnakeClass` (
  `id`   INT(10)     NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(64) NOT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SnakeClass`
--

LOCK TABLES `SnakeClass` WRITE;
/*!40000 ALTER TABLE `SnakeClass`
  DISABLE KEYS */;
INSERT INTO `SnakeClass` VALUES (1, 'default'), (2, 'special');
/*!40000 ALTER TABLE `SnakeClass`
  ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id`          INT(10)      NOT NULL AUTO_INCREMENT,
  `alias`       VARCHAR(64)  NOT NULL,
  `email`       VARCHAR(128) NOT NULL,
  `accountName` VARCHAR(64)  NOT NULL,
  `password`    VARCHAR(256) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `User_accountName_uindex` (`accountName`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User`
  DISABLE KEYS */;
INSERT INTO `User`
VALUES (1, 'alias1', 'user1@domain.fr', 'user1', '123456'), (2, 'alias2', 'user2@domain.fr', 'user2', '123456'),
  (3, 'alias3', 'user3@domain.fr', 'user3', '123456'), (4, 'alias4', 'user4@domain.fr', 'user4', '123456'),
  (5, 'alias5', 'user5@domain.fr', 'user5', '123456'),
  (6, 'hirand', 'violette.paulin@pviolette.fr', 'pviolette', '230197');
/*!40000 ALTER TABLE `User`
  ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2018-02-28 15:35:57
