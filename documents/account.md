## Account

### Create an Account
```java

JSONObject accountRequest = new JSONObject();
accountRequest.put("email","gauriagain.kumar@example.org");
accountRequest.put("phone","9000090000");
accountRequest.put("legal_business_name","Acme Corp");
accountRequest.put("business_type","partnership");
accountRequest.put("customer_facing_business_name","Example");
JSONObject profile = new JSONObject();
profile.put("category","healthcare");
profile.put("subcategory","clinic");
profile.put("description","Healthcare E-commerce platform");

JSONObject operation = new JSONObject();
operation.put("street1","507, Koramangala 6th block");
operation.put("street2","507, Koramangala");
operation.put("city","Bengaluru");
operation.put("state","Karnataka");
operation.put("postal_code","560047");
operation.put("country","IN");

JSONObject registered = new JSONObject();
registered.put("street1","507, Koramangala 1th block");
registered.put("street2","MG Road");
registered.put("city","Bengaluru");
registered.put("state","Karnataka");
registered.put("postal_code","560034");
registered.put("country","IN");

JSONObject addresses = new JSONObject();
addresses.put("operation",operation);
addresses.put("registered",registered);

profile.put("addresses",addresses);
profile.put("business_model","Online Clothing ( men, women, ethnic, modern ) fashion and lifestyle, accessories, t-shirt, shirt, track pant, shoes.");

JSONObject legalInfo = new JSONObject();
legalInfo.put("pan","AAACL1234C");
legalInfo.put("gst","18AABCU9603R1ZM");

accountRequest.put("profile",profile);
accountRequest.put("legal_info",legalInfo);

JSONObject brand = new JSONObject();
brand.put("color","FFFFFF");

accountRequest.put("brand",brand);

JSONObject notes = new JSONObject();
notes.put("internal_ref_id","123123");

accountRequest.put("notes",notes);
accountRequest.put("contact_name","Gaurav Kumar");

JSONObject contactInfo = new JSONObject();
JSONObject chargeback = new JSONObject();
chargeback.put("email","cb@example.org");

JSONObject refund = new JSONObject();
refund.put("email","cb@example.org");

JSONObject support = new JSONObject();
support.put("email","support@example.org");
support.put("phone","9999999998");
support.put("policy_url","https://www.google.com");

contactInfo.put("chargeback",chargeback);
contactInfo.put("refund",refund);
contactInfo.put("support",support);
accountRequest.put("contact_info",contactInfo);

JSONObject apps = new JSONObject();
ArrayList<String> url = new ArrayList<String>();
url.add("https://www.example.org");

apps.put("websites",url);
ArrayList<JSONObject> android = new ArrayList<JSONObject>();
JSONObject android_details = new JSONObject();
android_details.put("url","playstore.example.org");
android_details.put("name","Example");

apps.put("android",android);
android.add(android_details);
ArrayList<JSONObject> ios = new ArrayList<JSONObject>();
JSONObject ios_details = new JSONObject();
ios_details.put("url","appstore.example.org");
ios_details.put("name","Example");
ios.add(ios_details);
apps.put("android",android);
apps.put("ios",ios);

Account account = instance.account.create(accountRequest);
```

**Parameters:**

| Name          | Type        | Description                                 |
|---------------|-------------|---------------------------------------------|
| email*        | string      | The sub-merchant's business email address.  |
| phone*          | integer      | The sub-merchant's business phone number. The minimum length is 8 characters and the maximum length is 15.                       |
| legal_business_name*      | string | The name of the sub-merchant's business. For example, Acme Corp. The minimum length is 4 characters and the maximum length is 200.          |
| customer_facing_business_name | string | The sub-merchant billing label as it appears on the Razorpay Dashboard. The minimum length is 1 character and the maximum length is 255. |
| business_type         | string      | The type of business operated by the sub-merchant.Possible value is `proprietorship`, `partnership`, `private_limited`, `public_limited`, `llp`, `ngo`, `trust`, `society`, `not_yet_registered`, `huf` |
| reference_id         | string      |  Partner's external account reference id. The minimum length is 1 character and the maximum length is 512. |
| profile         | object      | All keys listed [here](https://razorpay.com/docs/api/partners/account-onboarding/#create-an-account) are supported |         
| legal_info         | object      | All keys listed [here](hhttps://razorpay.com/docs/api/partners/account-onboarding/#create-an-account) are supported |
| brand         | object      | All keys listed [here](https://razorpay.com/docs/api/partners/account-onboarding/#create-an-account) are supported |
| notes | object  | A key-value pair  |
| contact_name* | string  | The name of the contact. The minimum length is 4 and the maximum length is 255 characters. |
| contact_info | object  | All keys listed [here](https://razorpay.com/docs/api/partners/account-onboarding/#create-an-account) are supported |     
| apps | object  | All keys listed [here](https://razorpay.com/docs/api/partners/account-onboarding/#create-an-account) are supported |     


**Response:**
```json
{
  "id": "acc_GRWKk7qQsLnDjX",
  "type": "standard",
  "status": "created",
  "email": "gauriagain.kumar@example.org",
  "profile": {
    "category": "healthcare",
    "subcategory": "clinic",
    "addresses": {
      "registered": {
        "street1": "507, Koramangala 1st block",
        "street2": "MG Road",
        "city": "Bengaluru",
        "state": "KARNATAKA",
        "postal_code": 560034,
        "country": "IN"
      },
      "operation": {
        "street1": "507, Koramangala 6th block",
        "street2": "Kormanagalo",
        "city": "Bengaluru",
        "state": "KARNATAKA",
        "country": "IN",
        "postal_code": 560047
      }
    },
    "business_model": "Online Clothing ( men, women, ethnic, modern ) fashion and lifestyle, accessories, t-shirt, shirt, track pant, shoes."
  },
  "notes": {
    "internal_ref_id": "123123"
  },
  "created_at": 1611136837,
  "phone": "9000090000",
  "business_type": "partnership",
  "legal_business_name": "Acme Corp",
  "customer_facing_business_name": "Example",
  "legal_info": {
    "pan": "AAACL1234C",
    "gst": "18AABCU9603R1ZM"
  },
  "apps": {
    "websites": [
      "https://www.example.org"
    ],
    "android": [
      {
        "url": "playstore.example.org",
        "name": "Example"
      }
    ],
    "ios": [
      {
        "url": "appstore.example.org",
        "name": "Example"
      }
    ]
  },
  "brand": {
    "color": "#FFFFFF"
  },
  "contact_info": {
    "chargeback": {
      "email": "cb@example.org",
      "phone": null,
      "policy_url": null
    },
    "refund": {
      "email": "cb@example.org",
      "phone": null,
      "policy_url": null
    },
    "support": {
      "email": "support@example.org",
      "phone": "9999999998",
      "policy_url": "https://www.google.com"
    }
  }
}
```

-------------------------------------------------------------------------------------------------------

### Edit Account
```java
String accountId = "acc_GP4lfNA0iIMn5B";

JSONObject accountRequest = new JSONObject();
accountRequest.put("customer_facing_business_name","ABCD Ltd");

Account account = instance.account.edit(accountId,accountRequest);
```

**Parameters:**

| Name          | Type        | Description                                 |
|---------------|-------------|---------------------------------------------|
| phone          | integer      | The sub-merchant's business phone number. The minimum length is 8 characters and the maximum length is 15.                       |
| legal_business_name      | string | The name of the sub-merchant's business. For example, Acme Corp. The minimum length is 4 characters and the maximum length is 200.          |
| customer_facing_business_name | string | The sub-merchant billing label as it appears on the Razorpay Dashboard. The minimum length is 1 character and the maximum length is 255. |
| profile         | object      | All keys listed [here](https://razorpay.com/docs/api/partners/account-onboarding/#update-an-account) are supported |         
| legal_info         | object      | All keys listed [here](hhttps://razorpay.com/docs/api/partners/account-onboarding/#update-an-account) are supported |
| brand         | object      | All keys listed [here](https://razorpay.com/docs/api/partners/account-onboarding/#update-an-account) are supported |
| notes | object  | A key-value pair  |
| contact_name* | string  | The name of the contact. The minimum length is 4 and the maximum length is 255 characters. |
| contact_info | object  | All keys listed [here](https://razorpay.com/docs/api/partners/account-onboarding/#update-an-account) are supported |     
| apps | object  | All keys listed [here](https://razorpay.com/docs/api/partners/account-onboarding/#update-an-account) are supported |     

**Response:**
```json
{
  "id": "acc_GP4lfNA0iIMn5B",
  "type": "standard",
  "status": "created",
  "email": "gauri@example.org",
  "profile": {
    "category": "healthcare",
    "subcategory": "clinic",
    "addresses": {
      "registered": {
        "street1": "507, Koramangala 1st block",
        "street2": "MG Road-1",
        "city": "Bengalore",
        "state": "KARNATAKA",
        "postal_code": "560034",
        "country": "IN"
      }
    }
  },
  "notes": [],
  "created_at": 1610603081,
  "phone": "9000090000",
  "reference_id": "randomId",
  "business_type": "partnership",
  "legal_business_name": "Acme Corp",
  "customer_facing_business_name": "ABCD Ltd"
}
```
-------------------------------------------------------------------------------------------------------

### Delete an account
```java
String accountId = "acc_GP4lfNA0iIMn5B";

Account account = instance.account.delete(accountId);

```

**Parameters:**

| Name          | Type        | Description                                 |
|---------------|-------------|---------------------------------------------|
| accountId* | string   | The unique identifier of a sub-merchant account that must be deleted.  |

**Response:**
```json
{
  "id": "acc_GXQAkO2MrvBYg4",
  "type": "standard",
  "status": "suspended",
  "email": "gaurav.kumar@acme.org",
  "profile": {
    "category": "healthcare",
    "subcategory": "clinic",
    "addresses": {
      "registered": {
        "street1": "507, Koramangala 1st block",
        "street2": "MG Road",
        "city": "Bengaluru",
        "state": "KARNATAKA",
        "postal_code": "560034",
        "country": "IN"
      },
      "operation": {
        "street1": "507, Koramangala 1st block",
        "street2": "MG Road",
        "city": "Bengaluru",
        "state": "KARNATAKA",
        "country": "IN",
        "postal_code": "560034"
      }
    },
    "business_model": "Online Clothing ( men, women, ethnic, modern ) fashion and lifestyle, accessories, t-shirt, shirt, track pant, shoes."
  },
  "notes": {
    "internal_ref_id": "123123"
  },
  "created_at": 1612425180,
  "suspended_at": 1612425235,
  "phone": "9000090000",
  "reference_id": "account_COdeRandom",
  "business_type": "partnership",
  "legal_business_name": "Acme Corp Pvt Ltd",
  "customer_facing_business_name": "Acme",
  "legal_info": {
    "pan": "AAACL1234C",
    "gst": "18AABCU9603R1ZM"
  },
  "apps": {
    "websites": [
      "https://www.acme.org"
    ],
    "android": [
      {
        "url": "playstore.acme.org",
        "name": "Acme"
      }
    ],
    "ios": [
      {
        "url": "appstore.acme.org",
        "name": "Acme"
      }
    ]
  },
  "brand": {
    "color": "#FFFFFF"
  },
  "contact_name": "Gaurav Kumar",
  "contact_info": {
    "chargeback": {
      "email": "cb@acme.org",
      "phone": "9000090000",
      "policy_url": "https://www.google.com"
    },
    "refund": {
      "email": "cb@acme.org",
      "phone": "9898989898",
      "policy_url": "https://www.google.com"
    },
    "support": {
      "email": "support@acme.org",
      "phone": "9898989898",
      "policy_url": "https://www.google.com"
    }
  }
}
```

-------------------------------------------------------------------------------------------------------

### Fetch an account
```java
String accountId = "acc_GP4lfNA0iIMn5B";

Account account = instance.account.fetch(accountId);
```

**Parameters:**

| Name        | Type        | Description                                 |
|-------------|-------------|---------------------------------------------|
| accountId* | string      | The unique identifier of a sub-merchant account generated by Razorpay.  |

**Response:**
```json
{
  "id": "acc_GP4lfNA0iIMn5B",
  "type": "standard",
  "status": "created",
  "email": "gauri@example.org",
  "profile": {
    "category": "healthcare",
    "subcategory": "clinic",
    "addresses": {
      "registered": {
        "street1": "507, Koramangala 1st block",
        "street2": "MG Road-1",
        "city": "Bengalore",
        "state": "KARNATAKA",
        "postal_code": "560034",
        "country": "IN"
      }
    }
  },
  "notes": [],
  "created_at": 1610603081,
  "phone": "9000090000",
  "reference_id": "randomId",
  "business_type": "partnership",
  "legal_business_name": "Acme Corp",
  "customer_facing_business_name": "Example Pvt. Ltd."
}
```

-------------------------------------------------------------------------------------------------------

### Upload account documents
```java
String accountId = "acc_M83Uw27KXuC7c8";

JSONObject request = new JSONObject();
request.put("files","/Users/your_name/Downloads/sample_uploaded.jpeg");
request.put("document_type","business_proof_url");

Account account = instance.account.uploadAccountDoc(accountId, request);
```

**Parameters:**

| Name           | Type        | Description                                                                                                                                                                                                                                                                                                           |
|----------------|-------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| accountId*     | string      | The unique identifier of a sub-merchant account generated by Razorpay.                                                                                                                                                                                                                                                |
| file*          | string      | The URL generated once the business proof document is uploaded.                                                                                                                                                                                                                                                       |
| document_type* | string      | The documents valid for the proof type to be shared. Possible values : <br> business_proof_of_identification: `shop_establishment_certificate`, `gst_certificate`, `msme_certificate`, `business_proof_url`, `business_pan_url`, <br><br> additional_documents : `form_12_a_url`, `form_80g_url`, `cancelled_cheque`  |

**Response:**
```json
{
  "business_proof_of_identification": [
    {
      "type": "business_proof_url",
      "url": "<https://rzp.io/i/bzDKbNg>"
    }
  ]
}
```
-------------------------------------------------------------------------------------------------------

### Fetch account documents
```java
String accountId = "acc_LryDIBIjBDbOWy";

Account account = instance.account.fetchAccountDoc(accountId);
```

**Parameters:**

| Name        | Type        | Description                                                            |
|-------------|-------------|------------------------------------------------------------------------|
| accountId*  | string      | The unique identifier of a sub-merchant account generated by Razorpay. |

**Response:**
```json
{
  "business_proof_of_identification": [
    {
      "type": "business_proof_url",
      "url": "<https://rzp.io/i/bzDKbNg>"
    }
  ]
}
```
-------------------------------------------------------------------------------------------------------
**PN: * indicates mandatory fields**
<br>
<br>
**For reference click [here](https://razorpay.com/docs/api/partners/account-onboarding/)**