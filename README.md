# easybuild-framework
java框架

# 配置文件加密jasypt
1Application.java上增加注解@EnableEncryptableProperties；
2增加配置文件jasypt.encryptor.password = Afei@2018，这是加密的秘钥；
3所有明文密码替换为ENC(加密字符串)，例如ENC(XW2daxuaTftQ+F2iYPQu0g==)；
4引入一个MAVEN依赖；
maven坐标如下：
<dependency>
    <groupId>com.github.ulisesbocchio</groupId>
    <artifactId>jasypt-spring-boot</artifactId>
    <version>2.0.0</version>
</dependency>

