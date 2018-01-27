package com.company.model;

/**
 * Asset Model Object
 *<P>Model representing the Asset Class. Includes id </P>
 *
 */
public class Asset {
    private String id;

    public Asset(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
