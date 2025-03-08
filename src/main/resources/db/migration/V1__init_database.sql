DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
        `id` varchar(36) NOT NULL DEFAULT (uuid()),
        `name` varchar(64) NOT NULL,
        `image_url` varchar(1024) DEFAULT NULL,
        `created_on` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
        `created_by` varchar(64) NOT NULL,
        `suspended` tinyint(1) DEFAULT '0',
        `deleted` tinyint(1) DEFAULT '0',
        `deleted_by` varchar(64) DEFAULT NULL,
        `deleted_on` timestamp NULL DEFAULT NULL,
        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `uom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `uom` (
                       `id` varchar(36) NOT NULL DEFAULT (uuid()),
                       `name` varchar(32) NOT NULL,
                       `created_on` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                       `created_by` varchar(64) NOT NULL,
                       `suspended` tinyint(1) DEFAULT '0',
                       `deleted` tinyint(1) DEFAULT '0',
                       `deleted_by` varchar(64) DEFAULT NULL,
                       `deleted_on` timestamp NULL DEFAULT NULL,
                       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `product_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_template` (
                                    `id` varchar(36) NOT NULL DEFAULT (uuid()),
                                    `name` varchar(64) NOT NULL,
                                    `description` varchar(512) DEFAULT NULL,
                                    `image_url` varchar(512) DEFAULT NULL,
                                    `category_id` varchar(36) NOT NULL,
                                    `created_on` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                    `created_by` varchar(64) NOT NULL,
                                    `suspended` tinyint(1) DEFAULT '0',
                                    `deleted` tinyint(1) DEFAULT '0',
                                    `deleted_by` varchar(64) DEFAULT NULL,
                                    `deleted_on` timestamp NULL DEFAULT NULL,
                                    `brand` varchar(64) DEFAULT NULL,
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
                           `id` varchar(36) NOT NULL DEFAULT (uuid()),
                           `sku_code` varchar(64) NOT NULL,
                           `product_template_id` varchar(36) NOT NULL,
                           `uom_id` varchar(36) NOT NULL,
                           `price` decimal(13,2) NOT NULL,
                           `discount` decimal(10,0) DEFAULT '0',
                           `image_url` varchar(512) DEFAULT NULL,
                           `extra_description` varchar(512) DEFAULT NULL,
                           `new` tinyint(1) NOT NULL DEFAULT '0',
                           `sale` tinyint(1) NOT NULL DEFAULT '0',
                           `quantity` int NOT NULL DEFAULT '0',
                           `suspended` tinyint(1) DEFAULT '0',
                           `deleted` tinyint(1) DEFAULT '0',
                           `deleted_by` varchar(64) DEFAULT NULL,
                           `deleted_on` timestamp NULL DEFAULT NULL,
                           `created_by` varchar(64) NOT NULL,
                           PRIMARY KEY (`id`),
                           KEY `uom_id` (`uom_id`),
                           KEY `product_template_id` (`product_template_id`),
                           KEY `idx_sku_code` (`sku_code`),
                           CONSTRAINT `product_ibfk_1` FOREIGN KEY (`uom_id`) REFERENCES `uom` (`id`),
                           CONSTRAINT `product_ibfk_2` FOREIGN KEY (`product_template_id`) REFERENCES `product_template` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `product_variant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_variant` (
                                   `id` varchar(36) NOT NULL DEFAULT (uuid()),
                                   `name` varchar(64) NOT NULL,
                                   `created_on` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                   `created_by` varchar(64) NOT NULL,
                                   `suspended` tinyint(1) DEFAULT '0',
                                   `deleted` tinyint(1) DEFAULT '0',
                                   `deleted_by` varchar(64) DEFAULT NULL,
                                   `deleted_on` timestamp NULL DEFAULT NULL,
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `product_variant_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_variant_option` (
                                          `id` varchar(36) NOT NULL DEFAULT (uuid()),
                                          `name` varchar(64) NOT NULL,
                                          `created_on` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                          `created_by` varchar(64) NOT NULL,
                                          `suspended` tinyint(1) DEFAULT '0',
                                          `deleted` tinyint(1) DEFAULT '0',
                                          `deleted_by` varchar(64) DEFAULT NULL,
                                          `deleted_on` timestamp NULL DEFAULT NULL,
                                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `product_variant_option_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_variant_option_mapping` (
        `product_id` varchar(36) NOT NULL,
        `product_variant_id` varchar(36) NOT NULL,
        `product_variant_option_id` varchar(36) NOT NULL,
        UNIQUE KEY `idx_id_variant_option` (`product_id`,`product_variant_id`,`product_variant_option_id`),
        KEY `product_variant_id` (`product_variant_id`),
        KEY `product_variant_option_id` (`product_variant_option_id`),
        CONSTRAINT `product_variant_option_mapping_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
        CONSTRAINT `product_variant_option_mapping_ibfk_2` FOREIGN KEY (`product_variant_id`) REFERENCES `product_variant` (`id`),
        CONSTRAINT `product_variant_option_mapping_ibfk_3` FOREIGN KEY (`product_variant_option_id`) REFERENCES `product_variant_option` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
