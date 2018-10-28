create table LyonBlog.visitor_info
(
	id bigint(50) unsigned auto_increment
		primary key,
	userid int(50) null,
	articleId int(50) null,
	os varchar(10) not null,
	browser varchar(10) default '' not null,
	ip varchar(30) not null,
	url varchar(50) not null,
	viewdate datetime not null
)
;

