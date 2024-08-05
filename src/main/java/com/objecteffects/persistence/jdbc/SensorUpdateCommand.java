package com.objecteffects.persistence.jdbc;

import io.micronaut.serde.annotation.Serdeable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Serdeable
public class SensorUpdateCommand {
    @NotNull
    private final Long id;

    @NotBlank
    private final String name;

    public SensorUpdateCommand(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

}
