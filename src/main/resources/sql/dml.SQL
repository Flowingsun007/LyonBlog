INSERT INTO `article_main_categary` (`id`, `name`)
VALUES
	(1, 'Java'),
	(2, 'Python和数据'),
	(3, '数据库服务器'),
	(4, '计算机相关'),
	(5, '诗和远方');


INSERT INTO `article_second_categary` (`id`, `mainId`, `name`)
VALUES
  (1, 1, 'Java基础'),
  (2, 1, 'Spring系列'),
  (3, 1, 'Java项目实战'),
  (4, 1, '源码和理论'),
  (5, 1, '开源框架'),
  (6, 2, 'Python基础'),
  (7, 2, '爬虫和数据'),
  (8, 2, '算法和机器学习'),
  (9, 2, '常用库和应用'),
  (10, 3, 'MySQL'),
  (11, 3, 'NoSQL'),
  (12, 3, 'Redis'),
  (13, 3, '消息队列'),
  (14, 3, 'Tomcat'),
  (15, 3, 'Nginx'),
  (16, 4, 'Linux'),
  (17, 4, '计算机基础'),
  (18, 4, '实用工具'),
  (19, 4, '技术网站'),
  (20, 5, '分享转载'),
  (21, 5, '个人日记'),
  (22, 5, '书和视频'),
  (23, 5, '行為藝術'),
  (24, 5, '健康生活'),
  (25, 4, 'Web前端');




INSERT INTO `permission` (`permissionid`, `permission`, `description`)
VALUES
  (1, 'admin:delete', '删除文章操作'),
  (2, 'admin:editBlog', '编辑文章内容'),
  (3, 'admin:editTag', '修改文章标签'),
  (4, 'article:changeCategory', '修改文章分类'),
  (5, 'admin:home', '后台管理首页'),
  (6, 'article:comment', '评论文章'),
  (7, 'behavior:thank', '感谢文章'),
  (8, 'article:collect', '收藏文章'),
  (9, 'article:share', '分享文章'),
  (10, 'article:single', '浏览某篇文章'),
  (11, 'article:category', '浏览分类文章'),
  (12, 'user:userInfo', '取用户登录信息'),
  (13, 'behavior:uploadImage', '上传图片'),
  (14, 'behavior:elasticSearch', '关键字文章搜索');


INSERT INTO `role` (`roleid`, `role`, `description`)
VALUES
  (1, 'blogAdmin', '系统创建者'),
  (2, 'blogManager', '博客管理员'),
  (3, 'consumer', 'VIP会员'),
  (4, 'register', '注册用户'),
  (5, 'register_unchecked', '未激活注册用户'),
  (6, 'staff', '普通游客');



INSERT INTO `role_permission` (`role_id`, `permission_id`)
VALUES
  (1, 1),
  (1, 2),
  (1, 3),
  (1, 4),
  (1, 5),
  (1, 6),
  (1, 7),
  (1, 8),
  (1, 9),
  (1, 10),
  (1, 11),
  (1, 12),
  (1, 13),
  (1, 14),
  (2, 3),
  (2, 5),
  (2, 6),
  (2, 7),
  (2, 8),
  (2, 9),
  (2, 10),
  (2, 11),
  (2, 12),
  (2, 13),
  (2, 14),
  (3, 6),
  (3, 7),
  (3, 8),
  (3, 9),
  (3, 10),
  (3, 11),
  (3, 12),
  (3, 13),
  (3, 14),
  (4, 6),
  (4, 7),
  (4, 8),
  (4, 9),
  (4, 10),
  (4, 11),
  (4, 12),
  (4, 13),
  (4, 14),
  (5, 9),
  (5, 10),
  (5, 11),
  (5, 12),
  (5, 14),
  (6, 9),
  (6, 10),
  (6, 11),
  (6, 12);

