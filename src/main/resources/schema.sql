DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS `users` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL UNIQUE,
  creation_dt timestamp NOT NULL DEFAULT current_timestamp,
  updated_dt timestamp DEFAULT NULL
);
