## payment verification

```java
import com.razorpay.Utils;
```

### Verify payment verification

```java
String secret = "EnLs21M47BllR3X8PSFtjtbd";

JSONObject options = new JSONObject();
options.put("razorpay_order_id", "order_IEIaMR65cu6nz3");
options.put("razorpay_payment_id", "pay_IH4NVgf4Dreq1l");
options.put("razorpay_signature", "0d4e745a1838664ad6c9c9902212a32d627d68e917290b0ad5f08ff4561bc50f");

boolean status =  Utils.verifyPaymentSignature(options, secret);
```

**Parameters:**


| Name  | Type      | Description                                      |
|-------|-----------|--------------------------------------------------|
| razorpay_order_id*  | string | The id of the order to be fetched  |
| razorpay_payment_id*    | string | The id of the payment to be fetched |
| razorpay_signature* | string   | Signature returned by the Checkout. This is used to verify the payment. |
| secret* | string   | your api secret as secret |

-------------------------------------------------------------------------------------------------------
### Verify subscription verification

```js
String secret = "EnLs21M47BllR3X8PSFtjtbd";

JSONObject options = new JSONObject();
options.put("razorpay_subscription_id", "sub_ID6MOhgkcoHj9I");
options.put("razorpay_payment_id", "pay_IDZNwZZFtnjyym");
options.put("razorpay_signature", "601f383334975c714c91a7d97dd723eb56520318355863dcf3821c0d07a17693");

boolean status =  Utils.verifySubscription(options, secret);
```

**Parameters:**


| Name  | Type      | Description                                      |
|-------|-----------|--------------------------------------------------|
| razorpay_subscription_id*  | string | The id of the subscription to be fetched  |
| razorpay_payment_id*    | string | The id of the payment to be fetched |
| razorpay_signature* | string   | Signature returned by the Checkout. This is used to verify the payment. |
| secret* | string   | your api secret as secret |

-------------------------------------------------------------------------------------------------------
### Verify paymentlink verification

```js
String secret = "EnLs21M47BllR3X8PSFtjtbd";

JSONObject options = new JSONObject();
options.put("payment_link_reference_id", "TSsd1989");
options.put("razorpay_payment_id", "pay_IH3d0ara9bSsjQ");
options.put("payment_link_status", "paid");
options.put("payment_link_id", "plink_IH3cNucfVEgV68");
options.put("razorpay_signature", "07ae18789e35093e51d0a491eb9922646f3f82773547e5b0f67ee3f2d3bf7d5b");

boolean status =  Utils.verifyPaymentLink(options, secret);
```

**Parameters:**


| Name  | Type      | Description                                      |
|-------|-----------|--------------------------------------------------|
| payment_link_id*  | string | The id of the paymentlink to be fetched  |
| razorpay_payment_id*  | string | The id of the payment to be fetched  |
| payment_link_reference_id*  | string |  A reference number tagged to a Payment Link |
| payment_link_status*  | string | Current status of the link  |
| razorpay_signature* | string   | Signature returned by the Checkout. This is used to verify the payment. |
| secret* | string   | your api secret as secret |

-------------------------------------------------------------------------------------------------------

**PN: * indicates mandatory fields**
<br>
<br>