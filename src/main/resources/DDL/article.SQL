create table LyonBlog.article
(
	id int(20) auto_increment
		primary key,
	userid int(10) not null,
	article_title varchar(200) null,
	article_abstract varchar(500) null,
	article_content mediumtext null,
	create_date datetime null,
	edit_date datetime null,
	article_second_id int(10) null,
	article_main_id int(5) null,
	article_content_copy text null,
	constraint article_mainCategory_fkey
		foreign key (article_main_id) references lyonblog.article_main_categary (id),
	constraint article_secondCategory_fkey
		foreign key (article_second_id) references lyonblog.article_second_categary (id)
)
;

create index fk_article_categary
	on LyonBlog.article (article_second_id)
;

create index fk_article_maincat
	on LyonBlog.article (article_main_id)
;

