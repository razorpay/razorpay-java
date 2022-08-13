package com.razorpay;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.commons.lang3.text.WordUtils;


class ApiClient {

    String auth;

    ApiUtils apiUtils;

    private final String ENTITY = "entity";

    private final String COLLECTION = "collection";

    private final String ERROR = "error";

    private final String DESCRIPTION = "description";

    private final String STATUS_CODE = "code";

    private final int STATUS_OK = 200;

    private final int STATUS_MULTIPLE_CHOICE = 300;

    ApiClient(String auth,ApiUtils apiUtils) {
        this.auth = auth;
        this.apiUtils = apiUtils;
    }

    public <T extends Entity> T get(String path, JSONObject requestObject) throws RazorpayException, IOException, URISyntaxException, JSONException {

        String query = null;
        if(requestObject != null){
            query = queryBuilder(requestObject);
        }
        URL builder = getBuilder(path,query);
        String request = requestObject==null ? null : requestObject.toString();
        String response = apiUtils.processGetRequest(builder.toString(),request, auth);
        return processResponse(response,builder.toString());
    }

    public <T extends Entity> T post(String path, JSONObject requestObject) throws RazorpayException, IOException, URISyntaxException, JSONException {
        URL builder = getBuilder(path,null);
        String request = requestObject==null ? null : requestObject.toString();
        String response = apiUtils.processPostRequest(builder.toString(),request, auth);
        return processResponse(response,builder.toString());
    }

    public <T extends Entity> T put(String path, JSONObject requestObject) throws RazorpayException, JSONException, IOException, URISyntaxException {
        URL builder = getBuilder(path,null);
        String response = apiUtils.processPutRequest(builder.toString(),requestObject.toString(), auth);
        return processResponse(response,builder.toString());
    }

    public <T extends Entity> T patch(String path, JSONObject requestObject) throws RazorpayException, IOException, URISyntaxException, JSONException {
        URL builder = getBuilder(path,null);
        String response = apiUtils.processPatchRequest(builder.toString(),requestObject.toString(), auth);
        return processResponse(response,builder.toString());
    }

    public <T> T delete(String path, JSONObject requestObject) throws RazorpayException, IOException, URISyntaxException, JSONException {
        URL builder = getBuilder(path,null);
        String request = requestObject==null ? null : requestObject.toString();
        String response = apiUtils.processDeleteRequest(builder.toString(),request, auth);
        return processDeleteResponse(response,builder.toString());
    }

    <T extends Entity> ArrayList<T> getCollection(String path, JSONObject requestObject)
            throws RazorpayException, IOException, URISyntaxException, JSONException {
        String query = null;
        if(requestObject != null){
            query = queryBuilder(requestObject);
        }
        URL builder = getBuilder(path,query);
        String response = apiUtils.processGetRequest(builder.toString(),null, auth);
        return processCollectionResponse(response,builder.toString());
    }

    <T extends Entity> ArrayList<T> postCollection(String path, JSONObject requestObject) throws RazorpayException, IOException, URISyntaxException, JSONException {
        URL builder = getBuilder(path,null);
        String response = apiUtils.processPostRequest(builder.toString(),requestObject.toString(), auth);
        return processCollectionResponse(response,builder.toString());
    }


    private <T extends Entity> ArrayList<T> parseCollectionResponse(JSONArray jsonArray, URL  requestUrl)
            throws RazorpayException, JSONException {

        ArrayList<T> modelList = new ArrayList<T>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                T t = parseResponse(jsonObj, getEntity(jsonObj,requestUrl));
                modelList.add(t);
            }
            return modelList;
        } catch (RazorpayException e) {
            throw e;
        }
    }

    <T extends Entity> T processResponse(String response,String url) throws RazorpayException, JSONException, IOException {
        if (response == null) {
            throw new RazorpayException("Invalid Response from server");
        }

        JSONObject responseJson = new JSONObject(response);
        return parseResponse(responseJson, getEntity(responseJson, new URL(url)));

    }

    private <T extends Entity> T parseResponse(JSONObject jsonObject, String entity) throws RazorpayException {
        if (entity != null) {
            Class<T> cls = getClass(entity);
            try {
                return cls.getConstructor(JSONObject.class).newInstance(jsonObject);
            } catch (Exception e) {
                throw new RazorpayException("Unable to parse response because of " + e.getMessage());
            }
        }
        throw new RazorpayException("Unable to parse response");
    }

    <T extends Entity> ArrayList<T> processCollectionResponse(String response,String url)
            throws RazorpayException, JSONException, IOException {
        if (response == null) {
            throw new RazorpayException("Invalid Response from server");
        }
        String collectionName  = null;
        JSONObject responseJson = new JSONObject(response);
        collectionName = responseJson.has("payment_links")?  "payment_links": "items";
        return parseCollectionResponse(responseJson.getJSONArray(collectionName), new URL(url));
    }

    private  <T extends Object> T processDeleteResponse(String response,String url) throws RazorpayException, JSONException, IOException {
        if (response == null) {
            throw new RazorpayException("Invalid Response from server");
        }
        JSONObject responseJson = null;
        if(response.startsWith("[")){
            return (T) Collections.emptyList();
        }
        else if(response.length()==0){
            return null;
        }
        else{
            responseJson = new JSONObject(response);
        }

        return (T) parseResponse(responseJson, getEntity(responseJson, new URL(url)));
    }

    /*
     * this method will take http url as : https://api.razorpay.com/v1/invocies
     * and will return entity name with the help of @EntityNameURLMapping class
     */
    private String getEntityNameFromURL(URL url) {
        String[] path = url.getPath().split("/");
        String param = path[2];
        return EntityNameURLMapping.getEntityName(param);
    }

    private String getEntity(JSONObject jsonObj, URL url) throws JSONException {
        if(!jsonObj.has(ENTITY)) {
            return getEntityNameFromURL(url);
        }else if(jsonObj.get("entity").toString().equals("settlement.ondemand")){
            return "settlement";
        }else{
            return jsonObj.getString(ENTITY);
        }
    }

    private String queryBuilder(JSONObject requestObject) throws JSONException {
        String query = null;
            Iterator<String> keys = requestObject.keys();
            while(keys.hasNext()) {
                String key = keys.next();
                if(query==null){
                    query = "";
                }
                query += "&" + key + "=" + requestObject.get(key);
            }

        return query;
    }

    public static URL getBuilder(String path,String query) throws URISyntaxException, MalformedURLException {
        URI uri = new URI(Constants.SCHEME, Constants.HOSTNAME, "/"+Constants.VERSION + "/"+path+"", query,null);
        return uri.toURL();
    }

    private Class getClass(String entity) {
        try {
            String CapEntity = entity.substring(0, 1).toUpperCase() + entity.substring(1);
            String entityClass = "com.razorpay." + WordUtils.capitalize(entity, '_').replaceAll("_", "");
            return Class.forName(entityClass);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}