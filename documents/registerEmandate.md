## Register emandate and charge first payment together

### Create customer
```java
String json = "{\n" +
              "  name: \"Gaurav Kumar\",\n" +
              "  contact: 9123456780,\n" +
              "  email: \"gaurav.kumar@example.com\",\n" +
              "  fail_existing: 0,\n" +
              "  gstin: \"29XAbbA4369J1PA\",\n" +
              "  notes: {\n" +
              "    notes_key_1: \"Tea, Earl Grey, Hot\",\n" +
              "    notes_key_2: \"Tea, Earl Grey… decaf.\"\n" +
              "  }\n" +
              "}";
              
JSONObject request = new JSONObject(json);   
          
Customer customer = instance.Customers.create(request);
```
**Parameters:**

| Name          | Type        | Description                                 |
|---------------|-------------|---------------------------------------------|
| name*          | string      | Name of the customer                        |
| email        | string      | Email of the customer                       |
| contact      | string      | Contact number of the customer              |
| fail_existing | string | If a customer with the same details already exists, the request throws an exception by default. Possible value is `0` or `1`|
| notes         | object      | A key-value pair                            |

**Response:**
```json
{
  "id": "cust_1Aa00000000003",
  "entity": "customer",
  "name": "Gaurav Kumar",
  "email": "Gaurav.Kumar@example.com",
  "contact": "9000000000",
  "gstin": null,
  "notes": {
    "notes_key_1": "Tea, Earl Grey, Hot",
    "notes_key_2": "Tea, Earl Grey… decaf."
  },
  "created_at": 1582033731
}
```
-------------------------------------------------------------------------------------------------------

### Create order

```java
String json = "{\n" +
              "  amount: 100,\n" +
              "  currency: \"INR\",\n" +
              "  method: \"emandate\",\n" +
              "  receipt: \"Receipt No. 5\",\n" +
              "  notes: {\n" +
              "    \"note_key 1\": \"Beam me up Scotty\",\n" +
              "    \"note_key 2\": \"Engage\"\n" +
              "  },\n" +
              "  token: {\n" +
              "    first_payment_amount: 10000,\n" +
              "    auth_type: \"netbanking\",\n" +
              "    max_amount: 9999900,\n" +
              "    expire_at: 4102444799,\n" +
              "    notes: {\n" +
              "      \"note_key 1\": \"Tea, Earl Grey… decaf.\",\n" +
              "      \"note_key 2\": \"Tea. Earl Gray. Hot.\"\n" +
              "    },\n" +
              "    bank_account: {\n" +
              "      beneficiary_name: \"Gaurav Kumar\",\n" +
              "      account_number: 11214311215411,\n" +
              "      account_type: \"savings\",\n" +
              "      ifsc_code: \"HDFC0001233\"\n" +
              "    }\n" +
              "  }\n" +
              "}";
              
JSONObject request = new JSONObject(json);         
       
Order order = instance.Orders.create(request);
```

**Parameters:**

| Name            | Type    | Description                                                                  |
|-----------------|---------|------------------------------------------------------------------------------|
| amount*   | integer      | The amount to be captured (should be equal to the authorized amount, in paise) |
| currency*   | string  | The currency of the payment (defaults to INR)  |
| customerId*   | string      | The id of the customer to be fetched |
| method*      | string  | Payment method used to make the registration transaction. Possible value is `emandate`.  |
| receipt      | string  | Your system order reference id.  |
| token  | object  | All keys listed [here](https://razorpay.com/docs/api/recurring-payments/emandate/auto-debit/#112-create-an-order) are supported |
| notes | object  | A key-value pair  |

**Response:**
For create order response please click [here](https://razorpay.com/docs/api/recurring-payments/emandate/auto-debit/#112-create-an-order)

-------------------------------------------------------------------------------------------------------

### Create an Authorization Payment

Please refer this [doc](https://razorpay.com/docs/api/recurring-payments/emandate/auto-debit/#113-create-an-authorization-payment) for authorization payment

-----------------------------------------------------------------------------------------------------

### Create registration link

```java
String json = "{\n" +
              "  customer: {\n" +
              "    name: \"Gaurav Kumar\",\n" +
              "    email: \"gaurav.kumar@example.com\",\n" +
              "    contact: 9123456780\n" +
              "  },\n" +
              "  type: \"link\",\n" +
              "  amount: 100,\n" +
              "  currency: \"INR\",\n" +
              "  description: \"Registration Link for Gaurav Kumar\",\n" +
              "  subscription_registration: {\n" +
              "    first_payment_amount: 100,\n" +
              "    method: \"emandate\",\n" +
              "    auth_type: \"netbanking\",\n" +
              "    max_amount: 50000,\n" +
              "    expire_at: 1634215992,\n" +
              "    bank_account: {\n" +
              "      beneficiary_name: \"Gaurav Kumar\",\n" +
              "      account_number: 11214311215411,\n" +
              "      account_type: \"savings\",\n" +
              "      ifsc_code: \"HDFC0001233\"\n" +
              "    }\n" +
              "  },\n" +
              "  receipt: \"Receipt No. 5\",\n" +
              "  email_notify: 1,\n" +
              "  sms_notify: 1,\n" +
              "  expire_by: 1634215992,\n" +
              "  notes: {\n" +
              "    \"note_key 1\": \"Beam me up Scotty\",\n" +
              "    \"note_key 2\": \"Tea. Earl Gray. Hot.\"\n" +
              "  }\n" +
              "}";
              
JSONObject request = new JSONObject(json);       
        
Invoice invoice = instance.Invoices.createRegistrationLink(request);
```

**Parameters:**

| Name            | Type    | Description                                                   |
|-----------------|---------|---------------------------------------------------------------|
| customer   | object      | Details of the customer to whom the registration link will be sent. |
| type*  | object | the value is `link`. |
| amount*   | integer      | The amount to be captured (should be equal to the authorized amount, in paise) |
| currency*   | string  | The currency of the payment (defaults to INR)  |
| description*  | string      | A brief description of the payment.   |
| subscription_registration   | object  | All keys listed [here](https://razorpay.com/docs/api/recurring-payments/emandate/auto-debit/#121-create-a-registration-link) are supported  |
| receipt      | string  | Your system order reference id.  |
| sms_notify  | boolean  | SMS notifications are to be sent by Razorpay (default : 1)  |
| email_notify | boolean  | Email notifications are to be sent by Razorpay (default : 1)  |
| expire_by    | integer | The timestamp, in Unix format, till when the customer can make the authorization payment. |
| notes | object  | A key-value pair  |

**Response:**
For create registration link response please click [here](https://razorpay.com/docs/api/recurring-payments/emandate/auto-debit/#12-using-a-registration-link)
-------------------------------------------------------------------------------------------------------

## Create an order to charge the customer

```java
String json = "{\n" +
              "  \"amount\": \"100\",\n" +
              "  \"currency\": \"INR\",\n" +
              "  \"receipt\": \"Receipt No. 1\",\n" +
              "  \"notes\": {\n" +
              "    \"key1\": \"value3\",\n" +
              "    \"key2\": \"value2\"\n" +
              "  }\n" +
              "}";
              
JSONObject request = new JSONObject(json);         
       
Order order = instance.Orders.create(request);
```
**Parameters:**

| Name            | Type    | Description                                                                  |
|-----------------|---------|------------------------------------------------------------------------------|
| amount*   | integer      | The amount to be captured (should be equal to the authorized amount, in paise) |
| currency*   | string  | The currency of the payment (defaults to INR)  |
| receipt      | string  | Your system order reference id.  |
| notes | object  | A key-value pair  |

**Response:**
```json
{
   "id":"order_1Aa00000000002",
   "entity":"order",
   "amount":1000,
   "amount_paid":0,
   "amount_due":1000,
   "currency":"INR",
   "receipt":"Receipt No. 1",
   "offer_id":null,
   "status":"created",
   "attempts":0,
   "notes":{
      "notes_key_1":"Tea, Earl Grey, Hot",
      "notes_key_2":"Tea, Earl Grey… decaf."
   },
   "created_at":1579782776
}
```
-------------------------------------------------------------------------------------------------------

## Create a recurring payment

```java
String json = "{\n" +
              "  \"email\": \"gaurav.kumar@example.com\",\n" +
              "  \"contact\": \"9123456789\",\n" +
              "  \"amount\": 1000,\n" +
              "  \"currency\": \"INR\",\n" +
              "  \"order_id\": \"order_1Aa00000000002\",\n" +
              "  \"customer_id\": \"cust_1Aa00000000001\",\n" +
              "  \"token\": \"token_1Aa00000000001\",\n" +
              "  \"recurring\": \"1\",\n" +
              "  \"description\": \"Creating recurring payment for Gaurav Kumar\",\n" +
              "  \"notes\": {\n" +
              "    \"note_key 1\": \"Beam me up Scotty\",\n" +
              "    \"note_key 2\": \"Tea. Earl Gray. Hot.\"\n" +
              "  }\n" +
              "}";
  
JSONObject request = new JSONObject(json);  
              
Payment payment = instance.Payments.createRecurringPayment(request);
```
**Parameters:**

| Name            | Type    | Description                                                                  |
|-----------------|---------|------------------------------------------------------------------------------|
| email*   | string      | The customer's email address |
| contact*   | string      | The customer's phone number |
| amount*   | integer      | The amount to be captured (should be equal to the authorized amount, in paise) |
| currency*   | string  | The currency of the payment (defaults to INR)  |
| orderId*   | string      | The id of the order to be fetched |
| customerId*   | string      | The id of the customer to be fetched |
| tokenId*   | string      | The id of the token to be fetched |
| recurring*   | boolean      | Possible values is `0` or `1` |
| description  | string      | A brief description of the payment.   |
| notes | object  | A key-value pair  |

**Response:**
```json
{
  "razorpay_payment_id" : "pay_1Aa00000000001",
  "razorpay_order_id" : "order_1Aa00000000001",
  "razorpay_signature" : "9ef4dffbfd84f1318f6739a3ce19f9d85851857ae648f114332d8401e0949a3d"
}
```
-------------------------------------------------------------------------------------------------------

## Send/Resend notifications

```java
String InvoiceId = "inv_DAweOiQ7amIUVd";

String medium = "sms";

Invoice invoice = instance.Invoices.notifyBy(InvoiceId,medium);
```
**Parameters:**

| Name       | Type    |Description      |
|------------|---------|------------------------------------------------------------------------------|
| InvoiceId* | string      | The id of the invoice to be fetched |
| medium*    | string      | Possible values are `sms` or `email` |

**Response:**
```json
{
    "success": true
}
```
-------------------------------------------------------------------------------------------------------

## Cancel registration link

```java
String InvoiceId = "inv_DAweOiQ7amIUVd";

Invoice invoice = instance.Invoices.cancel(InvoiceId);
```
**Parameters:**

| Name       | Type    | Description                                                                  |
|------------|---------|------------------------------------------------------------------------------|
| InvoiceId* | string      | The id of the invoice to be fetched |

**Response:**
```json
{
    "id": "inv_FHrfRupD2ouKIt",
    "entity": "invoice",
    "receipt": "Receipt No. 1",
    "invoice_number": "Receipt No. 1",
    "customer_id": "cust_BMB3EwbqnqZ2EI",
    "customer_details": {
        "id": "cust_BMB3EwbqnqZ2EI",
        "name": "Gaurav Kumar",
        "email": "gaurav.kumar@example.com",
        "contact": "9123456780",
        "gstin": null,
        "billing_address": null,
        "shipping_address": null,
        "customer_name": "Gaurav Kumar",
        "customer_email": "gaurav.kumar@example.com",
        "customer_contact": "9123456780"
    },
    "order_id": "order_FHrfRw4TZU5Q2L",
    "line_items": [],
    "payment_id": null,
    "status": "cancelled",
    "expire_by": 4102444799,
    "issued_at": 1595491479,
    "paid_at": null,
    "cancelled_at": 1595491488,
    "expired_at": null,
    "sms_status": "sent",
    "email_status": "sent",
    "date": 1595491479,
    "terms": null,
    "partial_payment": false,
    "gross_amount": 100,
    "tax_amount": 0,
    "taxable_amount": 0,
    "amount": 100,
    "amount_paid": 0,
    "amount_due": 100,
    "currency": "INR",
    "currency_symbol": "₹",
    "description": "Registration Link for Gaurav Kumar",
    "notes": {
        "note_key 1": "Beam me up Scotty",
        "note_key 2": "Tea. Earl Gray. Hot."
    },
    "comment": null,
    "short_url": "https://rzp.io/i/QlfexTj",
    "view_less": true,
    "billing_start": null,
    "billing_end": null,
    "type": "link",
    "group_taxes_discounts": false,
    "created_at": 1595491480,
    "idempotency_key": null
}
```
-------------------------------------------------------------------------------------------------------

## Fetch token by payment id

```java
String PaymentId = "pay_1Aa00000000001";

Payment payment = instance.Payments.fetch(PaymentId)
```
**Parameters:**

| Name       | Type    | Description                                                                  |
|------------|---------|------------------------------------------------------------------------------|
| PaymentId* | string      | The id of the payment to be fetched |

**Response:**
For fetch token by payment id response please click [here](https://razorpay.com/docs/api/recurring-payments/emandate/auto-debit/#21-fetch-token-by-payment-id)

-------------------------------------------------------------------------------------------------------

## Fetch tokens by customer id

```java
String CustomerId = "cust_BMB3EwbqnqZ2EI";

List<Token> token = instance.Customers.fetchTokens(CustomerId);
```
**Parameters:**

| Name        | Type    | Description                                                                  |
|-------------|---------|------------------------------------------------------------------------------|
| CustomerId* | string      | The id of the customer to be fetched |

**Response:**
```json
{
  "entity": "collection",
  "count": 1,
  "items": [
    {
      "id": "token_FHf94Uym9tdYFJ",
      "entity": "token",
      "token": "2wDPM7VAlXtjAR",
      "bank": "HDFC",
      "wallet": null,
      "method": "emandate",
      "vpa": null,
      "recurring": true,
      "recurring_details": {
        "status": "confirmed",
        "failure_reason": null
      },
      "auth_type": "netbanking",
      "mrn": null,
      "used_at": 1595447381,
      "created_at": 1595447381,
      "bank_details": {
        "beneficiary_name": "Gaurav Kumar",
        "account_number": "1121431121541121",
        "ifsc": "HDFC0000001",
        "account_type": "savings"
      },
      "max_amount": 9999900,
      "expired_at": 1689971140,
      "dcc_enabled": false
    }
  ]
}
```
-------------------------------------------------------------------------------------------------------

## Delete tokens

```java
String CustomerId = "cust_BMB3EwbqnqZ2EI";

String TokenId = "token_FHf94Uym9tdYFJ";

instance.Customers.deleteToken(CustomerId, TokenId);
```
**Parameters:**

| Name        | Type    | Description                                                                  |
|-------------|---------|------------------------------------------------------------------------------|
| CustomerId* | string      | The id of the customer to be fetched |
| TokenId*    | string      | The id of the token to be fetched |

**Response:**
```json
{
    "deleted": true
}
```
-------------------------------------------------------------------------------------------------------

**PN: * indicates mandatory fields**
<br>
<br>
**For reference click [here](https://razorpay.com/docs/api/recurring-payments/emandate/auto-debit/)**