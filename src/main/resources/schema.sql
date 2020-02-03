DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
