-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.35 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for pgmanagement
CREATE DATABASE IF NOT EXISTS `pgmanagement` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `pgmanagement`;

-- Dumping structure for table pgmanagement.approvals
CREATE TABLE IF NOT EXISTS `approvals` (
  `approvalPK` bigint NOT NULL AUTO_INCREMENT,
  `userFK` bigint NOT NULL,
  `reviewstatus` enum('pending','approved','rejected') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`approvalPK`),
  KEY `FK2_apr_user` (`userFK`),
  CONSTRAINT `FK2_apr_user` FOREIGN KEY (`userFK`) REFERENCES `appusers` (`AppUserPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table pgmanagement.appusers
CREATE TABLE IF NOT EXISTS `appusers` (
  `AppUserPK` bigint NOT NULL AUTO_INCREMENT,
  `userfirstname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0',
  `userlastname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0',
  `emailid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0',
  `phonenumber` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0',
  `LoginFK` bigint NOT NULL,
  `RoleFK` bigint NOT NULL,
  `createddate` date NOT NULL,
  `gender` enum('M','F') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'M',
  `advtId` bigint(19) unsigned zerofill NOT NULL,
  PRIMARY KEY (`AppUserPK`) USING BTREE,
  KEY `FK1_role` (`RoleFK`) USING BTREE,
  KEY `FK2_login` (`LoginFK`) USING BTREE,
  CONSTRAINT `appusers_ibfk_1` FOREIGN KEY (`RoleFK`) REFERENCES `role` (`rolepk`),
  CONSTRAINT `appusers_ibfk_2` FOREIGN KEY (`LoginFK`) REFERENCES `login` (`LoginPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- Data exporting was unselected.

-- Dumping structure for procedure pgmanagement.checkifAlreadyScheduled
DELIMITER //
CREATE PROCEDURE `checkifAlreadyScheduled`(
	IN `appUserFk` BIGINT,
	IN `rentDueDate` DATE
)
BEGIN
SELECT r.* FROM Rent r WHERE r.appUserFK = appUserFk AND r.RentDueData = rentDueDate ;
END//
DELIMITER ;

-- Dumping structure for procedure pgmanagement.getMessages
DELIMITER //
CREATE PROCEDURE `getMessages`(
	IN `caseId` INT
)
BEGIN
SELECT mco.CommunicationFrom AS fromId,mco.CommunicationTo AS toId ,ms.Message AS message,ms.messageDirection AS messageDirection,mco.CaseID AS caseId,
ms.MessagePK AS messageFk, ms.createdDate FROM maincommunication mco 
INNER JOIN messages ms ON ms.CaseIDFK = mco.CaseID
WHERE mco.CaseID = caseId
ORDER BY ms.createdDate ASC;
END//
DELIMITER ;

-- Dumping structure for table pgmanagement.login
CREATE TABLE IF NOT EXISTS `login` (
  `LoginPK` bigint NOT NULL AUTO_INCREMENT,
  `LoginName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Createddate` datetime NOT NULL DEFAULT (now()),
  `DelFlag` tinyint NOT NULL DEFAULT '0',
  `SecretKey` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`LoginPK`) USING BTREE,
  UNIQUE KEY `LoginName` (`LoginName`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- Data exporting was unselected.

-- Dumping structure for table pgmanagement.maincommunication
CREATE TABLE IF NOT EXISTS `maincommunication` (
  `CaseID` bigint NOT NULL AUTO_INCREMENT,
  `CommunicationFrom` bigint NOT NULL DEFAULT '0',
  `CommunicationTo` bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (`CaseID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- Data exporting was unselected.

-- Dumping structure for table pgmanagement.messages
CREATE TABLE IF NOT EXISTS `messages` (
  `MessagePK` bigint NOT NULL AUTO_INCREMENT,
  `CaseIDFK` bigint NOT NULL,
  `Message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `messageDirection` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `createdDate` datetime NOT NULL DEFAULT (now()),
  PRIMARY KEY (`MessagePK`) USING BTREE,
  KEY `FK1_Case_COmm` (`CaseIDFK`) USING BTREE,
  CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`CaseIDFK`) REFERENCES `maincommunication` (`CaseID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- Data exporting was unselected.

-- Dumping structure for table pgmanagement.pgadvertisement
CREATE TABLE IF NOT EXISTS `pgadvertisement` (
  `AdvertisementID` bigint NOT NULL AUTO_INCREMENT,
  `RoomNo` int NOT NULL DEFAULT (0),
  `Floor` int NOT NULL DEFAULT (0),
  `FoodType` enum('VEG','NONVEG') NOT NULL,
  `NoofSharing` int NOT NULL DEFAULT '0',
  `vacancy` int NOT NULL,
  `Available` tinyint NOT NULL DEFAULT (0),
  PRIMARY KEY (`AdvertisementID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table pgmanagement.referalcodes
CREATE TABLE IF NOT EXISTS `referalcodes` (
  `referalPK` bigint NOT NULL AUTO_INCREMENT,
  `referalCode` varchar(50) NOT NULL DEFAULT '',
  `appuserFK` bigint NOT NULL DEFAULT (0),
  PRIMARY KEY (`referalPK`),
  KEY `FK1_app_user` (`appuserFK`),
  CONSTRAINT `FK1_app_user` FOREIGN KEY (`appuserFK`) REFERENCES `appusers` (`AppUserPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table pgmanagement.referals
CREATE TABLE IF NOT EXISTS `referals` (
  `referalPK` bigint NOT NULL AUTO_INCREMENT,
  `referalfrom` bigint NOT NULL,
  `ReferalTo` bigint NOT NULL,
  `claimStatus` enum('Settled','UnSettled') NOT NULL,
  `discountAmount` double NOT NULL,
  PRIMARY KEY (`referalPK`),
  KEY `FK1_from` (`referalfrom`) USING BTREE,
  KEY `FK2_to` (`ReferalTo`) USING BTREE,
  CONSTRAINT `FK1_from` FOREIGN KEY (`referalfrom`) REFERENCES `appusers` (`AppUserPK`),
  CONSTRAINT `FK2_to` FOREIGN KEY (`ReferalTo`) REFERENCES `appusers` (`AppUserPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table pgmanagement.registration
CREATE TABLE IF NOT EXISTS `registration` (
  `registrationPK` bigint NOT NULL AUTO_INCREMENT,
  `referals` varchar(50) NOT NULL,
  `adharNumber` varchar(50) NOT NULL DEFAULT '0',
  `address` varchar(50) NOT NULL DEFAULT '0',
  `appuserFK` bigint NOT NULL DEFAULT (0),
  PRIMARY KEY (`registrationPK`),
  KEY `FK1_app` (`appuserFK`),
  CONSTRAINT `FK1_app` FOREIGN KEY (`appuserFK`) REFERENCES `appusers` (`AppUserPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table pgmanagement.rent
CREATE TABLE IF NOT EXISTS `rent` (
  `rentPK` bigint NOT NULL AUTO_INCREMENT,
  `appUserFK` bigint NOT NULL,
  `isPending` tinyint NOT NULL,
  `ReceiptFK` bigint DEFAULT NULL,
  `RentPaid` double NOT NULL,
  `referalsdiscount` double DEFAULT NULL,
  `totalRent` double NOT NULL,
  `CreatedDate` datetime NOT NULL DEFAULT (now()),
  `referalsFK` bigint DEFAULT NULL,
  `RentDueData` date NOT NULL,
  PRIMARY KEY (`rentPK`) USING BTREE,
  KEY `FK2_StudentFK` (`appUserFK`) USING BTREE,
  CONSTRAINT `FK1_app_fee` FOREIGN KEY (`appUserFK`) REFERENCES `appusers` (`AppUserPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- Data exporting was unselected.

-- Dumping structure for table pgmanagement.rentreceipt
CREATE TABLE IF NOT EXISTS `rentreceipt` (
  `RentReceiptPK` bigint NOT NULL AUTO_INCREMENT,
  `Receipt` longblob NOT NULL,
  `fileName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`RentReceiptPK`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- Data exporting was unselected.

-- Dumping structure for table pgmanagement.role
CREATE TABLE IF NOT EXISTS `role` (
  `rolepk` bigint NOT NULL AUTO_INCREMENT,
  `rolename` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`rolepk`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- Data exporting was unselected.

-- Dumping structure for table pgmanagement.supporttickets
CREATE TABLE IF NOT EXISTS `supporttickets` (
  `TicketID` bigint NOT NULL AUTO_INCREMENT,
  `caseIDFK` bigint NOT NULL,
  `Reason` enum('RENT','MAINTAINANCE','REFERALS') NOT NULL,
  `appUserFK` bigint NOT NULL,
  PRIMARY KEY (`TicketID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
