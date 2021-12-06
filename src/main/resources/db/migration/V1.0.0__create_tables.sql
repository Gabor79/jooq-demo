-- Models are described at https://nts-eu.atlassian.net/wiki/spaces/SWARCH/pages/1633031279/Infrastructure+Health+Monitor#Domain-Model

CREATE TABLE host
(
    host_id             BIGINT                   NOT NULL,
    name                VARCHAR(255)             NOT NULL
);


CREATE TABLE outbox_event
(
    id                    UUID                        NOT NULL,
    aggregate_type        VARCHAR(255)                NOT NULL,
    aggregate_id          VARCHAR(255)                NOT NULL,
    "type"                VARCHAR(255)                NOT NULL,
    timestamp             TIMESTAMP WITH TIME ZONE    NOT NULL,
    payload               VARCHAR(8000)               NOT NULL
);

