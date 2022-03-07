## Funds

### Create a fund account
```java
String json = "{\n" +
        "  \"customer_id\":\"cust_Aa000000000001\",\n" +
        "  \"account_type\":\"bank_account\",\n" +
        "  \"bank_account\":{\n" +
        "    \"name\":\"Gaurav Kumar\",\n" +
        "    \"account_number\":\"11214311215411\",\n" +
        "    \"ifsc\":\"HDFC0000053\"\n" +
        "  }\n" +
        "}";

JSONObject request = new JSONObject(json);

FundAccount fundaccount = instance.FundAccount.create(request);
```

**Parameters:**

| Name          | Type        | Description                                 |
|---------------|-------------|---------------------------------------------|
| customerId*   | string      | The id of the customer to be fetched  |
| account_type* | string      | The bank_account to be linked to the customer ID  |
| bank_account* | object      | A key-value pair  |

**Response:**
```json
{
  "id":"fa_Aa00000000001",
  "entity":"fund_account",
  "customer_id":"cust_Aa000000000001",
  "account_type":"bank_account",
  "bank_account":{
    "name":"Gaurav Kumar",
    "account_number":"11214311215411",
    "ifsc":"HDFC0000053",
    "bank_name":"HDFC Bank"
  },
  "active":true,
  "created_at":1543650891
}
```
-------------------------------------------------------------------------------------------------------

### Fetch all fund accounts

```java
String CustomerId = "cust_Aa000000000001";

FundAccount fundaccount = instance.FundAccount.fetch(CustomerId);
```

**Parameters:**

| Name          | Type        | Description                                 |
|---------------|-------------|---------------------------------------------|
| customerId*   | string      | The id of the customer to be fetched  |

**Response:**
```json
{
  "id":"fa_Aa00000000001",
  "entity":"fund_account",
  "customer_id":"cust_Aa000000000001",
  "account_type":"bank_account",
  "bank_account":{
    "name":"Gaurav Kumar",
    "account_number":"11214311215411",
    "ifsc":"HDFC0000053",
    "bank_name":"HDFC Bank"
  },
  "active":true,
  "created_at":1543650891
}
```
-------------------------------------------------------------------------------------------------------

**PN: * indicates mandatory fields**
<br>
<br>
**For reference click [here](https://razorpay.com/docs/payments/customers/customer-fund-account-api/)**