package com.webexecuter.server.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import com.webexecuter.server.job.ScriptJobFactory;

@Configuration
public class SchedulerConfig {

    private final ScriptJobFactory scriptJobFactory;
    private String jdbcUrl;
    private String jdbcUser;
    private String jdbcPassword;

    public SchedulerConfig(ScriptJobFactory scriptJobFactory, 
                            @Value("${spring.datasource.url}") String jdbcUrl,
                            @Value("${spring.datasource.username") String jdbcUser,
                            @Value("${spring.datasource.password}") String jdbcPassword) {
        this.scriptJobFactory = scriptJobFactory;
        this.jdbcUrl = jdbcUrl;
        this.jdbcUser = jdbcUser;
        this.jdbcPassword = jdbcPassword;
    }

    @Bean
    SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setConfigLocation(new ClassPathResource("application.yml"));
        schedulerFactoryBean.setJobFactory(scriptJobFactory);
        return schedulerFactoryBean;
    }

    @Bean
    @QuartzDataSource
    DataSource quartzDataSource() {
        DataSource dataSource = DataSourceBuilder.create()
                .url(jdbcUrl)
                .username(jdbcUser)
                .password(jdbcPassword)
                .build();
        // schema init
        Resource initSchema = new ClassPathResource("quartz_tables_postgres.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
        return dataSource;
    }
}
