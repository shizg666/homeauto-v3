-- mybatisplus
查询时wrap推荐使用EntityWrapper
ipage信息推荐使用Pagination
dao继承SmartHomeBaseMapper

-- redis
使用redisUtil类

-- mqtt
调用时使用MqttFactory.getClient获取client操作对应的pub/sub任务
消息处理继承MessageBaseHandle，可参考DefaultMessageHandle

-- mongo
调用mongo时，使用dao继承自BasicMongoDao使用

-- 日期处理
使用LocalDateTimeUtil处理

-- 错误码定义
见ErrorCodeEnumConst

-- SpringContextUtil
用于根据name获取sping容器中的对象

-- mqtt的topic名称定义
MqttTopic

-- basedto
所有请求的参数封装对象的基类，直接继承，如果需要实现自定义的参数校验，则复写check方法

-- baseController
所有controller的基类，直接继承即可

-- common部件需要的配置说明
spring:
  data:
    mongodb:
      uri: mongodb://root:aA123456@40.73.77.122:27017/cloud?authSource=cloud
      #&replicaSet=cloud&slaveOk=true&write=1&readPreference=secondaryPreferred
      connections-per-host: 50
      #每个实例允许链接的最大数
      threads-allowed-to-block-for-connection-multiplier: 50
      # 超时时间
      connect-timeout: 5000
      socket-timeout: 3000
      max-wait-time: 1500
      #控制是否在一个连接时，系统会自动重试
      auto-connect-retry: true
      socket-keep-alive: true
  ## mysql
  datasource:
    url: jdbc:mysql://40.73.77.122:3306/cloud?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimeZone=CTT
    username: root
    password: aA123456
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource
    initialSize: 1
    minIdle: 3
    maxActive: 100
    maxWait: 60000
    timeBetweenEvictionRunsMillis: 60000
    minEvictableIdleTimeMillis: 30000
    validationQuery: select 'x'
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    poolPreparedStatements: true
    maxPoolPreparedStatementPerConnectionSize: 20
  ## redis链接
  redis:
    database: 0
## 如果是单点的redis，打开以下配置 start
    host: 40.73.77.122
    port: 6381
## 单点redis配置结束 end
    password: aA123456
## 如果是sentinel，打开以下配置 start
#    sentinel:
#      master: mymaster
#      nodes: [40.73.77.122:26379,40.73.77.122:26380,40.73.77.122:26381]
## sentinel配置结束
    lettuce:
      pool:
         # 连接池中的最大空闲连接 默认8      
        max-idle: 8
        # 连接池中的最小空闲连接 默认0
        min-idle: 0
        # 连接池最大连接数 默认8 ，负数表示没有限制
        max-active: 50
        # 连接池最大阻塞等待时间（使用负值表示没有限制） 默认-1
        max-wait: -1
    timeout: 30000

mqtt:
  server:
    url: tcp://40.73.77.122:1883
    client-id: smarthome_server
    
    
    
    
    
# 工具类
其他工具类建议直接使用三方jar包中的（相关jar包已引用）

* [集合相关](#集合相关)：
    * commons-collections4:
        * [CollectionUtils](#CollectionUtils)
* [数组相关](#数组相关)：
    * commons-lang3:
        * [ArrayUtils](#ArrayUtils)
* [I/O相关](#I/O相关)：
    * commons-io:
        * [文件操作](#文件操作)：
            * [FileUtils](#FileUtils)  
        * [I/O 操作](#I/O操作)：
            * [IOUtils](#IOUtils)  
* [计时](#计时)：
    * guava:
        * [Stopwatch](#Stopwatch)

