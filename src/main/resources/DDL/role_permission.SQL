create table LyonBlog.role_permission
(
	role_id tinyint(20) not null,
	permission_id tinyint(20) not null,
	constraint role_permission_nuique
		unique (role_id, permission_id)
)
;

