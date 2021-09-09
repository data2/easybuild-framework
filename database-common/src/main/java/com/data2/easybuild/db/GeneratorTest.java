package com.data2.easybuild.db;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
//import com.liuyanzhao.sens.controller.base.BaseController;

/**
 * @author liuyanzhao
 * @since 2021/7/9
 */
public class GeneratorTest {
    public static final String DB_NAME = "test";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "123456";
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver"; // 驱动包，MySQL5 需要去掉中间的.cj
    public static final String[] TABLE_NAME_ARR = {"records"}; // 需要生成代码的表名
    public static final String AUTHOR = "data2"; // author里显示的名字
    public static final String PACKAGE_PREFIX = "com.data2.easybuild.db"; // 项目包名前缀

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 2、全局配置
        mpg.setTemplateEngine(new FreemarkerTemplateEngine()); // 设置模板
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(AUTHOR);
        gc.setOpen(false); // 生成后是否打开资源管理器
        gc.setFileOverride(false); // 重新生成时文件是否覆盖
        // gc.setServiceName("%sService"); //去掉Service接口的首字母I。如果需要生成的service接口类最前面有I,这把这行注释掉
        gc.setMapperName("%sMapper");
        gc.setIdType(IdType.AUTO); // 主键策略, UUID使用IdType.UUID, 自增用AUTO
        gc.setDateType(DateType.ONLY_DATE);// 定义生成的实体类中日期类型
        gc.setSwagger2(false);// 开启Swagger2模式
        mpg.setGlobalConfig(gc);
        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/" + DB_NAME + "?serverTimezone=GMT%2b8&characterEncoding=UTF-8&useSSL=false");
        dsc.setDriverName(DB_DRIVER);
        dsc.setUsername(DB_USER);
        dsc.setPassword(DB_PASSWORD);
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);
        // 4、包配置
        PackageConfig pc = new PackageConfig();
        //		pc.setModuleName("模块名"); // 模块名，如果需要按模块生成，则打开这行代码，否则注释掉这行
        pc.setParent(PACKAGE_PREFIX);
        pc.setController("controller");
        pc.setEntity("model");
        pc.setService("service");
        pc.setMapper("dao");
        mpg.setPackageInfo(pc);
        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude(TABLE_NAME_ARR); // 生成的表
        strategy.setNaming(NamingStrategy.underline_to_camel);// 数据库表映射到实体的命名策略
        strategy.setTablePrefix("t_"); // 生成实体时去掉表前缀
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);// 数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(false); // lombok 模型 @Accessors(chain = true) setter链式操作
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);//去掉布尔值的is_前缀
        strategy.setRestControllerStyle(true); // restful api风格控制器
        //strategy.setControllerMappingHyphenStyle(true); // url中驼峰转连字符, 默认是驼峰命名
        // 设置controller父类
//        strategy.setSuperControllerClass(BaseController.class.getName());
        mpg.setStrategy(strategy);
        // 6、执行
        mpg.execute();
    }
}
