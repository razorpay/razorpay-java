## UPI

### Create customer
```java
JSONObject customerRequest = new JSONObject();
customerRequest.put("name","Gaurav Kumar");
customerRequest.put("contact","9123456780");
customerRequest.put("email","gaurav.kumar@example.com");
JSONObject notes = new JSONObject();
notes.put("notes_key_1","Tea, Earl Grey, Hot");
notes.put("notes_key_2","Tea, Earl Grey… decaf.");
customerRequest.put("notes",notes);

Customer customer = instance.customers.create(customerRequest);
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
JSONObject orderRequest = new JSONObject();
orderRequest.put("amount", 100);
orderRequest.put("currency", "INR");
orderRequest.put("customer_id", "cust_JDdNazagOgg9Ig");
orderRequest.put("method", "upi");
orderRequest.put("receipt", "receipt#1");
JSONObject token = new JSONObject();
token.put("max_amount","200000"); 
token.put("expire_at","2709971120");
token.put("frequency","monthly");
orderRequest.put("token", token);
JSONObject notes = new JSONObject();
notes.put("notes_key_1","Tea, Earl Grey, Hot");
notes.put("notes_key_2","Tea, Earl Grey… decaf.");
orderRequest.put("notes", notes);

Order order = instance.orders.create(orderRequest);
```

**Parameters:**

| Name            | Type    | Description                                                                  |
|-----------------|---------|------------------------------------------------------------------------------|
| amount*          | integer | Amount of the order to be paid                                               |
| currency*        | string  | Currency of the order. Currently only `INR` is supported.                      |
| method*        | string  | The authorization method. In this case the value will be `upi`                      |
| receipt         | string  | Your system order reference id.                                              |
| notes           | object  | A key-value pair                                                             |
| token*  | object  | All parameters listed [here](https://razorpay.com/docs/api/payments/recurring-payments/upi/create-authorization-transaction/#112-create-an-order) are supported  |


**Response:**
```json
{
  "id": "order_1Aa00000000002",
  "entity": "order",
  "amount": 100,
  "amount_paid": 0,
  "amount_due": 100,
  "currency": "INR",
  "receipt": "Receipt No. 1",
  "offer_id": null,
  "status": "created",
  "attempts": 0,
  "notes": {
    "notes_key_1": "Tea, Earl Grey, Hot",
    "notes_key_2": "Tea, Earl Grey… decaf."
    },
  "created_at": 1565172642
}
```
-------------------------------------------------------------------------------------------------------

### Create an Authorization Payment

Please refer this [doc](https://razorpay.com/docs/api/recurring-payments/upi/authorization-transaction/#113-create-an-authorization-payment) for authorization payment

-------------------------------------------------------------------------------------------------------

### Create registration link

```java
JSONObject registrationLinkRequest = new JSONObject();
JSONObject customer = new JSONObject();
customer.put("name","Gaurav Kumar");
customer.put("email","gaurav.kumar@example.com");
customer.put("contact","9123456780");
registrationLinkRequest.put("customer", customer);
registrationLinkRequest.put("type", "link");
registrationLinkRequest.put("amount", 100);
registrationLinkRequest.put("currency", "INR");
registrationLinkRequest.put("description", "Registration Link for Gaurav Kumar");
JSONObject subscriptionRegistration = new JSONObject();
subscriptionRegistration.put("method","upi");
subscriptionRegistration.put("max_amount",50000);
subscriptionRegistration.put("expire_at",1609423824);
subscriptionRegistration.put("frequency","monthly");
registrationLinkRequest.put("subscription_registration", subscriptionRegistration);
registrationLinkRequest.put("receipt", "Receipt No. #112");
registrationLinkRequest.put("email_notify", 1);
registrationLinkRequest.put("sms_notify", 1);
registrationLinkRequest.put("expire_by", 1580479824);
JSONObject notes = new JSONObject();
notes.put("notes_key_1","Tea, Earl Grey, Hot");
notes.put("notes_key_2","Tea, Earl Grey… decaf.");
registrationLinkRequest.put("notes", notes);

Invoice invoice = instance.invoices.createRegistrationLink(registrationLinkRequest);
```

**Parameters:**

| Name            | Type    | Description                                                                  |
|-----------------|---------|------------------------------------------------------------------------------|
| customer   | object      | All parameters listed [here](https://razorpay.com/docs/api/payments/recurring-payments/upi/create-authorization-transaction/#121-create-a-registration-link) are supported |
| type*        | string  | In this case, the value is `link`.                      |
| currency*        | string  | The 3-letter ISO currency code for the payment. Currently, only `INR` is supported. |
| amount*         | integer  | The payment amount in the smallest currency sub-unit.                 |
| description*    | string  | A description that appears on the hosted page. For example, `12:30 p.m. Thali meals (Gaurav Kumar`).                                                             |
| subscription_registration      | object  | All parameters listed [here](https://razorpay.com/docs/api/payments/recurring-payments/upi/create-authorization-transaction/#121-create-a-registration-link) are supported |
| sms_notify  | boolean  | SMS notifications are to be sent by Razorpay (default : 1)  |
| email_notify | boolean  | Email notifications are to be sent by Razorpay (default : 1)  |
| expire_by    | integer | The timestamp, in Unix format, till when the customer can make the authorization payment. |
| notes | object  | A key-value pair  |

**Response:**
```json
{
  "id": "inv_FHr1ekX0r2VCVK",
  "entity": "invoice",
  "receipt": "Receipt No. 23",
  "invoice_number": "Receipt No. 23",
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
  "order_id": "order_FHr1ehR3nmNeXo",
  "line_items": [],
  "payment_id": null,
  "status": "issued",
  "expire_by": 4102444799,
  "issued_at": 1595489219,
  "paid_at": null,
  "cancelled_at": null,
  "expired_at": null,
  "sms_status": "pending",
  "email_status": "pending",
  "date": 1595489219,
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
  "short_url": "https://rzp.io/i/ak1WxDB",
  "view_less": true,
  "billing_start": null,
  "billing_end": null,
  "type": "link",
  "group_taxes_discounts": false,
  "created_at": 1595489219,
  "idempotency_key": null
}
```
-------------------------------------------------------------------------------------------------------

### Send/Resend notifications

```java
String invoiceId = "inv_DAweOiQ7amIUVd";

String medium = "sms";

Invoice invoice = instance.invoices.notifyBy(invoiceId,medium);
```

**Parameters:**

| Name       | Type    | Description                                                                  |
|------------|---------|------------------------------------------------------------------------------|
| invoiceId* | string | The id of the invoice to be notified                         |
| medium*    | string | `sms`/`email`, Medium through which notification should be sent.                         |

**Response:**
```json
{
    "success": true
}
```
-------------------------------------------------------------------------------------------------------

### Cancel a registration link

```java
String invoiceId = "inv_DAweOiQ7amIUVd";

Invoice invoice = instance.invoices.cancel(invoiceId);
```

**Parameters:**

| Name       | Type    | Description                                                                  |
|------------|---------|------------------------------------------------------------------------------|
| invoiceId* | string | The id of the invoice to be cancelled                         |

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

### Fetch token by payment ID

```java
String paymentId = "pay_1Aa00000000001";

Payment payment = instance.payments.fetch(paymentId);
```

**Parameters:**

| Name       | Type   | Description                       |
|------------|--------|-----------------------------------|
| paymentId* | string | Id of the payment to be retrieved |

**Response:**
```json
{
  "id": "pay_FHfAzEJ51k8NLj",
  "entity": "payment",
  "amount": 100,
  "currency": "INR",
  "status": "captured",
  "order_id": "order_FHfANdTUYeP8lb",
  "invoice_id": null,
  "international": false,
  "method": "upi",
  "amount_refunded": 0,
  "refund_status": null,
  "captured": true,
  "description": null,
  "card_id": null,
  "bank": null,
  "wallet": null,
  "vpa": "gaurav.kumar@upi",
  "email": "gaurav.kumar@example.com",
  "contact": "+919876543210",
  "customer_id": "cust_DtHaBuooGHTuyZ",
  "token_id": "token_FHfAzGzREc1ug6",
  "notes": {
    "note_key 1": "Beam me up Scotty",
    "note_key 2": "Tea. Earl Gray. Hot."
  },
  "fee": 0,
  "tax": 0,
  "error_code": null,
  "error_description": null,
  "error_source": null,
  "error_step": null,
  "error_reason": null,
  "acquirer_data": {
    "rrn": "854977234911",
    "upi_transaction_id": "D0BED5A062ECDB3E9B3A1071C96BB273"
  },
  "created_at": 1595447490
}
```
-------------------------------------------------------------------------------------------------------

### Fetch tokens by customer ID

```java
String customerId = "cust_BMB3EwbqnqZ2EI";

List<Token> tokens = instance.customers.fetchTokens(customerId);
```

**Parameters:**

| Name        | Type        | Description                                 |
|-------------|-------------|---------------------------------------------|
| customerId* | string      | The id of the customer to be fetched |

**Response:**
```json
{
  "entity": "collection",
  "count": 1,
  "items": [
    {
      "id": "token_FHfAzGzREc1ug6",
      "entity": "token",
      "token": "9KHsdPaCELeQ0t",
      "bank": null,
      "wallet": null,
      "method": "upi",
      "vpa": {
        "username": "gaurav.kumar",
        "handle": "upi",
        "name": null
      },
      "recurring": true,
      "recurring_details": {
        "status": "confirmed",
        "failure_reason": null
      },
      "auth_type": null,
      "mrn": null,
      "used_at": 1595447490,
      "created_at": 1595447490,
      "start_time": 1595447455,
      "dcc_enabled": false
    }
  ]
}
```
-------------------------------------------------------------------------------------------------------

### Delete token

```java
String customerId = "cust_BMB3EwbqnqZ2EI";

String tokenId = "token_FHf94Uym9tdYFJ";

Customer customer = instance.customers.deleteToken(customerId, tokenId);
```

**Parameters:**

| Name        | Type        | Description                                 |
|-------------|-------------|---------------------------------------------|
| customerId* | string      | customer id for which token to deleted |
| tokenId*    | string      | The id of the token to be fetched |

**Response:**
```json
{
    "deleted": true
}
```
-------------------------------------------------------------------------------------------------------

### Create an order to charge the customer

```java

JSONObject orderRequest = new JSONObject();
orderRequest.put("amount", 1000);
orderRequest.put("currency", "INR");
orderRequest.put("payment_capture",true);
orderRequest.put("receipt", "Receipt No. 1");
JSONObject notes = new JSONObject();
notes.put("notes_key_1","Tea, Earl Grey, Hot");
notes.put("notes_key_2","Tea, Earl Grey… decaf.");
orderRequest.put("notes", notes);

Order order = instance.orders.create(orderRequest);
```

**Parameters:**

| Name            | Type    | Description                                                                  |
|-----------------|---------|------------------------------------------------------------------------------|
| amount*          | integer | Amount of the order to be paid                                               |
| currency*        | string  | Currency of the order. Currently only `INR` is supported.                      |
| receipt         | string  | Your system order reference id.                                              |
| notes           | object  | A key-value pair    |
| payment_capture  | boolean  | Indicates whether payment status should be changed to captured automatically or not. Possible values: true - Payments are captured automatically. false - Payments are not captured automatically. |


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

### Create a recurring payment

```java
JSONObject paymentRequest = new JSONObject();
paymentRequest.put("email", "gaurav.kumar@example.com");
paymentRequest.put("contact", "9123456789");
paymentRequest.put("amount", 1000);
paymentRequest.put("currency", "INR");
paymentRequest.put("order_id", "order_1Aa00000000002");
paymentRequest.put("customer_id", "cust_1Aa00000000001");
paymentRequest.put("token", "token_1Aa00000000001");
paymentRequest.put("recurring", 1);
paymentRequest.put("description", "Creating recurring payment for Gaurav Kumar");
JSONObject notes = new JSONObject();
paymentRequest.put("notes_key_1","Tea, Earl Grey, Hot");
paymentRequest.put("notes_key_2","Tea, Earl Grey… decaf.");

Payment payment = instance.payments.createRecurringPayment(paymentRequest);
```

**Parameters:**

| Name           | Type    | Description                                                                  |
|----------------|---------|------------------------------------------------------------------------------|
| email*         | string | The customer's email address.                                               |
| contact*       | string  | The customer's phone number.                      |
| amount*        | integer  | The amount you want to charge your customer. This should be the same as the amount in the order.                        |
| currency*       | string  | The 3-letter ISO currency code for the payment. Currently, only `INR` is supported. |
| order_id*       | string  | The unique identifier of the order created. |
| customer_id*      | string  | The `customer_id` for the customer you want to charge.  |
| token*       | string  | The `token_id` generated when the customer successfully completes the authorization payment. Different payment instruments for the same customer have different `token_id`.|
| recurring*       | string  | Determines if recurring payment is enabled or not. Possible values:<br>* `1` - Recurring is enabled.* `0` - Recurring is not enabled.|
| description       | string  | A user-entered description for the payment.|
| notes        | object  | Key-value pair that can be used to store additional information about the entity. Maximum 15 key-value pairs, 256 characters (maximum) each. |

**Response:**
```json
{
  "razorpay_payment_id" : "pay_1Aa00000000001",
  "razorpay_order_id" : "order_1Aa00000000001",
  "razorpay_signature" : "9ef4dffbfd84f1318f6739a3ce19f9d85851857ae648f114332d8401e0949a3d"
}
```
-------------------------------------------------------------------------------------------------------



**PN: * indicates mandatory fields**
<br>
<br>
**For reference click [here](https://razorpay.com/docs/api/recurring-payments/upi/authorization-transaction/)**