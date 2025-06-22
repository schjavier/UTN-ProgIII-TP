-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: bgzkzj03yydqpzbnqiiu
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id_address` bigint NOT NULL AUTO_INCREMENT,
  `city` varchar(50) NOT NULL,
  `number` varchar(5) NOT NULL,
  `street` varchar(50) NOT NULL,
  PRIMARY KEY (`id_address`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'New York','123','Main St'),(2,'Los Angeles','456','Oak Ave'),(3,'Chicago','789','Pine Rd'),(4,'Houston','101','Maple Dr'),(5,'Phoenix','202','Elm St'),(6,'Mar Del Plata','2390','Colon'),(14,'fuckville','123','Nowhere'),(15,'Ciudad','123','Calle 12');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `address_audit`
--

DROP TABLE IF EXISTS `address_audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address_audit` (
  `id_address` bigint NOT NULL,
  `revision` int NOT NULL,
  `revision_type` tinyint DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`revision`,`id_address`),
  CONSTRAINT `FKqnnfpmaq7i7gue3kyxvstsiy` FOREIGN KEY (`revision`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address_audit`
--

LOCK TABLES `address_audit` WRITE;
/*!40000 ALTER TABLE `address_audit` DISABLE KEYS */;
INSERT INTO `address_audit` VALUES (14,7,0,'fuckville','123','Nowhere'),(15,8,0,'Ciudad','123','Calle 12');
/*!40000 ALTER TABLE `address_audit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credential`
--

DROP TABLE IF EXISTS `credential`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `credential` (
  `id_credential` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(64) NOT NULL,
  `role` enum('ADMIN','EMPLOYEE','MANAGER') DEFAULT NULL,
  `username` varchar(16) NOT NULL,
  PRIMARY KEY (`id_credential`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credential`
--

LOCK TABLES `credential` WRITE;
/*!40000 ALTER TABLE `credential` DISABLE KEYS */;
INSERT INTO `credential` VALUES (7,'$2a$10$UIU/kuUo8kJiIhRQrG1Fd.PPkHgPcrgYdW2qc1UScQO8dNpA3PWf2','ADMIN','admin'),(8,'$2a$10$PX9S9KmCgoFUkQRp47aBLek0Q112szJAXiBEnycRhERCopRJm7Le2','EMPLOYEE','juliganga'),(9,'$2a$10$VUmKIGtrcRbfQq6hNyolAOr/xxN6epGd3MYLW77S8g1pjRKEMkiV6','MANAGER','angie');
/*!40000 ALTER TABLE `credential` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credential_audit`
--

DROP TABLE IF EXISTS `credential_audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `credential_audit` (
  `id_credential` bigint NOT NULL,
  `revision` int NOT NULL,
  `revision_type` tinyint DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','EMPLOYEE','MANAGER') DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`revision`,`id_credential`),
  CONSTRAINT `FKeg5f1y8iehnhjvm52odsau97n` FOREIGN KEY (`revision`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credential_audit`
--

LOCK TABLES `credential_audit` WRITE;
/*!40000 ALTER TABLE `credential_audit` DISABLE KEYS */;
INSERT INTO `credential_audit` VALUES (1,1,0,'$2a$10$762BbZ7myCjr5vwsrcNm4e/Rc1rKIm8ulBHlPxU2llL3ijt67WIv6','ADMIN','admin'),(2,2,0,'$2a$10$B2tn03d5Jj2X9MsNY5YIEeRYg/efJ7eqJr3sWaRNJv2hWUl.Kzaqm','MANAGER','juliganga'),(3,3,0,'$2a$10$xmbmhde3A72hW7.rEx.4HOKcxJ3sh6RhBrFcl8BElezHFN0hWM8Oa','MANAGER','juliganga1'),(4,4,0,'$2a$10$Je4wDdFQbrHnmOm8VVf37Oy63473Yl7PSclhn/y4O1/HPcXVNcvj6','MANAGER','juliganga2'),(5,5,0,'$2a$10$tHQ1K9CtVKbSRi0fz9JwnOhk..LzqbgEQdPqrHO.L6aY9JUNkY8B6','MANAGER','juliganga3'),(6,6,0,'$2a$10$cG.4Gf9mXog0mBriOFy72Oc4IjH4kFq804LN7Y5Oa71VGdSRCJ3am','MANAGER','juliganga4'),(7,9,0,'$2a$10$UIU/kuUo8kJiIhRQrG1Fd.PPkHgPcrgYdW2qc1UScQO8dNpA3PWf2','ADMIN','admin'),(8,10,0,'$2a$10$PX9S9KmCgoFUkQRp47aBLek0Q112szJAXiBEnycRhERCopRJm7Le2','EMPLOYEE','juliganga'),(9,11,0,'$2a$10$VUmKIGtrcRbfQq6hNyolAOr/xxN6epGd3MYLW77S8g1pjRKEMkiV6','MANAGER','angie');
/*!40000 ALTER TABLE `credential_audit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id_product` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `status` enum('DISABLED','ENABLED') DEFAULT NULL,
  PRIMARY KEY (`id_product`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Product 1','ENABLED'),(2,'Product 2','ENABLED'),(3,'Product 3','ENABLED'),(4,'Product 4','ENABLED'),(5,'Product 5','ENABLED'),(6,'Product 6','ENABLED'),(7,'Product 7','ENABLED'),(8,'Product 8','ENABLED'),(9,'Product 9','ENABLED'),(10,'Product 10','ENABLED'),(11,'Product 11','ENABLED'),(12,'Product 12','ENABLED'),(13,'Product 13','ENABLED'),(14,'Product 14','ENABLED'),(15,'Product 15','ENABLED'),(21,'Garbanzos Resacados','ENABLED');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_audit`
--

DROP TABLE IF EXISTS `product_audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_audit` (
  `id_product` bigint NOT NULL,
  `revision` int NOT NULL,
  `revision_type` tinyint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` enum('DISABLED','ENABLED') DEFAULT NULL,
  PRIMARY KEY (`revision`,`id_product`),
  CONSTRAINT `FKr53uf3vfchv02n76ew5j7nfib` FOREIGN KEY (`revision`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_audit`
--

LOCK TABLES `product_audit` WRITE;
/*!40000 ALTER TABLE `product_audit` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_audit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_supplier`
--

DROP TABLE IF EXISTS `product_supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_supplier` (
  `id_product_supplier` bigint NOT NULL AUTO_INCREMENT,
  `cost` decimal(38,2) NOT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `profit_margin` decimal(38,2) NOT NULL,
  `id_product` bigint DEFAULT NULL,
  `id_supplier` bigint DEFAULT NULL,
  PRIMARY KEY (`id_product_supplier`),
  KEY `FKn5o8g93yfw9hsm1ng835qn3eg` (`id_product`),
  KEY `FKddc3dp28rjg9vv8aanw3kwd3u` (`id_supplier`),
  CONSTRAINT `FKddc3dp28rjg9vv8aanw3kwd3u` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`),
  CONSTRAINT `FKn5o8g93yfw9hsm1ng835qn3eg` FOREIGN KEY (`id_product`) REFERENCES `product` (`id_product`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_supplier`
--

LOCK TABLES `product_supplier` WRITE;
/*!40000 ALTER TABLE `product_supplier` DISABLE KEYS */;
INSERT INTO `product_supplier` VALUES (1,10.00,12.00,20.00,1,1),(2,10.00,12.00,20.00,1,2),(3,15.00,18.75,25.00,2,2),(4,15.00,18.75,25.00,2,3),(5,20.00,26.00,30.00,3,3),(6,20.00,26.00,30.00,3,4),(7,25.00,33.75,35.00,4,4),(8,25.00,33.75,35.00,4,5),(9,30.00,42.00,40.00,5,5),(10,12.00,14.64,22.00,6,1),(11,14.00,17.36,24.00,7,2),(12,16.00,20.16,26.00,8,3),(13,18.00,23.04,28.00,9,4),(14,20.00,26.00,30.00,10,5),(15,22.00,29.04,32.00,11,1),(16,24.00,32.16,34.00,12,2),(17,26.00,35.36,36.00,13,3),(18,28.00,38.64,38.00,14,4),(19,30.00,42.00,40.00,15,5),(26,20.00,22.00,10.00,21,1);
/*!40000 ALTER TABLE `product_supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_supplier_audit`
--

DROP TABLE IF EXISTS `product_supplier_audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_supplier_audit` (
  `id_product_supplier` bigint NOT NULL,
  `revision` int NOT NULL,
  `revision_type` tinyint DEFAULT NULL,
  `cost` decimal(38,2) DEFAULT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `profit_margin` decimal(38,2) DEFAULT NULL,
  `id_product` bigint DEFAULT NULL,
  `id_supplier` bigint DEFAULT NULL,
  PRIMARY KEY (`revision`,`id_product_supplier`),
  CONSTRAINT `FKnnnogrbd9t79vnsvetcjqd164` FOREIGN KEY (`revision`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_supplier_audit`
--

LOCK TABLES `product_supplier_audit` WRITE;
/*!40000 ALTER TABLE `product_supplier_audit` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_supplier_audit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `revinfo`
--

DROP TABLE IF EXISTS `revinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `revinfo` (
  `rev` int NOT NULL AUTO_INCREMENT,
  `date` datetime(6) DEFAULT NULL,
  `id_user` bigint DEFAULT NULL,
  PRIMARY KEY (`rev`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `revinfo`
--

LOCK TABLES `revinfo` WRITE;
/*!40000 ALTER TABLE `revinfo` DISABLE KEYS */;
INSERT INTO `revinfo` VALUES (1,'2025-06-22 15:35:22.870000',NULL),(2,'2025-06-22 15:36:04.417000',1),(3,'2025-06-22 15:44:59.779000',1),(4,'2025-06-22 15:45:24.853000',1),(5,'2025-06-22 15:45:33.836000',1),(6,'2025-06-22 15:45:59.366000',1),(7,'2025-06-22 15:50:57.482000',1),(8,'2025-06-22 15:52:38.091000',1),(9,'2025-06-22 16:13:56.376000',NULL),(10,'2025-06-22 16:16:05.389000',7),(11,'2025-06-22 16:16:51.804000',7);
/*!40000 ALTER TABLE `revinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `id_supplier` bigint NOT NULL AUTO_INCREMENT,
  `company_name` varchar(75) NOT NULL,
  `cuit` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `id_address` bigint NOT NULL,
  PRIMARY KEY (`id_supplier`),
  UNIQUE KEY `UKh7ppmvd4rtj4pfquilyli4qox` (`id_address`),
  CONSTRAINT `FK6s9115t6timi265lj0j5mbb8x` FOREIGN KEY (`id_address`) REFERENCES `address` (`id_address`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (1,'Tech Solutions','20-30456789-1','contact@techsolutions.com','123456789',1),(2,'Green Supplies','20-45678901-2','info@greensupplies.com','987654321',2),(3,'Blue Widgets','20-56789012-3','sales@bluewidgets.com','456789123',3),(4,'Red Tools','20-67890123-4','support@redtools.com','789123456',4),(5,'Yellow Gadgets','20-78901234-5','hello@yellowgadgets.com','321654987',5),(6,'Proveedor Falso','20-95382562-6','prueba@proveedorfalso.com','1123556097',6);
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier_audit`
--

DROP TABLE IF EXISTS `supplier_audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier_audit` (
  `id_supplier` bigint NOT NULL,
  `revision` int NOT NULL,
  `revision_type` tinyint DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `cuit` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `id_address` bigint DEFAULT NULL,
  PRIMARY KEY (`revision`,`id_supplier`),
  CONSTRAINT `FK4w0rx1aj78ild67tjcjyl4gjq` FOREIGN KEY (`revision`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier_audit`
--

LOCK TABLES `supplier_audit` WRITE;
/*!40000 ALTER TABLE `supplier_audit` DISABLE KEYS */;
INSERT INTO `supplier_audit` VALUES (1,7,0,'Test_comp','23-11111111-9','test@email.com','2291449886',14),(2,8,0,'Empresa 1','23-11111111-9','test1@email.com','1111111111',15);
/*!40000 ALTER TABLE `supplier_audit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id_user` bigint NOT NULL AUTO_INCREMENT,
  `dni` varchar(8) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `status` enum('DISABLED','ENABLED') DEFAULT NULL,
  `id_credential` bigint NOT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `UKctqwgkqp9i6h1hq4qtuvhypsa` (`id_credential`),
  CONSTRAINT `FKo1vvfnp999flvbj227qq563cs` FOREIGN KEY (`id_credential`) REFERENCES `credential` (`id_credential`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (7,'0000000','The','Administrator','ENABLED',7),(8,'11111111','Juliganga','Program','ENABLED',8),(9,'22222222','Angie','Program','ENABLED',9);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_audit`
--

DROP TABLE IF EXISTS `user_audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_audit` (
  `id_user` bigint NOT NULL,
  `revision` int NOT NULL,
  `revision_type` tinyint DEFAULT NULL,
  `dni` varchar(255) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `status` enum('DISABLED','ENABLED') DEFAULT NULL,
  `id_credential` bigint DEFAULT NULL,
  PRIMARY KEY (`revision`,`id_user`),
  CONSTRAINT `FKirc48vgigvptqsfod4awbjeib` FOREIGN KEY (`revision`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_audit`
--

LOCK TABLES `user_audit` WRITE;
/*!40000 ALTER TABLE `user_audit` DISABLE KEYS */;
INSERT INTO `user_audit` VALUES (1,1,0,'0000000','The','Administrator','ENABLED',1),(2,2,0,'22645105','Juliganga','Testeador','ENABLED',2),(3,3,0,'22645101','Juliganga','Testeador','ENABLED',3),(4,4,0,'22645102','Juliganga','Testeador','ENABLED',4),(5,5,0,'22645103','Juliganga','Testeador','ENABLED',5),(6,6,0,'22645104','Juliganga','Testeador','ENABLED',6),(7,9,0,'0000000','The','Administrator','ENABLED',7),(8,10,0,'11111111','Juliganga','Program','ENABLED',8),(9,11,0,'22222222','Angie','Program','ENABLED',9);
/*!40000 ALTER TABLE `user_audit` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-22 16:32:22
