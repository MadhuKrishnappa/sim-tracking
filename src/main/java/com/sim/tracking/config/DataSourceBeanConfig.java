package com.sim.tracking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import org.apache.tomcat.jdbc.pool.DataSource;


@Configuration
public class DataSourceBeanConfig {

    @Autowired
    Environment env;


    @Bean("mysqlds")
    @Primary
    public DataSource getDataSourceMysql() {
        DataSource datasource = new DataSource();

        datasource.setUrl(env.getProperty("spring.datasource.mysql.url"));
        datasource.setUsername(env.getProperty("spring.datasource.mysql.username"));
        datasource.setPassword(env.getProperty("spring.datasource.mysql.password"));
        datasource.setDriverClassName(env.getProperty("spring.datasource.mysql.driver"));
        datasource.setMaxActive(
                Integer.valueOf(env.getProperty("spring.datasource.mysql.maxactive")));
        datasource.setMaxIdle(Integer.valueOf(env.getProperty("spring.datasource.mysql.maxidle")));
        datasource.setMaxWait(Integer.valueOf(env.getProperty("spring.datasource.mysql.maxwait")));
        datasource.setRemoveAbandoned(
                Boolean.valueOf(env.getProperty("spring.datasource.mysql.removeabandoned")));
        datasource.setRemoveAbandonedTimeout(
                Integer.valueOf(env.getProperty("spring.datasource.mysql.removeabandonedtimeout")));
        datasource.setDefaultAutoCommit(
                Boolean.valueOf(env.getProperty("spring.datasource.mysql.defaultautocommit")));
        datasource.setValidationQuery(env.getProperty("spring.datasource.mysql.validationquery"));
        datasource.setValidationInterval(
                Long.valueOf(env.getProperty("spring.datasource.mysql.validationinterval")));
        datasource.setTestOnBorrow(
                Boolean.valueOf(env.getProperty("spring.datasource.mysql.testonborrow")));
        datasource.setJdbcInterceptors(env.getProperty("spring.datasource.mysql.jdbcinterceptors"));
        return datasource;
    }

    @Bean("mysql")
    @Primary
    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplateMysql(@Qualifier("mysqlds") DataSource dataSource) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(dataSource);
        return template;
    }
}
