# Razorpay Java SDK

Official java bindings for the [Razorpay API](https://docs.razorpay.com/docs/payments).


## Documentation

Documentation of Razorpay's API and their usage is available at <https://docs.razorpay.com>

## Requirements

Java 1.7 or later

## Installation

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
<groupId>com.razorpay</groupId>
<artifactId>razorpay-java</artifactId>
<version>1.2.0</version>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "com.razorpay:razorpay-java:1.2.0"
```

## Usage

Instantiate `RazorpayClient` with `key_id` & `key_secret`. You can obtain the keys from the dashboard app <https://dashboard.razorpay.com/#/app/keys>

```java
// Initialize client
RazorpayClient razorpayClient = new RazorpayClient("key_id", "key_secret");
```
* Add custom headers to request (optional)
```java
Map<String, String> headers = new HashMap<String, String>();
razorpayClient.addHeaders(headers);
```

### [Payments](https://docs.razorpay.com/docs/return-objects#payment-entity)
* Fetch all payments
```java
List<Payment> payments = razorpayClient.Payments.fetchAll();
```

* Fetch a particular payment:
```java
Payment payment = razorpayClient.Payments.fetch("payment_id");
// The the Entity.get("attribute_key") method has flexible return types depending on the attribute
int amount = payment.get("amount");
String id = payment.get("id");
Date createdAt = payment.get("created_at");
```

* Capturing a payment:
```java
JSONObject options = new JSONObject();
options.put("amount", 1000);
razorpayClient.Payments.capture("payment_id", options);
// Note: Amount should be same as the original amount while creating the payment. The amount should be in paise.
```

* Refund a payment:
```java
// For full refunds
JSONObject refundRequest = new JSONObject();
refundRequest.put("payment_id", <payment_id>);
Refund refund = razorpayClient.Payments.refund(refundRequest);

// For partial refunds
JSONObject refundRequest = new JSONObject();
refundRequest.put("amount", <amount>);
refundRequest.put("payment_id", <payment_id>);
Refund refund = razorpay.Payments.refund(refundRequest);
// Note: Amount to be refunded should be less than or equal to the original amount.The amount should be in paise.
```

* Fetch all refunds for a payment:
```java
JSONObject refundRequest = new JSONObject();
refundRequest.put("payment_id", <payment_id>);
List<Refund> refund = razorpayClient.Payments.fetchAllRefunds(refundRequest);
```

* Fetch refund for a payment:
```java
Refund refund = razorpayClient.Payments.fetchRefund("refund_id");
```

### [Refunds](https://docs.razorpay.com/docs/return-objects#refund-entity)

* Fetch all refunds:
```java
List<Refund> refunds = razorpayClient.Refunds.fetchAll();
```

* Fetch a particular refund:
```java
Refund refund = razorpayClient.Refunds.fetch("refund_id");
```

### [Orders](https://docs.razorpay.com/docs/return-objects#order-entity)

* Create a new order:
```java
JSONObject options = new JSONObject();
options.put("amount", 5000); // Note: The amount should be in paise.
options.put("currency", "INR");
options.put("receipt", "txn_123456");
Order order = razorpayClient.Orders.create(options);
```

* Fetch a particular order:
```java
Order order = razorpayClient.Orders.fetch("order_id");
```
* Fetch all orders:
```java
List<Order> orders = razorpayClient.Orders.fetchAll();
```
* Fetch payments for an order:
```java
List<Payment> payments = razorpayClient.Orders.fetchPayments("order_id");
```

### [Invoices](https://docs.razorpay.com/v1/page/invoices)

* Create a new invoice:
```java
JSONObject lineItem = new JSONObject();
lineItem.put("amount", 100); // Note: The amount should be in paise.
lineItem.put("name", "name_invoice");

JSONArray lineItems = new JSONArray();
lineItems.put(lineItem);

JSONObject request = new JSONObject();
request.put("line_items", lineItems);
request.put("date", 1480768625); // Timestamp in seconds
request.put("currency", "INR");
request.put("sms_notify", "0"); 

Invoice invoice = razorpayClient.Invoices.create(request);
```

* Fetch a particular invoice:
```java
Invoice invoice = razorpayClient.Invoices.fetch("invoice_id");
```
* Fetch all invoices:
```java
List<Invoice> invoices = razorpayClient.Invoices.fetchAll();
```

### [Cards](https://docs.razorpay.com/v1/page/cards)

* Fetch card details:
```java
Card card = razorpayClient.Cards.fetch(id);
```

### [Customers]

* Create new customer
```java
JSONObject request = new JSONObject();
request.put("name", <name>);
request.put("email", <email>);
Customer customer = razorpayClient.Customers.create(request);
```

* Fetch customer details
```java
Customer customer = razorpayClient.Customers.fetch(customerId);
```

* Edit customer
```java
JSONObject request = new JSONObject();
request.put("name", <name>);
request.put("email", <email>);
Customer customer = razorpayClient.Customers.edit(customerId, request);
```

### [Tokens]

* Fetch tokens for a customer
```java
List<Token> tokens = razorpayClient.Customers.fetchTokens(customerId);
```
* Get a Token
```java
Token token = razorpayClient.Customers.fetchToken(customerId, tokenId);
```
