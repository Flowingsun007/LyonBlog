create table LyonBlog.discussion
(
	id int(50) auto_increment
		primary key,
	articleId int(30) not null,
	main_userId bigint(50) not null,
	main_commentId int(30) not null,
	userId bigint not null,
	discussion varchar(50) default '' not null,
	discussDate timestamp default CURRENT_TIMESTAMP null
)
;

