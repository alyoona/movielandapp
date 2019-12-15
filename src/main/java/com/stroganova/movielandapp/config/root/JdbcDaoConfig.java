package com.stroganova.movielandapp.config.root;

import com.stroganova.movielandapp.config.root.queryconfig.SqlQueryConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.stroganova.movielandapp.dao.jdbc")
@Import(SqlQueryConfig.class)
public class JdbcDaoConfig {

    @Bean
    public DataSource dataSource(@Value("${jdbc.url}") String url,
                                 @Value("${jdbc.username}") String user,
                                 @Value("${jdbc.password}") String password,
                                 @Value("${jdbc.maximumPoolSize:10}") int maximumPoolSize,
                                 @Value("${jdbc.maxLifetime:60000}") int maxLifetime,
                                 @Value("${jdbc.idleTimeout:30000}") int idleTimeout,
                                 @Value("${jdbc.dataSourceClassName:org.postgresql.ds.PGSimpleDataSource}")
                                         String dataSourceClassName
    ) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPoolName("springHikariCP");
        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setDataSourceClassName(dataSourceClassName);
        hikariConfig.setMaximumPoolSize(maximumPoolSize);
        hikariConfig.setMaxLifetime(maxLifetime);
        hikariConfig.setIdleTimeout(idleTimeout);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(password);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public NamedParameterJdbcTemplate namedJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }


    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }
}
