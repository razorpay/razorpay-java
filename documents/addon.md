## Addons

### Create an addon

```java
String SubscriptionId = "sub_I55auG9GnbsR8u";
String json = "{\n" +
        "  item:{\n" +
        "    name:\"Extra appala (papadum)\",\n" +
        "    amount:30000,\n" +
        "    currency:\"INR\",\n" +
        "    description:\"1 extra oil fried appala with meals\"\n" +
        "  },\n" +
        "  quantity:2\n" +
        "}";

JSONObject request = new JSONObject(json);

Subscription subscription = instance.Subscriptions.createAddon(SubscriptionId,request);
```

**Parameters:**

| Name  | Type      | Description                                      |
|-------|-----------|--------------------------------------------------|
| SubscriptionId*  | boolean | The subscription ID to which the add-on is being added. |
| items*  | object | Details of the add-on you want to create. |
| quantity*  | integer | This specifies the number of units of the add-on to be charged to the customer. |

**Response:**
```json
{
  "id":"ao_00000000000001",
  "entity":"addon",
  "item":{
    "id":"item_00000000000001",
    "active":true,
    "name":"Extra appala (papadum)",
    "description":"1 extra oil fried appala with meals",
    "amount":30000,
    "unit_amount":30000,
    "currency":"INR",
    "type":"addon",
    "unit":null,
    "tax_inclusive":false,
    "hsn_code":null,
    "sac_code":null,
    "tax_rate":null,
    "tax_id":null,
    "tax_group_id":null,
    "created_at":1581597318,
    "updated_at":1581597318
  },
  "quantity":2,
  "created_at":1581597318,
  "subscription_id":"sub_00000000000001",
  "invoice_id":null
}
```
-------------------------------------------------------------------------------------------------------

### Fetch all addons

```java
String json = "{\n" +
                 "\"count\" : 1\n" +
               "}";

JSONObject options = new JSONObject(json);

List<Addon> addon = instance.Addons.fetchAll(options);
```

**Parameters:**

| Name  | Type      | Description                                      |
|-------|-----------|--------------------------------------------------|
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
      "id": "ao_00000000000002",
      "entity": "addon",
      "item": {
        "id": "item_00000000000002",
        "active": true,
        "name": "Extra sweet",
        "description": "1 extra sweet of the day with meals",
        "amount": 90000,
        "unit_amount": 90000,
        "currency": "INR",
        "type": "addon",
        "unit": null,
        "tax_inclusive": false,
        "hsn_code": null,
        "sac_code": null,
        "tax_rate": null,
        "tax_id": null,
        "tax_group_id": null,
        "created_at": 1581597318,
        "updated_at": 1581597318
      },
      "quantity": 1,
      "created_at": 1581597318,
      "subscription_id": "sub_00000000000001",
      "invoice_id": "inv_00000000000001"
    }
  ]
}
```
-------------------------------------------------------------------------------------------------------

### Fetch an addon

```java
String AddonId = "ao_00000000000001";

Addon addon = instance.Addons.fetch(AddonId);
```

**Parameters:**

| Name            | Type    | Description     |
|-----------------|---------|------------------------------------|
| AddonId*          | string | addon id to be fetched            |

**Response:**
```json
{
  "id":"ao_00000000000001",
  "entity":"addon",
  "item":{
    "id":"item_00000000000001",
    "active":true,
    "name":"Extra appala (papadum)",
    "description":"1 extra oil fried appala with meals",
    "amount":30000,
    "unit_amount":30000,
    "currency":"INR",
    "type":"addon",
    "unit":null,
    "tax_inclusive":false,
    "hsn_code":null,
    "sac_code":null,
    "tax_rate":null,
    "tax_id":null,
    "tax_group_id":null,
    "created_at":1581597318,
    "updated_at":1581597318
  },
  "quantity":2,
  "created_at":1581597318,
  "subscription_id":"sub_00000000000001",
  "invoice_id":null
}
```
-------------------------------------------------------------------------------------------------------

### Delete an addon

```java
String AddonId = "ao_00000000000001";

instance.Addons.delete(AddonId)
```

**Parameters:**

| Name            | Type    | Description                                                                  |
|-----------------|---------|------------------------------------------------------------------------------|
| AddonId*          | string | addon id to be deleted |

**Response:**
```json
[]
```
-------------------------------------------------------------------------------------------------------

**PN: * indicates mandatory fields**
<br>
<br>
**For reference click [here](https://razorpay.com/docs/api/subscriptions/#add-ons)**