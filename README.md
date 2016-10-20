# Razorpay Java SDK

Official java bindings for the [Razorpay API](https://docs.razorpay.com/docs/payments).


## Documentation

Documentation of Razorpay's API and their usage is available at <https://docs.razorpay.com>

## Installation

## Usage

Instantiate `RazorpayClient` with `key_id` & `key_secret`. You can obtain the keys from the dashboard app <https://dashboard.razorpay.com/#/app/keys>

```java
// Initialize client
RazorpayClient razorpayClient = new RazorpayClient("key_id", "key_secret");

```
### [Payments](https://docs.razorpay.com/docs/return-objects#payment-entity)
* Fetch all payments
```java
List<Payment> payments = razorpayClient.Payment.fetchAll();
```
* Fetch a particular payment:
```java
Payment payment = razorpayClient.Payment.fetch("payment_id");
// The the Entity.get("attribute_key") method has flexible return types depending on the attribute
int amount = payment.get("amount");
String id = payment.get("id");
Date createdAt = payment.get("created_at");
```

* Capturing a payment:
```java
JSONObject options = new JSONObject();
options.put("amount", 1000);
razorpayClient.Payment.capture("payment_id", options);
// Note: Amount should be same as the original amount while creating the payment
```
* Refund a payment:
```java
// For full refunds
razorpayClient.Payment.refund("payment_id");

// For partial refunds
razorpayClient.Payment.refund("payment_id", "amount_to_be_refunded");
// Note: Amount to be refunded should be less than or equal to the original amount.
```

### [Refunds](https://docs.razorpay.com/docs/return-objects#refund-entity)

* Fetch all refunds:
```java
razorpayClient.Refund.fetchAll("payment_id");
```

* Fetch a particular refund:
```java
razorpayClient.Refund.fetch("payment_id", "refund_id");
```

### [Orders](https://docs.razorpay.com/docs/return-objects#order-entity)

* Create a new order:
```java
JSONObject options = new JSONObject();
options.put("amount", 5000);
options.put("currency", "INR");
options.put("receipt", "txn_123456");
Order order = razorpayClient.Order.create(options);
```

* Fetch a particular order:
```java
Order order = razorpayClient.Order.fetch("order_id");
// You can fetch payments for a particular order
List<Payment> payments = order.fetchPayments();
```

* Fetch all orders:
```java
List<Order> orders = razorpayClient.Order.fetchAll();
```
* Fetch payments for an order:
```java
List<Payment> payments = razorpayClient.Order.fetchPayments("order_id");
```
