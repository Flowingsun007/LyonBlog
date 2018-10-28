create table LyonBlog.article_tag_relation
(
	id int(20) auto_increment
		primary key,
	articleId int(20) not null,
	tagName varchar(30) default '' not null,
	tagId int(10) not null,
	createDate datetime not null
)
;

