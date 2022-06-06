## Contact

### Create Contact
```java
String jsonRequest = "{\n" +
        "\"name\": \"Gaurav Kumar\",\n" +
        "\"contact\": 9123456780,\n" +
        "\"email\": \"gaurav.kumar@example.com\",\n" +
        "\"type\": \"employee\",\n" +
        "\"reference_id\": Acme Contact ID 12345,\n" +
        "  notes: {\n" +
        "    notes_key_1: \"Tea, Earl Grey, Hot\",\n" +
        "    notes_key_2: \"Tea, Earl Grey… decaf.\"\n" +
        "  }\n" +
        "}";

JSONObject requestJson = new JSONObject(jsonRequest);

Contact contact = instance.contacts.create(request);
```

**Parameters:**

| Name          | Type        | Description                                 |
|---------------|-------------|---------------------------------------------|
| name*          | string      | Name of the customer                        |
| email        | string      | Email of the customer                       |
| contact      | string      | Contact number of the customer              |
| notes         | object      | A key-value pair                            |

**Response:**
```json
{
  "id": "cont_00000000000001",
  "entity": "contact",
  "name": "Gaurav Kumar",
  "contact": "9123456789",
  "email": "gaurav.kumar@example.com",
  "type": "self",
  "reference_id": "Acme Contact ID 12345",
  "batch_id": null,
  "active": true,
  "notes": {
    "notes_key_1":"Tea, Earl Grey, Hot",
    "notes_key_2":"Tea, Earl Grey… decaf."
  },
  "created_at": 1545320320
}
```

-------------------------------------------------------------------------------------------------------

### Update a contact
```java
String contactId = "cont_00000000000001"; 

String jsonRequest = "{\n" +
        "\"name\": \"Gaurav Kumar\",\n" +
        "\"email\": \"Gaurav.Kumar@example.com\",\n" +
        "\"contact\": 9123456789\n" +
        "\"type\": \"self\"\n" +
        "\"reference_id\": \"Acme Contact ID 12345\"\n" +
        "  notes: {\n" +
        "    notes_key_1: \"Tea, Earl Grey, Hot\",\n" +
        "    notes_key_2: \"Tea, Earl Grey… decaf.\"\n" +
        "  }\n" +
        "}";

JSONObject requestJson = new JSONObject(requestJson);

Contact contact = instance.contacts.edit(contact_Id,request);
```

**Parameters:**

| Name       | Type        | Description                         |
|------------|-------------|-------------------------------------|
| ContactId* | string      | The id of the contact to be updated |
| email      | string      | Email of the contact                |
| name       | string      | Name of the contact                 |
| contact    | string      | Contact number of the contact       |

**Response:**
```json
{
  "id": "cont_00000000000001",
  "entity": "contact",
  "name": "Gaurav Kumar",
  "contact": "9123456789",
  "email": "gaurav.kumar@example.com",
  "type": "self",
  "reference_id": "Acme Contact ID 12345",
  "batch_id": null,
  "active": true,
  "notes": {
    "notes_key_1":"Tea, Earl Grey, Hot",
    "notes_key_2":"Tea, Earl Grey… decaf."
  },
  "created_at": 1545320320
}

```
-------------------------------------------------------------------------------------------------------

### Fetch all customer
```java
String jsonRequest = "{\n" +
                       "\"count\" : 1\n" +
                      "}";

JSONObject requestJson = new JSONObject(jsonRequest);

List<Customer> customer = instance.customers.fetchAll(requestJson);
```

**Parameters:**

| Name          | Type        | Description                                 |
|---------------|-------------|---------------------------------------------|
| count | integer   | number of payments to fetch (default: 10)        |
| skip  | integer   | number of payments to be skipped (default: 0)    |

**Response:**
```json
{
  "entity":"collection",
  "count":1,
  "items":[
    {
      "id":"cust_1Aa00000000001",
      "entity":"customer",
      "name":"Gaurav Kumar",
      "email":"gaurav.kumar@example.com",
      "contact":"9876543210",
      "gstin":"29XAbbA4369J1PA",
      "notes":{
        "note_key_1":"September",
        "note_key_2":"Make it so."
      },
      "created_at ":1234567890
    }
  ]
}
```

-------------------------------------------------------------------------------------------------------

### Fetch a customer
```java
String customerId = "cust_1Aa00000000001";

Customer customer = instance.customers.fetch(customerId);
```

**Parameters:**

| Name        | Type        | Description                                 |
|-------------|-------------|---------------------------------------------|
| customerId* | string      | The id of the customer to be fetched  |

**Response:**
```json
{
  "id" : "cust_1Aa00000000001",
  "entity": "customer",
  "name" : "Saurav Kumar",
  "email" : "Saurav.kumar@example.com",
  "contact" : "+919000000000",
  "gstin":"29XAbbA4369J1PA",
  "notes" : [],
  "created_at ": 1234567890
}
```

-------------------------------------------------------------------------------------------------------

**PN: * indicates mandatory fields**
<br>
<br>
**For reference click [here](https://razorpay.com/docs/api/customers/)**