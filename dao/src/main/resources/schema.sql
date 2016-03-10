
drop table if exists `departments`;
drop table if exists `workers`;

CREATE TABLE `departments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  PRIMARY KEY (`id`))DEFAULT CHARSET=utf8;

  CREATE TABLE `workers` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `did` INT NULL,
    `name` VARCHAR(100) NULL,
    `birthday` DATE NULL,
    `salary` INT NULL,
    PRIMARY KEY (`id`))DEFAULT CHARSET=utf8;