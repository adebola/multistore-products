
CREATE TABLE `category`
(
    `id`          varchar(36)   NOT NULL DEFAULT (uuid()),
    `name`        varchar(64)   NOT NULL,
    `image_url`   varchar(1024) NULL,
    `created_on`  timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by`  varchar(64)   NOT NULL,
    `description` varchar(512)  NOT NULL,
    `disabled`    tinyint(1)             DEFAULT '0',
    `tenant_id`   varchar(36)   NOT NULL,
    UNIQUE (`name`, `tenant_id`),
    PRIMARY KEY (`id`)
);

CREATE TABLE `uom` (
        `id` varchar(36) NOT NULL DEFAULT (uuid()),
        `name` varchar(32) NOT NULL,
        `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `created_by` varchar(64) NOT NULL,
        `description` varchar(512) NOT NULL,
        `disabled` tinyint(1) NOT NULL DEFAULT '0',
        `tenant_id` varchar(36) NOT NULL,
        UNIQUE (`name`, `tenant_id`),
        PRIMARY KEY (`id`)
);


CREATE TABLE `product_template` (
        `id` varchar(36) NOT NULL DEFAULT (uuid()),
        `name` varchar(64) NOT NULL,
        `description` varchar(512) NULL,
        `image_url` varchar(512) NULL,
        `category_id` varchar(36) NOT NULL,
        `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `created_by` varchar(64) NOT NULL,
        `disabled` tinyint(1) NOT NULL DEFAULT '0',
        `brand` varchar(64) NULL,
        `tenant_id` varchar(36) NOT NULL,
        FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
        PRIMARY KEY (`id`)
);

CREATE TABLE `product` (
        `id` varchar(36) NOT NULL DEFAULT (uuid()),
        `sku_code` varchar(64) NULL,
        `product_template_id` varchar(36) NOT NULL,
        `uom_id` varchar(36) NOT NULL,
        `price` decimal(13,2) NOT NULL DEFAULT 0,
        `discount` decimal(10,0) NOT NULL DEFAULT '0',
        `image_url` varchar(512) NULL,
        `extra_description` varchar(512) NULL,
        `new` tinyint(1) NOT NULL DEFAULT '0',
        `sale` tinyint(1) NOT NULL DEFAULT '0',
        `quantity` int NOT NULL DEFAULT '0',
        `disabled` tinyint(1) NOT NULL DEFAULT '0',
        `created_by` varchar(64) NOT NULL,
        `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `tenant_id` varchar(36) NOT NULL,
        PRIMARY KEY (`id`),
        FOREIGN KEY (`uom_id`) REFERENCES uom (`id`),
        FOREIGN KEY (`product_template_id`) REFERENCES product_template (`id`)
);

CREATE TABLE `product_variant` (
        `id` varchar(36) NOT NULL DEFAULT (uuid()),
        `name` varchar(64) NOT NULL,
        `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `created_by` varchar(64) NOT NULL,
        `disabled` tinyint(1) NOT NULL DEFAULT '0',
        `tenant_id` varchar(36) NOT NULL,
        PRIMARY KEY (`id`)
);

CREATE TABLE `product_variant_option` (
        `id` varchar(36) NOT NULL DEFAULT (uuid()),
        `name` varchar(64) NOT NULL,
        `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `created_by` varchar(64) NOT NULL,
        `disabled` tinyint(1) NOT NULL DEFAULT '0',
        `tenant_id` varchar(36) NOT NULL,
        PRIMARY KEY (`id`)
);

CREATE TABLE `product_variant_option_mapping` (
        `product_id` varchar(36) NOT NULL,
        `product_variant_id` varchar(36) NOT NULL,
        `product_variant_option_id` varchar(36) NOT NULL,
        UNIQUE KEY `idx_id_variant_option` (`product_id`,`product_variant_id`,`product_variant_option_id`),
        CONSTRAINT `product_variant_option_mapping_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
        CONSTRAINT `product_variant_option_mapping_ibfk_2` FOREIGN KEY (`product_variant_id`) REFERENCES `product_variant` (`id`),
        CONSTRAINT `product_variant_option_mapping_ibfk_3` FOREIGN KEY (`product_variant_option_id`) REFERENCES `product_variant_option` (`id`)
);
