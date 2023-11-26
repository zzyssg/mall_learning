package com.zzy.malladmin.common;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;

/**
 * @ClassName MybatisGenerator
 * @Author ZZy
 * @Date 2023/9/28 16:00
 * @Description
 * @Version 1.0
 */
public class CommentGenerator extends DefaultCommentGenerator {


    private boolean addRemarkComments = false;
    private String EXAMPLE_SUFFIX = "Example";
    private String MAPPER_SUFFIX = "Mapper";
    private String API_MODEL_PROPERTY_CLASS_FULL_NAME = "io.swagger.annotations.ApiModelProperty";


    /**
     * 用户参数
     * @param properties
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));
    }

    /**
     * 给字段添加注释
     * @param field
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();

        if (this.addRemarkComments && StringUtility.stringHasValue(remarks)) {
            if (remarks.contains("\"")) {
                remarks.replace("\"", "'");
            }
            field.addJavaDocLine("@ApiModelProperty(value=\"" + remarks + "\")");
        }
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        super.addJavaFileComment(compilationUnit);
        String fullyQualifiedName = compilationUnit.getType().getFullyQualifiedName();
        if (!fullyQualifiedName.contains(EXAMPLE_SUFFIX) && !fullyQualifiedName.contains(MAPPER_SUFFIX)) {
            compilationUnit.addImportedType(new FullyQualifiedJavaType(fullyQualifiedName));
        }
    }

}
