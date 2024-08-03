package com.objecteffects.persistence.domain;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class Hidden {
    @Nullable
    private Long id;

    @NonNull
    private Long sensorId;

    @Nullable
    public Long getId() {
        return this.id;
    }

    public void setId(@Nullable Long _id) {
        this.id = _id;
    }

    @NonNull
    public Long getSensorId() {
        return this.sensorId;
    }

    public void setSensorId(@NonNull Long _sensorId) {
        this.sensorId = _sensorId;
    }
}
