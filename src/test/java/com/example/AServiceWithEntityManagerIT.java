package com.example;

import com.example.model.AJooq;
import com.example.service.AService;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class AServiceWithEntityManagerIT {

    @Inject
    AService underTest;
    @Inject
    EntityManager entityManager;


    @Test
    @TestTransaction
    void test1(){
        underTest.addHost(new AJooq(1L, "name1"));
        List<OutboxEventDto> outboxEvents = getOutboxEvents();
        assertThat(outboxEvents).hasSize(1);
    }

    @Test
    @TestTransaction
    void test2(){
        underTest.addHost(new AJooq(2L, "name2"));
        List<OutboxEventDto> outboxEvents = getOutboxEvents();
        assertThat(outboxEvents).hasSize(1);
    }

    private List<OutboxEventDto> getOutboxEvents() {
        Query outboxQuery =
                entityManager.createNativeQuery("SELECT aggregate_id,aggregate_type, type, payload FROM outbox_event");

        List<Object[]> objects = outboxQuery.getResultList();
        return objects.stream()
                .map(o -> new OutboxEventDto((String) o[0], (String) o[1], (String) o[2], (String) o[3]))
                .collect(Collectors.toList());
    }
}
