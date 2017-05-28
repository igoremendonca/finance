package br.com.jiva.finance.repository.test;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "br.com.jiva.finance.repository")
@EntityScan(basePackages = "br.com.jiva.finance.model")
@ComponentScan("br.com.jiva.finance")
@PropertySource(value={"classpath:application.properties"})
public class FinanceRepositoryTest {

    @Value("${spring.datasource.driver-class-name}") String dataSourceDriver;
    @Value("${spring.datasource.url}") String dataSourceUrl;
    @Value("${spring.datasource.username}") String dataSourceUsername;
    @Value("${spring.datasource.password}") String dataSourcePassword;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setDriverClassName(dataSourceDriver);
        dataSourceConfig.setJdbcUrl(dataSourceUrl);
        dataSourceConfig.setUsername(dataSourceUsername);
        dataSourceConfig.setPassword(dataSourcePassword);

        return new HikariDataSource(dataSourceConfig);
    }
}
