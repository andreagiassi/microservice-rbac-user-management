DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

CREATE TABLE `roles` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `role` varchar(80) NOT NULL UNIQUE
);

CREATE TABLE `users` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `username` varchar(255) NOT NULL UNIQUE,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL UNIQUE,
  gender TINYINT DEFAULT NULL,
  enabled TINYINT DEFAULT '1',
  `phone` varchar(20) DEFAULT NULL,
  `role_id` BIGINT(20) NOT NULL DEFAULT '1',
  creation_dt timestamp NOT NULL DEFAULT current_timestamp,
  updated_dt timestamp DEFAULT NULL,
  FOREIGN KEY (role_id) REFERENCES roles(id)
);
