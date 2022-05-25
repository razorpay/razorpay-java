package com.razorpay.payout.handlers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class ListResponseHandler<T> implements ResponseHandler<List<T>> {

    private final Class<T> type;
    private final String name;

    public ListResponseHandler(Class<T> type, String name) {
        this.type = type;
        this.name = name;
    }

    public List<T> parse(Response response) throws RazorpayException {
        if (response == null) {
            throw new RazorpayException("Invalid Response from server");
        }

        int statusCode = response.code();
        InputStream responseBody;
        List<T> result;
        try {
            responseBody = Objects.requireNonNull(response.body()).byteStream();
            if (statusCode >= 200 && statusCode < 300) {
                CollectionType collectionType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, type);
                JsonNode fromValue = OBJECT_MAPPER.readTree(responseBody).get(name);
                result = OBJECT_MAPPER.convertValue(fromValue, collectionType);
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
