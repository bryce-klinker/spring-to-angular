package com.spring.springtoangular;

public class Director {
    private final long id;
    private final String firstName;
    private final String lastName;

    public Director(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public long getId() {
        return id;
    }
}
