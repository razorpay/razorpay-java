package com.razorpay;

import java.util.Arrays;


public enum EntityNameURLMapping {
    
	INVOICES("invoice"),
	PAYMENTS("payment"),
	ITEMS("item");

    private String entity;

    EntityNameURLMapping(String entity) {
        this.entity= entity;
    }
    
    private String getEntity()
    {
        return entity;
    }

    public static String getEntityClassName(String urlStirng)
    {
        EntityNameURLMapping item = Arrays.asList(values()).stream().filter( val -> val.name().equalsIgnoreCase(urlStirng)).findFirst().orElseThrow(() -> new IllegalArgumentException("Unable to resolve"));
        return item.getEntity();
    }
}