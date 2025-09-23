# Razorpay Java SDK

Official java bindings for the [Razorpay API](https://docs.razorpay.com/docs/payments).


## Documentation

Documentation of Razorpay's API and their usage is available at <https://docs.razorpay.com>

## Requirements

Java 1.8 or later

Mock Tests Support till Java 1.8

## Installation

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
 <groupId>com.razorpay</groupId>
 <artifactId>razorpay-java</artifactId>
 <version>1.4.8</version>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
implementation "com.razorpay:razorpay-java:1.4.7"
```

## Usage
`RazorpayClient` can be instantiated via two ways:

### Using Private Auth
Instantiate `RazorpayClient` with `key_id` & `key_secret`. You can obtain the keys from the dashboard app <https://dashboard.razorpay.com/#/app/keys>

```java
// Initialize client
RazorpayClient instance = new RazorpayClient("key_id", "key_secret");
```
* Add custom headers to request (optional)
```java
Map<String, String> headers = new HashMap<String, String>();
razorpayClient.addHeaders(headers);
```

### Using Access Token
Instantiate `RazorpayClient` with `access_token`. The `access_token` can be obtained only in case if you are a platform partner. For more information, refer page - https://razorpay.com/docs/partners/platform/.

```java
// Initialize client
RazorpayClient instance = new RazorpayClient("access_token");
```
* Add custom headers to request (optional)
```java
Map<String, String> headers = new HashMap<String, String>();
razorpayClient.addHeaders(headers);
```

## Supported Resources
- [Addon](documents/addon.md)

- [Account](documents/account.md)

- [Item](documents/item.md)

- [Customer](documents/customers.md)

- [Token](documents/token.md)

- [Order](documents/order.md)

- [Payments](documents/payment.md)

- [Product Configuration](documents/productConfiguration.md)

- [Settlements](documents/settlement.md)

- [Stakeholder](documents/stakeholder.md)

- [Fund](documents/fund.md)

- [Refunds](documents/refund.md)

- [Invoice](documents/invoice.md)

- [Subscriptions](documents/subscription.md)

- [Payment Links](documents/paymentLink.md)

- [Smart Collect](documents/virtualAccount.md)

- [Route](documents/transfer.md)

- [QR Code](documents/qrcode.md)

- [Emandate](documents/emandate.md)

- [Cards](documents/card.md)

- [Paper NACH](documents/papernach.md)

- [UPI](documents/upi.md)

- [Register Emandate and Charge First Payment Together](documents/registerEmandate.md)

- [Register NACH and Charge First Payment Together](documents/registerNach.md)

- [Payment Verification](documents/paymentVerfication.md)

- [Webhook](documents/webhook.md)

- [OAuth Token Client](documents/oAuthTokenClient.md)

- [Dispute](documents/dispute.md)

- [Document](documents/document.md)
---

* Make custom requests

You can make custom API requests using clients. For example, here is how to make custom request to `/payments/path` endpoint.

```java
Entity response = razorpayClient.Payments.post("path", JSONObject requestBody);
```
