package com.razorpay.payout;

import com.razorpay.RazorpayException;
import com.razorpay.payout.model.Payout;
import junit.framework.TestCase;
import org.junit.Assert;

import java.io.IOException;
import java.util.List;

public class RXClientTest extends TestCase {

    private final String BASE_URL = "https://api.razorpay.com";
    private final String KEY = "<<FILL_YOUR_KEY>>";
    private final String SECRET = "<<FILL_YOUR_SECRET>>";

    private final String ACCOUNT_NUMBER = "<<FILL_YOUR_ACCOUNT_NUMBER>>";
    private final String PAYOUT_ID = "<<FILL_YOUR_PAYOUT_ID>>";

    public void testGetPayouts() throws IOException, RazorpayException {

        RXClient rxClient = new RXClient.Builder(BASE_URL).key(KEY).secret(SECRET).build();
        List<Payout> payouts = rxClient.getPayouts(ACCOUNT_NUMBER);
        Assert.assertNotNull(payouts);
    }

    public void testGetPayoutById() throws IOException, RazorpayException {

        RXClient rxClient = new RXClient.Builder(BASE_URL).key(KEY).secret(SECRET).build();
        Payout payout = rxClient.getPayoutById(PAYOUT_ID);
        Assert.assertNotNull(payout);
    }
}