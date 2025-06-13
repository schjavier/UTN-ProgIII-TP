-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bgzkzj03yydqpzbnqiiu
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
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
                           `street` varchar(50) NOT NULL,
                           `number` varchar(5) NOT NULL,
                           `city` varchar(20) NOT NULL,
                           PRIMARY KEY (`id_address`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'Main St','123','New York'),(2,'Oak Ave','456','Los Angeles'),(3,'Pine Rd','789','Chicago'),(4,'Maple Dr','101','Houston'),(5,'Elm St','202','Phoenix'),(6,'Colon','2390','Mar Del Plata');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit_product`
--

DROP TABLE IF EXISTS `audit_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audit_product` (
                                 `id_audit_product` bigint NOT NULL AUTO_INCREMENT,
                                 `id_user` bigint DEFAULT NULL,
                                 `date` datetime NOT NULL,
                                 `action` varchar(10) NOT NULL,
                                 `id_product` bigint DEFAULT NULL,
                                 `name` varchar(50) NOT NULL,
                                 `cost` decimal(10,2) NOT NULL,
                                 `profit_margin` decimal(10,2) DEFAULT NULL,
                                 `price` decimal(10,2) NOT NULL,
                                 PRIMARY KEY (`id_audit_product`),
                                 KEY `idProduct` (`id_product`),
                                 KEY `idUser` (`id_user`),
                                 CONSTRAINT `audit_product_ibfk_3` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`),
                                 CONSTRAINT `audit_product_ibfk_4` FOREIGN KEY (`id_product`) REFERENCES `product` (`id_product`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_product`
--

LOCK TABLES `audit_product` WRITE;
/*!40000 ALTER TABLE `audit_product` DISABLE KEYS */;
/*!40000 ALTER TABLE `audit_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credential`
--

DROP TABLE IF EXISTS `credential`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `credential` (
                              `id_credential` bigint NOT NULL AUTO_INCREMENT,
                              `username` varchar(50) NOT NULL,
                              `password` varchar(20) NOT NULL,
                              `role` varchar(15) NOT NULL,
                              PRIMARY KEY (`id_credential`),
                              UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credential`
--

LOCK TABLES `credential` WRITE;
/*!40000 ALTER TABLE `credential` DISABLE KEYS */;
/*!40000 ALTER TABLE `credential` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
                           `name` varchar(50) NOT NULL,
                           `status` varchar(10) NOT NULL,
                           `id_product` bigint NOT NULL AUTO_INCREMENT,
                           PRIMARY KEY (`id_product`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES ('Product 1','ENABLED',1),('Product 2','ENABLED',2),('Product 3','ENABLED',3),('Product 4','ENABLED',4),('Product 5','ENABLED',5),('Product 6','ENABLED',6),('Product 7','ENABLED',7),('Product 8','ENABLED',8),('Product 9','ENABLED',9),('Product 10','ENABLED',10),('Product 11','ENABLED',11),('Product 12','ENABLED',12),('Product 13','ENABLED',13),('Product 14','ENABLED',14),('Product 15','ENABLED',15),('Garbanzos Resacados','ENABLED',21);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_supplier`
--

DROP TABLE IF EXISTS `product_supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_supplier` (
                                    `id_product_supplier` bigint NOT NULL AUTO_INCREMENT,
                                    `id_product` bigint DEFAULT NULL,
                                    `id_supplier` bigint DEFAULT NULL,
                                    `cost` decimal(10,2) DEFAULT NULL,
                                    `profit_margin` decimal(10,2) DEFAULT NULL,
                                    `price` decimal(10,2) DEFAULT NULL,
                                    PRIMARY KEY (`id_product_supplier`),
                                    KEY `idProduct` (`id_product`),
                                    KEY `idSupplier` (`id_supplier`),
                                    CONSTRAINT `product_supplier_ibfk_1` FOREIGN KEY (`id_product`) REFERENCES `product` (`id_product`),
                                    CONSTRAINT `product_supplier_ibfk_2` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_supplier`
--

LOCK TABLES `product_supplier` WRITE;
/*!40000 ALTER TABLE `product_supplier` DISABLE KEYS */;
INSERT INTO `product_supplier` VALUES (1,1,1,10.00,20.00,12.00),(2,1,2,10.00,20.00,12.00),(3,2,2,15.00,25.00,18.75),(4,2,3,15.00,25.00,18.75),(5,3,3,20.00,30.00,26.00),(6,3,4,20.00,30.00,26.00),(7,4,4,25.00,35.00,33.75),(8,4,5,25.00,35.00,33.75),(9,5,5,30.00,40.00,42.00),(10,6,1,12.00,22.00,14.64),(11,7,2,14.00,24.00,17.36),(12,8,3,16.00,26.00,20.16),(13,9,4,18.00,28.00,23.04),(14,10,5,20.00,30.00,26.00),(15,11,1,22.00,32.00,29.04),(16,12,2,24.00,34.00,32.16),(17,13,3,26.00,36.00,35.36),(18,14,4,28.00,38.00,38.64),(19,15,5,30.00,40.00,42.00),(26,21,1,20.00,10.00,22.00);
/*!40000 ALTER TABLE `product_supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
                            `id_supplier` bigint NOT NULL AUTO_INCREMENT,
                            `cuit` varchar(255) NOT NULL,
                            `email` varchar(255) NOT NULL,
                            `company_name` varchar(255) NOT NULL,
                            `phone_number` varchar(255) NOT NULL,
                            `id_address` bigint DEFAULT NULL,
                            PRIMARY KEY (`id_supplier`),
                            KEY `id_address` (`id_address`),
                            CONSTRAINT `supplier_ibfk_1` FOREIGN KEY (`id_address`) REFERENCES `address` (`id_address`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (1,'20304567891','contact@techsolutions.com','Tech Solutions','123456789',1),(2,'20456789012','info@greensupplies.com','Green Supplies','987654321',2),(3,'20567890123','sales@bluewidgets.com','Blue Widgets','456789123',3),(4,'20678901234','support@redtools.com','Red Tools','789123456',4),(5,'20789012345','hello@yellowgadgets.com','Yellow Gadgets','321654987',5),(6,'20953825626','prueba@proveedorfalso.com','Proveedor Falso','1123556097',6);
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
                        `id_user` bigint NOT NULL AUTO_INCREMENT,
                        `firstname` varchar(50) NOT NULL,
                        `lastname` varchar(50) NOT NULL,
                        `dni` varchar(8) NOT NULL,
                        `status` varchar(10) NOT NULL,
                        `id_credential` bigint DEFAULT NULL,
                        PRIMARY KEY (`id_user`),
                        UNIQUE KEY `dni` (`dni`),
                        KEY `idCredential` (`id_credential`),
                        CONSTRAINT `user_ibfk_1` FOREIGN KEY (`id_credential`) REFERENCES `credential` (`id_credential`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-30 23:35:46
/*
SELECT * FROM supplier;
SELECT * FROM product_supplier;

DELETE FROM product_supplier;

SELECT * FROM user;*/