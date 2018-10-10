create table LyonBlog.comment
(
	id int(50) auto_increment
		primary key,
	articleId int(30) not null,
	userId bigint(50) not null,
	comment varchar(50) default '' not null,
	commentDate timestamp default CURRENT_TIMESTAMP null
)
;

