package com.razorpay;

import java.util.List;

import org.json.JSONObject;


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

    /**
     * It is wrapper of fetchAll with parameter here sending null defines fetchAll
     * with a default values without filteration
     * @throws RazorpayException RazorpayException
     * @return List of QR Codes
     */
    public List<QrCode> fetchAll() throws RazorpayException {
        return fetchAll(null);
    }

    /**
     * This method get list of QrCodes filtered by parameters @request
     * @throws RazorpayException RazorpayException
     * @param request JSONObject request
     * @return list of QR Codes
     */
    public List<QrCode> fetchAll(JSONObject request) throws RazorpayException {
       return getCollection(Constants.QRCODE_LIST, request);
    }

    public List<QrCode> fetchAllPayments(String id) throws RazorpayException {
        return fetchAllPayments(id,null);
    }

    public List<QrCode> fetchAllPayments(String id,JSONObject request) throws RazorpayException {
        return getCollection(Constants.QRCODE_LIST+"/"+id+"/"+Constants.QRCODE_FETCH_PAYMENT, request);
    }
    
    public QrCode close(String id) throws RazorpayException {
        return post(String.format(Constants.QRCODE_CLOSE, id), null);
    }

}
