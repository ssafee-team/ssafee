DROP TABLE IF EXISTS `auto_orders`,
`auto_payers`,
`chats`,
`creators`,
`managers`,
`menu_categories`,
`menus`,
`menus_option_categories`,
`option_categories`,
`options`,
`order_deliveries`,
`order_menus`,
`order_menu_option_categories`,
`order_menu_options`,
`participants`,
`parties`,
`shops`,
`users`;

CREATE TABLE `shops` (
    `shop_id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(32) NOT NULL,
    `address` varchar(255) NOT NULL,
    `phone` varchar(32) NOT NULL,
    `image` varchar(512) NULL,
    `enabled_auto_order` BOOL NOT NULL DEFAULT FALSE,
    `deleted` BOOL NOT NULL DEFAULT FALSE
);

CREATE TABLE `menu_categories` (
    `menu_category_id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(32) NOT NULL,
    `deleted` BOOL NOT NULL DEFAULT FALSE,
    `shop_id` BIGINT NOT NULL,
    FOREIGN KEY (`shop_id`) REFERENCES `shops`(`shop_id`)
);

CREATE TABLE `menus` (
    `menu_id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(32) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    `price` INT NOT NULL,
    `image` VARCHAR(512) NULL,
    `deleted` BOOL NOT NULL DEFAULT FALSE,
    `menu_category_id` BIGINT NOT NULL,
    FOREIGN KEY (`menu_category_id`) REFERENCES `menu_categories`(`menu_category_id`)
);

CREATE TABLE `option_categories` (
    `option_category_id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(32) NOT NULL,
    `required` BOOL NOT NULL,
    `max_count` INT NULL,
    `deleted` BOOL NOT NULL DEFAULT FALSE
);

CREATE TABLE `menus_option_categories` (
    `menu_id` BIGINT NOT NULL,
    `option_category_id` BIGINT NOT NULL,
    PRIMARY KEY (`menu_id`, `option_category_id`),
    FOREIGN KEY (`menu_id`) REFERENCES `menus`(`menu_id`),
    FOREIGN KEY (`option_category_id`) REFERENCES `option_categories`(`option_category_id`)
);

CREATE TABLE `options` (
    `option_id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(32) NOT NULL,
    `price` INT NOT NULL,
    `deleted` BOOL NOT NULL DEFAULT FALSE,
    `option_category_id` BIGINT NOT NULL,
    FOREIGN KEY (`option_category_id`) REFERENCES `option_categories`(`option_category_id`)
);

CREATE TABLE `users` (
    `user_id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `email` VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE `parties` (
    `party_id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `access_code` CHAR(10) NOT NULL UNIQUE,
    `name` varchar(32) NOT NULL,
    `generation` INT NOT NULL,
    `classroom` INT NOT NULL,
    `last_order_time` DATETIME NOT NULL,
    `created_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `shop_id` BIGINT NOT NULL,
    -- `user_id` BIGINT NOT NULL,
    FOREIGN KEY (`shop_id`) REFERENCES `shops`(`shop_id`),
    FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`)
);

CREATE TABLE `creators` (
    `creator_id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `email` VARCHAR(255) NOT NULL,
    `name` VARCHAR(8) NOT NULL,
    `bank` VARCHAR(32) NOT NULL,
    `account` VARCHAR(32) NOT NULL,
    `party_id` BIGINT NOT NULL,
    FOREIGN KEY (`party_id`) REFERENCES `parties`(`party_id`)
);

CREATE TABLE `chats` (
    `chat_id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `content` VARCHAR(255) NOT NULL,
    `created_time` DATETIME NOT NULL,
    `party_id` BIGINT NOT NULL,
    FOREIGN KEY (`party_id`) REFERENCES `parties`(`party_id`)
);

CREATE TABLE `participants` (
    `participant_id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(8) NULL,
    `is_creator` BOOL NOT NULL DEFAULT FALSE,
    `is_carrier` BOOL NOT NULL DEFAULT FALSE,
    `payed` BOOL NOT NULL DEFAULT FALSE,
    `pay_confirmed` BOOL NOT NULL DEFAULT FALSE,
    `created_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `party_id` BIGINT NOT NULL,
    FOREIGN KEY (`party_id`) REFERENCES `parties`(`party_id`)
);

CREATE TABLE `order_menus` (
    `order_menu_id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `created_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `menu_id` BIGINT NOT NULL,
    `participant_id` BIGINT NOT NULL,
    `party_id` BIGINT NOT NULL,
    FOREIGN KEY (`menu_id`) REFERENCES `menus`(`menu_id`),
    FOREIGN KEY (`participant_id`) REFERENCES `participants`(`participant_id`),
    FOREIGN KEY (`party_id`) REFERENCES `parties`(`party_id`)
);

CREATE TABLE `order_menu_option_categories` (
    `order_menu_option_category_id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `order_menu_id` BIGINT NOT NULL,
    `option_category_id` BIGINT NOT NULL,
    FOREIGN KEY (`order_menu_id`) REFERENCES `order_menus`(`order_menu_id`),
    FOREIGN KEY (`option_category_id`) REFERENCES `option_categories`(`option_category_id`)
);

CREATE TABLE `order_menu_options` (
    `order_menu_option_id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `order_menu_option_category_id` BIGINT NOT NULL,
    `option_id` BIGINT NOT NULL,
    FOREIGN KEY (`order_menu_option_category_id`) REFERENCES `order_menu_option_categories`(`order_menu_option_category_id`),
    FOREIGN KEY (`option_id`) REFERENCES `options`(`option_id`)
);

CREATE TABLE `order_deliveries` (
    `order_delivery_id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `fee` INT NOT NULL,
    `wanted_time` DATETIME NULL,
    `expected_time` DATETIME NULL,
    `arrived_time` DATETIME NULL,
    `created_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `party_id` BIGINT NOT NULL,
    FOREIGN KEY (`party_id`) REFERENCES `parties`(`party_id`)
);

CREATE TABLE `auto_orders` (
    `auto_order_id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `access_code` CHAR(10) NULL UNIQUE,
    `accepted_time` DATETIME NOT NULL,
    `created_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `party_id` BIGINT NOT NULL,
    FOREIGN KEY (`party_id`) REFERENCES `parties`(`party_id`)
);

CREATE TABLE `auto_payers` (
    `auto_payer_id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `phone` VARCHAR(32) NOT NULL,
    `creator_id` BIGINT NOT NULL,
    `auto_order_id` BIGINT NOT NULL,
    FOREIGN KEY (`creator_id`) REFERENCES `creators`(`creator_id`),
    FOREIGN KEY (`auto_order_id`) REFERENCES `auto_orders`(`auto_order_id`)
);

CREATE TABLE `managers` (
    `manager_id` BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `shop_id` BIGINT NOT NULL,
    FOREIGN KEY (`shop_id`) REFERENCES `shops`(`shop_id`)
);
