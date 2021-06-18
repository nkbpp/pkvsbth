//package ru.pfr.configuration;
//
//
//
//import java.util.HashMap;
//import java.util.Map;
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(
//        entityManagerFactoryRef = "asvEntityManager",
//        basePackages =  "ru.pfr.repo.asv",
//        transactionManagerRef = "asvTransactionManager"
//)
//public class AsvDS {
//
//    @Bean(name = "asvDataSource")
//    @ConfigurationProperties(prefix = "asv.spring")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "asvEntityManager")
//    public LocalContainerEntityManagerFactoryBean
//    asvEntityManagerFactory(
//            @Qualifier("asvDataSource") DataSource dataSource,
//            EntityManagerFactoryBuilder builder
//    ) {
//        return
//                builder
//                        .dataSource(dataSource)
//                        .packages("ru.pfr.model.asv")//
//                        .persistenceUnit("asv")//
//                        .build();//
//    }
//
//    @Bean(name = "asvTransactionManager")
//    public PlatformTransactionManager asvTransactionManager(
//            @Qualifier("asvEntityManager") EntityManagerFactory
//                    asvEntityManagerFactory
//    ) {
//        return new JpaTransactionManager(asvEntityManagerFactory);
//    }
//
//}

package ru.pfr.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "asvEntityManager",
        basePackages = "ru.pfr.repo.asv",
        transactionManagerRef = "asvTransactionManager"
)
public class AsvDS {

    @Bean(name = "asvDataSource")
    @ConfigurationProperties(prefix = "asv.spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "asvEntityManager")
    public LocalContainerEntityManagerFactoryBean
    asvEntityManagerFactory(
            @Qualifier("asvDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder
    ) {
        return
                builder
                        .dataSource(dataSource)
                        .packages("ru.pfr.model.asv")//
                        .persistenceUnit("asv")//
                        .build();//
    }


    @Bean(name = "asvTransactionManager")
    public PlatformTransactionManager asvTransactionManager(
            @Qualifier("asvEntityManager") EntityManagerFactory
                    asvEntityManagerFactory
    ) {
        return new JpaTransactionManager(asvEntityManagerFactory);
    }

}
