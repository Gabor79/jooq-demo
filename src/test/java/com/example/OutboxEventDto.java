package com.example;

import java.util.Objects;


final class OutboxEventDto {

    private final String aggregateId;
    private final String aggregateType;
    private final String type;
    private final String payload;

    public OutboxEventDto(String aggregateId, String aggregateType, String type, String payload) {
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.type = type;
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OutboxEventDto that = (OutboxEventDto) o;
        return aggregateId.equals(that.aggregateId) && aggregateType.equals(that.aggregateType)
                && type.equals(that.type) && payload.equals(that.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aggregateId, aggregateType, type, payload);
    }
}
