package com.razorpay;

import okhttp3.*;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.json.JSONObject;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaseTest {

    private OkHttpClient okHttpClient;
    Response mockedResponse;

    static final String TEST_SECRET_KEY = "test";

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        mockGetCall();

    }

    private void mockGetCall() throws IOException, IllegalAccessException {

        okHttpClient = mock(OkHttpClient.class);
        mockedResponse = mock(Response.class);
        Field clientField =FieldUtils.getDeclaredField(ApiUtils.class,"client",true);
        FieldUtils.writeField(clientField,new ApiUtils(),okHttpClient);

        Call call = mock(Call.class);
        when(call.execute()).thenReturn(mockedResponse);
        when(okHttpClient.newCall(anyObject())).thenReturn(call);

    }

    protected void mockResponseHTTPCodeFromExternalClient(int code)
    {
        when(mockedResponse.code()).thenReturn(code);
    }
    protected void mockURL(List<String> urlString)
    {
        HttpUrl url = mock(HttpUrl.class);
        when(url.pathSegments()).thenReturn(urlString);
        Request request = mock(Request.class);
        when(request.url()).thenReturn(url);
        when(mockedResponse.request()).thenReturn(request);
    }
    protected void mockResponseFromExternalClient(String response) throws IOException {
        JSONObject parse = new JSONObject(response);
        ResponseBody rb = mock(ResponseBody.class);
        when(rb.string()).thenReturn(parse.toString());
        when(mockedResponse.body()).thenReturn(rb);
    }

    protected OkHttpClient getOkHttpClient()
    {
        return okHttpClient;
    }
}