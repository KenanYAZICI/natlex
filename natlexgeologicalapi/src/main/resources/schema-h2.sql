CREATE TABLE `section` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL,
  `last_modified_date` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `section_name` (`name`)
);

CREATE TABLE `geological` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `code` varchar(255),
  `section_id` int(11) NOT NULL,  
  `created_date` datetime NOT NULL,
  `last_modified_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `geological_name` (`name`),
  CONSTRAINT `geological_section_fk` FOREIGN KEY (`section_id`) REFERENCES `section` (`id`)
);