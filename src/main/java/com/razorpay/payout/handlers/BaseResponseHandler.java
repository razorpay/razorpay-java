package com.razorpay.payout.handlers;

import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class BaseResponseHandler<T> implements ResponseHandler<T> {

    private final Class<T> type;

    public BaseResponseHandler(Class<T> type) {
        this.type = type;
    }

    public T parse(Response response) throws RazorpayException {
        if (response == null) {
            throw new RazorpayException("Invalid Response from server");
        }

        int statusCode = response.code();
        InputStream responseBody;
        T result;
        try {
            responseBody = Objects.requireNonNull(response.body()).byteStream();
            if (statusCode >= 200 && statusCode < 300) {
                result = OBJECT_MAPPER.readValue(responseBody, type);
            } else {
                log.error("[HTTP] Response Code {}", statusCode);
                Map errorResponse = OBJECT_MAPPER.readValue(responseBody, Map.class);
                log.error("[HTTP] Response Body {}", errorResponse);
                throw new RazorpayException("Server returned error");
            }
        } catch (IOException e) {
            throw new RazorpayException(e.getMessage());
        }

        return result;
    }
}