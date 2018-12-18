# 【概述】
这是在第一版的【个人博客系统】 的基础上开发的第二版个人博客系统，基于SSM框架的Java8、Maven构建，github地址:https://github.com/Flowingsun007/LyonBlog
这个项目是个人学java时自己做的，从前端-到后端-数据库都是100%原创的~第一版的项目是jsp+servlet写的，学了SSM后重构了一次才有的这个项目，里面代码写的比较水，勿喷~新人学习玩耍还是可以的~P.S.后面有时间会不断重构和优化里面的代码^_^

## 1.项目展示
### 功能：
用户登录注册、MD5加密、邮箱验证。
博客文章前台展示、文章点赞、评论、收藏，支持对评论进行点赞和讨论。
后台admin管理，支持文章的新增、修改、删除、文章和标签批量管理。
个人中心，查看收藏的文章、用户上传图片、照片墙。

## 2.技术栈
### 前端：
html+css+javascript;bootstrap+jquery+ajax;
### 模板引擎：
velocity
### 开源弹框组件：
sweetalert
### 开源Markdown编辑器：
Editor.md
### 容器：
Tomcat
### 权限：
Shrio
### 后端：
Spring+SpringMVC+Mybatis+Mysql+Druid
### Redis：
由于2个Tomcat可能部署在不同的服务器上，故涉及到session共享的问题，此处用redis来管理所有session,同时redis兼缓存一些文章分类信息、标签信息等。
### Nginx：
反向代理+动静分离。Nginx作为统一入口，静态资源请求如js、图片、css文件等直接由nginx处理，动态请求转发至Tomcat中处理。
目前配置了2个Tomcat，Nginx采取默认的轮训处理请求。
### ElasticSearch：
ElasticSearch是流行的全文检索服务器，主要用于博客搜索。Logstash设定了简单的增量导入，从Mysql中定时查询文章内容放入Elasticsearch中，从而提供博客文章全文检索的功能，避免直接查数据库带来较大的开销。
开发环境：
MacOS系统+Idea开发+Maven构建+Git版本控制
### 架构图：


## 3.项目部署
由于项目是在Mac上开发的，所以对Linux系统比较友好。Github地址：Flowingsun007/LyonBlog ，欢迎点赞~
有三个分支：
master：本地mac上开发，同步更新的分支
server：用于部署服务器的分支
windows：仅仅是为了跑起来而新建的此分支，不保证及时更新
### 项目标准配置：

1.Mysql——5.6
2.Tomcat——9(2个)
3.Nginx——1.14.0
4.Redis——4.0.10
5.Elaticsearch、Logstash——6.4.2
#### Mysql配置
a.数据库连接：resources文件夹下的jdbc.properties
b.数据库的sql包括ddl和dml，放在项目resources文件夹下。
#### Tomcat配置
只需要改server.xml文件中的几个地方即可，这里提供我本地的配置文件做参考。
Nginx、Redis配置
同样，提供本地参考，所有的关键配置文件都放在resources/conf文件夹下。
#### log4j配置
主要是log4j.properties里修改几种类别日志存放路径
### 运行顺序
Nginx和Redis先启动，然后启动项目，在Idea中可以直接启动Tomcat、或者手动打war包放入Tomcat中运行。

