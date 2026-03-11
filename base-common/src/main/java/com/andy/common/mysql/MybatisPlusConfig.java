package com.andy.common.mysql;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor inner = new PaginationInnerInterceptor();
        inner.setDbType(DbType.MYSQL);
        inner.setMaxLimit(500L);
        inner.setOverflow(true);
        interceptor.addInnerInterceptor(inner);
        return interceptor;
    }
}
