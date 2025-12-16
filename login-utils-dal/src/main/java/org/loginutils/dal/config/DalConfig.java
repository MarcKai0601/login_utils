package org.loginutils.dal.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "org.loginutils.dal.mappers")
public class DalConfig {
}
