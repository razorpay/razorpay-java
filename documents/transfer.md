## Transfers

### Create transfers from payment

```java
String paymentId = "pay_E8JR8E0XyjUSZd";
String json = "{\n" +
              "   \"transfers\": [\n" +
              "    {\n" +
              "      \"account\": 'acc_HgzcrXeSLfNP9U',\n" +
              "      \"amount\": 100,\n" +
              "      \"currency\": \"INR\",\n" +
              "      \"notes\": {\n" +
              "        \"name\": \"Gaurav Kumar\",\n" +
              "        \"roll_no\": \"IEC2011025\"\n" +
              "      },\n" +
              "      \"linked_account_notes\": [\n" +
              "        \"branch\"\n" +
              "      ],\n" +
              "      \"on_hold\": 1,\n" +
              "      \"on_hold_until\": 1671222870\n" +
              "    }\n" +
              "  ]\n" +
              " }";
              
JSONObject request = new JSONObject(json);
              
Payment payment = instance.payments.transfer(paymentId,request);
```

**Parameters:**

| Name       | Type        | Description                                 |
|------------|-------------|---------------------------------------------|
| PaymentId* | string      | The id of the payment to be fetched  |
| transfers  | object     | All parameters listed [here](https://razorpay.com/docs/api/route/#create-transfers-from-payments) are supported |

**Response:**
```json
{
  "entity": "collection",
  "count": 1,
  "items": [
    {
      "id": "trf_E9uhYLFLLZ2pks",
      "entity": "transfer",
      "source": "pay_E8JR8E0XyjUSZd",
      "recipient": "acc_CPRsN1LkFccllA",
      "amount": 100,
      "currency": "INR",
      "amount_reversed": 0,
      "notes": {
        "name": "Gaurav Kumar",
        "roll_no": "IEC2011025"
      },
      "on_hold": true,
      "on_hold_until": 1671222870,
      "recipient_settlement_id": null,
      "created_at": 1580218356,
      "linked_account_notes": [
        "roll_no"
      ],
      "processed_at": 1580218357
    }
  ]
}
```
-------------------------------------------------------------------------------------------------------

### Create transfers from order

```java
String jsonRequest = "{\n" +
            "  \"amount\": 2000,\n" +
            "  \"currency\": \"INR\",\n" +
            "  \"transfers\": [\n" +
            "    {\n" +
            "      \"account\": \"acc_CPRsN1LkFccllA\",\n" +
            "      \"amount\": 1000,\n" +
            "      \"currency\": \"INR\",\n" +
            "      \"notes\": {\n" +
            "        \"branch\": \"Acme Corp Bangalore North\",\n" +
            "        \"name\": \"Gaurav Kumar\"\n" +
            "      },\n" +
            "      \"linked_account_notes\": [\n" +
            "        \"branch\"\n" +
            "      ],\n" +
            "      \"on_hold\": 1,\n" +
            "      \"on_hold_until\": 1671222870\n" +
            "    },\n" +
            "    {\n" +
            "      \"account\": \"acc_CNo3jSI8OkFJJJ\",\n" +
            "      \"amount\": 1000,\n" +
            "      \"currency\": \"INR\",\n" +
            "      \"notes\": {\n" +
            "        \"branch\": \"Acme Corp Bangalore South\",\n" +
            "        \"name\": \"Saurav Kumar\"\n" +
            "      },\n" +
            "      \"linked_account_notes\": [\n" +
            "        \"branch\"\n" +
            "      ],\n" +
            "      \"on_hold\": 0\n" +
            "    }\n" +
            "  ]\n" +
            "}";
              
JSONObject requestJson = new JSONObject(jsonRequest);
              
Order order = instance.orders.create(requestJson);
```

**Parameters:**

| Name          | Type        | Description                                 |
|---------------|-------------|---------------------------------------------|
| amount*   | integer      | The transaction amount, in paise |
| currency*   | string  | The currency of the payment (defaults to INR)  |
|  receipt      | string      | A unique identifier provided by you for your internal reference. |
| transfers   | object     | All parameters listed [here](https://razorpay.com/docs/api/route/#create-transfers-from-orders) are supported |

**Response:**
```json
{
  "id": "order_E9uTczH8uWPCyQ",
  "entity": "order",
  "amount": 2000,
  "amount_paid": 0,
  "amount_due": 2000,
  "currency": "INR",
  "receipt": null,
  "offer_id": null,
  "status": "created",
  "attempts": 0,
  "notes": [],
  "created_at": 1580217565,
  "transfers": [
    {
      "recipient": "acc_CPRsN1LkFccllA",
      "amount": 1000,
      "currency": "INR",
      "notes": {
        "branch": "Acme Corp Bangalore North",
        "name": "Gaurav Kumar"
      },
      "linked_account_notes": [
        "branch"
      ],
      "on_hold": true,
      "on_hold_until": 1671222870
    },
    {
      "recipient": "acc_CNo3jSI8OkFJJJ",
      "amount": 1000,
      "currency": "INR",
      "notes": {
        "branch": "Acme Corp Bangalore South",
        "name": "Saurav Kumar"
      },
      "linked_account_notes": [
        "branch"
      ],
      "on_hold": false,
      "on_hold_until": null
    }
  ]
}
```
-------------------------------------------------------------------------------------------------------

### Direct transfers

```java
String jsonRequest = "{\n" +
                 "\account\": \"acc_CPRsN1LkFccllA\",\n +
                 "\"amount\": 500,\n" +
                 "\"currency\": \"INR\"\n" +
               "}";

JSONObject requestJson = new JSONObject(jsonRequest);
               
Transfer transfer = instance.transfers.create(requestJson);
```

**Parameters:**

| Name          | Type        | Description                                 |
|---------------|-------------|---------------------------------------------|
| accountId*   | string      | The id of the account to be fetched  |
| amount*   | integer      | The amount to be captured (should be equal to the authorized amount, in paise) |
| currency*   | string  | The currency of the payment (defaults to INR)  |

**Response:**
```json
{
  "id": "trf_E9utgtfGTcpcmm",
  "entity": "transfer",
  "source": "acc_CJoeHMNpi0nC7k",
  "recipient": "acc_CPRsN1LkFccllA",
  "amount": 100,
  "currency": "INR",
  "amount_reversed": 0,
  "notes": [],
  "fees": 1,
  "tax": 0,
  "on_hold": false,
  "on_hold_until": null,
  "recipient_settlement_id": null,
  "created_at": 1580219046,
  "linked_account_notes": [],
  "processed_at": 1580219046
}
```
-------------------------------------------------------------------------------------------------------

### Fetch transfer for a payment

```java
String paymentId = "pay_E9up5WhIfMYnKW";

List<Payment> payments = instance.payments.fetchAllTransfers(paymentId)
```

**Parameters:**

| Name       | Type        | Description                                 |
|------------|-------------|---------------------------------------------|
| PaymentId* | string      | The id of the payment to be fetched  |

**Response:**
```json
{
  "entity": "collection",
  "count": 1,
  "items": [
    {
      "id": "trf_EAznuJ9cDLnF7Y",
      "entity": "transfer",
      "source": "pay_E9up5WhIfMYnKW",
      "recipient": "acc_CMaomTz4o0FOFz",
      "amount": 1000,
      "currency": "INR",
      "amount_reversed": 100,
      "notes": [],
      "fees": 3,
      "tax": 0,
      "on_hold": false,
      "on_hold_until": null,
      "recipient_settlement_id": null,
      "created_at": 1580454666,
      "linked_account_notes": [],
      "processed_at": 1580454666
    }
  ]
}
```
-------------------------------------------------------------------------------------

### Fetch transfer

```java
String transferId = "trf_E7V62rAxJ3zYMo";

Transfer transfer = instance.transfers.fetch(transferId);
```

**Parameters:**

| Name          | Type        | Description                                 |
|---------------|-------------|---------------------------------------------|
| transferId*   | string      | The id of the transfer to be fetched  |

**Response:**
```json
{
  "id": "trf_E7V62rAxJ3zYMo",
  "entity": "transfer",
  "source": "pay_E6j30Iu1R7XbIG",
  "recipient": "acc_CMaomTz4o0FOFz",
  "amount": 100,
  "currency": "INR",
  "amount_reversed": 0,
  "notes": [],
  "fees": 1,
  "tax": 0,
  "on_hold": false,
  "on_hold_until": null,
  "recipient_settlement_id": null,
  "created_at": 1579691505,
  "linked_account_notes": [],
  "processed_at": 1579691505
}
```
-------------------------------------------------------------------------------------------------------

### Fetch transfers for a settlement

```java
String jsonRequest = {\"recipient_settlement_id\":\"setl_DHYJ3dRPqQkAgV\"}";

JSONObject requestRequest = new JSONObject(jsonRequest);

List<Transfer> transfers = instance.transfers.fetchAll(requestRequest);
```

**Parameters:**

| Name          | Type        | Description                                 |
|---------------|-------------|---------------------------------------------|
| recipient_settlement_id*   | string    | The recipient settlement id obtained from the settlement.processed webhook payload.  |

**Response:**
```json
{
  "entity": "collection",
  "count": 1,
  "items": [
    {
      "id": "trf_DGSTeXzBkEVh48",
      "entity": "transfer",
      "source": "pay_DGSRhvMbOqeCe7",
      "recipient": "acc_CMaomTz4o0FOFz",
      "amount": 500,
      "currency": "INR",
      "amount_reversed": 0,
      "notes": [],
      "fees": 2,
      "tax": 0,
      "on_hold": false,
      "on_hold_until": null,
      "recipient_settlement_id": "setl_DHYJ3dRPqQkAgV",
      "created_at": 1568110256,
      "linked_account_notes": [],
      "processed_at": null
    }
  ]
}
```
-------------------------------------------------------------------------------------------------------

### Fetch settlement details

```java
String jsonRequest = "{\n" +
              "\"expand[]\" : \"recipient_settlement\"  \n" +
              "}";

JSONObject requestJson = new JSONObject(jsonRequest);
              
List<Transfer> transfer = instance.transfers.fetchAll(requestJson);
```

**Parameters:**

| Name          | Type        | Description                                 |
|---------------|-------------|---------------------------------------------|
| expand*   | string    | Supported value is `recipient_settlement`  |

**Response:**
```json
{
  "entity": "collection",
  "count": 1,
  "items": [
    {
      "id": "trf_DGSTeXzBkEVh48",
      "entity": "transfer",
      "source": "pay_DGSRhvMbOqeCe7",
      "recipient": "acc_CMaomTz4o0FOFz",
      "amount": 500,
      "currency": "INR",
      "amount_reversed": 0,
      "notes": [],
      "fees": 2,
      "tax": 0,
      "on_hold": false,
      "on_hold_until": null,
      "recipient_settlement_id": "setl_DHYJ3dRPqQkAgV",
      "recipient_settlement": {
        "id": "setl_DHYJ3dRPqQkAgV",
        "entity": "settlement",
        "amount": 500,
        "status": "failed",
        "fees": 0,
        "tax": 0,
        "utr": "CN0038699836",
        "created_at": 1568349124
      },
      "created_at": 1568110256,
      "linked_account_notes": [],
      "processed_at": null
    }
  ]
}
```
-------------------------------------------------------------------------------------------------------

### Refund payments and reverse transfer from a linked account

```java
String paymentId = "pay_EAdwQDe4JrhOFX";

String jsonRequest = "{\n" +
                    "\"amount\" : 100,\n" +
                    "\"reverse_all\" : 1\n" +
                    "})";
              
JSONObject requestRequest = new JSONObject(jsonRequest);  
            
Payment payment = instance.payments.refund(paymentId,requestJson);
```

**Parameters:**

| Name        | Type        | Description                                 |
|-------------|-------------|---------------------------------------------|
| paymentId*  | string      | The id of the payment to be fetched  |
| amount*     | integer      | The amount to be captured (should be equal to the authorized amount, in paise) |
| reverse_all | boolean    | Reverses transfer made to a linked account. Possible values:<br> * `1` - Reverses transfer made to a linked account.<br>* `0` - Does not reverse transfer made to a linked account.|

**Response:**
```json
{
  "id": "rfnd_EAzovSwG8jBnGf",
  "entity": "refund",
  "amount": 100,
  "currency": "INR",
  "payment_id": "pay_EAdwQDe4JrhOFX",
  "notes": [],
  "receipt": null,
  "acquirer_data": {
    "rrn": null
  },
  "created_at": 1580454723
}
```
-------------------------------------------------------------------------------------------------------

### Fetch payments of a linked account

```java
String jsonRequest = "{\"X-Razorpay-Account\":\"acc_CPRsN1LkFccllA\"}"

JSONObject requestJson = new JSONObject(jsonRequest);

List<Payment> payments = instance.payments.fetchAll(requestJson);
```

**Parameters:**

| Name          | Type        | Description                                 |
|---------------|-------------|---------------------------------------------|
| X-Razorpay-Account   | string      | The linked account id to fetch the payments received by linked account |

**Response:**
```json
{
  "entity": "collection",
  "count": 2,
  "items": [
    {
      "id": "pay_E9uth3WhYbh9QV",
      "entity": "payment",
      "amount": 100,
      "currency": "INR",
      "status": "captured",
      "order_id": null,
      "invoice_id": null,
      "international": null,
      "method": "transfer",
      "amount_refunded": 0,
      "refund_status": null,
      "captured": true,
      "description": null,
      "card_id": null,
      "bank": null,
      "wallet": null,
      "vpa": null,
      "email": "",
      "contact": null,
      "notes": [],
      "fee": 0,
      "tax": 0,
      "error_code": null,
      "error_description": null,
      "created_at": 1580219046
    }
  ]
}
```
-------------------------------------------------------------------------------------------------------

### Reverse transfers from all linked accounts

```java
String transferId = "trf_EAznuJ9cDLnF7Y";

String jsonRequest = "{\n" +
                "\"amount\":100\n" +
               "}";
              
JSONObject requestJson = new JSONObject(jsonRequest);       
        
Transfer transfer = instance.transfers.reversal(transferId,requestJson);
```

**Parameters:**

| Name        | Type        | Description                                 |
|-------------|-------------|---------------------------------------------|
| transferId* | string      | The id of the transfer to be fetched  |
| amount      | integer      | The amount to be captured (should be equal to the authorized amount, in paise) |

**Response:**
```json
{
  "id": "rvrsl_EB0BWgGDAu7tOz",
  "entity": "reversal",
  "transfer_id": "trf_EAznuJ9cDLnF7Y",
  "amount": 100,
  "fee": 0,
  "tax": 0,
  "currency": "INR",
  "notes": [],
  "initiator_id": "CJoeHMNpi0nC7k",
  "customer_refund_id": null,
  "created_at": 1580456007
}
```
-------------------------------------------------------------------------------------------------------

### Hold settlements for transfers
```java
String paymentId = "pay_EB1R2s8D4vOAKG";

String jsonRequest = "{\n" +
                "  \"transfers\": [\n" +
                "    {\n" +
                "      \"amount\": 100,\n" +
                "      \"account\": \"acc_CMaomTz4o0FOFz\",\n" +
                "      \"currency\": \"INR\",\n" +
                "      \"on_hold\": 1\n" +
                "    }\n" +
                "  ]\n" +
                "}";
              
JSONObject requestJson = new JSONObject(jsonRequest);    
          
Payment payment = instance.payments.transfer(paymentId,requestJson);
```

**Parameters:**

| Name       | Type        | Description                                 |
|------------|-------------|---------------------------------------------|
| paymentId* | string      | The id of the payment to be fetched  |
| transfers  | array     | All parameters listed here https://razorpay.com/docs/api/route/#hold-settlements-for-transfers are supported |

**Response:**
```json
{
  "entity": "collection",
  "count": 1,
  "items": [
    {
      "id": "trf_EB1VJ4Ux4GMmxQ",
      "entity": "transfer",
      "source": "pay_EB1R2s8D4vOAKG",
      "recipient": "acc_CMaomTz4o0FOFz",
      "amount": 100,
      "currency": "INR",
      "amount_reversed": 0,
      "notes": [],
      "fees": 1,
      "tax": 0,
      "on_hold": true,
      "on_hold_until": null,
      "recipient_settlement_id": null,
      "created_at": 1580460652,
      "linked_account_notes": [],
      "processed_at": 1580460652
    }
  ]
}
```
-------------------------------------------------------------------------------------------------------

### Modify settlement hold for transfers
```java
String transferId = "trf_EB17rqOUbzSCEE";

String jsonRequest = "{\n" +
                "\"on_hold\": \"1\",\n" +
                "\"on_hold_until\": \"1679691505\"\n" +
               "}";
               
JSONObject requestJson = new JSONObject(jsonRequest);    
              
Transfer transfer = instance.transfers.edit(transferId,requestJson);
```

**Parameters:**

| Name          | Type    | Description                                 |
|---------------|---------|---------------------------------------------|
| transferId*   | string      | The id of the transfer to be fetched  |
| on_hold*   | boolean      | Possible values is `0` or `1`  |
| on_hold_until   | integer      | Timestamp, in Unix, that indicates until when the settlement of the transfer must be put on hold |

**Response:**
```json
{
  "id": "trf_EB17rqOUbzSCEE",
  "entity": "transfer",
  "source": "pay_EAeSM2Xul8xYRo",
  "recipient": "acc_CMaomTz4o0FOFz",
  "amount": 100,
  "currency": "INR",
  "amount_reversed": 0,
  "notes": [],
  "fees": 1,
  "tax": 0,
  "on_hold": true,
  "on_hold_until": 1679691505,
  "recipient_settlement_id": null,
  "created_at": 1580459321,
  "linked_account_notes": [],
  "processed_at": 1580459321
}
```

-------------------------------------------------------------------------------------------------------

**PN: * indicates mandatory fields**
<br>
<br>
**For reference click [here](https://razorpay.com/docs/api/route/#transfers/)**