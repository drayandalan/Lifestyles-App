CREATE DATABASE `lifestyles_app`;

USE `lifestyles_app`;

CREATE TABLE `user_app` (
	`id` int NOT NULL AUTO_INCREMENT,
	`username` VARCHAR(1000) NOT NULL,
	`password` VARCHAR(1000) NOT NULL,
	`email` VARCHAR(1000) DEFAULT NULL,
    PRIMARY KEY (`id`),
	UNIQUE `uq_username` (`username`),
	KEY `INDEX_username` (`username`) USING BTREE
);