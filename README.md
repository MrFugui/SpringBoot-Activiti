## 啦啦啦啦啦，富贵同学又开始开坑了，出了个免费的专栏，主要给大家从0基础开始用springBoot集成第三方的插件或者功能，如果这篇专栏能帮到你，一定不要忘了点一个赞哦！！欢迎大家收藏分享
![在这里插入图片描述](https://img-blog.csdnimg.cn/385dc942abfc4a019d845055328814c1.png#pic_center)
## 第一步，导入jar包

```java
		  <dependency>
            <groupId>org.activiti</groupId>
            <artifactId>activiti-spring-boot-starter-basic</artifactId>
            <version>5.22.0</version>
        </dependency>
```
这是activiti的核心包，我们还添加其他的jar包

```java
		  <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.30</version>
        </dependency>
```
## 第二步，编写配置文件

```java
server:
  port: 8089
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/activiti
    driver-class-name: com.mysql.jdbc.Driver
    username: root
    password: 123456
```
## 第三步，创建activiti库
![在这里插入图片描述](https://img-blog.csdnimg.cn/c1e5a2c049834f8294b8934e335cb638.png)

## 第四步，运行启动类
![在这里插入图片描述](https://img-blog.csdnimg.cn/4b3fd9ef3d8247b4ada367b2becfbd90.png)
好，遇到这个时候就出现问题了，这是富贵同学替大家踩坑了，大家记得避开
**启动报错Could not find class [org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration]**
这个问题是activiti里面本身会依赖Security，所以我们将他排除，我们在启动类上加上

```java
exclude = SecurityAutoConfiguration.class
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/f933930881cf4f7fbfc3f2bbe59c88f7.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5o6J5aS05Y-R55qE546L5a-M6LS1,size_18,color_FFFFFF,t_70,g_se,x_16)
好的，我们启动，这个时候又碰见另外的一个问题
**Caused by: java.io.FileNotFoundException: class path resource [processes/] cannot be resolved to URL because it does not exist**
这个是因为activiti在启动的时候会去找bpm文件，会去到默认的processes文件夹中找，
这个时候我们在配置类中加入

```java
  activiti:
    check-process-definitions: false
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/3fe6c68b88894df7b53e58b2bc16c122.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5o6J5aS05Y-R55qE546L5a-M6LS1,size_14,color_FFFFFF,t_70,g_se,x_16)
这个时候就启动成功啦！
![在这里插入图片描述](https://img-blog.csdnimg.cn/53fcb552cb2b404ba4b3ce748c4f38bb.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5o6J5aS05Y-R55qE546L5a-M6LS1,size_20,color_FFFFFF,t_70,g_se,x_16)
## 第五步.启动成功之后查看我们的数据库
![在这里插入图片描述](https://img-blog.csdnimg.cn/7297ae6bcaf54c06b47d2b0bc51e388c.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5o6J5aS05Y-R55qE546L5a-M6LS1,size_20,color_FFFFFF,t_70,g_se,x_16)
就会发现25张表已经自动生成了！
好了，就是这么的简单，完整代码请移至[SpringBoot+Activiti ](https://gitee.com/WangFuGui-Ma/spring-boot-activiti)查看
![在这里插入图片描述](https://img-blog.csdnimg.cn/a866736dfb41420f8d8a8484d1e9abb7.jpg?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA5o6J5aS05Y-R55qE546L5a-M6LS1,size_10,color_FFFFFF,t_70,g_se,x_16#pic_center)
