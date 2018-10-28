create table LyonBlog.comment_like
(
	id int(50) auto_increment
		primary key,
	articleId int(30) not null,
	mainUserId bigint(50) not null,
	commentId int(50) not null,
	userId bigint(50) not null,
	likeDate timestamp default CURRENT_TIMESTAMP null
)
;

