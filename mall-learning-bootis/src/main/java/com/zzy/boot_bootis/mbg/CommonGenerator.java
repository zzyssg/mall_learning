package com.zzy.boot_bootis.mbg;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;

public class CommonGenerator extends DefaultCommentGenerator {
    private boolean addRemarkComments = false;

    /*配置参数信息*/
    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));

    }

    /*字段添加注释*/
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();
        //是否添加备注信息
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            addFieldJavaDoc(field, remarks);
        }

    }

    /*给model字段添加注释*/
    private void addFieldJavaDoc(Field field,String remarks) {
        field.addJavaDocLine("/**");
        //获取数据库字段的备注
        String[] remarkLines = remarks.split(System.getProperty("line.separator"));
        for (String remarkLine : remarkLines) {
            field.addJavaDocLine("*" + remarkLine);
        }
        field.addJavaDocLine("*/");
    }




}
