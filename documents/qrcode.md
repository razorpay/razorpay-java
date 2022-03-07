## Qr Codes

### Create Qr code

```java
String json = "{\n" +
              "  type: \"upi_qr\",\n" +
              "  name: \"Store_1\",\n" +
              "  usage: \"single_use\",\n" +
              "  fixed_amount: true,\n" +
              "  payment_amount: 300,\n" +
              "  description: \"For Store 1\",\n" +
              "  customer_id: \"cust_HKsR5se84c5LTO\",\n" +
              "  close_by: 1681615838,\n" +
              "  notes: {\n" +
              "    purpose: \"Test UPI QR code notes\"\n" +
              "  }\n" +
              "}";
              
JSONObject request = new JSONObject(json);

QrCode qrcode = instance.qrCode.create(request);
```

**Parameters:**

| Name            | Type    | Description                                                                  |
|-----------------|---------|------------------------------------------------------------------------------|
| type*          | string | The type of QR code i.e, `upi_qr`/`bharat_qr`                                  |
| name          | string | Label entered to identify the QR code.                              |
| usage*          | string | Indicates if the QR code should be allowed to accept single payment or multiple payments i.e, `single_use`/`multiple_use`     |
| fixed_amount          | boolean | Indicates if the QR should accept payments of specific amounts or any amount. |
| payment_amount(* mandatory if fixed_amount is true)          | integer | Indicates if the QR should accept payments of specific amounts or any amount. |
| customer_id          | string | Unique identifier of the customer the QR code is linked with |
| description          | string | A brief description about the QR code. |
| close_by          | integer | UNIX timestamp at which the QR code is scheduled to be automatically closed. The time must be at least 15 minutes after the current time.  |
| notes          | object | Key-value pair that can be used to store additional information about the QR code. Maximum 15 key-value pairs, 256 characters (maximum) each. |

**Response:**
```json
{
  "id": "qr_HMsVL8HOpbMcjU",
  "entity": "qr_code",
  "created_at": 1623660301,
  "name": "Store_1",
  "usage": "single_use",
  "type": "upi_qr",
  "image_url": "https://rzp.io/i/BWcUVrLp",
  "payment_amount": 300,
  "status": "active",
  "description": "For Store 1",
  "fixed_amount": true,
  "payments_amount_received": 0,
  "payments_count_received": 0,
  "notes": {
    "purpose": "Test UPI QR code notes"
  },
  "customer_id": "cust_HKsR5se84c5LTO",
  "close_by": 1681615838
}
```
-------------------------------------------------------------------------------------------------------

### Create Qr code with GST

```java
String json = "{\n" +
              "  type: \"upi_qr\",\n" +
              "  name: \"Store_1\",\n" +
              "  usage: \"single_use\",\n" +
              "  fixed_amount: true,\n" +
              "  payment_amount: 300,\n" +
              "  description: \"For Store 1\",\n" +
              "  customer_id: \"cust_HKsR5se84c5LTO\",\n" +
              "  close_by: 1681615838,\n" +
              "  notes: {\n" +
              "    purpose: \"Test UPI QR code notes\"\n" +
              "  },\n" +
              "  tax_invoice: {\n" +
              "    number: \"INV001\",\n" +
              "    date: 1589994898,\n" +
              "    customer_name: \"Gaurav Kumar\",\n" +
              "    business_gstin: \"06AABCU9605R1ZR\",\n" +
              "    gst_amount: 4000,\n" +
              "    cess_amount: 0,\n" +
              "    supply_type: \"interstate\"\n" +
              "  }\n" +
              "}";
              
JSONObject request = new JSONObject(json);

QrCode qrcode = instance.qrCode.create(request);
```

**Parameters:**

| Name            | Type    | Description                                                                  |
|-----------------|---------|------------------------------------------------------------------------------|
| type*          | string | The type of QR code i.e, `upi_qr`/`bharat_qr`                                  |
| name          | string | Label entered to identify the QR code.                              |
| usage*          | string | Indicates if the QR code should be allowed to accept single payment or multiple payments i.e, `single_use`/`multiple_use`     |
| fixed_amount          | boolean | Indicates if the QR should accept payments of specific amounts or any amount. |
| payment_amount(* mandatory if fixed_amount is true)          | integer | Indicates if the QR should accept payments of specific amounts or any amount. |
| customer_id          | string | Unique identifier of the customer the QR code is linked with |
| description          | string | A brief description about the QR code. |
| close_by          | integer | UNIX timestamp at which the QR code is scheduled to be automatically closed. The time must be at least 15 minutes after the current time.  |
| notes          | object | Key-value pair that can be used to store additional information about the QR code. Maximum 15 key-value pairs, 256 characters (maximum) each. |
| tax_invoice          | object | This block contains information about the invoices. If not provided, the transaction will default to non-GST compliant UPI flow. |

**Response:**
```json
{
  "id": "qr_HMsVL8HOpbMcjU",
  "entity": "qr_code",
  "created_at": 1623660301,
  "name": "Store_1",
  "usage": "single_use",
  "type": "upi_qr",
  "image_url": "https://rzp.io/i/BWcUVrLp",
  "payment_amount": 300,
  "status": "active",
  "description": "For Store 1",
  "fixed_amount": true,
  "payments_amount_received": 0,
  "payments_count_received": 0,
  "notes": {
    "purpose": "Test UPI QR code notes"
  },
  "customer_id": "cust_HKsR5se84c5LTO",
  "close_by": 1681615838,
  "tax_invoice": {
    "number": "INV001",
    "date": 1589994898,
    "customer_name": "Gaurav Kumar",
    "business_gstin": "06AABCU9605R1ZR",
    "gst_amount": 4000,
    "cess_amount": 0,
    "supply_type": "interstate"
  }
}
```
-------------------------------------------------------------------------------------------------------

### Fetch all Qr code

```java
String json = "{\n" +
                 "\"count\" : 1\n" +
               "}";

JSONObject options = new JSONObject(json);

List<QrCode> qrcode = instance.QrCode.fetchAll(options);
```

**Parameters:**

| Name            | Type    | Description                                                                  |
|-----------------|---------|------------------------------------------------------------------------------|
| from  | timestamp | timestamp after which the payments were created  |
| to    | timestamp | timestamp before which the payments were created |
| count | integer   | number of payments to fetch (default: 10)        |
| skip  | integer   | number of payments to be skipped (default: 0)    |

**Response:**
```json
{
  "entity": "collection",
  "count": 1,
  "items": [
    {
      "id": "qr_HO2jGkWReVBMNu",
      "entity": "qr_code",
      "created_at": 1623914648,
      "name": "Store_1",
      "usage": "single_use",
      "type": "upi_qr",
      "image_url": "https://rzp.io/i/w2CEwYmkAu",
      "payment_amount": 300,
      "status": "active",
      "description": "For Store 1",
      "fixed_amount": true,
      "payments_amount_received": 0,
      "payments_count_received": 0,
      "notes": {
        "purpose": "Test UPI QR code notes"
      },
      "customer_id": "cust_HKsR5se84c5LTO",
      "close_by": 1681615838,
      "closed_at": null,
      "close_reason": null
    }
  ]
}
```

-------------------------------------------------------------------------------------------------------

### Fetch a Qr code

```java
String QrCodeId = "qr_HO2r1MDprYtWRT";

QrCode qrcode = instance.qrCode.fetch(QrCodeId);
```

**Parameters:**

| Name     | Type    | Description                                                                  |
|----------|---------|------------------------------------------------------------------------------|
| QrCodeId | string | The id of the qr code to be fetched  |

**Response:**
```json
{
  "id": "qr_HO2r1MDprYtWRT",
  "entity": "qr_code",
  "created_at": 1623915088,
  "name": "Store_1",
  "usage": "single_use",
  "type": "upi_qr",
  "image_url": "https://rzp.io/i/oCswTOcCo",
  "payment_amount": 300,
  "status": "active",
  "description": "For Store 1",
  "fixed_amount": true,
  "payments_amount_received": 0,
  "payments_count_received": 0,
  "notes": {
    "purpose": "Test UPI QR code notes"
  },
  "customer_id": "cust_HKsR5se84c5LTO",
  "close_by": 1681615838,
  "closed_at": null,
  "close_reason": null
}
```
-------------------------------------------------------------------------------------------------------

### Fetch a Qr code for customer id

```java
String CustomerId = "cust_HKsR5se84c5LTO";

JSONObject request = new JSONObject("{\"customer_id\":"+CustomerId+"}");

List<QrCode> qrcode = instance.QrCode.fetchAll(request)
```

**Parameters:**

| Name        | Type    | Description                                                                  |
|-------------|---------|------------------------------------------------------------------------------|
| CustomerId* | string | The id of the customer to which qr code need to be fetched  |

**Response:**
```json
{
  "entity": "collection",
  "count": 1,
  "items": [
    {
      "id": "qr_HMsgvioW64f0vh",
      "entity": "qr_code",
      "created_at": 1623660959,
      "name": "Store_1",
      "usage": "single_use",
      "type": "upi_qr",
      "image_url": "https://rzp.io/i/DTa2eQR",
      "payment_amount": 300,
      "status": "active",
      "description": "For Store 1",
      "fixed_amount": true,
      "payments_amount_received": 0,
      "payments_count_received": 0,
      "notes": {
        "purpose": "Test UPI QR code notes"
      },
      "customer_id": "cust_HKsR5se84c5LTO",
      "close_by": 1681615838
    }
  ]
}
```
-------------------------------------------------------------------------------------------------------

### Fetch a Qr code for payment id

```java
 String PaymentId = "pay_FVmAstJWfsD3SO";

 JSONObject request = new JSONObject("{\"payment_id\":"+PaymentId+"}");

List<QrCode> qrcode = instance.QrCode.fetchAll(request);
```

**Parameters:**

| Name       | Type    | Description                                                                  |
|------------|---------|------------------------------------------------------------------------------|
| PaymentId* | string | The id of the payment to which qr code need to be fetched  |

**Response:**
```json
{
  "entity": "collection",
  "count": 1,
  "items": [
    {
      "id": "qr_HMsqRoeVwKbwAF",
      "entity": "qr_code",
      "created_at": 1623661499,
      "name": "Fresh Groceries",
      "usage": "multiple_use",
      "type": "upi_qr",
      "image_url": "https://rzp.io/i/eI9XD54Q",
      "payment_amount": null,
      "status": "active",
      "description": "Buy fresh groceries",
      "fixed_amount": false,
      "payments_amount_received": 1000,
      "payments_count_received": 1,
      "notes": [],
      "customer_id": "cust_HKsR5se84c5LTO",
      "close_by": 1624472999,
      "close_reason": null
    }
  ]
}
```
-------------------------------------------------------------------------------------------------------

### Fetch Payments for a QR Code

```java
String QrCodeId = "qr_HMsVL8HOpbMcjU";

String json = "{\n" +
                 "\"count\" : 1\n" +
               "}";
JSONObject options = new JSONObject(json);   
            
List<QrCode> qrcode = instance.qrCode.fetchAllPayments(QrCodeId, options)
```

**Parameters:**

| Name      | Type    | Description                                                                  |
|-----------|---------|------------------------------------------------------------------------------|
| QrCodeId* | string | The id of the qr code to which payment where made |
| from      | timestamp | timestamp after which the payments were created  |
| to        | timestamp | timestamp before which the payments were created |
| count     | integer   | number of payments to fetch (default: 10)        |
| skip      | integer   | number of payments to be skipped (default: 0)    |

**Response:**
```json
{
  "entity": "collection",
  "count": 1,
  "items": [
    {
      "id": "pay_HMtDKn3TnF4D8x",
      "entity": "payment",
      "amount": 500,
      "currency": "INR",
      "status": "captured",
      "order_id": null,
      "invoice_id": null,
      "international": false,
      "method": "upi",
      "amount_refunded": 0,
      "refund_status": null,
      "captured": true,
      "description": "QRv2 Payment",
      "card_id": null,
      "bank": null,
      "wallet": null,
      "vpa": "gauri.kumari@okhdfcbank",
      "email": "gauri.kumari@example.com",
      "contact": "+919999999999",
      "customer_id": "cust_HKsR5se84c5LTO",
      "notes": [],
      "fee": 0,
      "tax": 0,
      "error_code": null,
      "error_description": null,
      "error_source": null,
      "error_step": null,
      "error_reason": null,
      "acquirer_data": {
        "rrn": "116514257019"
      },
      "created_at": 1623662800
    }
  ]
}
```
-------------------------------------------------------------------------------------------------------

### Close a QR Code

```js
String QrCodeId = "qr_HMsVL8HOpbMcjU";

QrCode qrcode = instance.QrCode.close(QrCodeId);
```

**Parameters:**

| Name      | Type    | Description                                                                  |
|-----------|---------|------------------------------------------------------------------------------|
| QrCodeId* | string | The id of the qr code to be closed |

**Response:**
```json
{
  "id": "qr_HMsVL8HOpbMcjU",
  "entity": "qr_code",
  "created_at": 1623660301,
  "name": "Store_1",
  "usage": "single_use",
  "type": "upi_qr",
  "image_url": "https://rzp.io/i/BWcUVrLp",
  "payment_amount": 300,
  "status": "closed",
  "description": "For Store 1",
  "fixed_amount": true,
  "payments_amount_received": 0,
  "payments_count_received": 0,
  "notes": {
    "purpose": "Test UPI QR code notes"
  },
  "customer_id": "cust_HKsR5se84c5LTO",
  "close_by": 1681615838,
  "closed_at": 1623660445,
  "close_reason": "on_demand"
}
```
-------------------------------------------------------------------------------------------------------


**PN: * indicates mandatory fields**
<br>
<br>
**For reference click [here](https://razorpay.com/docs/qr-codes/)**