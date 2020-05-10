ALTER TABLE `brain1`.`topic` 
ADD COLUMN `current_posts` INT NOT NULL DEFAULT 0 AFTER `active_currently`;

CREATE TABLE `post_tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `post_id` varchar(45) NOT NULL,
  `tag_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `post_id_f_idx` (`post_id`),
  KEY `tag_id_f_idx` (`tag_id`),
  CONSTRAINT `post_id_f` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`),
  CONSTRAINT `tag_id_f` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `wrongly_answered` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` varchar(29) DEFAULT NULL,
  `pid` varchar(45) NOT NULL,
  `topic` varchar(45) DEFAULT NULL,
  `answered_wrongly_times` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `uid_i` (`uid`),
  KEY `topic_f_wa` (`topic`),
  KEY `pid_f_wa_idx` (`pid`),
  CONSTRAINT `pid_f_wa` FOREIGN KEY (`pid`) REFERENCES `post` (`id`),
  CONSTRAINT `topic_f_wa` FOREIGN KEY (`topic`) REFERENCES `topic` (`name`),
  CONSTRAINT `uid_f` FOREIGN KEY (`uid`) REFERENCES `user` (`firebase_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
