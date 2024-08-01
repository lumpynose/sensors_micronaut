package com.objecteffects.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Property;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.Sql;
import io.micronaut.test.annotation.TransactionMode;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest(transactionMode = TransactionMode.SINGLE_TRANSACTION)
@Property(name = "datasources.default.dialect", value = "H2")
@Property(name = "datasources.default.driverClassName", value = "org.h2.Driver")
@Property(name = "datasources.default.schema-generate", value = "CREATE_DROP")
@Property(name = "datasources.default.url", value = "jdbc:h2:mem:devDb;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE")
@Property(name = "datasources.default.username", value = "sa")

@Sql({ "classpath:create.sql", "classpath:insert.sql" })
class DataSourceTest {
    final static Logger log = LoggerFactory.getLogger(DataSourceTest.class);

    @Inject
    EmbeddedServer server;

    @Inject
    ApplicationContext context;

    @Inject
    DataSource dataSource;

    @Test
    void dataIsInserted() throws Exception {
        assertEquals(List.of("Aardvark", "Albatross"),
            readAllNames(this.dataSource));
    }

    List<String> readAllNames(DataSource dataSource) throws SQLException {
        log.info("datasource: {}", dataSource.toString());

        var result = new ArrayList<String>();

        try (
            Connection ds = dataSource.getConnection();
            PreparedStatement ps =
                ds.prepareStatement("select name from MyTable");
            ResultSet rslt = ps.executeQuery()) {

            while (rslt.next()) {
                result.add(rslt.getString(1));
            }
        }
        return result;
    }
}
