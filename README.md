#  **MySpringBoot** 

## 项目介绍
Spring Boot 2.1 的典型项目框架，主要应用在网站或移动App的服务端。  
涉及到的技术包括 Spring Boot、mybatis、swagger、logback、tk.mapper、pagehelper、RabbitMq、kafka、RocketMq、JWT、Snowflake等。  
项目有完整详细的使用说明、代码注释以及环境搭建说明。  

## 测试服务器环境搭建
本项目的运行依赖于测试服务器。  
这里主要介绍使用 CentOS + Docker Compose 搭建测试服务器环境。  

### CentOS + Doker Compose
部署CentOS7服务器。  
安装Docker： 
请参照此页说明安装：https://docs.docker.com/install/linux/docker-ce/centos/  
安装Docker Compose：  
```
yum -y install epel-release 
yum -y install python-pip 
pip install docker-compose 
docker-compose version
```
安装完Docker Compose后，在CentOS中建立一个工作目录，例如：
```
mkdir -p /work/docker/volumes
```
其中docker目录中存放docker-compose.yml文件，volumes目录中存放docker容器中映射出来的文件夹，这样会很清晰。

### MySQL
因为MySQL和mariadb完全兼容，这里我们选择安装mariadb，具体的docker-compose配置：
```
  mariadb:
    image: mariadb
    container_name: mymariadb
    ports:
      - "3306:3306"
    volumes:
      - "/work/docker/volumes/mariadb/data:/var/lib/mysql"
    environment:
      MYSQL_ROOT_PASSWORD: "123456"
      MYSQL_ROOT_HOST: "%"
```
这里将容器的数据文件夹映射到了：/work/docker/volumes/mariadb/data

### RabbitMQ
要下载带management插件的rabbitmq，否则没有管理端：  
docker-compose配置：
```
  rabbitmq:
    image: rabbitmq:management
    container_name: myrabbitmq
    hostname: rabbitmq1
    ports:
      - "5671:5671"
      - "5672:5672"
      - "4369:4369"
      - "25672:25672"
      - "15671:15671"
      - "15672:15672"
    volumes:
      - /work/docker/volumes/rabbitmq/data:/var/lib/rabbitmq
      - /work/docker/volumes/rabbitmq/log:/var/log/rabbitmq
```
容器启动成功之后就可以访问web管理端了：http://宿主机IP:15672，默认创建了一个 guest 用户，密码也是 guest。

### Rocket MQ
Rocket MQ 的 Docker 部署比较麻烦：
1. 先克隆项目：https://github.com/apache/rocketmq-externals
2. 修改 rocketmq-externals\rocketmq-docker\4.3.0\rocketmq-broker\Dockerfile 文件，在 sh mqbroker 语句后面加参数，改为： 
```
sh mqbroker -c /opt/conf/broker.properties
```
这样做可以把配置目录映射出来，便于在docker外配置broker。
3. 进入 rocketmq-externals\rocketmq-docker\4.3.0\ 目录，执行build命令，建立三个 Docker image：
```
docker build -t apache/rocketmq-base:4.3.0 --build-arg version=4.3.0 ./rocketmq-base
docker build -t apache/rocketmq-namesrv:4.3.0 ./rocketmq-namesrv
docker build -t apache/rocketmq-broker:4.3.0 ./rocketmq-broker
```                                                                        
4. 创建 /work/docker/volumes/rocketmq/broker/conf 文件夹，并添加配置文件 broker.properties ：
```
# 所属集群名字
# 附加：如果有多个master，那么每个master配置的名字应该一样，要不然识别不了对方，不知道是一个集群内部的
# brokerClusterName=rocketmq-cluster

# 此处需手动更改 #
# broker名字，注意此处不同的配置文件填写的不一样
# 附加：按配置文件文件名来匹配
brokerName=broker-0
# 0 表示Master, > 0 表示slave
brokerId=0

# 当前broker监听的IP
brokerIP1=192.168.255.100

# 存在broker主从时，在broker主节点上配置了brokerIP2的话,broker从节点会连接主节点配置的brokerIP2来同步
# brokerIP2=

# 此处许手动更改 #
#（此处nameserver跟host配置相匹配，9876为默认rk服务默认端口）nameServer 地址，分号分割
# 附加：broker启动时会跟nameserver建一个长连接，broker通过长连接才会向nameserver发新建的topic主题，然后java的客户端才能跟nameserver端发起长连接，向nameserver索取topic，找到topic主题之后，判断其所属的broker，建立长连接进行通讯，这是一个至关重要的路由的概念，重点，也是区别于其它版本的一个重要特性
# namesrvAddr=rocketmq-nameserver1:9876;rocketmq-nameserver2:9876

# 在发送消息时，自动创建服务器不存在的Topic，默认创建的队列数
# defaultTopicQueueNums=4

# 是否允许Broker 自动创建Topic，建议线下开启，线上关闭
autoCreateTopicEnable=true

# 是否允许Broker自动创建订阅组，建议线下开启，线上关闭
autoCreateSubscriptionGroup=true

# Broker 对外服务的监听端口
# listenPort=10911

# 删除文件时间点，默认是凌晨4点
# deleteWhen=04

# 文件保留时间，默认48小时
# fileReservedTime=120

# commitLog每个文件的大小默认1G
# 附加：消息实际存储位置，和ConsumeQueue是mq的核心存储概念，之前搭建2m环境的时候创建在store下面，用于数据存储，consumequeue是一个逻辑的概念，消息过来之后，consumequeue并不是把消息所有保存起来，而是记录一个数据的位置，记录好之后再把消息存到commitlog文件里
# mapedFileSizeCommitLog=1073741824

# ConsumeQueue每个文件默认存30W条，根据业务情况调整
# mapedFileSizeConsumeQueue=300000
# destroyMapedFileIntervalForcibly=120000
# redeleteHangedFileInterval=120000

# 检测物理文件磁盘空间
# diskMaxUsedSpaceRatio=88

# 存储路径
# storePathRootDir=/usr/local/rocketmq/store

# commitLog存储路径
# storePathCommitLog=/usr/local/rocketmq/store/commitlog

# 消费队列存储路径
# storePathConsumeQueue=/usr/local/rocketmq/store/consumequeue

# 消息索引存储路径
# storePathIndex=/usr/local/rocketmq/store/index

# checkpoint 文件存储路径
# storeCheckpoint=/usr/local/rocketmq/store/checkpoint

# abort 文件存储路径
# abortFile=/usr/local/rocketmq/store/abort

# 限制的消息大小
# maxMessageSize=65536
# flushCommitLogLeastPages=4
# flushConsumeQueueLeastPages=2
# flushCommitLogThoroughInterval=10000
# flushConsumeQueueThoroughInterval=60000

# Broker 的角色
# - ASYNC_MASTER 异步复制Master
# - SYNC_MASTER 同步双写Master
# - SLAVE
# brokerRote=ASYNC_MASTER

# 刷盘方式
# - ASYNC_FLUSH 异步刷盘
# - SYNC_FLUSH 同步刷盘
# flushDiskType=ASYNC_FLUSH
# checkTransactionMessageEnable=false

# 发消息线程池数量
# sendMessageTreadPoolNums=128

# 拉消息线程池数量
# pullMessageTreadPoolNums=128
```                                                                          
这里最重要的配置是：brokerIP1=192.168.255.100 ，这里根据服务器的实际访问IP进行配置。 
如果不配置此项，会导致客户端无法取得正确的 boker 访问地址，而导致消息收发失败。 

5. docker-compose配置，注意是3个容器：
```
  rocketmq-namesrv:
    image: apache/rocketmq-namesrv:4.3.0
    container_name: rmqnamesrv
    ports:
      - 9876:9876
    volumes:
      - /work/docker/volumes/rocketmq/namesrv/logs:/opt/logs
      - /work/docker/volumes/rocketmq/namesrv/store:/opt/store
      - /work/docker/volumes/rocketmq/broker/conf:/opt/conf
      
  rocketmq-broker:
    image: apache/rocketmq-broker:4.3.0
    container_name: rmqbroker
    ports:
      - 10909:10909
      - 10911:10911
    volumes:
      - /work/docker/volumes/rocketmq/broker/logs:/opt/logs
      - /work/docker/volumes/rocketmq/broker/store:/opt/store
    environment:
      NAMESRV_ADDR: "rmqnamesrv:9876"
      JAVA_OPT: "${JAVA_OPT} -server -Xms512m -Xmx512m -Xmn256m -XX:PermSize=128m -XX:MaxPermSize=128m"
    links:
      - rocketmq-namesrv:namesrv
    depends_on:
      - rocketmq-namesrv
      
  rocketmq-console-ng:
    image: styletang/rocketmq-console-ng
    container_name: rmqconsole
    ports:
      - 8090:8080
    environment:
      JAVA_OPTS: "-Drocketmq.namesrv.addr=192.168.255.100:9876 -Dcom.rocketmq.sendMessageWithVIPChannel=false"
```
注意看上文提到的配置文件目录被映射出来了。  
容器启动成功之后就可以访问web管理端了：http://宿主机IP:8090

### Kafka
Kafaka依赖zookeeper，我们这里使用wurstmeister/kafka和wurstmeister/zookeeper这两个版本的镜像。
管理使用sheepkiller/kafka-manager镜像。
docker-compose配置：
```
  zookeeper:
    image: wurstmeister/zookeeper
    container_name: myzookeeper
    ports:
      - 2181:2181
    volumes:
      - /etc/localtime:/etc/localtime
      
  kafka:
    image: wurstmeister/kafka
    container_name: mykafka
    ports:
      - 9092:9092
    volumes:
      - /etc/localtime:/etc/localtime
    links:
      - zookeeper
    depends_on:
      - zookeeper
    environment:
      KAFKA_ZOOKEEPER_CONNECT: "zookeeper:2181"
      KAFKA_ADVERTISED_HOST_NAME: "192.168.255.100"
      KAFKA_ADVERTISED_PORT: "9092"
      
  kafka-manager:  
    image: sheepkiller/kafka-manager
    environment:
        ZK_HOSTS: 192.168.255.100
    ports:  
      - 9000:9000
```
容器启动成功之后就可以访问web管理端了：http://宿主机IP:9000

### 最终的 Docker Compose 配置文件
将文件存储为：docker-compose.yml
```
version: "3"

services:
 
  mariadb:
    image: mariadb
    container_name: mymariadb
    restart: unless-stopped
    ports:
      - "3306:3306"
    volumes:
      - "/work/docker/volumes/mariadb/data:/var/lib/mysql"
    environment:
      MYSQL_ROOT_PASSWORD: "123456"
      MYSQL_ROOT_HOST: "%"
 
  rabbitmq:
    image: rabbitmq:management
    container_name: myrabbitmq
    restart: unless-stopped
    hostname: rabbitmq1
    ports:
      - "5671:5671"
      - "5672:5672"
      - "4369:4369"
      - "25672:25672"
      - "15671:15671"
      - "15672:15672"
    volumes:
      - /work/docker/volumes/rabbitmq/data:/var/lib/rabbitmq
      - /work/docker/volumes/rabbitmq/log:/var/log/rabbitmq
      
  rocketmq-namesrv:
    image: apache/rocketmq-namesrv:4.3.0
    container_name: rmqnamesrv
    restart: unless-stopped
    ports:
      - 9876:9876
    volumes:
      - /work/docker/volumes/rocketmq/namesrv/logs:/opt/logs
      - /work/docker/volumes/rocketmq/namesrv/store:/opt/store
      
  rocketmq-broker:
    image: apache/rocketmq-broker:4.3.0
    container_name: rmqbroker
    restart: unless-stopped
    ports:
      - 10909:10909
      - 10911:10911
    volumes:
      - /work/docker/volumes/rocketmq/broker/logs:/opt/logs
      - /work/docker/volumes/rocketmq/broker/store:/opt/store
      - /work/docker/volumes/rocketmq/broker/conf:/opt/conf
    environment:
      NAMESRV_ADDR: "192.168.255.100:9876"
    links:
      - rocketmq-namesrv
    depends_on:
      - rocketmq-namesrv
      
  rocketmq-console-ng:
    image: styletang/rocketmq-console-ng
    container_name: rmqconsole
    restart: unless-stopped
    ports:
      - 8090:8080
    environment:
      JAVA_OPTS: "-Drocketmq.namesrv.addr=192.168.255.100:9876 -Dcom.rocketmq.sendMessageWithVIPChannel=false"
      
  zookeeper:
    image: wurstmeister/zookeeper
    container_name: myzookeeper
    restart: unless-stopped
    hostname: zookeeper1
    ports:
      - 2181:2181
    volumes:
      - /etc/localtime:/etc/localtime
      
  kafka:
    image: wurstmeister/kafka
    container_name: mykafka
    restart: unless-stopped
    ports:
      - 9092:9092
    volumes:
      - /etc/localtime:/etc/localtime
    links:
      - zookeeper
    depends_on:
      - zookeeper
    environment:
      KAFKA_ZOOKEEPER_CONNECT: "zookeeper:2181"
      KAFKA_ADVERTISED_HOST_NAME: "192.168.255.100"
      KAFKA_ADVERTISED_PORT: "9092"
      KAFKA_BROKER_ID: 1
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_CREATE_TOPICS: "topic_test:1:1"
      
  kafka-manager:
    image: sheepkiller/kafka-manager
    container_name: mykafkamanager
    restart: unless-stopped
    environment:
      ZK_HOSTS: "192.168.255.100:2181"
      APPLICATION_SECRET: "letmein"
    ports:
      - 9000:9000

```
创建文件后，执行：
```
docker-compose up -d
```

## 运行项目
1. 在 IDEA 中打开项目，IDEA 必须安装 lombok 插件；
2. DataBase/MySQL/Table 目录中是数据库建表脚本，在 MySQL 中执行建表；
3. 运行项目后，在浏览器中输入地址：http://localhost:8080/myboot/swagger-ui.html ，进行测试；
4. 详见代码注释。