package com.objecteffects.persistence.jdbc;

import com.objecteffects.persistence.domain.Sensor;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.exceptions.DataAccessException;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JdbcRepository(dialect = Dialect.H2)
public interface SensorRepository extends PageableRepository<Sensor, Long> {

    Sensor save(@NonNull @NotBlank String name);

    @Transactional
    default Sensor saveWithException(@NonNull @NotBlank String name) {
        save(name);
        throw new DataAccessException("test exception");
    }

    long update(@NonNull @NotNull @Id Long id, @NonNull @NotBlank String name);
}
