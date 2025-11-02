package com.github.xuning888.helloim.store.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import java.util.Map;

/**
 * @author xuning
 * @date 2025/9/21 16:17
 */
public class ImDataSourceProperties {

    private Map<String, DataSourceProperties> dataSources;

    public Map<String, DataSourceProperties> getDataSources() {
        return dataSources;
    }

    public void setDataSources(Map<String, DataSourceProperties> dataSources) {
        this.dataSources = dataSources;
    }
}