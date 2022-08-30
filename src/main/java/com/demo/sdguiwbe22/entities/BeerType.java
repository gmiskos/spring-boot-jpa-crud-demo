package com.demo.sdguiwbe22.entities;

public enum BeerType {
    LAGER("LAGER"),
    PILSNER("PILSNER");

    private String beerType;

    BeerType(String beerType) {
        this.beerType = beerType;
    }

    public String getText() {
        return this.beerType;
    }

    public String getBeerType() {
        return beerType;
    }

    public static BeerType fromString(String text) {
        for (BeerType b : BeerType.values()) {
            if (b.beerType.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
