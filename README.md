# 【概述】
这个项目是个人学java时自己做的，从前端-到后端-数据库都是100%原创的~最初的版本是jsp+servlet写的，学了SSM后重构了一次才有的这个SSM版的【个人博客项目】，里面代码写的比较水，没有用统一的异常处理类，很多Java代码没有精简抽象，有的模块也只是具备基本功能，对不起观众了~囧！新人看一看，玩一玩还是可以的
## 1.项目展示
### 【页面】：
![](https://pic3.zhimg.com/v2-8938aa61be97b55c37009744c761a44a_r.jpg)
![](https://pic3.zhimg.com/v2-cdc3a4b107a010dc54203b33bfc50176_r.jpg)
![](https://pic2.zhimg.com/v2-5f1e438570a24223d22356e0e48c91ed_r.jpg)
### 【功能】：
用户登录注册、MD5加密、邮箱验证。
博客文章前台展示、文章点赞、评论、收藏，支持对评论进行点赞和讨论。
后台admin管理，支持文章的新增、修改、删除、文章和标签批量管理。
个人中心，查看收藏的文章、用户上传图片、照片墙。
## 2.技术栈
#### 开发环境：
macOS/Win10系统+Idea开发+Maven构建+Git版本控制
#### 前端：
html+css+javascript;bootstrap+jquery+ajax;
#### 模板引擎：
velocity
#### 开源弹框组件：
sweetalert
#### Markdown编辑器：
Editor.md
#### 容器：
Tomcat9
#### 权限：
Apache Shrio
#### 后端：
Spring+SpringMVC+Mybatis+Mysql+Druid
#### 缓存+session管理：
Redis。由于2个Tomcat可能部署在不同的服务器上，故涉及到session共享的问题，此处用redis来管理所有session,同时redis兼缓存一些文章分类信息、标签信息等。
#### 反向代理+动静分离：
Nginx。Nginx作为统一入口，静态资源请求如js、图片、css文件等直接由nginx处理，动态请求转发至Tomcat中处理。
目前配置了2个Tomcat，Nginx采取默认的轮训处理请求。
#### 全文检索：
ElasticSearch是流行的全文检索服务器，主要用于博客搜索。Logstash设定了简单的增量导入，从Mysql中定时查询文章内容放入Elasticsearch中，从而提供博客文章全文检索的功能，避免直接查数据库带来较大的开销。
#### 架构图：
![](https://pic3.zhimg.com/80/v2-72fae8ab4a314f253d214dcb3f70d80e_hd.jpg)
## 3.项目部署
由于主项目是在Mac上开发的，所以对Linux系统比较友好。Github地址：Flowingsun007/LyonBlog ，欢迎点赞~
### 【分支】：
* master：本地mac上开发，同步更新的分支
* centOS：用于在阿里云服务器上的分支，基本和master分支类似，有个别配置如jdbc.properties、log4j.properties、EmailService.java(发送注册邮件)有些许不同
* windows：仅仅是为了跑起来而新建的此分支，不保证及时更新。BTW:我发现mac上看的前端效果比较好看，到windows上就比较丑了....我也很无奈~
### 【项目标准配置】：
* 0.JDK1.8
* 1.Mysql5.7
* 2.Tomcat9(2个)
* 3.Nginx1.14.0
* 4.Redis4.0.10
* 5.Elaticsearch、Logstash——6.4.2
#### Mysql配置
* a.数据库连接：resources文件夹下的jdbc.properties
* b.数据库的sql包括ddl和dml，放在项目resources文件夹下。
#### Tomcat配置
只需要改server.xml文件中的几个地方即可，这里提供我本地的配置文件做参考。
#### log4j配置
主要是log4j.properties里修改几种类别日志存放路径
#### Nginx、Redis配置
同样，提供本地参考，所有的关键配置文件都放在resources/conf文件夹下。
#### 静态资源配置
由于项目用的是Nginx，动静分离的，所以静态资源全部放置在Nginx下的html文件夹下，可以在百度网盘下载：
链接：https://pan.baidu.com/s/1K2Ahz_L4cYR04YtgdCmSGA 
提取码：bdbb 
### 【启动和运行】
#### 项目启动标准配置：
Nginx+Redis+2个Tomcat+Elasticsearch+Logstash
#### 项目启动最简配置：
Nginx+Redis+1个Tomcat
#### 启动顺序：
首先安装好Mysql，Mysql的表和数据建好、安装和配置Tomcat、Nginx、Redis，然后就可以启动了。
Nginx和Redis先启动，然后启动Tomcat，在Idea中可以直接启动Tomcat、或者手动打war包放入Tomcat中运行。
##### Tomcat启动完成后再启动Elasticsearch+logstash。Elasticsearch+logstash是可选项，可以不启动。
### 【文件夹结构】
![](https://pic1.zhimg.com/80/v2-a95cec3239527b265b191c368e888e04_hd.jpg)
