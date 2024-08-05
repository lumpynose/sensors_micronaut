package com.objecteffects.persistence.jdbc;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.objecteffects.persistence.domain.Sensor;

import io.micronaut.data.exceptions.DataAccessException;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.Status;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@ExecuteOn(TaskExecutors.BLOCKING)
@Controller("/sensors")
public class SensorController {

    protected final SensorRepository SensorRepository;

    public SensorController(SensorRepository SensorRepository) {
        this.SensorRepository = SensorRepository;
    }

    @Get("/{id}")
    public Optional<Sensor> show(Long id) {
        return this.SensorRepository
            .findById(id);
    }

    @Put
    public HttpResponse<?> update(@Body @Valid SensorUpdateCommand command) {
        this.SensorRepository.update(command.getId(), command.getName());
        return HttpResponse
            .noContent()
            .header(HttpHeaders.LOCATION, location(command.getId()).getPath());
    }

    @Get("/list")
    public List<Sensor> list(@Valid Pageable pageable) {
        return this.SensorRepository.findAll(pageable).getContent();
    }

    @Post
    public HttpResponse<Sensor> save(@Body("name") @NotBlank String name) {
        Sensor Sensor = this.SensorRepository.save(name);

        return HttpResponse
            .created(Sensor)
            .headers(headers -> headers.location(location(Sensor.getId())));
    }

    @Post("/ex")
    public HttpResponse<Sensor> saveExceptions(@Body @NotBlank String name) {

        try {
            Sensor Sensor = this.SensorRepository.saveWithException(name);
            return HttpResponse
                .created(Sensor)
                .headers(headers -> headers.location(location(Sensor.getId())));
        }
        catch (DataAccessException e) {
            return HttpResponse.noContent();
        }
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    public void delete(Long id) {
        this.SensorRepository.deleteById(id);
    }

    protected URI location(Long id) {
        return URI.create("/sensors/" + id);
    }

    protected URI location(Sensor Sensor) {
        return location(Sensor.getId());
    }
}
