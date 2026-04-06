-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: cosmetics_management
-- ------------------------------------------------------
-- Server version	8.0.44

CREATE DATABASE IF NOT EXISTS `cosmetics_management` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `cosmetics_management`;

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
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_brands_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES (1,'MAC','Thương hiệu trang điểm chuyên nghiệp'),(2,'La Roche-Posay','Dược mỹ phẩm chăm sóc da'),(3,'L\'Oréal','Thương hiệu mỹ phẩm quốc dân'),(4,'Innisfree','Mỹ phẩm thiên nhiên Hàn Quốc'),(5,'The Ordinary','Sản phẩm chăm sóc da đặc trị'),(6,'Dior','Thương hiệu mỹ phẩm cao cấp');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_categories_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Skincare','Sản phẩm chăm sóc da mặt'),(2,'Makeup','Sản phẩm trang điểm'),(3,'Bodycare','Chăm sóc cơ thể'),(4,'Haircare','Chăm sóc tóc'),(5,'Fragrance','Nước hoa và xịt thơm'),(7,'s','s');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `customer_code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `full_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `gender` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tier` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `points` int NOT NULL DEFAULT '0',
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_customers_code` (`customer_code`),
  UNIQUE KEY `uk_customers_phone` (`phone`),
  CONSTRAINT `ck_customers_gender` CHECK (((`gender` in (_utf8mb4'MALE',_utf8mb4'FEMALE',_utf8mb4'OTHER')) or (`gender` is null))),
  CONSTRAINT `ck_customers_status` CHECK ((`status` in (_utf8mb4'ACTIVE',_utf8mb4'INACTIVE',_utf8mb4'BLOCKED')))
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'KH001','Nguyễn Thị Hoa','FEMALE','0901111111','SILVER',180,'ACTIVE',NULL),(2,'KH002','Trần Văn Nam','MALE','0902222222','MEMBER',4,'ACTIVE',NULL),(3,'KH003','Lê Ngọc Yến','FEMALE','0903333333','GOLD',500,'ACTIVE',NULL),(4,'KH004','Phạm Quỳnh Anh','FEMALE','0904444444','VIP',1200,'ACTIVE',NULL),(5,'KH005','Hoàng Bảo Sơn','MALE','0905555555','MEMBER',10,'ACTIVE',NULL),(6,'KH006','Đỗ Mai Linh','FEMALE','0906666666','SILVER',200,'ACTIVE',NULL),(7,'KH007','Vũ Hà Phương','FEMALE','0907777777','MEMBER',80,'ACTIVE',NULL),(8,'KH008','Ngô Quốc Khánh','MALE','0908888888','GOLD',650,'ACTIVE',NULL),(9,'KH009','Bùi Thanh Trúc','FEMALE','0909999999','VIP',1500,'ACTIVE',NULL),(10,'KH010','Lý Tấn Phát','MALE','0910101010','SILVER',250,'BLOCKED',NULL),(11,'KH011','Đặng Thùy Chi','FEMALE','0911222333','MEMBER',0,'ACTIVE',NULL),(12,'KH012','Vương Hải Đăng','MALE','0912333444','MEMBER',30,'ACTIVE',NULL),(13,'KH013','Mai Kim Ngân','FEMALE','0913444555','GOLD',800,'ACTIVE',NULL),(14,'KH014','Trịnh Yến Nhi','FEMALE','0914555666','SILVER',300,'ACTIVE',NULL),(15,'KH015','Đoàn Nhật Minh','MALE','0915666777','MEMBER',5,'ACTIVE',NULL);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `import_receipt_details`
--

DROP TABLE IF EXISTS `import_receipt_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `import_receipt_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `receipt_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `quantity` int NOT NULL DEFAULT '1',
  `import_price` decimal(38,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_import_details_receipt` (`receipt_id`),
  KEY `fk_import_details_product` (`product_id`),
  CONSTRAINT `fk_import_details_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_import_details_receipt` FOREIGN KEY (`receipt_id`) REFERENCES `import_receipts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ck_import_details_quantity` CHECK ((`quantity` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `import_receipt_details`
--

LOCK TABLES `import_receipt_details` WRITE;
/*!40000 ALTER TABLE `import_receipt_details` DISABLE KEYS */;
INSERT INTO `import_receipt_details` VALUES (1,1,1,10,350000.00),(2,1,2,5,500000.00),(3,2,3,20,250000.00),(4,3,1,1,0.00),(5,4,1,10,0.00),(6,4,2,10,0.00),(7,5,1,10,0.00);
/*!40000 ALTER TABLE `import_receipt_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `import_receipts`
--

DROP TABLE IF EXISTS `import_receipts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `import_receipts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `receipt_code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `supplier_id` bigint NOT NULL,
  `staff_id` bigint NOT NULL,
  `import_date` datetime(6) NOT NULL,
  `total_amount` decimal(38,2) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_imports_code` (`receipt_code`),
  KEY `fk_imports_supplier` (`supplier_id`),
  KEY `fk_imports_staff` (`staff_id`),
  CONSTRAINT `fk_imports_staff` FOREIGN KEY (`staff_id`) REFERENCES `staffs` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_imports_supplier` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `import_receipts`
--

LOCK TABLES `import_receipts` WRITE;
/*!40000 ALTER TABLE `import_receipts` DISABLE KEYS */;
INSERT INTO `import_receipts` VALUES (1,'PN001',1,1,'2026-03-10 00:00:00.000000',5000000.00),(2,'PN002',2,1,'2026-03-15 00:00:00.000000',3000000.00),(3,'PN1774345762851',1,2,'2026-03-24 16:49:22.851000',0.00),(4,'PN1774345793567',1,2,'2026-03-24 16:49:53.567000',0.00),(5,'PN1775018067939',1,1,'2026-04-01 11:34:27.939000',0.00);
/*!40000 ALTER TABLE `import_receipts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_details`
--

DROP TABLE IF EXISTS `invoice_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `invoice_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `quantity` int NOT NULL DEFAULT '1',
  `unit_price` decimal(38,2) NOT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoice_details_invoice` (`invoice_id`),
  KEY `fk_invoice_details_product` (`product_id`),
  CONSTRAINT `fk_invoice_details_invoice` FOREIGN KEY (`invoice_id`) REFERENCES `invoices` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_invoice_details_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `ck_invoice_details_quantity` CHECK ((`quantity` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_details`
--

LOCK TABLES `invoice_details` WRITE;
/*!40000 ALTER TABLE `invoice_details` DISABLE KEYS */;
INSERT INTO `invoice_details` VALUES (1,1,1,1,550000.00,NULL),(2,1,3,1,450000.00,NULL),(3,2,11,1,3500000.00,NULL),(4,3,1,1,550000.00,NULL),(5,4,2,1,850000.00,NULL),(6,5,3,2,450000.00,NULL),(7,6,1,1,550000.00,NULL),(8,7,1,1,550000.00,NULL),(9,8,1,2,550000.00,NULL),(10,9,12,5,800000.00,NULL),(11,10,1,1,550000.00,NULL),(12,11,1,1,550000.00,NULL),(13,12,1,1,550000.00,NULL),(14,13,1,2,550000.00,NULL),(15,14,1,1,550000.00,NULL),(16,15,1,1,550000.00,NULL),(17,16,1,1,550000.00,NULL),(18,17,1,1,550000.00,NULL);
/*!40000 ALTER TABLE `invoice_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoices`
--

DROP TABLE IF EXISTS `invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoices` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `invoice_code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `customer_id` bigint DEFAULT NULL,
  `staff_id` bigint NOT NULL,
  `invoice_date` datetime NOT NULL,
  `total_amount` decimal(38,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_invoices_code` (`invoice_code`),
  KEY `fk_invoices_customer` (`customer_id`),
  KEY `fk_invoices_staff` (`staff_id`),
  CONSTRAINT `fk_invoices_customer` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_invoices_staff` FOREIGN KEY (`staff_id`) REFERENCES `staffs` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices`
--

LOCK TABLES `invoices` WRITE;
/*!40000 ALTER TABLE `invoices` DISABLE KEYS */;
INSERT INTO `invoices` VALUES (1,'HD001',1,2,'2026-03-20 10:30:00',1000000.00),(2,'HD002',3,3,'2026-03-21 14:15:00',3500000.00),(3,'HD1774325139170',1,1,'2026-03-24 11:05:39',550000.00),(4,'HD1774325184936',2,1,'2026-03-24 11:06:25',850000.00),(5,'HD1774344323228',14,1,'2026-03-24 16:25:23',900000.00),(6,'HD1774350234136',1,1,'2026-03-24 18:03:54',550000.00),(7,'HD1774351252669',1,1,'2026-03-24 18:20:53',550000.00),(8,'HD1774418137333',1,1,'2026-03-25 12:55:37',1100000.00),(9,'HD1774517305233',NULL,1,'2026-03-26 16:28:25',4000000.00),(10,'HD1774518557441',1,1,'2026-03-26 16:49:17',550000.00),(11,'HD1774518673703',1,1,'2026-03-26 16:51:14',550000.00),(12,'HD1774518710994',1,1,'2026-03-26 16:51:51',550000.00),(13,'HD1774611163026',NULL,1,'2026-03-27 18:32:43',950000.00),(14,'HD1774693963035',2,1,'2026-03-28 17:32:43',445000.00),(15,'HD1775012053813',1,1,'2026-04-01 09:54:14',550000.00),(16,'HD1775016244957',1,1,'2026-04-01 11:04:05',550000.00),(17,'HD1775303887584',1,2,'2026-04-04 18:58:08',500000.00);
/*!40000 ALTER TABLE `invoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `barcode` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` decimal(38,2) NOT NULL,
  `quantity` int NOT NULL DEFAULT '0',
  `category_id` bigint NOT NULL,
  `brand_id` bigint NOT NULL,
  `expiration_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_products_barcode` (`barcode`),
  KEY `fk_products_category` (`category_id`),
  KEY `fk_products_brand` (`brand_id`),
  KEY `idx_products_name` (`name`),
  CONSTRAINT `fk_products_brand` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_products_category` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `ck_products_quantity` CHECK ((`quantity` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'SP001','Son thỏi MAC Ruby Woo',550000.00,57,1,1,'2026-04-10'),(2,'SP002','Kem nền MAC Studio Fix Fluid',850000.00,39,2,1,NULL),(3,'SP003','Kem chống nắng La Roche-Posay Anthelios',450000.00,98,1,2,NULL),(4,'SP004','Sữa rửa mặt La Roche-Posay Effaclar',350000.00,80,1,2,NULL),(5,'SP005','Nước tẩy trang L\'Oréal Micellar',150000.00,150,1,3,NULL),(6,'SP006','Kem dưỡng tóc L\'Oréal Elseve',200000.00,60,4,3,NULL),(7,'SP007','Mặt nạ đất sét Innisfree Volcanic',280000.00,70,1,4,NULL),(8,'SP008','Kem dưỡng ẩm Innisfree Green Tea',320000.00,90,1,4,NULL),(9,'SP009','Serum The Ordinary Niacinamide 10% + Zinc 1%',250000.00,120,1,5,NULL),(10,'SP010','Tẩy da chết The Ordinary AHA 30% + BHA 2%',290000.00,40,1,5,NULL),(11,'SP011','Nước hoa Dior Sauvage 100ml',3500000.00,15,5,6,NULL),(12,'SP012','Son dưỡng Dior Lip Glow',800000.00,40,2,6,NULL),(13,'SP013','Sữa tắm L\'Oréal Men Expert',180000.00,65,3,3,NULL),(14,'SP014','Phấn phủ MAC Prep + Prime',750000.00,25,2,1,NULL),(15,'SP015','Kem dưỡng phục hồi La Roche-Posay B5',380000.00,110,1,2,NULL),(16,'SP016','dưỡng lông chim',150000.00,100,7,3,NULL),(17,'SP017','abcc',1200.00,15,1,1,NULL);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotions`
--

DROP TABLE IF EXISTS `promotions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` bit(1) NOT NULL,
  `code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `discount_percent` int NOT NULL,
  `end_date` date NOT NULL,
  `max_discount_amount` decimal(38,2) DEFAULT NULL,
  `min_purchase_amount` decimal(38,2) DEFAULT NULL,
  `start_date` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKjdho73ymbyu46p2hh562dk4kk` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotions`
--

LOCK TABLES `promotions` WRITE;
/*!40000 ALTER TABLE `promotions` DISABLE KEYS */;
INSERT INTO `promotions` VALUES (1,_binary '','LOIDEPTRAI','Giảm 10% khi áp mã',11,'2026-03-30',20000000.00,20000000.00,'2026-03-27'),(2,_binary '','VALENTINE','Giảm 10% khi áp mã',10,'2026-04-10',50000.00,200000.00,'2026-04-04');
/*!40000 ALTER TABLE `promotions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staffs`
--

DROP TABLE IF EXISTS `staffs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staffs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `staff_code` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `full_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'STAFF',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_staffs_code` (`staff_code`),
  UNIQUE KEY `uk_staffs_username` (`username`),
  CONSTRAINT `ck_staffs_role` CHECK ((`role` in (_utf8mb4'ADMIN',_utf8mb4'STAFF')))
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staffs`
--

LOCK TABLES `staffs` WRITE;
/*!40000 ALTER TABLE `staffs` DISABLE KEYS */;
INSERT INTO `staffs` VALUES (1,'NV001','Quản trị viên','admin','12345','ADMIN'),(2,'NV002','Nhân viên Bán hàng 1','staff1','12345','STAFF'),(3,'NV003','Nhân viên Bán hàng 2','staff2','12345','STAFF'),(4,'NV004','Nguyễn Bảo Lợi','staff3','12345','STAFF');
/*!40000 ALTER TABLE `staffs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suppliers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_suppliers_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` VALUES (1,'Công ty TNHH L\'Oreal VN','Quận 1, TP.HCM','0901234567'),(2,'Nhà phân phối AmorePacific','Quận 3, TP.HCM','0912345678'),(3,'Công ty CP Mỹ phẩm Cao cấp','Quận Cầu Giấy, Hà Nội','0987654321');
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-06 17:53:11
