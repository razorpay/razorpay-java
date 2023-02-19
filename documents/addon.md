## Addons

### Create an addon

#### Old Method
```java
        
String subscriptionId = "sub_I55auG9GnbsR8u";

JSONObject addonRequest = new JSONObject();
JSONObject items = new JSONObject();
items.put("name","Extra appala (papadum)");
items.put("amount","3000");
items.put("currency","INR");
items.put("description","1 extra oil fried appala with meals");
addonRequest.put("item",items);
addonRequest.put("quantity",2);

Addon addon = instance.subscriptions.createAddon(subscriptionId,addonRequest);
```
#### New Method

```java
Addon addon = Addon.builder()
                .items(
                    AddOnItem.builder()
                    .name("Test")
                    .amount(3000)
                    .currency("INR")
                    .description("Test description")
                    .build()
                )
                .quantity(2)
                .build();

        if(addon.validateAddOn()){
            Addon createdAddOn = instance.subscriptions.createAddon(subscriptionId,addonRequest.toJson());
        }
        
```
**Parameters:**

| Name            | Type      | Description                                      |
|-----------------|-----------|--------------------------------------------------|
| subscriptionId* | boolean | The subscription ID to which the add-on is being added. |
| items*          | object | Details of the add-on you want to create. |
| quantity*       | integer | This specifies the number of units of the add-on to be charged to the customer. |

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
JSONObject params = new JSONObject();
params.put("count","1");

List<Addon> addon = instance.addons.fetchAll(params);
```

**Parameters:**

| Name  | Type      | Description                                      |
|-------|-----------|---------------------------------------------------|
| from  |  timestamp | timestamp after which the payments were created  |
| to    |  timestamp | timestamp before which the payments were created |
| count |  integer   | number of payments to fetch (default: 10)        |
| skip  |  integer   | number of payments to be skipped (default: 0)    |

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
String addonId = "ao_00000000000001";

Addon addon = instance.addons.fetch(addonId);
```

**Parameters:**

| Name     | Type    | Description     |
|----------|---------|------------------------------------|
| addonId* | string | addon id to be fetched            |

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
String addonId = "ao_00000000000001";

List<Addon> addon = instance.addons.delete(addonId);
```

**Parameters:**

| Name     | Type    | Description                                                                  |
|----------|---------|------------------------------------------------------------------------------|
| addonId* | string | addon id to be deleted |

**Response:**
```json
[]
```
-------------------------------------------------------------------------------------------------------

**PN: * indicates mandatory fields**
<br>
<br>
**For reference click [here](https://razorpay.com/docs/api/subscriptions/#add-ons)**