-- V1__create_user_app.sql
CREATE TABLE IF NOT EXISTS user_app (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) DEFAULT NULL,
    active_token VARCHAR(500) DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uq_username (username),
    UNIQUE KEY active_token_UNIQUE (active_token),
    KEY INDEX_username (username) USING BTREE,
    KEY INDEX_active_token (active_token) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
