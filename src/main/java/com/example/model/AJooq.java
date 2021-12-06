package com.example.model;


public class AJooq {
    private final Long id;
    private final String name;

    public AJooq(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
