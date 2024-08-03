package com.objecteffects.persistence;

import javax.sql.DataSource;

import com.objecteffects.persistence.mapper.SensorMapperImpl;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.context.annotation.Factory;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Factory
public class MybatisFactory {
    final static Logger log = LoggerFactory.getLogger(SensorMapperImpl.class);

    @Inject
    DataSource dataSource;

    @Singleton
    public SqlSessionFactory sqlSessionFactory() {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();

        Environment environment =
            new Environment("dev", transactionFactory, this.dataSource);

        Configuration configuration = new Configuration(environment);
        configuration.addMappers("com.objecteffects.persistence.mapper");

        return new SqlSessionFactoryBuilder().build(configuration);
    }
}
