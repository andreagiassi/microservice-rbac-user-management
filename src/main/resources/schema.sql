DROP TABLE IF EXISTS contacts;
DROP TABLE IF EXISTS addresses;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

CREATE TABLE `roles` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `role` varchar(80) NOT NULL UNIQUE
);

CREATE TABLE `users` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `username` varchar(100) NOT NULL UNIQUE,
  `password` varchar(255) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `surname` varchar(100) DEFAULT NULL,
  gender TINYINT DEFAULT NULL,
  enabled TINYINT DEFAULT '1',
  `role_id` BIGINT(20) NOT NULL DEFAULT '1',
  creation_dt timestamp NOT NULL DEFAULT current_timestamp,
  updated_dt timestamp DEFAULT current_timestamp,
  login_dt timestamp NULL,
  note varchar(255) DEFAULT NULL,
  FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE `contacts` (
  user_id BIGINT(20) NOT NULL PRIMARY KEY,
  `email` varchar(255) NOT NULL UNIQUE,
  `phone` varchar(20) DEFAULT NULL,
  skype varchar(255) DEFAULT NULL,
  facebook varchar(255) DEFAULT NULL,
  linkedin varchar(255) DEFAULT NULL,
  website varchar(255) DEFAULT NULL,
  note varchar(255) DEFAULT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE `addresses` (
  user_id BIGINT(20) NOT NULL PRIMARY KEY,
  `address` varchar(255) DEFAULT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `country` varchar(20) DEFAULT NULL,
  `zip_code` varchar(20) DEFAULT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

DROP VIEW IF EXISTS enabled_users;

CREATE VIEW enabled_users AS
SELECT username, roles.role, contacts.email, contacts.phone
FROM users
INNER JOIN roles on roles.id = users.role_id
INNER JOIN contacts on contacts.user_id = users.id
WHERE
enabled IS TRUE;
