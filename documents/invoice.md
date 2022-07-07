## Invoices

### Create Invoice

Request #1
In this example, an invoice is created using the customer and item details. Here, the customer and item are created while creating the invoice.
```java
JSONObject invoiceRequest = new JSONObject();
invoiceRequest.put("type", "invoice");
invoiceRequest.put("description", "Invoice for the month of January 2020");
invoiceRequest.put("partial_payment",true);
JSONObject customer = new JSONObject();
customer.put("name","Gaurav Kumar");
customer.put("contact","9999999999");
customer.put("email","gaurav.kumar@example.com");
JSONObject billingAddress = new JSONObject();
billingAddress.put("line1","Ground & 1st Floor, SJR Cyber Laskar");
billingAddress.put("line2","Hosur Road");
billingAddress.put("zipcode","560068");
billingAddress.put("city","Bengaluru");
billingAddress.put("state","Karnataka");
billingAddress.put("country","in");
customer.put("billing_address",billingAddress);
JSONObject shippingAddress = new JSONObject();
shippingAddress.put("line1","Ground & 1st Floor, SJR Cyber Laskar");
shippingAddress.put("line2","Hosur Road");
shippingAddress.put("zipcode","560068");
shippingAddress.put("city","Bengaluru");
shippingAddress.put("state","Karnataka");
shippingAddress.put("country","in");
customer.put("shipping_address",shippingAddress);
invoiceRequest.put("customer",customer);
List<Object> lines = new ArrayList<>();
JSONObject lineItems = new JSONObject();
lineItems.put("name","Master Cloud Computing in 30 Days");
lineItems.put("description","Book by Ravena Ravenclaw");
lineItems.put("amount",399);
lineItems.put("currency","INR");
lineItems.put("quantity",1);
lines.add(lineItems);
invoiceRequest.put("line_items",lines);
invoiceRequest.put("email_notify", 1);
invoiceRequest.put("sms_notify", 1);
invoiceRequest.put("currency","INR");
invoiceRequest.put("expire_by", 1580479824);

Invoice invoice = instance.invoices.create(invoiceRequest);
```

**Parameters:**

| Name            | Type    | Description                                                                  |
|-----------------|---------|------------------------------------------------------------------------------|
|type*          | string | entity type (here its invoice)                                               |
|description        | string  | A brief description of the invoice.                      |
|customer_id           | string  | customer id for which invoice need be raised   |
|draft           | string  |  Invoice is created in draft state when value is set to `1`   |
| customer*     | object | All parameters listed [here](https://razorpay.com/docs/api/payments/invoices/#create-an-invoice) are supported           |
| line_items    | array | All parameters listed [here](https://razorpay.com/docs/api/payments/invoices/#create-an-invoice) are supported |
|expire_by           | integer  | Details of the line item that is billed in the invoice.  |
|sms_notify           | boolean  | Details of the line item that is billed in the invoice.  |
|email_notify           | boolean  | Details of the line item that is billed in the invoice.  |
|partial_payment | boolean  | Indicates whether customers can make partial payments on the invoice . Possible values: true - Customer can make partial payments. false (default) - Customer cannot make partial payments. |
| currency*   | string  | The currency of the payment (defaults to INR)  |


Request #2
In this example, an invoice is created using existing `customer_id` and `item_id`
```java
JSONObject invoiceRequest = new JSONObject();
invoiceRequest.put("type", "invoice");
invoiceRequest.put("date", "1589994898");
invoiceRequest.put("customer_id","cust_JDdNazagOgg9Ig");
List<Object> lines = new ArrayList<>();
JSONObject lineItems = new JSONObject();
lineItems.put("item_id","item_J7lZCyxMVeEtYB");
lines.add(lineItems);
invoiceRequest.put("line_items",lines);

Invoice invoice = instance.invoices.create(invoiceRequest);
```

**Parameters:**

| Name            | Type    | Description                                                                  |
|-----------------|---------|------------------------------------------------------------------------------|
|type*          | string | entity type (here its invoice)                                               |
|description        | string  | A brief description of the invoice.                      |
|customer_id           | string  | customer id for which invoice need be raised                     |
| customer*     | array | All parameters listed [here](https://razorpay.com/docs/api/payments/invoices/#create-an-invoice) are supported           |
| line_items    | array | All parameters listed [here](https://razorpay.com/docs/api/payments/invoices/#create-an-invoice) are supported |
| sms_notify  | boolean  | SMS notifications are to be sent by Razorpay (default : 1)  |
| currency*  (conditionally mandatory) | string  | The 3-letter ISO currency code for the payment. Currently, only `INR` is supported. |
| email_notify | boolean  | Email notifications are to be sent by Razorpay (default : 1)  |
| expire_by    | integer | The timestamp, in Unix format, till when the customer can make the authorization payment. |

**Response:**

For create invoice response please click [here](https://razorpay.com/docs/api/invoices/#create-an-invoice)

-------------------------------------------------------------------------------------------------------

### Fetch all invoices

```java
List<Invoice> invoice = instance.invoices.fetchAll();
```

**Parameters:**

| Name            | Type    | Description                                                                  |
|-----------------|---------|------------------------------------------------------------------------------|
|type          | string | entity type (here its invoice)                                               |
|payment_id        | string  | The unique identifier of the payment made by the customer against the invoice.                      |
|customer_id           | string  | The unique identifier of the customer.                     |
|receipt           | string  |  The unique receipt number that you entered for internal purposes.                     |

**Response:**

For fetch all invoice response please click [here](https://razorpay.com/docs/api/invoices/#fetch-multiple-invoices)

-------------------------------------------------------------------------------------------------------

### Fetch invoice

```java
String invoiceId = "inv_E7q0tqkxBRzdau";

Invoice invoice = instance.invoices.fetch(invoiceId);
```

**Parameters:**

| Name       | Type    | Description                                                                  |
|------------|---------|------------------------------------------------------------------------------|
| invoiceId* | string | The id of the invoice to be fetched                         |

**Response:**
```json
{
  "id": "inv_E7q0tqkxBRzdau",
  "entity": "invoice",
  "receipt": null,
  "invoice_number": null,
  "customer_id": "cust_E7q0trFqXgExmT",
  "customer_details": {
    "id": "cust_E7q0trFqXgExmT",
    "name": "Gaurav Kumar",
    "email": "gaurav.kumar@example.com",
    "contact": "9999999999",
    "gstin": null,
    "billing_address": {
      "id": "addr_E7q0ttqh4SGhAC",
      "type": "billing_address",
      "primary": true,
      "line1": "Ground & 1st Floor, SJR Cyber Laskar",
      "line2": "Hosur Road",
      "zipcode": "560068",
      "city": "Bengaluru",
      "state": "Karnataka",
      "country": "in"
    },
    "shipping_address": {
      "id": "addr_E7q0ttKwVA1h2V",
      "type": "shipping_address",
      "primary": true,
      "line1": "Ground & 1st Floor, SJR Cyber Laskar",
      "line2": "Hosur Road",
      "zipcode": "560068",
      "city": "Bengaluru",
      "state": "Karnataka",
      "country": "in"
    },
    "customer_name": "Gaurav Kumar",
    "customer_email": "gaurav.kumar@example.com",
    "customer_contact": "9999999999"
  },
  "order_id": "order_E7q0tvRpC0WJwg",
  "line_items": [
    {
      "id": "li_E7q0tuPNg84VbZ",
      "item_id": null,
      "ref_id": null,
      "ref_type": null,
      "name": "Master Cloud Computing in 30 Days",
      "description": "Book by Ravena Ravenclaw",
      "amount": 399,
      "unit_amount": 399,
      "gross_amount": 399,
      "tax_amount": 0,
      "taxable_amount": 399,
      "net_amount": 399,
      "currency": "INR",
      "type": "invoice",
      "tax_inclusive": false,
      "hsn_code": null,
      "sac_code": null,
      "tax_rate": null,
      "unit": null,
      "quantity": 1,
      "taxes": []
    }
  ],
  "payment_id": null,
  "status": "issued",
  "expire_by": 1589765167,
  "issued_at": 1579765167,
  "paid_at": null,
  "cancelled_at": null,
  "expired_at": null,
  "sms_status": "pending",
  "email_status": "pending",
  "date": 1579765167,
  "terms": null,
  "partial_payment": true,
  "gross_amount": 399,
  "tax_amount": 0,
  "taxable_amount": 399,
  "amount": 399,
  "amount_paid": 0,
  "amount_due": 399,
  "currency": "INR",
  "currency_symbol": "₹",
  "description": "Invoice for the month of January 2020",
  "notes": [],
  "comment": null,
  "short_url": "https://rzp.io/i/2wxV8Xs",
  "view_less": true,
  "billing_start": null,
  "billing_end": null,
  "type": "invoice",
  "group_taxes_discounts": false,
  "created_at": 1579765167
}
```
-------------------------------------------------------------------------------------------------------

### Update invoice

```java
String invoiceId = "inv_DAweOiQ7amIUVd";

JSONObject invoiceRequest = new JSONObject();
List<Object> lines = new ArrayList<>();
JSONObject lineItems = new JSONObject();
lineItems.put("id","li_JZzL5KfAkHgEaV");
lineItems.put("name","Book / English August - Updated name and quantity");
lineItems.put("quantity",1);
JSONObject lineItems1 = new JSONObject();
lineItems1.put("name","Book / A Wild Sheep Chase");
lineItems1.put("amount","200");
lineItems1.put("currency","INR");
lineItems1.put("quantity",1);
lines.add(lineItems);
lines.add(lineItems1);
invoiceRequest.put("line_items",lines);
JSONObject notes = new JSONObject();
notes.put("updated-key","An updated note.");
invoiceRequest.put("notes", notes);

Invoice invoice = instance.invoices.edit(invoiceId,invoiceRequest);
```

**Parameters:**

| Name            | Type    | Description                                                                  |
|-----------------|---------|------------------------------------------------------------------------------|
| invoiceId*          | string | The id of the invoice to be fetched                         |

**Response:**
For update invoice response please click [here](https://razorpay.com/docs/api/invoices/#update-an-invoice)

-------------------------------------------------------------------------------------------------------

### Issue an invoice

```java
String invoiceId = "inv_DAweOiQ7amIUVd";

Invoice invoice = instance.invoices.issue(invoiceId);
```

**Parameters:**

| Name       | Type    | Description                                                                  |
|------------|---------|------------------------------------------------------------------------------|
| invoiceId* | string | The id of the invoice to be issued                         |

**Response:**
```json
{
  "id": "inv_DAweOiQ7amIUVd",
  "entity": "invoice",
  "receipt": "#0961",
  "invoice_number": "#0961",
  "customer_id": "cust_DAtUWmvpktokrT",
  "customer_details": {
    "id": "cust_DAtUWmvpktokrT",
    "name": "Gaurav Kumar",
    "email": "gaurav.kumar@example.com",
    "contact": "9977886633",
    "gstin": null,
    "billing_address": {
      "id": "addr_DAtUWoxgu91obl",
      "type": "billing_address",
      "primary": true,
      "line1": "318 C-Wing, Suyog Co. Housing Society Ltd.",
      "line2": "T.P.S Road, Vazira, Borivali",
      "zipcode": "400092",
      "city": "Mumbai",
      "state": "Maharashtra",
      "country": "in"
    },
    "shipping_address": null,
    "customer_name": "Gaurav Kumar",
    "customer_email": "gaurav.kumar@example.com",
    "customer_contact": "9977886633"
  },
  "order_id": "order_DBG3P8ZgDd1dsG",
  "line_items": [
    {
      "id": "li_DAweOizsysoJU6",
      "item_id": null,
      "name": "Book / English August - Updated name and quantity",
      "description": "150 points in Quidditch",
      "amount": 400,
      "unit_amount": 400,
      "gross_amount": 400,
      "tax_amount": 0,
      "taxable_amount": 400,
      "net_amount": 400,
      "currency": "INR",
      "type": "invoice",
      "tax_inclusive": false,
      "hsn_code": null,
      "sac_code": null,
      "tax_rate": null,
      "unit": null,
      "quantity": 1,
      "taxes": []
    },
    {
      "id": "li_DAwjWQUo07lnjF",
      "item_id": null,
      "name": "Book / A Wild Sheep Chase",
      "description": null,
      "amount": 200,
      "unit_amount": 200,
      "gross_amount": 200,
      "tax_amount": 0,
      "taxable_amount": 200,
      "net_amount": 200,
      "currency": "INR",
      "type": "invoice",
      "tax_inclusive": false,
      "hsn_code": null,
      "sac_code": null,
      "tax_rate": null,
      "unit": null,
      "quantity": 1,
      "taxes": []
    }
  ],
  "payment_id": null,
  "status": "issued",
  "expire_by": 1567103399,
  "issued_at": 1566974805,
  "paid_at": null,
  "cancelled_at": null,
  "expired_at": null,
  "sms_status": null,
  "email_status": null,
  "date": 1566891149,
  "terms": null,
  "partial_payment": false,
  "gross_amount": 600,
  "tax_amount": 0,
  "taxable_amount": 600,
  "amount": 600,
  "amount_paid": 0,
  "amount_due": 600,
  "currency": "INR",
  "currency_symbol": "₹",
  "description": "This is a test invoice.",
  "notes": {
    "updated-key": "An updated note."
  },
  "comment": null,
  "short_url": "https://rzp.io/i/K8Zg72C",
  "view_less": true,
  "billing_start": null,
  "billing_end": null,
  "type": "invoice",
  "group_taxes_discounts": false,
  "created_at": 1566906474,
  "idempotency_key": null
}
```
-------------------------------------------------------------------------------------------------------

### Delete an invoice

```java
String invoiceId = "inv_DAweOiQ7amIUVd";

List<Invoice> invoice = instance.invoices.delete(invoiceId);
```

**Parameters:**

| Name       | Type    | Description                                                                  |
|------------|---------|------------------------------------------------------------------------------|
| invoiceId* | string | The id of the invoice to be deleted                         |

**Response:**
```
[]
```
-------------------------------------------------------------------------------------------------------

### Cancel an invoice

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
  "id": "inv_E7q0tqkxBRzdau",
  "entity": "invoice",
  "receipt": null,
  "invoice_number": null,
  "customer_id": "cust_E7q0trFqXgExmT",
  "customer_details": {
    "id": "cust_E7q0trFqXgExmT",
    "name": "Gaurav Kumar",
    "email": "gaurav.kumar@example.com",
    "contact": "9972132594",
    "gstin": null,
    "billing_address": {
      "id": "addr_E7q0ttqh4SGhAC",
      "type": "billing_address",
      "primary": true,
      "line1": "Ground & 1st Floor, SJR Cyber Laskar",
      "line2": "Hosur Road",
      "zipcode": "560068",
      "city": "Bengaluru",
      "state": "Karnataka",
      "country": "in"
    },
    "shipping_address": {
      "id": "addr_E7q0ttKwVA1h2V",
      "type": "shipping_address",
      "primary": true,
      "line1": "Ground & 1st Floor, SJR Cyber Laskar",
      "line2": "Hosur Road",
      "zipcode": "560068",
      "city": "Bengaluru",
      "state": "Karnataka",
      "country": "in"
    },
    "customer_name": "Gaurav Kumar",
    "customer_email": "gaurav.kumar@example.com",
    "customer_contact": "9972132594"
  },
  "order_id": "order_E7q0tvRpC0WJwg",
  "line_items": [
    {
      "id": "li_E7q0tuPNg84VbZ",
      "item_id": null,
      "ref_id": null,
      "ref_type": null,
      "name": "Master Cloud Computing in 30 Days",
      "description": "Book by Ravena Ravenclaw",
      "amount": 399,
      "unit_amount": 399,
      "gross_amount": 399,
      "tax_amount": 0,
      "taxable_amount": 399,
      "net_amount": 399,
      "currency": "INR",
      "type": "invoice",
      "tax_inclusive": false,
      "hsn_code": null,
      "sac_code": null,
      "tax_rate": null,
      "unit": null,
      "quantity": 1,
      "taxes": []
    }
  ],
  "payment_id": null,
  "status": "cancelled",
  "expire_by": null,
  "issued_at": 1579765167,
  "paid_at": null,
  "cancelled_at": 1579768206,
  "expired_at": null,
  "sms_status": "sent",
  "email_status": "sent",
  "date": 1579765167,
  "terms": null,
  "partial_payment": false,
  "gross_amount": 399,
  "tax_amount": 0,
  "taxable_amount": 399,
  "amount": 399,
  "amount_paid": 0,
  "amount_due": 399,
  "currency": "INR",
  "currency_symbol": "₹",
  "description": null,
  "notes": [],
  "comment": null,
  "short_url": "https://rzp.io/i/2wxV8Xs",
  "view_less": true,
  "billing_start": null,
  "billing_end": null,
  "type": "invoice",
  "group_taxes_discounts": false,
  "created_at": 1579765167,
  "idempotency_key": null
}
```
-------------------------------------------------------------------------------------------------------

### Send notification

```java
String invoiceId = "inv_DAweOiQ7amIUVd";

String medium = "sms";

Invoice invoice = instance.invoices.notifyBy(invoiceId,medium);
```

**Parameters:**

| Name            | Type    | Description                                                                  |
|-----------------|---------|------------------------------------------------------------------------------|
| invoiceId*          | string | The id of the invoice to be notified                         |
| medium*          | string | `sms`/`email`, Medium through which notification should be sent.                         |

**Response:**
```json
{
    "success": true
}
```
-------------------------------------------------------------------------------------------------------

**PN: * indicates mandatory fields**
<br>
<br>
**For reference click [here](https://razorpay.com/docs/api/invoices)**