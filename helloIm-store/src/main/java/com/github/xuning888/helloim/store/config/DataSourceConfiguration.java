package com.github.xuning888.helloim.store.config;

import com.github.xuning888.helloim.store.constant.DataSources;
import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xuning
 * @date 2025/9/21 16:17
 */
@MapperScan("com.github.xuning888.helloim.store.mapper")
@Configuration
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "im.datasource")
    public ImDataSourceProperties imDataSourceProperties() {
        return new ImDataSourceProperties();
    }

    @Bean(name = "datasource0")
    public DataSource dataSource0(ImDataSourceProperties dataSourceProperties) {
        return initDataSource(dataSourceProperties, DataSources.datasource0);
    }


    @Bean(name = "datasource1")
    public DataSource dataSource1(ImDataSourceProperties dataSourceProperties) {
        return initDataSource(dataSourceProperties, DataSources.datasource1);
    }


    @Bean(name = "datasource2")
    public DataSource dataSource2(ImDataSourceProperties dataSourceProperties) {
        return initDataSource(dataSourceProperties, DataSources.datasource2);
    }

    @Bean(name = "datasource3")
    public DataSource dataSource3(ImDataSourceProperties dataSourceProperties) {
        return initDataSource(dataSourceProperties, DataSources.datasource3);
    }

    @Bean
    @Primary
    public DataSource dynamicDataSource(
            DataSource datasource0,
            DataSource datasource1,
            DataSource datasource2,
            DataSource datasource3) {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSources.datasource0.name(), datasource0);
        dataSourceMap.put(DataSources.datasource1.name(), datasource1);
        dataSourceMap.put(DataSources.datasource2.name(), datasource2);
        dataSourceMap.put(DataSources.datasource3.name(), datasource3);
        return new DynamicDataSource(datasource0, dataSourceMap);
    }

    private DataSource initDataSource(ImDataSourceProperties imDataSourceProperties, DataSources dataSources) {
        DataSourceProperties properties = imDataSourceProperties.getDataSources().get(dataSources.name());
        HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        dataSource.setPoolName(properties.getName());
        try (Connection connection = dataSource.getConnection()) {
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }
}