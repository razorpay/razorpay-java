package com.razorpay;

import okhttp3.*;
import org.json.JSONObject;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BaseTest {
    @InjectMocks
    protected AddonClient client = new AddonClient("test");
    private OkHttpClient okHttpClient;
    Response mockedResponse;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        mockGetCall();

    }

    private void mockGetCall() throws IOException {

        okHttpClient = mock(OkHttpClient.class);
        mockedResponse = mock(Response.class);
        Field clientField = ReflectionUtils.findField(ApiUtils.class, "client", OkHttpClient.class);
        clientField.setAccessible(true);
        ReflectionUtils.setField(clientField,new ApiUtils(),okHttpClient);
        Call call = mock(Call.class);
        when(call.execute()).thenReturn(mockedResponse);
        when(okHttpClient.newCall(anyObject())).thenReturn(call);
    }

    protected void mockResponseHTTPCodeFromExternalClient(int code)
    {
        when(mockedResponse.code()).thenReturn(200);
    }
    protected void mockURL(List<String> urlString)
    {
        HttpUrl url = mock(HttpUrl.class);
        when(url.pathSegments()).thenReturn(urlString);
        Request request = mock(Request.class);
        when(request.url()).thenReturn(url);
        when(mockedResponse.request()).thenReturn(request);
    }
    protected void mockResponseFromExternalClient(String jsonObject) throws IOException {
        JSONObject parse = new JSONObject(jsonObject);
        ResponseBody rb = mock(ResponseBody.class);
        when(rb.string()).thenReturn(parse.toString());
        when(mockedResponse.body()).thenReturn(rb);
    }
}
