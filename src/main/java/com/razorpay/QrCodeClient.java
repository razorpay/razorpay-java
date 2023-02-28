package com.razorpay;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class QrCodeClient extends ApiClient {

    QrCodeClient(String auth, ApiUtils apiUtils) {
        super(auth,apiUtils);
    }

    public QrCode create(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return post(Constants.QRCODE_CREATE, request);
    }

    public QrCode fetch(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return get(String.format(Constants.QRCODE_FETCH, id), null);
    }

    /**
     * It is wrapper of fetchAll with parameter here sending null defines fetchAll
     * with a default values without filteration
     * @throws RazorpayException
     */
    public List<QrCode> fetchAll() throws RazorpayException, JSONException, IOException, URISyntaxException {
        return fetchAll(null);
    }

    /**
     * This method get list of QrCodes filtered by parameters @request
     * @throws RazorpayException
     */
    public List<QrCode> fetchAll(JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return getCollection(Constants.QRCODE_LIST, request);
    }

    public List<QrCode> fetchAllPayments(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return fetchAllPayments(id,null);
    }

    public List<QrCode> fetchAllPayments(String id, JSONObject request) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return getCollection(Constants.QRCODE_LIST+"/"+id+"/"+Constants.QRCODE_FETCH_PAYMENT, request);
    }

    public QrCode close(String id) throws RazorpayException, JSONException, IOException, URISyntaxException {
        return post(String.format(Constants.QRCODE_CLOSE, id), null);
    }

}