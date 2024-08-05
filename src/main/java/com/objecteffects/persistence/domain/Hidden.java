package com.objecteffects.persistence.domain;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;

import jakarta.validation.constraints.NotNull;

@Serdeable
@MappedEntity
public class Hidden {
    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    private Long id;

    @NotNull
    private Long sensorId;

    public Long getId() {
        return this.id;
    }

    public void setId(Long _id) {
        this.id = _id;
    }

    @NotNull
    public Long getSensorId() {
        return this.sensorId;
    }

    public void setSensorId(@NotNull Long _sensorId) {
        this.sensorId = _sensorId;
    }
}
