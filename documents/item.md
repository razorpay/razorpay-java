## items

### Create item

```java
String jsonRequest = "{\n" +
          "\"name\": \"Book / English August\",\n" +
          "\"description\": \"An indian story, Booker prize winner.\",\n" +
          "\"amount\": 20000,\n" +
          "\"currency\": \"INR\"\n" +
        "}";

JSONObject requestJson = new JSONObject(jsonRequest);

Item item = instance.items.create(requestJson);
```

**Parameters:**

| Name            | Type    | Description                                                                  |
|-----------------|---------|------------------------------------------------------------------------------|
| name*          | string | Name of the item.                    |
| description        | string  | A brief description of the item.  |
| amount         | integer  | Amount of the order to be paid     |
| currency           | string  | Currency of the order. Currently only `INR` is supported.    |

**Response:**
```json
{
  "id": "item_7Oxp4hmm6T4SCn",
  "active": true,
  "name": "Book / English August",
  "description": "An indian story, Booker prize winner.",
  "amount": 20000,
  "currency": "INR"
}
```

-------------------------------------------------------------------------------------------------------

### Fetch all items

```java
String jsonRequest = "{\n" +
                       "\"count\" : 1\n" +
                      "}";

JSONObject requestJson = new JSONObject(jsonRequest);

List<Item> item = instance.items.fetchAll(requestJson);
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
  "count": 3,
  "items": [
    {
      "id": "item_7Oy8OMV6BdEAac",
      "active": true,
      "name": "Book / Ignited Minds",
      "description": null,
      "amount": 15000,
      "currency": "INR"
    },
    {
      "id": "item_7Oxp4hmm6T4SCn",
      "active": true,
      "name": "Book / English August",
      "description": "An indian story, Booker prize winner.",
      "amount": 20000,
      "currency": "INR"
    },
    {
      "id": "item_7OxoGnoxCuUKbo",
      "active": true,
      "name": "Book / English August",
      "description": null,
      "amount": 20000,
      "currency": "INR"
    }
  ]
}
```
-------------------------------------------------------------------------------------------------------
### Fetch particular item

```java
String itemId = "item_7Oxp4hmm6T4SCn";

Item item = instance.items.fetch(itemId)
```
**Parameters**

| Name    | Type   | Description                         |
|---------|--------|-------------------------------------|
| itemId* | string | The id of the item to be fetched |

**Response:**
```json
{
  "id": "item_7Oxp4hmm6T4SCn",
  "active": true,
  "name": "Book / English August",
  "description": "An indian story, Booker prize winner.",
  "amount": 20000,
  "currency": "INR"
}
```

-------------------------------------------------------------------------------------------------------

### Update item

```java
String itemId = "item_7Oy8OMV6BdEAac";

String jsonRequest = "{\n" +
              "  \"name\": \"Book / Ignited Minds - Updated name!\",\n" +
              "  \"description\": \"New descirption too. :).\",\n" +
              "  \"amount\": 20000,\n" +
              "  \"currency\": \"INR\",\n" +
              "  \"active\": true\n" +
              "}";
              
JSONObject requestJson = new JSONObject(jsonRequest);
              
Item item = instance.items.edit(itemId, requestJson);
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
  "id": "item_7Oy8OMV6BdEAac",
  "active": true,
  "name": "Book / Ignited Minds - Updated name!",
  "description": "New descirption too. :)",
  "amount": 15000,
  "currency": "INR"
}
```
-------------------------------------------------------------------------------------------------------
### Delete item

```java
String itemId = "item_7Oy8OMV6BdEAac";

JSONObject item  = instance.items.delete(itemId)
```
**Parameters**

| Name     | Type   | Description                         |
|----------|--------|-------------------------------------|
| itemId* | string | The id of the item to be fetched |

**Response:**

```json
{}
```
-------------------------------------------------------------------------------------------------------

**PN: * indicates mandatory fields**
<br>
<br>
**For reference click [here](https://razorpay.com/docs/api/items)**