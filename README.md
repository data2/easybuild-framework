# easybuild-framework
java框架

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

    请求体中frontId前端设值，每次请求传递不同的值，后端依据排重
或者请求头中设置frontId

    或者请求体传值hash

    然后针对ip+frontId 或者 ip+hash进行redis排重

    后端设计到请求级别，controller增加注解 @DisableDuplicateSubmit

