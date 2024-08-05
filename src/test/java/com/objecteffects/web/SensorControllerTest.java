package com.objecteffects.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.objecteffects.persistence.domain.Sensor;
import com.objecteffects.persistence.jdbc.SensorUpdateCommand;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;

import jakarta.inject.Inject;

@MicronautTest
@TestMethodOrder(OrderAnnotation.class)
public class SensorControllerTest {
    @Inject
    @Client("/")
    HttpClient client;

    Long id;
    List<Long> sensorIds = new ArrayList<>();

    @Test
    public void testFindNonExistingsensorReturns404() {
        HttpClientResponseException thrown =
            assertThrows(HttpClientResponseException.class, () -> {
                this.client.toBlocking()
                    .exchange(HttpRequest.GET("/sensors/99"));
            });

        assertNotNull(thrown.getResponse());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
    }

    @Test
    public void post1() {
        HttpRequest<?> request = HttpRequest.POST("/sensors",
            Collections.singletonMap("name", "DevOps"));
        HttpResponse<?> response = this.client.toBlocking().exchange(request);
        this.sensorIds.add(entityId(response));

        assertEquals(HttpStatus.CREATED, response.getStatus());
    }

//    @Test
//    @Order(1)
    public void post2() {
        HttpRequest<?> request = HttpRequest.POST("/sensors",
            Collections.singletonMap("name", "Microservices"));
        HttpResponse<?> response = this.client.toBlocking().exchange(request);

        assertEquals(HttpStatus.CREATED, response.getStatus());

        List<Long> sensorIds = new ArrayList<>();

        this.id = entityId(response);
        sensorIds.add(this.id);
        request = HttpRequest.GET("/sensors/" + this.id);

        Sensor sensor =
            this.client.toBlocking().retrieve(request, Sensor.class);

        assertEquals("Microservices", sensor.getName());
    }

//    @Test
//    @Order(2)
    public void testsensorCrudOperations() {
        HttpRequest<?> request = HttpRequest.PUT("/sensors",
            new SensorUpdateCommand(this.id, "Micro-services"));
        HttpResponse<?> response = this.client.toBlocking().exchange(request);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());

        request = HttpRequest.GET("/sensors/" + this.id);

        Sensor sensor =
            this.client.toBlocking().retrieve(request, Sensor.class);

        sensor = this.client.toBlocking().retrieve(request, Sensor.class);
        assertEquals("Micro-services", sensor.getName());

        request = HttpRequest.GET("/sensors/list");
        List<Sensor> sensors = this.client.toBlocking().retrieve(request,
            Argument.of(List.class, Sensor.class));

        assertEquals(2, sensors.size());

        request = HttpRequest.POST("/sensors/ex",
            Collections.singletonMap("name", "Microservices"));
        response = this.client.toBlocking().exchange(request);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());

        request = HttpRequest.GET("/sensors/list");
        sensors = this.client.toBlocking().retrieve(request,
            Argument.of(List.class, Sensor.class));

        assertEquals(2, sensors.size());

        request = HttpRequest.GET("/sensors/list?size=1");
        sensors = this.client.toBlocking().retrieve(request,
            Argument.of(List.class, Sensor.class));

        assertEquals(1, sensors.size());
        assertEquals("DevOps", sensors.get(0).getName());

        request = HttpRequest.GET("/sensors/list?size=1&sort=name,desc");
        sensors = this.client.toBlocking().retrieve(request,
            Argument.of(List.class, Sensor.class));

        assertEquals(1, sensors.size());
        assertEquals("Micro-services", sensors.get(0).getName());

        request = HttpRequest.GET("/sensors/list?size=1&page=2");
        sensors = this.client.toBlocking().retrieve(request,
            Argument.of(List.class, Sensor.class));

        assertEquals(0, sensors.size());

        // cleanup:
        for (Long sensorId : this.sensorIds) {
            request = HttpRequest.DELETE("/sensors/" + sensorId);
            response = this.client.toBlocking().exchange(request);
            assertEquals(HttpStatus.NO_CONTENT, response.getStatus());
        }
    }

    protected Long entityId(HttpResponse<?> response) {
        String path = "/sensors/";
        String value = response.header(HttpHeaders.LOCATION);

        if (value == null) {
            return null;
        }

        int index = value.indexOf(path);

        if (index != -1) {
            return Long.valueOf(value.substring(index + path.length()));
        }

        return null;
    }
}
