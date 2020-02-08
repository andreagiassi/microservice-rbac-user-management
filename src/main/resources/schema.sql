DROP TABLE IF EXISTS contacts;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

CREATE TABLE `roles` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `role` varchar(80) NOT NULL UNIQUE
);

CREATE TABLE `users` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `username` varchar(100) NOT NULL UNIQUE,
  `name` varchar(100) DEFAULT NULL,
  `surname` varchar(100) DEFAULT NULL,
  gender TINYINT DEFAULT NULL,
  enabled TINYINT DEFAULT '1',
  `role_id` BIGINT(20) NOT NULL DEFAULT '1',
  creation_dt timestamp NOT NULL DEFAULT current_timestamp,
  updated_dt timestamp DEFAULT NULL,
  note varchar(255) DEFAULT NULL,
  FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE `contacts` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT(20) NOT NULL,
  `email` varchar(255) NOT NULL UNIQUE,
  `phone` varchar(20) DEFAULT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id)
);