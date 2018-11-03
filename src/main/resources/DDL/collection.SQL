CREATE TABLE `collection` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `articleId` int(50) DEFAULT NULL,
  `userId` bigint(50) DEFAULT NULL,
  `collectDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uaerId-articleId_unique_key` (`userId`,`articleId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

