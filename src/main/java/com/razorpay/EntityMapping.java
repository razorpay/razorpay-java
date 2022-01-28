package com.razorpay;

import java.util.Arrays;


public enum EntityMapping {
    
    Invoice("invoices"),
    Payment("payments"),
    Default("empty");

    private String entity;

    EntityMapping(String entity) {
        this.entity= entity;
    }
    
    private String getEntity()
    {
        return entity;
    }

    public static String getEntityClassName(String urlStirng)
    {
        EntityMapping item = Arrays.asList(values()).stream().filter( val -> val.name().equalsIgnoreCase(urlStirng)).findFirst().orElseThrow(() -> new IllegalArgumentException("Unable to resolve"));
        return item.getEntity();
    }
}