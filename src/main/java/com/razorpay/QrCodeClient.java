package com.razorpay;

import java.util.List;

import org.json.JSONObject;

import okhttp3.Response;

public class QrCodeClient extends ApiClient {

    QrCodeClient(String auth) {
        super(auth);
    }

    public QrCode create(JSONObject request) throws RazorpayException {
        return post(Constants.VERSION, Constants.QRCODE_CREATE, request);
    }

    public QrCode fetch(String id) throws RazorpayException {
        return get(Constants.VERSION, String.format(Constants.QRCODE_FETCH, id), null);
    }

    /**
     * It is wrapper of fetchAll with parameter here sending null defines fetchAll
     * with a default values without filteration
     * @throws RazorpayException
     */
    public List<QrCode> fetchAll() throws RazorpayException {
        return fetchAll(null);
    }

    /**
     * This method get list of QrCodes filtered by parameters @request
     * @throws RazorpayException
     */
    public List<QrCode> fetchAll(JSONObject request) throws RazorpayException {
       return getCollection(Constants.VERSION, Constants.QRCODE_LIST, request);
    }

    public List<QrCode> fetchAllPayments(String id) throws RazorpayException {
        return fetchAllPayments(id,null);
    }

    public List<QrCode> fetchAllPayments(String id,JSONObject request) throws RazorpayException {
        return getCollection(Constants.VERSION, Constants.QRCODE_LIST+"/"+id+"/"+Constants.QRCODE_FETCH_PAYMENT, request);
    }
    
    public QrCode close(String id) throws RazorpayException {
        return post(Constants.VERSION, String.format(Constants.QRCODE_CLOSE, id), null);
    }

}
