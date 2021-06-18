package ru.pfr.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "pkvEntityManager",
        basePackages = "ru.pfr.repo.pkv"
)
public class PkvDS {

    @Primary
    @Bean(name = "pkvDataSource")
    @ConfigurationProperties(prefix = "pkv.spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "pkvEntityManager")
    public LocalContainerEntityManagerFactoryBean
    pkvEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("pkvDataSource") DataSource dataSource
    ) {
        return
                builder
                        .dataSource(dataSource)
                        .packages("ru.pfr.model.pkv")//
                        .persistenceUnit("pkv")//
                        .build();//
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager pkvTransactionManager(
            @Qualifier("pkvEntityManager") EntityManagerFactory
                    pkvEntityManagerFactory
    ) {
        return new JpaTransactionManager(pkvEntityManagerFactory);
    }

}
