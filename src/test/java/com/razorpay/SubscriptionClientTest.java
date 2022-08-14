package com.razorpay;


import org.json.JSONObject;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;


import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class SubscriptionClientTest extends BaseTest {

    public static final String TEST_PLAN_ID = "plan_00000000000001";
    public static final String TEST_SUBSCRIPTION_ID = "sub_00000000000001";
    public static final String TEST_SUBSCRIPTION_ID_2 = "testSubId";
    public static final String PLAN_ID = "plan_id";
    public static final String SUBSCRIPTION_ID = "id";
    public static final String ADDON_ID = "id";
    public static final String ADDON_SUBSCRIPTION_ID = "subscription_id";
    public static final String TEST_ADDON_ID = "ao_00000000000001";
    public static final String ADDON_ITEM_ID = "item_00000000000001";
    public static final String TEST_OFFER = "TEST_OFFER";
    @Mock
    ApiUtils apiUtils;

    @Test
    public void testCreate() throws Exception {
        JSONObject request =  new JSONObject("{\n" +
                "  \"plan_id\":\"plan_00000000000001\",\n" +
                "  \"total_count\":6,\n" +
                "  \"quantity\": 1,\n" +
                "  \"customer_notify\":1,\n" +
                "  \"start_at\":1580453311,\n" +
                "  \"expire_by\":1580626111,\n" +
                "  \"addons\":[\n" +
                "    {\n" +
                "      \"item\":{\n" +
                "        \"name\":\"Delivery charges\",\n" +
                "        \"amount\":30000,\n" +
                "        \"currency\":\"INR\"\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"offer_id\":\"offer_JHD834hjbxzhd38d\",\n" +
                "  \"notes\":{\n" +
                "    \"notes_key_1\":\"Tea, Earl Grey, Hot\",\n" +
                "    \"notes_key_2\":\"Tea, Earl Grey… decaf.\"\n" +
                "  }\n" +
                "}");

        String mockedResponseJson = "{\n" +
                "  \"id\": \"sub_00000000000001\",\n" +
                "  \"entity\": \"subscription\",\n" +
                "  \"plan_id\": \"plan_00000000000001\",\n" +
                "  \"status\": \"created\",\n" +
                "  \"current_start\": null,\n" +
                "  \"current_end\": null,\n" +
                "  \"ended_at\": null,\n" +
                "  \"quantity\": 1,\n" +
                "  \"notes\":{\n" +
                "    \"notes_key_1\":\"Tea, Earl Grey, Hot\",\n" +
                "    \"notes_key_2\":\"Tea, Earl Grey… decaf.\"\n" +
                "  },\n" +
                "  \"charge_at\": 1580453311,\n" +
                "  \"start_at\": 1580626111,\n" +
                "  \"end_at\": 1583433000,\n" +
                "  \"auth_attempts\": 0,\n" +
                "  \"total_count\": 6,\n" +
                "  \"paid_count\": 0,\n" +
                "  \"customer_notify\": true,\n" +
                "  \"created_at\": 1580280581,\n" +
                "  \"expire_by\": 1580626111,\n" +
                "  \"short_url\": \"https://rzp.io/i/z3b1R61A9\",\n" +
                "  \"has_scheduled_changes\": false,\n" +
                "  \"change_scheduled_at\": null,\n" +
                "  \"source\": \"api\",\n" +
                "  \"offer_id\":\"offer_JHD834hjbxzhd38d\",\n" +
                "  \"remaining_count\": 5\n" +
                "}";

        apiUtils = mock(ApiUtils.class);
        URL builder = ApiClient.getBuilder(Constants.SUBSCRIPTION_CREATE, null);
        mockPostRequest(apiUtils,builder,request.toString(), mockedResponseJson);

        SubscriptionClient subscriptionClient = new SubscriptionClient("test",apiUtils);
        Subscription subscription = subscriptionClient.create(request);
        assertEquals(TEST_PLAN_ID, subscription.get(PLAN_ID));
        assertEquals(TEST_SUBSCRIPTION_ID, subscription.get(SUBSCRIPTION_ID));
    }


    @Test
    public void testFetch() throws Exception {

        String mockedResponseJson = "{\n" +
                "  \"id\": \"sub_00000000000001\",\n" +
                "  \"entity\": \"subscription\",\n" +
                "  \"plan_id\": \"plan_00000000000001\",\n" +
                "  \"customer_id\": \"cust_D00000000000001\",\n" +
                "  \"status\": \"active\",\n" +
                "  \"current_start\": 1577355871,\n" +
                "  \"current_end\": 1582655400,\n" +
                "  \"ended_at\": null,\n" +
                "  \"quantity\": 1,\n" +
                "  \"notes\":{\n" +
                "    \"notes_key_1\": \"Tea, Earl Grey, Hot\",\n" +
                "    \"notes_key_2\": \"Tea, Earl Grey… decaf.\"\n" +
                "  },\n" +
                "  \"charge_at\": 1577385991,\n" +
                "  \"start_at\": 1577385991,\n" +
                "  \"end_at\": 1603737000,\n" +
                "  \"auth_attempts\": 0,\n" +
                "  \"total_count\": 6,\n" +
                "  \"paid_count\": 1,\n" +
                "  \"customer_notify\": true,\n" +
                "  \"created_at\": 1577356081,\n" +
                "  \"expire_by\": 1577485991,\n" +
                "  \"short_url\": \"https://rzp.io/i/z3b1R61A9\",\n" +
                "  \"has_scheduled_changes\": false,\n" +
                "  \"change_scheduled_at\": null,\n" +
                "  \"source\": \"api\",\n" +
                "  \"offer_id\":\"offer_JHD834hjbxzhd38d\",\n" +
                "  \"remaining_count\": 5\n" +
                "}";

        apiUtils = mock(ApiUtils.class);
        URL builder = ApiClient.getBuilder(String.format(Constants.SUBSCRIPTION, TEST_SUBSCRIPTION_ID), null);
        mockGetRequest(apiUtils,builder,null, mockedResponseJson);

        SubscriptionClient subscriptionClient = new SubscriptionClient("test",apiUtils);
        Subscription subscription = subscriptionClient.fetch(TEST_SUBSCRIPTION_ID);
        assertEquals(TEST_PLAN_ID, subscription.get(PLAN_ID));
        assertEquals(TEST_SUBSCRIPTION_ID, subscription.get(SUBSCRIPTION_ID));
    }

    @Test
    public void testFetchAll() throws Exception {
        String mockedResponseJson = getSubscriptionCollectionMockedResponse();
        apiUtils = mock(ApiUtils.class);
        URL builder = ApiClient.getBuilder(Constants.SUBSCRIPTION_LIST, null);
        mockGetRequest(apiUtils,builder,null, mockedResponseJson);

        SubscriptionClient subscriptionClient = new SubscriptionClient("test",apiUtils);

        List<Subscription> subscriptionList = subscriptionClient.fetchAll();
        assertEquals(TEST_PLAN_ID, subscriptionList.get(0).get(PLAN_ID));
        assertEquals(TEST_SUBSCRIPTION_ID, subscriptionList.get(0).get(SUBSCRIPTION_ID));
        assertEquals(TEST_PLAN_ID, subscriptionList.get(1).get(PLAN_ID));
        assertEquals(TEST_SUBSCRIPTION_ID_2, subscriptionList.get(1).get(SUBSCRIPTION_ID));
    }

    private String getSubscriptionCollectionMockedResponse() {
        return "{\n" +
                "  \"entity\": \"collection\",\n" +
                "  \"count\": 2,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"id\": \"" + TEST_SUBSCRIPTION_ID + "\",\n" +
                "      \"entity\": \"subscription\",\n" +
                "      \"plan_id\": \"" + TEST_PLAN_ID + "\",\n" +
                "      \"customer_id\": null,\n" +
                "      \"status\": \"active\",\n" +
                "      \"current_start\": 1645527920,\n" +
                "      \"current_end\": 1647887400,\n" +
                "      \"ended_at\": null,\n" +
                "      \"quantity\": 1,\n" +
                "      \"notes\": [\n" +
                "        \n" +
                "      ],\n" +
                "      \"charge_at\": 1647887400,\n" +
                "      \"start_at\": 1645527920,\n" +
                "      \"end_at\": 1653157800,\n" +
                "      \"auth_attempts\": 0,\n" +
                "      \"total_count\": 4,\n" +
                "      \"paid_count\": 1,\n" +
                "      \"customer_notify\": true,\n" +
                "      \"created_at\": 1645527865,\n" +
                "      \"expire_by\": null,\n" +
                "      \"short_url\": \"https:\\/\\/rzp.io\\/i\\/NkOtrmtV61\",\n" +
                "      \"has_scheduled_changes\": false,\n" +
                "      \"change_scheduled_at\": null,\n" +
                "      \"source\": \"dashboard\",\n" +
                "      \"payment_method\": \"card\",\n" +
                "      \"offer_id\": null,\n" +
                "      \"remaining_count\": 3\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"" + TEST_SUBSCRIPTION_ID_2 + "\",\n" +
                "      \"entity\": \"subscription\",\n" +
                "      \"plan_id\": \"" + TEST_PLAN_ID + "\",\n" +
                "      \"customer_id\": null,\n" +
                "      \"status\": \"active\",\n" +
                "      \"current_start\": 1645525263,\n" +
                "      \"current_end\": 1647887400,\n" +
                "      \"ended_at\": null,\n" +
                "      \"quantity\": 5,\n" +
                "      \"notes\": [\n" +
                "        \n" +
                "      ],\n" +
                "      \"charge_at\": 1647887400,\n" +
                "      \"start_at\": 1645525263,\n" +
                "      \"end_at\": 1658428200,\n" +
                "      \"auth_attempts\": 0,\n" +
                "      \"total_count\": 6,\n" +
                "      \"paid_count\": 1,\n" +
                "      \"customer_notify\": false,\n" +
                "      \"created_at\": 1645523724,\n" +
                "      \"expire_by\": null,\n" +
                "      \"short_url\": \"https:\\/\\/rzp.io\\/i\\/UI21N7A\",\n" +
                "      \"has_scheduled_changes\": false,\n" +
                "      \"change_scheduled_at\": null,\n" +
                "      \"source\": \"dashboard\",\n" +
                "      \"payment_method\": \"card\",\n" +
                "      \"offer_id\": null,\n" +
                "      \"remaining_count\": 5\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }


    @Test
    public void testCancel() throws Exception {
        String mockedResponseJson = getMockedResponseJson();
        apiUtils = mock(ApiUtils.class);
        URL builder = ApiClient.getBuilder(String.format(Constants.SUBSCRIPTION_CANCEL, TEST_SUBSCRIPTION_ID), null);
        mockPostRequest(apiUtils,builder,null, mockedResponseJson);

        SubscriptionClient subscriptionClient = new SubscriptionClient("test",apiUtils);
        Subscription subscription = subscriptionClient.cancel(TEST_SUBSCRIPTION_ID);
        assertEquals(TEST_PLAN_ID, subscription.get(PLAN_ID));
        assertEquals(TEST_SUBSCRIPTION_ID, subscription.get(SUBSCRIPTION_ID));
    }

    @Test
    public void testCreateAddon() throws Exception {
        String mockedResponseJson = getAddonMockedResponse();
        apiUtils = mock(ApiUtils.class);
        URL builder = ApiClient.getBuilder(String.format(Constants.SUBSCRIPTION_ADDON_CREATE, TEST_SUBSCRIPTION_ID), null);
        mockPostRequest(apiUtils,builder,new JSONObject(getCreateAddonRequest()).toString(), mockedResponseJson);
        SubscriptionClient subscriptionClient = new SubscriptionClient("test",apiUtils);
        Addon addon = subscriptionClient.createAddon(TEST_SUBSCRIPTION_ID, new JSONObject(getCreateAddonRequest()));
        assertEquals(TEST_SUBSCRIPTION_ID, addon.get(ADDON_SUBSCRIPTION_ID));
        assertEquals(TEST_ADDON_ID, addon.get(ADDON_ID));
    }


    @Test
    public void testUpdate() throws Exception {
        String mockedResponseJson = getResponse(TEST_PLAN_ID, TEST_SUBSCRIPTION_ID, 2);
        apiUtils = mock(ApiUtils.class);
        URL builder = ApiClient.getBuilder(String.format(Constants.SUBSCRIPTION, TEST_SUBSCRIPTION_ID), null);
        mockPatchRequest(apiUtils,builder,new JSONObject(getRequest()).toString(), mockedResponseJson);

        SubscriptionClient subscriptionClient = new SubscriptionClient("test",apiUtils);
        Subscription subscription = subscriptionClient.update(TEST_SUBSCRIPTION_ID, new JSONObject(getRequest()));
        assertEquals(TEST_SUBSCRIPTION_ID, subscription.get(SUBSCRIPTION_ID));
        assertEquals(2, subscription.get("quantity"));
    }

    @Test
    public void testFetchPendingUpdate() throws Exception {
        String mockedResponseJson = getMockedResponseJson();
        apiUtils = mock(ApiUtils.class);
        URL builder = ApiClient.getBuilder(String.format(Constants.SUBSCRIPTION_PENDING_UPDATE, TEST_SUBSCRIPTION_ID), null);
        mockGetRequest(apiUtils,builder,null, mockedResponseJson);
        SubscriptionClient subscriptionClient = new SubscriptionClient("test",apiUtils);
        Subscription subscription = subscriptionClient.fetchPendingUpdate(TEST_SUBSCRIPTION_ID);
        assertEquals(TEST_SUBSCRIPTION_ID, subscription.get(SUBSCRIPTION_ID));
        assertEquals(1, subscription.get("quantity"));
    }

    @Test
    public void testCancelPendingUpdate() throws Exception {
        String mockedResponseJson = getMockedResponseJson();
        apiUtils = mock(ApiUtils.class);
        URL builder = ApiClient.getBuilder(String.format(Constants.SUBSCRIPTION_CANCEL_SCHEDULED_UPDATE, TEST_SUBSCRIPTION_ID), null);
        mockPostRequest(apiUtils,builder,null, mockedResponseJson);
        SubscriptionClient subscriptionClient = new SubscriptionClient("test",apiUtils);
        Subscription subscription = subscriptionClient.cancelPendingUpdate(TEST_SUBSCRIPTION_ID);
        assertEquals(TEST_SUBSCRIPTION_ID, subscription.get(SUBSCRIPTION_ID));
        assertEquals(1, subscription.get("quantity"));
    }

    @Test
    public void testPause() throws Exception {
        String mockedResponseJson = getMockedResponseJson();
        apiUtils = mock(ApiUtils.class);
        URL builder = ApiClient.getBuilder(String.format(Constants.PAUSE_SUBSCRIPTION, TEST_SUBSCRIPTION_ID), null);
        mockPostRequest(apiUtils,builder,new JSONObject(getRequest()).toString(), mockedResponseJson);
        SubscriptionClient subscriptionClient = new SubscriptionClient("test",apiUtils);
        Subscription subscription = subscriptionClient.pause(TEST_SUBSCRIPTION_ID, new JSONObject(getRequest()));
        assertEquals(TEST_SUBSCRIPTION_ID, subscription.get(SUBSCRIPTION_ID));
        assertEquals(1, subscription.get("quantity"));
    }

    @Test
    public void testResume() throws Exception {

        String mockedResponseJson = getMockedResponseJson();
        apiUtils = mock(ApiUtils.class);
        URL builder = ApiClient.getBuilder(String.format(Constants.RESUME_SUBSCRIPTION, TEST_SUBSCRIPTION_ID), null);
        mockPostRequest(apiUtils,builder,new JSONObject(getRequest()).toString(), mockedResponseJson);
        SubscriptionClient subscriptionClient = new SubscriptionClient("test",apiUtils);
        Subscription subscription = subscriptionClient.resume(TEST_SUBSCRIPTION_ID, new JSONObject(getRequest()));
        assertEquals(TEST_SUBSCRIPTION_ID, subscription.get(SUBSCRIPTION_ID));
        assertEquals(1, subscription.get("quantity"));
    }

    @Test
    public void testDeleteSubscriptionOffer() throws Exception {

        String mockedResponseJson = getMockedResponseJson();
        apiUtils = mock(ApiUtils.class);
        URL builder = ApiClient.getBuilder(String.format(Constants.SUBSCRIPTION_OFFER, TEST_SUBSCRIPTION_ID, TEST_OFFER), null);
        mockDeleteRequest(apiUtils,builder,null, mockedResponseJson);
        SubscriptionClient subscriptionClient = new SubscriptionClient("test",apiUtils);
        Subscription subscription = subscriptionClient.deleteSubscriptionOffer(TEST_SUBSCRIPTION_ID, TEST_OFFER);
        assertEquals(TEST_SUBSCRIPTION_ID, subscription.get(SUBSCRIPTION_ID));
    }

    private String getAddonMockedResponse() {
        return "{\n" +
                "  \"id\":\"" + TEST_ADDON_ID + "\",\n" +
                "  \"entity\":\"addon\",\n" +
                "  \"item\":{\n" +
                "    \"id\":\"" + ADDON_ITEM_ID + "\",\n" +
                "    \"active\":true,\n" +
                "    \"name\":\"Extra appala (papadum)\",\n" +
                "    \"description\":\"1 extra oil fried appala with meals\",\n" +
                "    \"amount\":30000,\n" +
                "    \"unit_amount\":30000,\n" +
                "    \"currency\":\"INR\",\n" +
                "    \"type\":\"addon\",\n" +
                "    \"unit\":null,\n" +
                "    \"tax_inclusive\":false,\n" +
                "    \"hsn_code\":null,\n" +
                "    \"sac_code\":null,\n" +
                "    \"tax_rate\":null,\n" +
                "    \"tax_id\":null,\n" +
                "    \"tax_group_id\":null,\n" +
                "    \"created_at\":1581597318,\n" +
                "    \"updated_at\":1581597318\n" +
                "  },\n" +
                "  \"quantity\":2,\n" +
                "  \"created_at\":1581597318,\n" +
                "  \"subscription_id\":\"" + TEST_SUBSCRIPTION_ID + "\",\n" +
                "  \"invoice_id\":null\n" +
                "}";
    }

    private String getCreateAddonRequest() {
        return "{\n" +
                "  \"item\":{\n" +
                "    \"name\":\"Extra appala (papadum)\",\n" +
                "    \"amount\":30000,\n" +
                "    \"currency\":\"INR\",\n" +
                "    \"description\":\"1 extra oil fried appala with meals\"\n" +
                "  },\n" +
                "  \"quantity\":2\n" +
                "}";
    }
    private String getMockedErrorResponse() {
        return "{\n" +
                "  \"error\": {\n" +
                "    \"code\": \"BAD_REQUEST_ERROR\",\n" +
                "    \"description\": \"Either end_at or total_count should be sent and not both.\"\n" +
                "  }\n" +
                "}";
    }

    private String getFetchAllFilteredRequestPlanIdAndCount2() {
        return "{\n" +
                "  \"plan_id\": \"" + TEST_PLAN_ID + "\",\n" +
                "  \"count\": 1\n" +
                "}";
    }

    private String getRequest() {
        return "{\n" +
                "  \"plan_id\":\"" + TEST_PLAN_ID + "\",\n" +
                "  \"total_count\":6,\n" +
                "  \"quantity\":1,\n" +
                "  \"start_at\":1735689600,\n" +
                "  \"expire_by\":1893456000,\n" +
                "  \"customer_notify\":1,\n" +
                "  \"addons\":[\n" +
                "    {\n" +
                "      \"item\":{\n" +
                "        \"name\":\"Delivery charges\",\n" +
                "        \"amount\":30000,\n" +
                "        \"currency\":\"INR\"\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"offer_id\":\"{offer_id}\",\n" +
                "  \"notes\":{\n" +
                "    \"notes_key_1\":\"Tea, Earl Grey, Hot\",\n" +
                "    \"notes_key_2\":\"Tea, Earl Grey… decaf.\"\n" +
                "  }\n" +
                "}";
    }

    private String getMockedResponseJson() {
        return getResponse(TEST_PLAN_ID, TEST_SUBSCRIPTION_ID, 1);
    }

    private String getResponse(String testPlanId, String testSubscriptionId, int quantity) {
        return "{\n" +
                "  \"end_at\": 1653157800,\n" +
                "  \"paid_count\": 1,\n" +
                "  \"notes\": [\n" +
                "    \n" +
                "  ],\n" +
                "  \"created_at\": 1645527865,\n" +
                "  \"source\": \"dashboard\",\n" +
                "  \"start_at\": 1645527920,\n" +
                "  \"current_end\": 1647887400,\n" +
                "  \"charge_at\": 1647887400,\n" +
                "  \"short_url\": \"dummy\",\n" +
                "  \"change_scheduled_at\": null,\n" +
                "  \"id\": \"" + testSubscriptionId + "\",\n" +
                "  \"expire_by\": null,\n" +
                "  \"payment_method\": \"card\",\n" +
                "  \"quantity\": " + quantity + ",\n" +
                "  \"has_scheduled_changes\": false,\n" +
                "  \"remaining_count\": 3,\n" +
                "  \"total_count\": 4,\n" +
                "  \"offer_id\": null,\n" +
                "  \"customer_notify\": true,\n" +
                "  \"auth_attempts\": 0,\n" +
                "  \"customer_id\": null,\n" +
                "  \"entity\": \"subscription\",\n" +
                "  \"plan_id\": \"" + testPlanId + "\",\n" +
                "  \"ended_at\": null,\n" +
                "  \"status\": \"active\",\n" +
                "  \"current_start\": 1645527920\n" +
                "}";
    }

}