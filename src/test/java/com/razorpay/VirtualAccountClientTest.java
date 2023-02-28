package com.razorpay;


import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mock;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class VirtualAccountClientTest extends BaseTest{

    @Mock
    ApiUtils apiUtils;

    private static final String VIRTUAL_ACCOUNT_ID = "va_DlGmm7jInLudH9";
    private static final String CUSTOMER_ID = "cust_DzbSeP2RJD1ZHg";
    private static final String CORP_NAME = "Acme Corp";
    private static final String ACTIVE_STATUS = "active";
    private static final String CLOSED_STATUS = "closed";

    /**
     * VirtualAccount is created using the bank-account and customer details.
     * @throws RazorpayException
     */
    @Test
    public void create() throws Exception{

        JSONObject request = new JSONObject("{\"receivers\":" +
                "{\"types\":[\"bank_account\"]}," +
                "\"allowed_payers\":[{\"type\":\"bank_account\"," +
                "\"bank_account\":{\"ifsc\":\"UTIB0000013\"," +
                "\"account_number\":\"914010012345679\"}}," +
                "{\"type\":\"bank_account\"," +
                "\"bank_account\":{\"ifsc\":\"UTIB0000014\"," +
                "\"account_number\":\"914010012345680\"}}]," +
                "\"description\":\"VirtualAccountcreatedforRaftarSoft\"," +
                "\"customer_id\":\"cust_CaVDm8eDRSXYME\"," +
                "\"close_by\":1681615838," +
                "\"notes\":{\"project_name\":\"BankingSoftware\"}}");

        String mockedResponseJson = "{\n" +
                "  \"id\":\"va_DlGmm7jInLudH9\",\n" +
                "  \"name\":\"Acme Corp\",\n" +
                "  \"entity\":\"virtual_account\",\n" +
                "  \"status\":\"active\",\n" +
                "  \"description\":\"Virtual Account created for Raftar Soft\",\n" +
                "  \"amount_expected\":null,\n" +
                "  \"notes\":{\n" +
                "    \"project_name\":\"Banking Software\"\n" +
                "  },\n" +
                "  \"amount_paid\":0,\n" +
                "  \"customer_id\":\"cust_CaVDm8eDRSXYME\",\n" +
                "  \"receivers\":[\n" +
                "    {\n" +
                "      \"id\":\"ba_DlGmm9mSj8fjRM\",\n" +
                "      \"entity\":\"bank_account\",\n" +
                "      \"ifsc\":\"RATN0VAAPIS\",\n" +
                "      \"bank_name\": \"RBL Bank\",\n" +
                "      \"name\":\"Acme Corp\",\n" +
                "      \"notes\":[],\n" +
                "      \"account_number\":\"2223330099089860\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"allowed_payers\": [\n" +
                "    {\n" +
                "      \"type\": \"bank_account\",\n" +
                "      \"id\":\"ba_DlGmm9mSj8fjRM\",\n" +
                "      \"bank_account\": {\n" +
                "        \"ifsc\": \"UTIB0000013\",\n" +
                "        \"account_number\": \"914010012345679\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"bank_account\",\n" +
                "      \"id\":\"ba_Cmtnm5tSj6agUW\",\n" +
                "      \"bank_account\": {\n" +
                "        \"ifsc\": \"UTIB0000014\",\n" +
                "        \"account_number\": \"914010012345680\"\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"close_by\":1681615838,\n" +
                "  \"closed_at\":null,\n" +
                "  \"created_at\":1574837626\n" +
                "}";

        try {
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(Constants.VIRTUAL_ACCOUNT_CREATE, null);
            mockPostRequest(apiUtils,builder,request.toString(), mockedResponseJson);

            VirtualAccountClient virtualAccountClient = new VirtualAccountClient("test",apiUtils);
            VirtualAccount fetch = virtualAccountClient.create(request);
            assertNotNull(fetch);
            assertEquals(VIRTUAL_ACCOUNT_ID,fetch.get("id"));
            assertEquals(ACTIVE_STATUS,fetch.get("status"));
            assertEquals(CORP_NAME,fetch.get("name"));
            assertTrue(fetch.has("allowed_payers"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Retrieve all the virtualAccount details using virtualAccount id.
     * @throws RazorpayException
     */
    @Test
    public void fetch() throws Exception{
        String mockedResponseJson = "{\n" +
                "  \"id\": "+VIRTUAL_ACCOUNT_ID+",\n" +
                "  \"name\": \"Acme Corp\",\n" +
                "  \"entity\": \"virtual_account\",\n" +
                "  \"status\": \"active\",\n" +
                "  \"description\": \"Virtual Account for Raftar Soft\",\n" +
                "  \"amount_expected\": 5000,\n" +
                "  \"notes\": [],\n" +
                "  \"amount_paid\": null,\n" +
                "  \"customer_id\": \"cust_9xnHzNGIEY4TAV\",\n" +
                "  \"receivers\": [\n" +
                "    {\n" +
                "      \"id\": \"ba_D6Vw76RrHA3DC9\",\n" +
                "      \"entity\": \"bank_account\",\n" +
                "      \"ifsc\": \"RATN0VAAPIS\",\n" +
                "      \"bank_name\": \"RBL Bank\",\n" +
                "      \"name\": \"Acme Corp\",\n" +
                "      \"notes\": [],\n" +
                "      \"account_number\": \"2223330025991681\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"allowed_payers\": [\n" +
                "    {\n" +
                "      \"type\": \"bank_account\",\n" +
                "      \"id\":\"ba_DlGmm9mSj8fjRM\",\n" +
                "      \"bank_account\": {\n" +
                "        \"ifsc\": \"UTIB0000013\",\n" +
                "        \"account_number\": \"914010012345679\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"type\": \"bank_account\",\n" +
                "      \"id\":\"ba_Cmtnm5tSj6agUW\",\n" +
                "      \"bank_account\": {\n" +
                "        \"ifsc\": \"UTIB0000014\",\n" +
                "        \"account_number\": \"914010012345680\"\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"close_by\": null,\n" +
                "  \"closed_at\": 1568109789,\n" +
                "  \"created_at\": 1565939036\n" +
                "}";

        try {
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(String.format(Constants.VIRTUAL_ACCOUNT_GET, VIRTUAL_ACCOUNT_ID), null);
            mockGetRequest(apiUtils,builder,null, mockedResponseJson);

            VirtualAccountClient virtualAccountClient = new VirtualAccountClient("test",apiUtils);
            VirtualAccount fetch = virtualAccountClient.fetch(VIRTUAL_ACCOUNT_ID);
            assertNotNull(fetch);
            assertEquals(VIRTUAL_ACCOUNT_ID,fetch.get("id"));
            assertEquals(ACTIVE_STATUS,fetch.get("status"));
            assertEquals(CORP_NAME,fetch.get("name"));
            assertTrue(fetch.has("allowed_payers"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Details of all the virtualAccount can be retrieved.
     * @throws RazorpayException
     */
    @Test
    public void fetchAll() throws Exception{
        String mockedResponseJson = "{\n" +
                "  \"entity\": \"collection\",\n" +
                "  \"count\": 1,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"id\": \"va_Di5gbNptcWV8fQ\",\n" +
                "      \"name\": \"Acme Corp\",\n" +
                "      \"entity\": \"virtual_account\",\n" +
                "      \"status\": \"closed\",\n" +
                "      \"description\": \"Virtual Account created for M/S ABC Exports\",\n" +
                "      \"amount_expected\": 2300,\n" +
                "      \"notes\": {\n" +
                "        \"material\": \"teakwood\"\n" +
                "      },\n" +
                "      \"amount_paid\": 239000,\n" +
                "      \"customer_id\": \"cust_DOMUFFiGdCaCUJ\",\n" +
                "      \"receivers\": [\n" +
                "        {\n" +
                "          \"id\": \"ba_Di5gbQsGn0QSz3\",\n" +
                "          \"entity\": \"bank_account\",\n" +
                "          \"ifsc\": \"RATN0VAAPIS\",\n" +
                "          \"bank_name\": \"RBL Bank\",\n" +
                "          \"name\": \"Acme Corp\",\n" +
                "          \"notes\": [],\n" +
                "          \"account_number\": \"1112220061746877\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"allowed_payers\": [\n" +
                "        {\n" +
                "          \"type\": \"bank_account\",\n" +
                "          \"id\":\"ba_DlGmm9mSj8fjRM\",\n" +
                "          \"bank_account\": {\n" +
                "            \"ifsc\": \"UTIB0000013\",\n" +
                "            \"account_number\": \"914010012345679\"\n" +
                "          }\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"bank_account\",\n" +
                "          \"id\":\"ba_Cmtnm5tSj6agUW\",\n" +
                "          \"bank_account\": {\n" +
                "            \"ifsc\": \"UTIB0000014\",\n" +
                "            \"account_number\": \"914010012345680\"\n" +
                "          }\n" +
                "        }\n" +
                "      ],\n" +
                "      \"close_by\": 1574427237,\n" +
                "      \"closed_at\": 1574164078,\n" +
                "      \"created_at\": 1574143517\n" +
                "    },\n" +
                "  ]\n" +
                "}";

        try {
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(Constants.VIRTUAL_ACCOUNT_LIST,  null);
            mockGetRequest(apiUtils,builder,null, mockedResponseJson);
            VirtualAccountClient virtualAccountClient = new VirtualAccountClient("test",apiUtils);
            List<VirtualAccount> fetch = virtualAccountClient.fetchAll();
            assertNotNull(fetch);
            assertTrue(fetch.get(0).has("id"));
            assertTrue(fetch.get(0).has("entity"));
            assertTrue(fetch.get(0).has("name"));
            assertTrue(fetch.get(0).has("status"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Close a virtualAccount using virtualAccount id
     * @throws RazorpayException
     */
    @Test
    public void close() throws Exception{
        String mockedResponseJson = "{\n" +
                "  \"id\":"+VIRTUAL_ACCOUNT_ID+",\n" +
                "  \"name\":\"Acme Corp\",\n" +
                "  \"entity\":\"virtual_account\",\n" +
                "  \"status\":\"closed\",\n" +
                "  \"description\":\"Virtual Account created for M/S ABC Exports\",\n" +
                "  \"amount_expected\":null,\n" +
                "  \"notes\":{\n" +
                "    \"material\":\"teakwood\"\n" +
                "  },\n" +
                "  \"amount_paid\":239000,\n" +
                "  \"customer_id\":\"cust_DOMUFFiGdCaCUJ\",\n" +
                "  \"receivers\":[\n" +
                "    {\n" +
                "      \"id\":\"vpa_CkTmLXqVYPkbxx\",\n" +
                "      \"entity\":\"vpa\",\n" +
                "      \"username\": \"rpy.payto00000468657501\",\n" +
                "      \"handle\": \"icici\",\n" +
                "      \"address\": \"rpy.payto00000468657501@icici\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"close_by\":1574427237,\n" +
                "  \"closed_at\":1574164078,\n" +
                "  \"created_at\":1574143517\n" +
                "}";

        try {
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(String.format(Constants.VIRTUAL_ACCOUNT_CLOSE, VIRTUAL_ACCOUNT_ID),  null);
            mockPostRequest(apiUtils,builder,null, mockedResponseJson);
            VirtualAccountClient virtualAccountClient = new VirtualAccountClient("test",apiUtils);
            VirtualAccount fetch = virtualAccountClient.close(VIRTUAL_ACCOUNT_ID);
            assertNotNull(fetch);
            assertEquals(VIRTUAL_ACCOUNT_ID,fetch.get("id"));
            assertEquals(CLOSED_STATUS,fetch.get("status"));
            assertEquals(CORP_NAME,fetch.get("name"));
            assertTrue(fetch.has("receivers"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Add Receiver to an Existing Virtual Account
     * @throws RazorpayException
     */
    @Test
    public void addReceiver() throws Exception{
        JSONObject request = new JSONObject("{\"types\":[\"vpa\"],\"vpa\":{\"descriptor\":\"gaurikumar\"}}");

        String mockedResponseJson = "{\n" +
                "  \"id\": "+VIRTUAL_ACCOUNT_ID+",\n" +
                "  \"name\": \"Acme Corp\",\n" +
                "  \"entity\": \"virtual_account\",\n" +
                "  \"status\": \"active\",\n" +
                "  \"description\": \"\",\n" +
                "  \"amount_expected\": null,\n" +
                "  \"notes\": [],\n" +
                "  \"amount_paid\": 0,\n" +
                "  \"customer_id\": \"cust_DzbSeP2RJD1ZHg\",\n" +
                "  \"receivers\": [\n" +
                "    {\n" +
                "      \"id\": \"ba_DzcFjVqAMSCEIW\",\n" +
                "      \"entity\": \"bank_account\",\n" +
                "      \"ifsc\":\"RATN0VAAPIS\",\n" +
                "      \"bank_name\": \"RBL Bank\",\n" +
                "      \"name\": \"Acme Corp\",\n" +
                "      \"notes\": [],\n" +
                "      \"account_number\": \"2223333232194699\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"vpa_DzcZR5ofjCUKAx\",\n" +
                "      \"entity\": \"vpa\",\n" +
                "      \"username\": \"rpy.payto00000gaurikumar\",\n" +
                "      \"handle\": \"icici\",\n" +
                "      \"address\": \"rpy.payto00000gaurikumar@icici\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"close_by\": null,\n" +
                "  \"closed_at\": null,\n" +
                "  \"created_at\": 1577969986\n" +
                "}";

        try {
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(String.format(Constants.VIRTUAL_ACCOUNT_RECEIVERS, VIRTUAL_ACCOUNT_ID),  null);
            mockPostRequest(apiUtils,builder,request.toString(), mockedResponseJson);
            VirtualAccountClient virtualAccountClient = new VirtualAccountClient("test",apiUtils);
            VirtualAccount fetch = virtualAccountClient.addReceiver(VIRTUAL_ACCOUNT_ID, request);
            assertNotNull(fetch);
            assertEquals(VIRTUAL_ACCOUNT_ID,fetch.get("id"));
            assertEquals(CUSTOMER_ID,fetch.get("customer_id"));
            assertEquals(CORP_NAME,fetch.get("name"));
            assertTrue(fetch.has("receivers"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

    /**
     * Add an Allowed Payer Account
     * @throws RazorpayException
     */
    @Test
    public void addAllowedPayers() throws Exception{
        JSONObject request = new JSONObject("{\"type\":\"bank_account\"," +
                "\"bank_account\":" +
                "{\"ifsc\":\"UTIB0000013\"," +
                "\"account_number\":\"914010012345679\"}}");

        String mockedResponseJson = "{\n" +
                "  \"id\":\"va_DlGmm7jInLudH9\",\n" +
                "  \"name\":\"Acme Corp\",\n" +
                "  \"entity\":\"virtual_account\",\n" +
                "  \"status\":\"active\",\n" +
                "  \"description\":\"Virtual Account created for Raftar Soft\",\n" +
                "  \"amount_expected\":null,\n" +
                "  \"notes\":{\n" +
                "    \"project_name\":\"Banking Software\"\n" +
                "  },\n" +
                "  \"amount_paid\":0,\n" +
                "  \"customer_id\":\"cust_DzbSeP2RJD1ZHg\",\n" +
                "  \"receivers\":[\n" +
                "    {\n" +
                "      \"id\":\"ba_DlGmm9mSj8fjRM\",\n" +
                "      \"entity\":\"bank_account\",\n" +
                "      \"ifsc\":\"RATN0VAAPIS\",\n" +
                "      \"bank_name\": \"RBL Bank\",\n" +
                "      \"name\":\"Acme Corp\",\n" +
                "      \"notes\":[],\n" +
                "      \"account_number\":\"2223330099089860\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"allowed_payers\": [\n" +
                "    {\n" +
                "      \"type\": \"bank_account\",\n" +
                "      \"id\":\"ba_DlGmm9mSj8fjRM\",\n" +
                "      \"bank_account\": {\n" +
                "        \"ifsc\": \"UTIB0000013\",\n" +
                "        \"account_number\": \"914010012345679\"\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"close_by\":1681615838,\n" +
                "  \"closed_at\":null,\n" +
                "  \"created_at\":1574837626\n" +
                "}";

        try {
            apiUtils = mock(ApiUtils.class);
            URL builder = ApiClient.getBuilder(String.format(Constants.VIRTUAL_ACCOUNT_ALLOWEDPAYERS, VIRTUAL_ACCOUNT_ID),  null);
            mockPostRequest(apiUtils,builder,request.toString(), mockedResponseJson);
            VirtualAccountClient virtualAccountClient = new VirtualAccountClient("test",apiUtils);
            VirtualAccount response = virtualAccountClient.addAllowedPayers(VIRTUAL_ACCOUNT_ID, request);
            assertNotNull(response);
            assertEquals(VIRTUAL_ACCOUNT_ID,response.get("id"));
            assertEquals(CUSTOMER_ID,response.get("customer_id"));
            assertEquals(CORP_NAME,response.get("name"));
            assertTrue(response.has("allowed_payers"));
        } catch (IOException e) {
            assertTrue(false);
        }
    }

}