package com.objecteffects.persistence.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.serde.annotation.Serdeable;

import jakarta.validation.constraints.NotBlank;

@Serdeable
public class Sensor {
    @Nullable
    private Long id;

    @NonNull
    @NotBlank
    private String name;

    @NotBlank
    @Nullable
    private String displayName;

    @Nullable
    private Integer channel;

    @Nullable
    public Long getId() {
        return this.id;
    }

    public void setId(@Nullable Long _id) {
        this.id = _id;
    }

    @NonNull
    public String getName() {
        return this.name;
    }

    public void setName(@NonNull String _name) {
        this.name = _name;
    }

    @NotBlank
    @Nullable
    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(@NotBlank @Nullable String _displayName) {
        this.displayName = _displayName;
    }

    @Nullable
    public Integer getChannel() {
        return this.channel;
    }

    public void setChannel(@Nullable Integer _channel) {
        this.channel = _channel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(this.id).append(this.name)
            .append(this.displayName).append(this.channel)
            .build();

    }
}
