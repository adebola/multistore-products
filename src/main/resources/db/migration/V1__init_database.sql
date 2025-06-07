CREATE TABLE `category` (
    `id`          varchar(36)   NOT NULL DEFAULT (UUID()),
    `name`        varchar(64)   NOT NULL,
    `image_url`   varchar(1024) NULL,
    `created_on`  timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by`  varchar(64)   NOT NULL,
    `description` varchar(1024)  NOT NULL,
    `disabled`    tinyint(1)             DEFAULT '0',
    `tenant_id`   varchar(36)   NOT NULL,
    UNIQUE (`name`, `tenant_id`),
    PRIMARY KEY (`id`)
);

CREATE TABLE `product` (
        `id` varchar(36) NOT NULL DEFAULT (UUID()),
        `name` varchar(64)   NOT NULL,
        `category_id` varchar(36) NOT NULL,
        `brand` varchar(64) NULL,
        `image_url` varchar(512) NULL,
        `description` varchar(1024) NOT NULL,
        `disabled` tinyint(1) NOT NULL DEFAULT '0',
        `created_by` varchar(64) NOT NULL,
        `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `tenant_id` varchar(36) NOT NULL,
        UNIQUE (`name`, `tenant_id`),
        FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE,
        PRIMARY KEY (`id`)
);

CREATE TABLE `uom` (
        `id` varchar(36) NOT NULL DEFAULT (UUID()),
        `product_id` varchar(36) NOT NULL,
        `name` varchar(32) NOT NULL,
        `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `created_by` varchar(64) NOT NULL,
        `disabled` tinyint(1) NOT NULL DEFAULT '0',
        UNIQUE (`name`, `product_id`),
        FOREIGN KEY (product_id) REFERENCES `product` (id) ON DELETE CASCADE,
        PRIMARY KEY (`id`)
);

CREATE TABLE `product_variant` (
        `id` varchar(36) NOT NULL DEFAULT (UUID()),
        `name` varchar(64) NOT NULL,
        `product_id` varchar(36) NOT NULL,
        `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `created_by` varchar(64) NOT NULL,
        `disabled` tinyint(1) NOT NULL DEFAULT '0',
        FOREIGN KEY (product_id) REFERENCES `product` (id) ON DELETE CASCADE,
        UNIQUE (`name`, `product_id`),
        PRIMARY KEY (`id`)
);

CREATE TABLE `product_variant_option` (
        `id` varchar(36) NOT NULL DEFAULT (UUID()),
        `name` varchar(64) NOT NULL,
        `product_variant_id` varchar(36) NOT NULL,
        `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `created_by` varchar(64) NOT NULL,
        `disabled` tinyint(1) NOT NULL DEFAULT '0',
        `tenant_id` varchar(36) NOT NULL,
        FOREIGN KEY (product_variant_id) REFERENCES `product_variant` (id) ON DELETE CASCADE,
        UNIQUE (`name`, `product_variant_id`),
        PRIMARY KEY (`id`)
);

CREATE TABLE `product_sku` (
        `id` varchar(36) NOT NULL DEFAULT (UUID()),
        `sku` varchar(64) NOT NULL,
        `product_id` varchar(36) NOT NULL,
        `product_variant_id` varchar(36) NOT NULL,
        `product_variant_option_id` varchar(36) NOT NULL,
        `uom_id` varchar(36) NOT NULL,
        `price` DECIMAL(13,2) NOT NULL DEFAULT 0,
        `discount` DECIMAL(10,2) NOT NULL DEFAULT '0',
        `new` tinyint(1) NOT NULL DEFAULT '0',
        `sale` tinyint(1) NOT NULL DEFAULT '0',
        `quantity` int NOT NULL DEFAULT '0',
        `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        `created_by` varchar(64) NOT NULL,
        `disabled` tinyint(1) NOT NULL DEFAULT '0',
        `tenant_id` varchar(36) NOT NULL,
        UNIQUE KEY (`product_id`,`product_variant_id`,`product_variant_option_id`, `uom_id`, `tenant_id`),
        FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
        FOREIGN KEY (`product_variant_id`) REFERENCES `product_variant` (`id`),
        FOREIGN KEY (`product_variant_option_id`) REFERENCES `product_variant_option` (`id`),
        FOREIGN KEY (`uom_id`) REFERENCES `uom` (`id`),
        PRIMARY KEY (`id`)
);

CREATE TABLE `product_inventory` (
    `product_sku_id` varchar(36) NOT NULL,
    `quantity` int NOT NULL DEFAULT '0',
    `last_updated` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE (product_sku_id),
    FOREIGN KEY (`product_sku_id`) REFERENCES `product_sku` (`id`)
);

CREATE TABLE `uom_conversion` (
    `id` varchar(36) NOT NULL DEFAULT (UUID()),
    `from_uom_id` varchar(36) NOT NULL,
    `to_uom_id` varchar(36) NOT NULL,
    `conversion_factor` DECIMAL(10,2) NOT NULL, -- Factor to convert from `from_uom_id` to `to_uom_id`
    `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` varchar(64) NOT NULL,
    `tenant_id` varchar(36) NOT NULL,
    UNIQUE (`from_uom_id`, `to_uom_id`, `tenant_id`), -- Ensure unique conversions per tenant
    FOREIGN KEY (`from_uom_id`) REFERENCES `uom` (`id`),
    FOREIGN KEY (`to_uom_id`) REFERENCES `uom` (`id`),
    PRIMARY KEY (`id`)
);


CREATE TABLE `product_sku_price_history` (
    `id` varchar(36) NOT NULL DEFAULT (UUID()),
    `product_sku_id` varchar(36) NOT NULL,
    `price` DECIMAL(13,2) NOT NULL,
    `effective_from` timestamp NOT NULL,
    `created_by` varchar(64) NOT NULL,
    `tenant_id` varchar(36) NOT NULL,
    FOREIGN KEY (`product_sku_id`) REFERENCES `product_sku` (`id`),
    PRIMARY KEY (`id`)
);

CREATE TABLE `product_inventory_movement` (
    `id` varchar(36) NOT NULL DEFAULT (UUID()),
    `product_sku_id` varchar(36) NOT NULL,
    `quantity_change` int NOT NULL,
    `movement_type` varchar(20) NOT NULL,
    `reference_id` varchar(36),
    `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `created_by` varchar(64) NOT NULL,
    `tenant_id` varchar(36) NOT NULL,
    FOREIGN KEY (`product_sku_id`) REFERENCES `product_sku` (`id`),
    PRIMARY KEY (`id`)
);

-- Add to the product table
ALTER TABLE `product` ADD INDEX `idx_tenant_category` (`tenant_id`, `category_id`);
-- Add to product_sku table
ALTER TABLE `product_sku` ADD INDEX `idx_tenant_product` (`tenant_id`, `product_id`);