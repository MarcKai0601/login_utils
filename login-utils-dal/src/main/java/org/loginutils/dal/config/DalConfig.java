package org.loginutils.dal.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@AutoConfiguration
@MapperScan(basePackages = "org.loginutils.dal.mappers")
@EnableJpaRepositories(basePackages = "org.loginutils.dal.repository.jpa")
@EntityScan(basePackages = "org.loginutils.dal.entity")
@ComponentScan(basePackages = "org.loginutils.dal") // 掃描 dal 裡所有 component
public class DalConfig {
}
