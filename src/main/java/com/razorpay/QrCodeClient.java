package com.razorpay;

import java.util.List;

import org.json.JSONObject;

import okhttp3.Response;

public class QrCodeClient extends ApiClient {

    QrCodeClient(String auth) {
        super(auth);
    }

    public QrCode create(JSONObject request) throws RazorpayException {
        return post(Constants.QRCODE_CREATE, request);
    }

    public QrCode fetch(String id) throws RazorpayException {
        return get(String.format(Constants.QRCODE_FETCH, id), null);
    }
    
    public List<QrCode> fetchAll() throws RazorpayException {
        return fetchAll(null);
    }

    public List<QrCode> fetchAll(JSONObject request) throws RazorpayException {
       return getCollection(Constants.QRCODE_LIST, request);
    }

    public List<QrCode> fetchAllPayments(String id,JSONObject request) throws RazorpayException {
        return getCollection(Constants.QRCODE_LIST+"/"+id+"/"+Constants.QRCODE_FETCH_PAYMENT, request);
    }
    
    public QrCode close(String id) throws RazorpayException {
        return post(String.format(Constants.QRCODE_CLOSE, id), null);
    }

}
