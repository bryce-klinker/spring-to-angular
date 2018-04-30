package com.spring.springtoangular;

public class Movie {
    private final long id;
    private final String title;
    private final String rating;
    private final double criticRating;
    private final double ticketPrice;
    private final long directorId;

    public Movie(long id, String title, String rating, double criticRating, double ticketPrice, long directorId) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.criticRating = criticRating;
        this.ticketPrice = ticketPrice;
        this.directorId = directorId;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getRating() {
        return rating;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public double getCriticRating() {
        return criticRating;
    }

    public long getDirectorId() {
        return directorId;
    }
}
