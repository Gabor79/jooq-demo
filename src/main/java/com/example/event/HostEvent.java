package com.example.event;

import com.example.model.AJooq;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.outbox.quarkus.ExportedEvent;

import java.io.IOException;
import java.time.Instant;

public final class HostEvent implements ExportedEvent<String, String> {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final AJooq host;
    private final Instant instant;

    public HostEvent(AJooq host, Instant instant){
        this.host=host;
        this.instant = instant;
    }
    @Override
    public String getAggregateId() {
        return String.valueOf(host.getId());
    }

    @Override
    public String getAggregateType() {
        return "service";
    }

    @Override
    public String getType() {
        return "ServiceCreated";
    }

    @Override
    public Instant getTimestamp() {
        return instant;
    }

    @Override
    public String getPayload() {
        String s = null;
        try {
             s = MAPPER.writeValueAsString(host);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}
