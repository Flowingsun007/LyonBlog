create table LyonBlog.blog_user
(
	id bigint(30) auto_increment
		primary key,
	username varchar(30) default '' not null,
	userpass varchar(50) default '' not null,
	telephone varchar(30) not null,
	useremail varchar(30) default '' not null,
	headImage varchar(50) null,
	salt varchar(50) default '' null,
	userstatus tinyint default '0' null,
	constraint telephone_unique
		unique (telephone),
	constraint useremail_unique
		unique (useremail)
)
;

