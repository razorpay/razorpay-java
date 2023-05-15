package com.razorpay;

public class IinClient extends ApiClient{

    IinClient(String auth) {
        super(auth);
    }

    public Iin fetch(String id) throws RazorpayException {
        return get(Constants.VERSION, String.format(Constants.IIN_FETCH, id), null);
    }

}
