## Customer

### Create customer
```java
String jsonRequest = "{\n" +
        "\"name\": \"Gaurav Kumar\",\n" +
        "\"contact\": 9123456780,\n" +
        "\"email\": \"gaurav.kumar@example.com\",\n" +
        "\"fail_existing\": 0,\n" +
        "\"gstin: \"29XAbbA4369J1PA\",\n" +
        "  notes: {\n" +
        "    notes_key_1: \"Tea, Earl Grey, Hot\",\n" +
        "    notes_key_2: \"Tea, Earl Grey… decaf.\"\n" +
        "  }\n" +
        "}";

JSONObject requestJson = new JSONObject(jsonRequest);

Customer customer = instance.customers.create(request);
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
  "id" : "cust_1Aa00000000004",
  "entity": "customer",
  "name" : "Gaurav Kumar",
  "email" : "gaurav.kumar@example.com",
  "contact" : "9123456780",
  "gstin": "29XAbbA4369J1PA",
  "notes":{
    "notes_key_1":"Tea, Earl Grey, Hot",
    "notes_key_2":"Tea, Earl Grey… decaf."
  },
  "created_at ": 1234567890
}
```

-------------------------------------------------------------------------------------------------------

### Edit customer
```java
String customerId = "cust_1Aa00000000003"; 

String jsonRequest = "{\n" +
        "\"name\": \"Gaurav Kumar\",\n" +
        "\"email\": \"Gaurav.Kumar@example.com\",\n" +
        "\"contact\": 9000000000\n" +
        "}";

JSONObject requestJson = new JSONObject(requestJson);

Customer customer = instance.customers.edit(customerId,request);
```

**Parameters:**

| Name        | Type        | Description                                 |
|-------------|-------------|---------------------------------------------|
| CustomerId* | string      | The id of the customer to be updated  |
| email       | string      | Email of the customer                       |
| name        | string      | Name of the customer                        |
| contact     | string      | Contact number of the customer              |

**Response:**
```json
{
  "id": "cust_1Aa00000000003",
  "entity": "customer",
  "name": "Gaurav Kumar",
  "email": "Gaurav.Kumar@example.com",
  "contact": "9000000000",
  "gstin": null,
  "notes": {
    "notes_key_1": "Tea, Earl Grey, Hot",
    "notes_key_2": "Tea, Earl Grey… decaf."
  },
  "created_at": 1582033731
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