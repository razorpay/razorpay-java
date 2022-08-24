package com.razorpay;

import java.util.*;

/**
 * Enum name is acting as url and entity is denoting Entity class
 * ex: https://api.razorpay.com/v1/invoices
 * getEntityName method will take "invoices" from above mentioned url
 * and will return "invoice" as entity name as mentioned below in mapping INVOICES("invoice")
 */

public enum EntityNameURLMapping {

    INVOICES("invoice"),
    SETTLEMENTS("settlement"),
    PAYMENTS("payment"),
    PAYMENT_LINKS("payment_link"),
    ITEMS("item"),
    CUSTOMERS("customer");

    private String entity;

    EntityNameURLMapping(String entity) {
        this.entity= entity;
    }

    private String getEntity()
    {
        return entity;
    }

    public static String getEntityName(String urlString)
    {
        EntityNameURLMapping entityNames[]  = EntityNameURLMapping.values();
        for(EntityNameURLMapping entityName : entityNames){
            if(urlString.equals(entityName.toString().toLowerCase())){
                return entityName.entity;
            }
        }
        throw new IllegalArgumentException("Unable to resolve");
    }
}