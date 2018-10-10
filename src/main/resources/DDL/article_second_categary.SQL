create table LyonBlog.article_second_categary
(
	id int(10) auto_increment
		primary key,
	mainId int(10) null,
	name varchar(50) null,
	constraint fk_secondary_category
		foreign key (mainId) references lyonblog.article_main_categary (id)
)
;

