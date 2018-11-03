create table LyonBlog.thank
(
	id int(50) auto_increment
		primary key,
	articleId int(30) null,
	userId bigint(50) null,
	thankDate datetime null,
	constraint `uaerId-articleId_unique_key`
		unique (userId, articleId)
)
;

