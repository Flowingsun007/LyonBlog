create table LyonBlog.role
(
	roleid tinyint(20) not null
		primary key,
	role varchar(50) not null,
	description varchar(50) not null comment '角色描述'
)
;

