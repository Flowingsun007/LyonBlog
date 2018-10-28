create table LyonBlog.picture
(
	id int(30) auto_increment
		primary key,
	userId bigint(30) not null,
	createDate datetime not null,
	filePath varchar(150) default '' not null,
	details varchar(150) default '' not null,
	constraint filePath
		unique (filePath)
)
;

