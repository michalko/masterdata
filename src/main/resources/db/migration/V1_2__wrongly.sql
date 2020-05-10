
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
