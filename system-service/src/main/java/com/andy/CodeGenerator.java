package com.andy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Arrays;
import java.util.List;

public class CodeGenerator {

    private static final String url = "jdbc:mysql://47.116.214.177:3306/iss-system?useSSL=false&characterEncoding=utf8";

    private static final String userName = "root";

    public static final String password = "RUlVIM0zZG";

    public static final String packageName = "com.andy.passport";

    public static final String moduleName = "";

    public static final List<String> tables = Arrays.asList(
//            "system_data_resource",
//            "system_data_role_rule",
//            "system_data_user_rule",
//            "system_menu",
//            "system_org",
//            "system_role",
//            "system_role_menu",
//            "system_user",
//            "system_user_org",
//            "system_user_role"
            "system_sub_system"
            );

    public static void main(String[] args) {
        FastAutoGenerator.create(url, userName, password)
            .globalConfig((builder -> {
                builder.outputDir(outPutDir())
                        .author("Andy.Liu")
                        .enableSwagger()
                        .dateType(DateType.ONLY_DATE);
            }))
            .packageConfig((builder -> {
                builder.parent(packageName)
                        .moduleName(moduleName);
            }))
            .strategyConfig((builder -> {
                builder.addInclude(tables)
                        .controllerBuilder().enableRestStyle().enableHyphenStyle().enableFileOverride().build()
                        .serviceBuilder().formatServiceFileName("%sService").formatServiceImplFileName("%sServiceImpl").enableFileOverride().build()
                        .entityBuilder()
                            .enableLombok()
                            .enableTableFieldAnnotation()
                            .superClass("com.andy.common.entity.BaseEntity")
                            .versionColumnName("version")
                            .columnNaming(NamingStrategy.underline_to_camel)
                            .idType(IdType.AUTO)
                            .enableFileOverride()
                            .build();
            }))
            .execute();
    }

    public static String outPutDir(){
        String basePath = System.getProperty("user.dir");
        return basePath + "/system-service/src/main/java";
    }

}
