这是在第一版的【个人博客系统】 的基础上开发的第二版个人博客系统，基于SSM框架的Java8、Maven构建，详细说明参考知乎——https://zhuanlan.zhihu.com/p/46244562

技术栈
前端：采用基础的Html+css+js，Bootstrap+Jquery+Ajax;页面模板采用的是前后端分离的:Velocity
后端：基于Spring、SpringMVC、Mybatis
数据库：MySQL、Druid连接池
其他技术:Nginx、Redis、Tomcat、Shrio权限控制、FastJson、谷歌protostuff序列化、开发:Idea、版本控制:Git、日志:Log4J。

Nginx做请求转发(负载均衡)兼动静分离。一台Nginx将动态请求分发至2台Tomcat上，静态请求(图片、js、css等)Nginx直接处理。由于2个Tomcat可能部署在不同的服务器上，故涉及到session共享的问题，此处用redis来管理所有session,同时redis兼缓存一些文章分类信息、标签信息等。

优势:
1.Nginx动静分离，可以加快访问速度，多Tomcat有效均衡节点负载
2.Redis实现共享session，速度快，用户访问任意Tomcat后访问信息不丢失。
劣势:
1.此处只有一个Nginx,没有备用机器，万一Nginx所在的服务器挂了，整个项目就挂了。
2.目前使用2个Tomcat,后期根据需求可以拓展多个Tomcat，不过随着Tomcat数量的增加，Redis存session的压力也会随之增加。
3.有的功能只是简单使用，并没有涉及复杂的使用，整个项目的有些流程和功能也不太完善，比较适合新手学习折腾。
