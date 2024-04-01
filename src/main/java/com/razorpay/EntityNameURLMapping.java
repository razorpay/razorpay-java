package com.razorpay;

import java.util.Arrays;

 /**
 * Enum name is acting as url and entity is denoting Entity class
  * ex: https://api.razorpay.com/v1/invocies
  * getEntityName method will take "invoices" from above mentioned url
  * and will return "invoice" as entity name as mentioned below in mapping INVOICES("invoice")
 */

public enum EntityNameURLMapping {
    IINS("iin"),
    INVOICES("invoice"),
    SETTLEMENTS("settlement"),
    PAYMENTS("payment"),
    PAYMENT_LINKS("payment_link"),
    ITEMS("item"),
    CUSTOMERS("customer"),
    CARDS("card"),
    TOKENS("token"),
    ACCOUNTS("account"),
    TOKEN("oauth_token"),
    REVOKE("oauth_token");
    private String entity;

    EntityNameURLMapping(String entity) {
        this.entity= entity;
    }
    
    private String getEntity()
    {
        return entity;
    }

    public static String getEntityName(String urlStirng)
    {
        EntityNameURLMapping item = Arrays.asList(values()).stream().filter( val -> val.name().equalsIgnoreCase(urlStirng)).findFirst().orElseThrow(() -> new IllegalArgumentException("Unable to resolve"));
        return item.getEntity();
    }
}