-- Create syntax for TABLE 'article_main_categary'
CREATE TABLE `article_main_categary`
(
  `id`   int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'article_second_categary'
CREATE TABLE `article_second_categary`
(
  `id`     int(10) NOT NULL AUTO_INCREMENT,
  `mainId` int(10) DEFAULT NULL,
  `name`   varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY      `fk_secondary_category` (`mainId`),
  CONSTRAINT `fk_secondary_category` FOREIGN KEY (`mainId`) REFERENCES `article_main_categary` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'article_tag'
CREATE TABLE `article_tag`
(
  `id`         int(10) NOT NULL AUTO_INCREMENT,
  `tagName`    varchar(50) NOT NULL DEFAULT '',
  `createDate` datetime    NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tagName` (`tagName`)
) ENGINE=InnoDB AUTO_INCREMENT=543 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'article_tag_relation'
CREATE TABLE `article_tag_relation`
(
  `id`         int(20) NOT NULL AUTO_INCREMENT,
  `articleId`  int(20) NOT NULL,
  `tagName`    varchar(30) NOT NULL DEFAULT '',
  `tagId`      int(10) NOT NULL,
  `createDate` datetime    NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1102 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'blog_user'
CREATE TABLE `blog_user`
(
  `id`         bigint(30) NOT NULL AUTO_INCREMENT,
  `username`   varchar(30) NOT NULL DEFAULT '',
  `userpass`   varchar(50) NOT NULL DEFAULT '',
  `telephone`  varchar(30) NOT NULL,
  `useremail`  varchar(30) NOT NULL DEFAULT '',
  `headImage`  varchar(50)          DEFAULT NULL,
  `salt`       varchar(50)          DEFAULT '',
  `userstatus` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `telephone_unique` (`telephone`),
  UNIQUE KEY `useremail_unique` (`useremail`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'collection'
CREATE TABLE `collection`
(
  `id`          int(50) NOT NULL AUTO_INCREMENT,
  `articleId`   int(50) NOT NULL,
  `userId`      bigint(50) NOT NULL,
  `collectDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uaerId-articleId_unique_key` (`userId`,`articleId`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'comment'
CREATE TABLE `comment`
(
  `id`          int(50) NOT NULL AUTO_INCREMENT,
  `articleId`   int(30) NOT NULL,
  `userId`      bigint(50) NOT NULL,
  `comment`     varchar(50) NOT NULL DEFAULT '',
  `commentDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'comment_like'
CREATE TABLE `comment_like`
(
  `id`         int(50) NOT NULL AUTO_INCREMENT,
  `articleId`  int(30) NOT NULL,
  `mainUserId` bigint(50) NOT NULL,
  `commentId`  int(50) NOT NULL,
  `userId`     bigint(50) NOT NULL,
  `likeDate`   timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'discussion'
CREATE TABLE `discussion`
(
  `id`             int(50) NOT NULL AUTO_INCREMENT,
  `articleId`      int(30) NOT NULL,
  `main_userId`    bigint(50) NOT NULL,
  `main_commentId` int(30) NOT NULL,
  `userId`         bigint(20) NOT NULL,
  `discussion`     varchar(50) NOT NULL DEFAULT '',
  `discussDate`    timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'permission'
CREATE TABLE `permission`
(
  `permissionid` tinyint(20) NOT NULL,
  `permission`   varchar(50) NOT NULL,
  `description`  varchar(50) NOT NULL,
  PRIMARY KEY (`permissionid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'picture'
CREATE TABLE `picture`
(
  `id`         int(30) NOT NULL AUTO_INCREMENT,
  `userId`     bigint(30) NOT NULL,
  `createDate` datetime     NOT NULL,
  `filePath`   varchar(150) NOT NULL DEFAULT '',
  `details`    varchar(150) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `filePath` (`filePath`)
) ENGINE=InnoDB AUTO_INCREMENT=272 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'role'
CREATE TABLE `role`
(
  `roleid`      tinyint(20) NOT NULL,
  `role`        varchar(50) NOT NULL,
  `description` varchar(50) NOT NULL COMMENT '角色描述',
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'role_permission'
CREATE TABLE `role_permission`
(
  `role_id`       tinyint(20) NOT NULL,
  `permission_id` tinyint(20) NOT NULL,
  UNIQUE KEY `role_permission_nuique` (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'thank'
CREATE TABLE `thank`
(
  `id`        int(50) NOT NULL AUTO_INCREMENT,
  `articleId` int(30) NOT NULL,
  `userId`    bigint(50) NOT NULL,
  `thankDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uaerId-articleId_unique_key` (`userId`,`articleId`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'user_role'
CREATE TABLE `user_role`
(
  `user_id` bigint(20) NOT NULL,
  `role_id` tinyint(20) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'article'
CREATE TABLE `article`
(
  `id`                   int(20) NOT NULL AUTO_INCREMENT,
  `userid`               int(10) NOT NULL,
  `article_title`        varchar(200) DEFAULT NULL,
  `article_abstract`     varchar(500) DEFAULT NULL,
  `article_content`      mediumtext,
  `create_date`          datetime     DEFAULT NULL,
  `edit_date`            datetime     DEFAULT NULL,
  `article_second_id`    int(10) DEFAULT NULL,
  `article_main_id`      int(5) DEFAULT NULL,
  `article_content_copy` text,
  `public_permission` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY                    `fk_article_categary` (`article_second_id`),
  KEY                    `fk_article_maincat` (`article_main_id`),
  CONSTRAINT `article_mainCategory_fkey` FOREIGN KEY (`article_main_id`) REFERENCES `article_main_categary` (`id`),
  CONSTRAINT `article_secondCategory_fkey` FOREIGN KEY (`article_second_id`) REFERENCES `article_second_categary` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=368 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'visitor_info'
CREATE TABLE `visitor_info`
(
  `id`        bigint(50) unsigned NOT NULL AUTO_INCREMENT,
  `userId`    bigint(50) DEFAULT NULL,
  `articleId` int(50) DEFAULT NULL,
  `os`        varchar(10)  NOT NULL,
  `browser`   varchar(10)  NOT NULL DEFAULT '',
  `ip`        varchar(30)  NOT NULL,
  `sourceUrl` varchar(100)          DEFAULT NULL,
  `targetUrl` varchar(100) NOT NULL DEFAULT '',
  `dateTime`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4307 DEFAULT CHARSET=utf8;
