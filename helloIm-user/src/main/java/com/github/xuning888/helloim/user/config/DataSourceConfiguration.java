package com.github.xuning888.helloim.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuning
 * @date 2025/10/7 01:44
 */
@Configuration
@MapperScan("com.github.xuning888.helloim.user.mapper")
public class DataSourceConfiguration {
}
