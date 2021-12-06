package com.example;

import com.example.model.AJooq;
import com.example.service.AService;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.jooq.DSLContext;
import org.jooq.Record4;
import org.jooq.Result;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.example.generated.meta.tables.OutboxEvent.OUTBOX_EVENT;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class AServiceWithJooqIT {

    @Inject
    AService underTest;
    @Inject
    DSLContext jooq;

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

    @SuppressWarnings("rawtypes")
    private List<OutboxEventDto> getOutboxEvents() {
        Result<Record4<String, String, String, String>> outboxEvents =
                jooq.select(OUTBOX_EVENT.AGGREGATE_ID, OUTBOX_EVENT.AGGREGATE_TYPE, OUTBOX_EVENT.TYPE,
                            OUTBOX_EVENT.PAYLOAD)
                    .from(OUTBOX_EVENT)
                    .fetch();

        List<OutboxEventDto> outboxEventsList = new ArrayList<>();
        for (Record4 record : outboxEvents) {
            String aggregateType = record.get(OUTBOX_EVENT.AGGREGATE_TYPE, String.class);
            String aggregateId = record.get(OUTBOX_EVENT.AGGREGATE_ID, String.class);
            String type = record.get(OUTBOX_EVENT.TYPE, String.class);
            String payload = record.get(OUTBOX_EVENT.PAYLOAD, String.class);
            OutboxEventDto outboxEvent = new OutboxEventDto(aggregateId, aggregateType, type, payload);
            outboxEventsList.add(outboxEvent);
        }
        return outboxEventsList;
    }
}
