package com.example.service;

import com.example.event.HostEvent;
import com.example.model.AJooq;
import io.debezium.outbox.quarkus.ExportedEvent;
import org.jooq.DSLContext;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.Instant;

import static com.example.generated.meta.Tables.HOST;


@ApplicationScoped
public class AService {
    private final Event<ExportedEvent<String, String>> event;
    private final DSLContext jooq;

    @Inject
    public AService(Event<ExportedEvent<String, String>> event, DSLContext jooq ){
        this.event =event;
        this.jooq = jooq;
    }

    @Transactional
    public void addHost(AJooq host) {
        jooq.insertInto(HOST, HOST.HOST_ID, HOST.NAME).values(host.getId(),host.getName()).execute();
        event.fire(new HostEvent(host, Instant.now()));
    }
}
