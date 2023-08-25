# BI平台

## 开发流程
1. 提交分析目标和分析文件（excel）
2. 后台校验内容，然后调用AI接口，返回响应

## 接口访问
- http://localhost:8080/doc.html

## AI优化
- 持续输入、持续优化
- 接受内容有限，输入数据压缩、内容压缩（可以使用AI压缩）

## AI调用技巧
- 给定身份
- 数据格式
- 详细描述，控制输入输出

## 整合ES
- SpringBoot和ES版本关系
  - https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/
- maven依赖
```xml
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-elasticsearch -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
    <version>2.7.2</version>
</dependency>
```

## es地址
https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-search.html







## AI问答
1. 通过内容提问，调用AI进行问答，将AI问答内容入库
2. AI问答内容通过es进行分词处理
3. es搜索关键词，带出相关问答文章
4. ik分词器 https://gitee.com/star8521/elasticsearch-analysis-ik?_from=gitee_search


## 数据字典表设计
1. 类型和数据分开
- 字典类型表(sys_dict_type)
  - 主键（id）、字典统一编码（分类dict_type）、字典名称(dict_name)、状态（status 启用、禁用）、remark备注、
- 字段数据表(sys_dict_data)
  - id、dict_type(关联字段)、dict_label、dict_value、status(enable,disable)、dict_sort、remark

2. 在一张表，通过fid进行区分，fid为0的为根类型，
- 字段数据表(sys_dict)
    - id、字典统一编码（分类dict_type，跟随父类）、字典名称(dict_name，跟随父类)、dict_label、dict_value、status(enable,disable)、dict_sort（默认0）、remark、fid(父类id，默认0)

## RabbitMQ

### 安装
#### macos homebrew安装
- 官方文档：https://www.rabbitmq.com/install-homebrew.html
  - 安装 brew install rabbitmq
  - 启动 brew services start rabbitmq
- homebrew启动服务报错 https://www.niwoxuexi.com/blog/cheng/article/2229
- 访问不到5672端口的问题 https://blog.csdn.net/aduocd/article/details/112783498

#### centos 安装
- erlang和mq的包可以从github下载
- erlang安装(Direct Downloads from GitHub)： https://github.com/rabbitmq/erlang-rpm
  - curl -sfL -O https://github.com/rabbitmq/erlang-rpm/releases/download/v25.3.2.2/erlang-25.3.2.2-1.el7.x86_64.rpm
- 安装mq，并启动服务
  - http://www.taodudu.cc/news/show-64999.html?action=onClick
- 创建mq账户 https://www.rabbitmq.com/access-control.htm



## 常见问题
### MybatisX 选择module路径不对，
删除.idea, restart idea