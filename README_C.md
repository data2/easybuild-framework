# easybuild-framework

[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/data2/easybuild-framework.svg?branch=main)](https://travis-ci.org/data2/easybuild-framework)
[![Coverage Status](https://coveralls.io/repos/github/data2/easybuild-framework/badge.svg?branch=main)](https://coveralls.io/github/data2/easybuild-framework?branch=main)

![image](https://user-images.githubusercontent.com/13504729/132335761-e1241d9a-3151-4cfe-941d-f657608c8447.png)


# Aop

通过切面Aspect 对所有的controller中的方法进行拦截 

 + 入参统一校验
 + 解决-请求重复提交
 + 记录日志  TODO：指定方式 发送kafka、控制台打印
 + 统计接口耗时
 + 统一异常处理
 + 统一出参格式

# 配置文件加密jasypt

```
1、Application.java上增加注解@EnableEncryptableProperties；

2、增加配置文件jasypt.encryptor.password = encodepawd，这是加密的秘钥；

3、所有明文密码替换为ENC(加密字符串)，例如ENC(XW2daxuaTftQ+F2iYPQu0g==)；

```

其中第三步的加密字符串的生成方式为

```java
 
加密
java -cp jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="dbpassword" password=encodepawd algorithm=PBEWithMD5AndDES
 
解密
java -cp jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringDecryptionCLI input="BRn0kKO3x7NVaziI1f2/8ovMh+0IhZ2P" password=dqbusi123456 algorithm=PBEWithMD5AndDES
 
```


# 防重 - 请求

两种方式

```
请求体中frontId前端设值，每次请求传递不同的值，后端依据排重或者请求头中设置frontId

或者请求体传值hash

然后针对ip+frontId 或者 ip+hash进行redis排重

后端设计到请求级别，controller增加注解 @DisableDuplicateSubmit
    
easy.dup.open: true
```
    
实验
```java
@PostMapping("/testDup")
@DisableDuplicateSubmit(type = DupEnum.REQUEST_HASH, timeout = 2000)
@ResponseBody
public Object testDup(@RequestBody OrderBean orderBean){
    return orderBean.getOrderId();
}
```

 # 请求参数加密 

eg:

```
京东后台 >
        微信支付网关
美团后台 >
```

商家后台对请求微信支付网关的接口入参进行加密，微信解密

所以如果您想要将自己服务的接口安全的暴露给其他调用者，可以使用加密功能

```
支持@RequestBody post方式 application/json
    
encrypt-request:
    enable: true
      
前端传递参数公钥加密，后台私钥解密，私钥存储在resources/key/private_key.txt
```

测试

```java
启动example，访问http://localhost:8080/easybuild/okay/testEncrypt

@PostMapping("/testEncrypt")
@EncryptRequest
@ResponseBody
public Object testEncrypt(@RequestBody OrderBean orderBean){
}

curl -X POST "http://localhost:8081/easybuild/okay/testEncrypt" -H "accept: */*" -H "Content-Type: application/json" -d "IHXX1i8IGuy211ecqsE9X3kpKXbbTwUUNz5wuFkkbUFUDCDzf69t243wEyS9VZ951aR85zYeLiMzHr8gmcYhuGZwNq9/seAyFrxPd4EvNXGrZmcHN/klJoibYIJYW6usIMg5ceNSQeAMK6jFUjIv02fYK7aCjOmJ6LwxWfsKn7dnAC86FzV0zCnWTGQSKnoz52/ghX7tU5Q+66V5SihZKM7s7LTKICQPmwZ/H1NRbzUHR1pLzox/kRTkx46LnNYKLRuaswEC8PGq4dnxL36WPH3kMe+ELXOa1Az7U1jGVSFBrPN/3Ts1C6npea9BzF2LvX7pQsOavjOhctgW6QNh3A=="

返回
{
  "success": true,
  "data": "1231",
  "code": "0",
  "message": "success"
}
```


# 是否开启分布式锁

```
easy.lock.open: true
```

# redis配置 

```yml
easy:
  redis:
    redisson:
      file: classpath:redisson.yaml
      config:
        singleServerConfig:
          idleConnectionTimeout: 10000
          connectTimeout: 10000
          timeout: 3000
          retryAttempts: 3
          retryInterval: 1500
          subscriptionsPerConnection: 5
          address: "redis://127.0.0.1:6379"
          subscriptionConnectionMinimumIdleSize: 1
          subscriptionConnectionPoolSize: 50
          connectionMinimumIdleSize: 24
          connectionPoolSize: 64
          database: 0
          dnsMonitoringInterval: 5000
```

# http线程池配置
 
使用restTemplate 
    
```
easy:
  http:
    connect-timeout: 30000
    connection-request-timeout: 30000
    socket-timeout: 60000
    max-total: 200
    default-max-per-route: 200
```
 
 
# 跨域请求， 指定开放哪些域名可以访问

```
easy:
  cors:
    enable: true
    allowOrigin: localhost:8080
```
           
# 是否开启swagger
    
```
easy:
  swagger:
    enable: true
    profile: test
```
 
 # 打印应用详细环境参数

```
easy:
  context:
    debug: true
    pretty: false
```
 

 # 登陆拦截

全局的登陆验证，开启open, 自定义login实现逻辑，实现LoginService接口，声明成组件即可

```
easy:
  login:
    open: true
    path-patterns:
      - /**
    exclude-path-patterns:
      - /*/*.html
      - /*/*.js
      - /*/*.css
      - /*/*.woff
      - /*/*.ttf

```
   

# 序列生成器
    
采用雪花算法snowFlake，64bit

配置完毕自动开启

```
easy:
  seq:
    workerId: 1
    datacenterId: 1

```
            
# 集成rocketmq 消息队列
    
rocketmq配置
    
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

生产 

```java

开启事务，注入TransactionMQProducer，否则注入
     
@Autowired
private TransactionMQProducer transactionMQProducer;
     
@Autowired
private DefaultMQProducer defaultMQProducer;
     

```
    
消费 > pull和push两种方式，代码如下

```java

1、pull, 继承PullConsumerJob即可，run中进行消费
     
@Component
@Consumer(consumerGroup = "test_consumer_group", topic = "test_topic", namesrvAddr = "")
public class TestPullConsumerJob extends AbstractPullConsumerJob {

    @Override
    public void run(String... args) {
        // DO your business, with consumer
        //consumer.fetchConsumeOffset();
    }
}

     
2、push，继承PushConsumerJob，创建处理监听器
     
@Component
@Consumer(consumerGroup = "test_consumer_group1", topic = "test_topic", namesrvAddr = "", listener = TestPushMessageListener.class)
public class TestPushConsumer extends AbstractPushConsumerJob {
}
     
@Component
public class TestPushMessageListener implements MessageListenerConcurrently {
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        // DO you business
        return null;
    }
}
     
     
 ``` 

# 验证码

```java

@Component
public class ServiceImpl extends AbstractImageCode{}

继承抽象类，saveCode、getCode实现验证码的存/取


```


