## items

### Create item

```java
JSONObject itemRequest = new JSONObject();
itemRequest.put("name","Book / English August");
itemRequest.put("description","An indian story, Booker prize winner.");
itemRequest.put("amount", 20000);
itemRequest.put("currency","INR");

Item item = instance.items.create(itemRequest);
```

**Parameters:**

| Name        | Type    | Description                                                                  |
|-------------|---------|------------------------------------------------------------------------------|
| name*       | string | Name of the item.                    |
| description | string  | A brief description of the item.  |
| amount*     | integer  | Amount of the order to be paid     |
| currency*   | string  | Currency of the order. Currently only `INR` is supported.    |

**Response:**
```json
{
    "id": "item_JnjKnSWxjILdWu",
    "active": true,
    "name": "Book / English August",
    "description": "An indian story, Booker prize winner.",
    "amount": 20000,
    "unit_amount": 20000,
    "currency": "INR",
    "type": "invoice",
    "unit": null,
    "tax_inclusive": false,
    "hsn_code": null,
    "sac_code": null,
    "tax_rate": null,
    "tax_id": null,
    "tax_group_id": null,
    "created_at": 1656597363
}
```

-------------------------------------------------------------------------------------------------------

### Fetch all items

```java
JSONObject params = new JSONObject();
params.put("count","1");

List<Item> item = instance.items.fetchAll(params);
```
**Parameters:**

| Name  | Type      | Description                                      |
|-------|-----------|--------------------------------------------------|
| from  | timestamp | timestamp after which the item were created  |
| to    | timestamp | timestamp before which the item were created |
| count | integer   | number of item to fetch (default: 10)        |
| skip  | integer   | number of item to be skipped (default: 0)    |
| active   | boolean  | Possible values is `0` or `1` |

**Response:**
```json
{
    "entity": "collection",
    "count": 1, 
    "items": [
        {
            "id": "item_JnjKnSWxjILdWu",
            "active": true,
            "name": "Book / English August",
            "description": "An indian story, Booker prize winner.",
            "amount": 20000,
            "unit_amount": 20000,
            "currency": "INR",
            "type": "invoice",
            "unit": null,
            "tax_inclusive": false,
            "hsn_code": null,
            "sac_code": null,
            "tax_rate": null,
            "tax_id": null,
            "tax_group_id": null,
            "created_at": 1656597363
        }
    ]
}

```
-------------------------------------------------------------------------------------------------------
### Fetch particular item

```java
String itemId = "item_7Oxp4hmm6T4SCn";

Item item = instance.items.fetch(itemId);
```
**Parameters**

| Name    | Type   | Description                         |
|---------|--------|-------------------------------------|
| itemId* | string | The id of the item to be fetched |

**Response:**
```json
{
    "id": "item_JnjKnSWxjILdWu",
    "active": true,
    "name": "Book / English August",
    "description": "An indian story, Booker prize winner.",
    "amount": 20000,
    "unit_amount": 20000,
    "currency": "INR",
    "type": "invoice",
    "unit": null,
    "tax_inclusive": false,
    "hsn_code": null,
    "sac_code": null,
    "tax_rate": null,
    "tax_id": null,
    "tax_group_id": null,
    "created_at": 1656597363
}
```

-------------------------------------------------------------------------------------------------------

### Update item

```java
String itemId = "item_7Oy8OMV6BdEAac";

JSONObject itemRequest = new JSONObject();
itemRequest.put("name","Book / Ignited Minds - Updated name!");
itemRequest.put("description","An indian story, Booker prize winner.");
itemRequest.put("amount", 20000);
itemRequest.put("currency","INR");
itemRequest.put("active",true);
              
Item item = instance.items.edit(itemId, itemRequest);
```
**Parameters**

| Name        | Type   | Description                         |
|-------------|--------|-------------------------------------|
| ItemId*     | string | The id of the item to be fetched |
| name        | string | Name of the item.                    |
| description | string  | A brief description of the item.  |
| amount      | integer  | Amount of the order to be paid     |
| currency    | string  | Currency of the order. Currently only `INR` is supported.    |
| active      | boolean  | Possible values is `0` or `1` |

**Response:**

```json
{
    "id": "item_JnjKnSWxjILdWu",
    "active": true,
    "name": "Book / Ignited Minds - Updated name!",
    "description": "New descirption too.",
    "amount": 20000,
    "unit_amount": 20000,
    "currency": "INR",
    "type": "invoice",
    "unit": null,
    "tax_inclusive": false,
    "hsn_code": null,
    "sac_code": null,
    "tax_rate": null,
    "tax_id": null,
    "tax_group_id": null,
    "created_at": 1656597363
}
```
-------------------------------------------------------------------------------------------------------
### Delete item

```java
String itemId = "item_7Oy8OMV6BdEAac";

List<Item> item  = instance.items.delete(itemId)
```
**Parameters**

| Name     | Type   | Description                         |
|----------|--------|-------------------------------------|
| itemId* | string | The id of the item to be fetched |

**Response:**

```json
[]
```
-------------------------------------------------------------------------------------------------------

**PN: * indicates mandatory fields**
<br>
<br>
**For reference click [here](https://razorpay.com/docs/api/items)**