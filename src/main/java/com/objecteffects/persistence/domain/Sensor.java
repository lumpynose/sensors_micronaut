package com.objecteffects.persistence.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;

import jakarta.validation.constraints.NotBlank;

@Serdeable
@MappedEntity
public class Sensor {
    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    private Long id;

    @NonNull
    @NotBlank
    private String name;

    @Nullable
    private String displayName;

    @Nullable
    private Integer channel;

    public Long getId() {
        return this.id;
    }

    public void setId(Long _id) {
        this.id = _id;
    }

    @NonNull
    public String getName() {
        return this.name;
    }

    public void setName(@NonNull String _name) {
        this.name = _name;
    }

    @Nullable
    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(@NonNull String _displayName) {
        this.displayName = _displayName;
    }

    @Nullable
    public Integer getChannel() {
        return this.channel;
    }

    public void setChannel(Integer _channel) {
        this.channel = _channel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(this.id).append(this.name)
            .append(this.displayName).append(this.channel)
            .build();

    }
}
