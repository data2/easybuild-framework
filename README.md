# easybuild-framework

[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/data2/easybuild-framework.svg?branch=main)](https://travis-ci.org/data2/easybuild-framework)
[![Coverage Status](https://coveralls.io/repos/github/data2/salmon/badge.svg)](https://coveralls.io/github/data2/salmon)

java框架

# Aop

通过切面Aspect 对所有的controller中的方法进行拦截 

    入参统一校验
    解决-请求重复提交
    记录日志  TODO：指定方式 发送kafka、控制台打印
    统计接口耗时
    统一异常处理
    统一出参格式

# 配置文件加密jasypt
    1Application.java上增加注解@EnableEncryptableProperties；

    2增加配置文件jasypt.encryptor.password = encodepawd，这是加密的秘钥；

    3所有明文密码替换为ENC(加密字符串)，例如ENC(XW2daxuaTftQ+F2iYPQu0g==)；
    
    4引入一个MAVEN依赖；
        maven坐标如下：
        <dependency>
            <groupId>com.github.ulisesbocchio</groupId>
            <artifactId>jasypt-spring-boot</artifactId>
            <version>2.0.0</version>
        </dependency>
    
        其中第三步的加密字符串的生成方式为：
        java -cp jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="dbpassword" password=encodepawd algorithm=PBEWithMD5AndDES


# 防重 - 请求

两种方式

    请求体中frontId前端设值，每次请求传递不同的值，后端依据排重或者请求头中设置frontId

    或者请求体传值hash

    然后针对ip+frontId 或者 ip+hash进行redis排重

    后端设计到请求级别，controller增加注解 @DisableDuplicateSubmit
    
    easy.dup.open: true


# 是否开启分布式锁

    easy.lock.open: true
    
# redis配置 
    easy:
        redis:
            host: localhost
            port: 6379
            maxTotal:
            maxIdle:
            minIdle:

# http线程池配置
 
    使用restTemplate 
    
    easy:
      http:
        connect-timeout: 30000
        connection-request-timeout: 30000
        socket-timeout: 60000
        max-total: 200
        default-max-per-route: 200
 
 
# 跨域请求， 指定开放哪些域名可以访问
    
    easy:
        cors:
            open: true
            allowOrigin: www.test1.com,www.test2.com
           
# 是否开启swagger
    
    easy:
        swagger:
            enable: true
            profile: test
 
 # 打印应用详细环境参数
    easy:
        context:
            debug: true
            pretty: true
 

 # 登陆拦截
    全局的登陆验证，开启open, 自定义login实现逻辑，实现LoginService接口，声明成组件即可
    easy:
         login:
            open: true
            path-patterns:
              - /**
            exclude-path-patterns:
              - /**/*.html
              - /**/*.js
              - /**/*.css
              - /**/*.woff
              - /**/*.ttf
   
 # 请求参数加密 
    支持@RequestBody post方式 application/json
    
    encrypt-request:
      enable: true
      
    前端传递参数公钥加密，后台私钥解密，私钥存储在resources/key/private_key.txt

# 序列生成器
    
    采用雪花算法snowFlake，64bit
    
    配置完毕自动开启
    easy:
        seq:
            datacenterId: 1
            workerId : 1
            
# 集成rocketmq 消息队列
    
    pull push 消费两种方式
    
    ```
    easy:
        rocketmq:
             producer:
               transaction:
                 enable: true
               nameSrvAddr: localhost:9876
               group: ProducerGroup
               topic: default_topic
               tag: '*'
             consumer:
               nameSrvAddr: localhost:9876
               group: ConsumerGroup
               topic: default_topic
               tag: '*'   
     ```
     
  ```
     pull, 继承PullConsumerJob即可，run中进行消费
     
     @Component
     @Consumer(consumerGroup = "test_consumer_group", topic = "test_topic", namesrvAddr = "")
     public class TestPullConsumerJob extends PullConsumerJob {
     
         @Override
         public void run() {
             // DO your business, with consumer
             //consumer.fetchConsumeOffset();
         }
     }
     
     push，继承PushConsumerJob，创建处理监听器
     
     @Component
     @Consumer(consumerGroup = "test_consumer_group", topic = "test_topic", namesrvAddr = "", listener = TestPushMessageListener.class)
     public class TestPushConsumer extends PushConsumerJob {
     }
     
     @Component
     public class TestPushMessageListener implements MessageListenerConcurrently {
         @Override
         public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
             // DO you business
             return null;
         }
     }
     
     开启事务，注入TransactionMQProducer，否则注入
     
     @Autowired
     private TransactionMQProducer transactionMQProducer;
     
     @Autowired
     private DefaultMQProducer defaultMQProducer;
     
     
 ``` 
