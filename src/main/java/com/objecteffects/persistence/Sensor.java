package com.objecteffects.persistence;

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
    private Boolean hide;

    @Nullable
    public Long getId() {
        return this.id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return this.name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NotBlank
    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(@NotBlank String displayName) {
        this.displayName = displayName;
    }

    @Nullable
    public Boolean getHide() {
        return this.hide;
    }

    public void setHide(@Nullable Boolean hide) {
        this.hide = hide;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(this.id).append(this.name)
            .append(this.displayName).append(this.hide).toString();

    }
}
