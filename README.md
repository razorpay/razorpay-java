# razorpay-java

Java wrapper for the Razorpay API.

# Usage
```java
// Initialize client
RazorpayClient razorpayClient = new RazorpayClient("api_key", "api_secret");

// Fetch all payments
List<Payment> payments = razorpayClient.Payment.fetchAll();
```
