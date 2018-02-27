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
 <version>1.3.4</version>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "com.razorpay:razorpay-java:1.3.4"
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

* Create Transfer for a payment:
```java
JSONObject request = new JSONObject();
    
JSONArray transfers = new JSONArray();

JSONObject transfer = new JSONObject();
transfer.put("amount", <amount>); // The amount should be in paise.
transfer.put("currency", "INR");
transfer.put("account", <account_id>);
    
transfers.put(transfer);
request.put("transfers", transfers);

List<Transfer> transfers = razorpayClient.Payments.transfer("payment_id", request);
```

* Fetch all transfers for a payment:
```java
List<Transfers> transfers = razorpayClient.Payments.fetchAllTransfers("payment_id");
```

* Fetch payment bank transfer
```java
BankTransfer bankTransfer = razorpayClient.Payments.fetchBankTransfers("payment_id");
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

### Verification
You can use the Utils class to verify the signature received in response to a payment made using Orders API
```java
JSONObject paymentResponse = new JSONObject();
options.put("razorpay_order_id", "<order_id>"); 
options.put("razorpay_payment_id", "<payment_id>");
options.put("razorpay_signature", "<signature>");
Utils.verifyPaymentSignature(paymentResponse, "<secret_key>");
```
You can also verify the signature of the received webhook:
```java
Utils.verifyWebhookSignature("<webhook_payload>", "<webhook_signature>", "<webhook_secret>");
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

### [Customers](https://docs.razorpay.com/v1/page/card-saving)

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

### [Tokens](https://docs.razorpay.com/v1/page/card-saving)

* Fetch tokens for a customer
```java
List<Token> tokens = razorpayClient.Customers.fetchTokens(customerId);
```
* Get a Token
```java
Token token = razorpayClient.Customers.fetchToken(customerId, tokenId);
```
* Delete a Token
```java
razorpayClient.Customers.deleteToken(customerId, tokenId);
```

### [Transfers](https://docs.razorpay.com/v1/page/marketplace)

* Create direct Transfer
```java
JSONObject request = new JSONObject();
request.put("amount", <amount>); // The amount should be in paise.
request.put("currency", "INR");
request.put("account", <account_id>);
    
Transfer transfer = razorpayClient.Transfers.create(request);
```

* Edit a Transfer
```java
JSONObject request = new JSONObject();
request.put("on_hold", true); // The amount should be in paise.
Transfer transfer = razorpayClient.Transfers.edit(request);
```

* Create reversal of a Transfer
```java
JSONObject request = new JSONObject();
request.put("amount", <amount>); // The amount should be in paise.

Reversal reversal = razorpayClient.Transfers.reversal("transfer_id", request);
```

* Fetch a particular transfer
```java
Transfer transfer = razorpayClient.Transfers.fetch("transfer_id");
```

* Fetch all transfers
```java
List<Transfer> transfers = razorpayClient.Transfers.fetchAll();
```

### [Subscriptions](https://razorpay.com/docs/subscriptions/)

* Create a plan
```java
JSONObject request = new JSONObject();
request.put("period", "weekly");
request.put("interval", 1);

JSONObject item = new JSONObject();
item.put("name", "Test Weekly 1 plan");
item.put("description", "Description for the weekly 1 plan");
item.put("amount", 600);
item.put("currency", "INR");
request.put("item", item);

Plan plan = razorpayClient.Plans.create(request);
```

* Fetch a plan
```java
Plan plan = razorpayClient.Plans.fetch("<plan_id>");
```

* Fetch all plans
```java
List<Plan> listPlans = razorpayClient.Plans.fetchAll();
```

* Create a subscription
```java
JSONObject request = new JSONObject();
request.put("plan_id", "<plan_id>");
request.put("customer_notify", 1);
request.put("total_count", 6);
request.put("start_at", 1495995837);

JSONArray addons = new JSONArray();
JSONObject addon = new JSONObject();
JSONObject item = new JSONObject();
item.put("name", "Delivery charges");
item.put("amount", 30000);
item.put("currency", "INR");
addon.put("item", item);
addons.put(addon);
request.put("addons", addons);

Subscription subscription = razorpayClient.Subscriptions.create(request);
```

* Fetch a subscription
```java
Subscription subscription = razorpayClient.Subscriptions.fetch("<subscription_id>");
```

* Fetch all subscription
```java
List<Subscription> listSubscriptions = razorpayClient.Subscriptions.fetchAll();
```

* Cancel a subscription
```java
Subscription subscription = razorpayClient.Subscriptions.cancel("<subscription_id>");
```

* Add addon
```java
JSONObject request = new JSONObject();
request.put("quantity", 2);

JSONObject addonItem = new JSONObject();
addonItem.put("name", "Extra Chair");
addonItem.put("amount", 30000);
addonItem.put("currency", "INR");
request.put("item", addonItem);

Addon addon = razorpayClient.Subscriptions.createAddon(<subscription_id>, request);
```

* Fetch Addon
```java
Addon addon = razorpayClient.Addons.fetch(<addon_id>);
```

* Delete Addon
```java
razorpayClient.Addons.delete(<addon_id>);
```

### Virtual Accounts

* Create a virtual account
```java
JSONObject request = new JSONObject();
JSONArray receiverTypeArray = new JSONArray();
receiverTypeArray.put("bank_account");
request.put("receiver_types", receiverTypeArray);
JSONObject notes = new JSONObject();
notes.put("receiver_key", "receiver_value");
request.put("notes", notes);
request.put("description", "First Virtual Account");

VirtualAccount virtualAccount = razorpayClient.VirtualAccounts.create(request);
```

* Fetch Virtual Account
```java
VirtualAccount virtualAccount = razorpayClient.VirtualAccounts.fetch("<virtual_account_id>");
```

* Fetch all Virtual Accounts
```java
List<VirtualAccount> virtualAccountList = razorpayClient.VirtualAccounts.fetchAll();
```

* Close Virtual Account
```java
VirtualAccount virtualAccount = razorpayClient.VirtualAccounts.close("<virtual_account_id>");
```

* List Virtual Account payments
```java
List<Payment> paymentList = razorpayClient.VirtualAccounts.fetchPayments("virtual_account_id");
```

* Make custom requests

You can make custom API requests using clients. For example, here is how to make custom request to `/payments/path` endpoint. 

```java
Entity response = razorpayClient.Payments.post("path", JSONObject requestBody);
```