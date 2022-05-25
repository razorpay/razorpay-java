package com.razorpay.payout.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.razorpay.RazorpayException;
import okhttp3.Response;

public interface ResponseHandler<T> {
    ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    T parse(Response response) throws RazorpayException;
}
