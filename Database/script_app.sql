CREATE DATABASE `lifestyles_app`;

USE `lifestyles_app`;

CREATE TABLE `user_app` (
	`id` int NOT NULL AUTO_INCREMENT,
	`username` VARCHAR(100) NOT NULL,
	`password` VARCHAR(100) NOT NULL,
	`email` VARCHAR(100) DEFAULT NULL,
	`active_token` VARCHAR(500) DEFAULT NULL,
    PRIMARY KEY (`id`),
	UNIQUE `uq_username` (`username`),
	UNIQUE `active_token_UNIQUE` (`active_token`),
	KEY `INDEX_username` (`username`) USING BTREE,
	KEY `INDEX_active_token` (`active_token`) USING BTREE
);