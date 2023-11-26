package com.zzy.malladmin.common;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Generator
 * @Author ZZy
 * @Date 2023/9/29 3:07
 * @Description 生成类，运行后通过mbg生成 model、mapper和swagger注释
 * @Version 1.0
 */
public class Generator {

    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<>();
        boolean overwrite = true;

        //创建configuration
        InputStream is = Generator.class.getResourceAsStream("/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration configuration = cp.parseConfiguration(is);
        is.close();
        //创建MGB
        DefaultShellCallback shellCallback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(configuration, shellCallback, warnings);
        myBatisGenerator.generate(null);
        //打印警告信息
        for (String warning : warnings) {
            System.out.println(warning);
        }

    }


}
