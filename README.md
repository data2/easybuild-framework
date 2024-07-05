# easybuild-framework

[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Coverage Status](https://coveralls.io/repos/github/data2/easybuild-framework/badge.svg?branch=main)](https://coveralls.io/github/data2/easybuild-framework?branch=main)

![image](https://user-images.githubusercontent.com/13504729/132335761-e1241d9a-3151-4cfe-941d-f657608c8447.png)

# Aop

Intercept all methods in the controller through the Aspect

 + Unified verification of incoming parameters
 + Resolve-request repeated submission
 + Log TODO: specify the method to send Kafka, console print
 + Time-consuming statistics interface
 + Unified exception handling
 + Unified parameter output format

# Configuration file encryption jasypt

```
1. Add the annotation @EnableEncryptableProperties to Application.java;

2. Add the configuration file jasypt.encryptor.password = encodepawd, which is the encryption key;

3. Replace all plaintext passwords with ENC (encrypted string), for example ENC (XW2daxuaTftQ+F2iYPQu0g==);

```

The method of generating the encrypted string in the third step is

```java
 
encryption
java -cp jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input="dbpassword" password=encodepawd algorithm=PBEWithMD5AndDES
 
Decrypt
java -cp jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringDecryptionCLI input="BRn0kKO3x7NVaziI1f2/8ovMh+0IhZ2P" password=dqbusi123456 algorithm=PBEWithMD5AndDES
 
```


# Anti-weight-request

Two ways

```
The frontId in the request body is set at the front-end value, and a different value is passed in each request. The back-end sets the frontId according to the deduplication or the request header.

Or request body to pass value hash

Then perform redis reset for ip+frontId or ip+hash

The back-end is designed to the request level, and the controller adds the annotation @DisableDuplicateSubmit
    
easy.dup.open: true
```
    
experiment
```java
@PostMapping("/testDup")
@DisableDuplicateSubmit(type = DupEnum.REQUEST_HASH, timeout = 2000)
@ResponseBody
public Object testDup(@RequestBody OrderBean orderBean){
    return orderBean.getOrderId();
}
```

 # Request parameter encryption

eg:

```
Jingdong background>
         WeChat payment gateway
Meituan Backstage>
```

The merchant backend encrypts the input parameters of the interface requesting WeChat payment gateway, and decrypts WeChat

So if you want to safely expose your service interface to other callers, you can use encryption

```
Support @RequestBody post method application/json
    
encrypt-request:
     enable: true
      
The front-end passes parameters public key encryption, background private key decryption, and the private key is stored in resources/key/private_key.txt
```

test

```java
Start the example, visit http://localhost:8080/easybuild/okay/testEncrypt

@PostMapping("/testEncrypt")
@EncryptRequest
@ResponseBody
public Object testEncrypt(@RequestBody OrderBean orderBean){
}

curl -X POST "http://localhost:8081/easybuild/okay/testEncrypt" -H "accept: */*" -H "Content-Type: application/json" -d "IHXX1i8IGuy211ecqsE9X3kpKXbbTwUUNz5wuFkkbUFUDCDzf69t243wEyS9VZ951aR85zYeLiMzHr8gmcYhuGZwNq9/seAyFrxPd4EvNXGrZmcHN/klJoibYIJYW6usIMg5ceNSQeAMK6jFUjIv02fYK7aCjOmJ6LwxWfsKn7dnAC86FzV0zCnWTGQSKnoz52/ghX7tU5Q+66V5SihZKM7s7LTKICQPmwZ/H1NRbzUHR1pLzox/kRTkx46LnNYKLRuaswEC8PGq4dnxL36WPH3kMe+ELXOa1Az7U1jGVSFBrPN/3Ts1C6npea9BzF2LvX7pQsOavjOhctgW6QNh3A=="

return
{
  "success": true,
  "data": "1231",
  "code": "0",
  "message": "success"
}
```

# Whether to open the distributed lock

```
easy.lock.open: true
```

# redis configuration

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
 
 
# Cross-domain request, specify which domain names are open for access

```
easy:
   cors:
     enable: true
     allowOrigin: localhost:8080
```
           
# Whether to open swagger
    
```
easy:
   swagger:
     enable: true
     profile: test
```
 
  # Print application detailed environmental parameters

```
easy:
   context:
     debug: true
     pretty: false
```
 

  # Login interception

Global login verification, open open, customize login implementation logic, implement LoginService interface, and declare it as a component

```
easy:
   login:
     open: true
     path-patterns:
       -/**
     exclude-path-patterns:
       -/*/*.html
       -/*/*.js
       -/*/*.css
       -/*/*.woff
       -/*/*.ttf

```
   

# Sequence generator
    
Using snowflake algorithm snowFlake, 64bit

Automatically open after configuration

```
easy:
   seq:
     workerId: 1
     datacenterId: 1

```
            
# Integrate rocketmq message queue
    
rocketmq configuration
    
```
easy:
  rocketmq:
    producer:
      transaction:
        enable: true
      nameSrvAddr: localhost:9876
      group: ProducerGroup
      topic: default_topic
      tag:'*'
    consumer:
      nameSrvAddr: localhost:9876
      group: ConsumerGroup
      topic: default_topic
      tag:'*'
      
```

Production 

```java

Open the transaction, inject TransactionMQProducer, otherwise inject
     
@Autowired
private TransactionMQProducer transactionMQProducer;
     
@Autowired
private DefaultMQProducer defaultMQProducer;
     

```
    
Consumption> pull and push two ways, the code is as follows

```java

1. Pull, just inherit PullConsumerJob, and consume in run
     
@Component
@Consumer(consumerGroup = "test_consumer_group", topic = "test_topic", namesrvAddr = "")
public class TestPullConsumerJob extends AbstractPullConsumerJob {

    @Override
    public void run(String... args) {
        // DO your business, with consumer
        //consumer.fetchConsumeOffset();
    }
}

     
2. Push, inherit PushConsumerJob, create processing listener
     
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

# Verification code

```java

@Component
public class ServiceImpl extends AbstractImageCode{}

Inherit the abstract class, saveCode, getCode to realize the storage/withdrawal of the verification code


```


