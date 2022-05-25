package com.razorpay.payout;

import com.razorpay.RazorpayException;
import com.razorpay.payout.handlers.BaseResponseHandler;
import com.razorpay.payout.handlers.ListResponseHandler;
import com.razorpay.payout.handlers.ResponseHandler;
import com.razorpay.payout.model.Payout;
import lombok.Getter;
import okhttp3.*;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.razorpay.payout.HttpClient.fire;

public class RXClient implements Closeable {

    @Getter
    private final String url;
    private final String authToken;
    private final Map<String, String> headers;

    private RXClient(String url, String authToken, Map<String, String> headers) {
        this.url = url;
        this.authToken = authToken;
        this.headers = headers;
    }

    @Override
    public void close() {
        //close http connection or anything else which is opened at the time of program start
    }

    // Payout Operations
    public List<Payout> getPayouts(String accountNumber) throws IOException, RazorpayException {
        String finalUrl = url + "/v1/payouts?account_number=" + accountNumber;
        ResponseHandler<List<Payout>> responseHandler = new ListResponseHandler<>(Payout.class, "items");
        return fire("GET", finalUrl, "", prepareHeaders(), responseHandler);
    }

    public Payout getPayoutById(String payoutId) throws IOException, RazorpayException {
        if (!payoutId.startsWith("pout_")) throw new RazorpayException("Payout Id is incorrect");
        String finalUrl = url + "/v1/payouts/" + payoutId;
        ResponseHandler<Payout> responseHandler = new BaseResponseHandler<>(Payout.class);
        return fire("GET", finalUrl, "", prepareHeaders(), responseHandler);
    }

    //Http Client Abstraction

    private Map<String, String> prepareHeaders() {
        headers.put("Authorization", authToken);
        return headers;
    }

    public static class Builder {
        private final String url;
        private String key;
        private String secret;
        private final Map<String, String> headers;
        private boolean enableLogging;

        public Builder(String url) {
            this.url = url;
            this.headers = new HashMap<>();
        }

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder secret(String secret) {
            this.secret = secret;
            return this;
        }

        public Builder enableLogging(boolean enableLogging) {
            this.enableLogging = enableLogging;
            return this;
        }

        public Builder addHeaders(String name, String value) {
            Objects.requireNonNull(name, "Header name cannot be null");
            Objects.requireNonNull(value, "Header value cannot be null");
            headers.put(name, value);
            return this;
        }

        public RXClient build() throws RazorpayException {
            Objects.requireNonNull(key, "Key cannot be null");
            Objects.requireNonNull(secret, "Secret cannot be null");

            //Initializing Http Client
            HttpClient.createHttpClientInstance(enableLogging);
            return new RXClient(url, Credentials.basic(key, secret), headers);
        }
    }
}


