create table LyonBlog.article_tag
(
	id int(10) auto_increment
		primary key,
	tagName varchar(50) default '' not null,
	createDate datetime not null,
	constraint tagName
		unique (tagName)
)
;

