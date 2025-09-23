# Subscriptions

<details>
<summary>Relevant source files</summary>

The following files were used as context for generating this wiki page:

- [src/main/java/com/razorpay/Subscription.java](src/main/java/com/razorpay/Subscription.java)
- [src/main/java/com/razorpay/SubscriptionClient.java](src/main/java/com/razorpay/SubscriptionClient.java)

</details>



## Purpose and Scope

This document covers the subscription management functionality in the Razorpay Java SDK, including subscription lifecycle operations, cancellation, and addon management. Subscriptions enable recurring billing scenarios where customers are charged automatically at regular intervals.

For information about creating and managing plans that define subscription templates, see [Plans](#7.1). For detailed addon operations beyond subscription context, see [Addons](#7.3).

## Overview

The subscription system in the Razorpay Java SDK provides a complete solution for managing recurring billing scenarios. Subscriptions are built on top of plans and can be customized with addons for additional charges.

### Core Components

| Component | Purpose | File Location |
|-----------|---------|---------------|
| `SubscriptionClient` | Main interface for subscription operations | [src/main/java/com/razorpay/SubscriptionClient.java]() |
| `Subscription` | Data model representing subscription entities | [src/main/java/com/razorpay/Subscription.java]() |

### Subscription Lifecycle States

```mermaid
stateDiagram-v2
    [*] --> created : "SubscriptionClient.create()"
    created --> active : "Customer payment successful"
    created --> cancelled : "SubscriptionClient.cancel()"
    active --> cancelled : "SubscriptionClient.cancel()"
    active --> halted : "Payment failure / Insufficient funds"
    halted --> active : "Payment retry successful"
    halted --> cancelled : "Max retry attempts exceeded"
    cancelled --> [*]
```

**Sources:** [src/main/java/com/razorpay/SubscriptionClient.java:13-36]()

## SubscriptionClient Operations

The `SubscriptionClient` class extends `ApiClient` and provides methods for complete subscription lifecycle management.

### Architecture Overview

```mermaid
graph TB
    subgraph "Client Layer"
        RC["RazorpayClient"]
        SC["SubscriptionClient"]
    end
    
    subgraph "HTTP Layer"
        AC["ApiClient"]
        AU["ApiUtils"]
    end
    
    subgraph "Data Models"
        SUB["Subscription"]
        ADDON["Addon"]
        ENT["Entity"]
    end
    
    subgraph "API Endpoints"
        CREATE["Constants.SUBSCRIPTION_CREATE"]
        GET["Constants.SUBSCRIPTION_GET"]
        LIST["Constants.SUBSCRIPTION_LIST"]
        CANCEL["Constants.SUBSCRIPTION_CANCEL"]
        ADDON_CREATE["Constants.SUBSCRIPTION_ADDON_CREATE"]
    end
    
    RC --> SC
    SC --> AC
    AC --> AU
    SC --> CREATE
    SC --> GET
    SC --> LIST
    SC --> CANCEL
    SC --> ADDON_CREATE
    SC --> SUB
    SC --> ADDON
    SUB --> ENT
    ADDON --> ENT
```

**Sources:** [src/main/java/com/razorpay/SubscriptionClient.java:7-11]()

### Core Operations

#### Subscription Creation

The `create` method accepts a `JSONObject` containing subscription parameters and returns a `Subscription` entity.

```mermaid
sequenceDiagram
    participant APP as "Application"
    participant SC as "SubscriptionClient"
    participant AC as "ApiClient"
    participant API as "Razorpay API"
    
    APP->>SC: "create(JSONObject request)"
    SC->>AC: "post(Constants.SUBSCRIPTION_CREATE, request)"
    AC->>API: "POST /v1/subscriptions"
    API-->>AC: "JSON Response"
    AC->>AC: "new Subscription(jsonObject)"
    AC-->>SC: "Subscription entity"
    SC-->>APP: "Subscription entity"
```

**Sources:** [src/main/java/com/razorpay/SubscriptionClient.java:13-15]()

#### Subscription Retrieval

Two retrieval patterns are supported:

| Method | Purpose | API Endpoint Reference |
|--------|---------|----------------------|
| `fetch(String id)` | Retrieve single subscription | `Constants.SUBSCRIPTION_GET` |
| `fetchAll()` | Retrieve all subscriptions | `Constants.SUBSCRIPTION_LIST` |
| `fetchAll(JSONObject request)` | Retrieve filtered subscriptions | `Constants.SUBSCRIPTION_LIST` |

**Sources:** [src/main/java/com/razorpay/SubscriptionClient.java:17-27]()

#### Subscription Cancellation

The `cancel` method terminates an active subscription by posting to the cancellation endpoint.

```mermaid
graph LR
    APP["Application"] --> SC["SubscriptionClient.cancel()"]
    SC --> API["POST Constants.SUBSCRIPTION_CANCEL"]
    API --> SUB["Updated Subscription Entity"]
    SUB --> APP
```

**Sources:** [src/main/java/com/razorpay/SubscriptionClient.java:29-31]()

#### Addon Management

Subscriptions support dynamic addon creation through the `createAddon` method, which allows adding extra charges to existing subscriptions.

```mermaid
flowchart TD
    START["Existing Subscription"] --> ADDON_REQ["JSONObject addon request"]
    ADDON_REQ --> CREATE_ADDON["SubscriptionClient.createAddon(id, request)"]
    CREATE_ADDON --> POST_API["POST Constants.SUBSCRIPTION_ADDON_CREATE"]
    POST_API --> ADDON_ENTITY["Addon Entity"]
    ADDON_ENTITY --> END["Addon attached to subscription"]
```

**Sources:** [src/main/java/com/razorpay/SubscriptionClient.java:33-35]()

## Data Model Structure

The `Subscription` class follows the standard entity pattern used throughout the SDK.

### Inheritance Hierarchy

```mermaid
classDiagram
    class Entity {
        -JSONObject modelJson
        +get(key) Object
        +has(key) boolean
        +toJson() JSONObject
    }
    
    class Subscription {
        +Subscription(JSONObject jsonObject)
    }
    
    Entity <|-- Subscription
```

**Sources:** [src/main/java/com/razorpay/Subscription.java:5-10]()

## Integration Patterns

### Complete Subscription Workflow

```mermaid
graph TD
    subgraph "Prerequisites"
        PLAN["Plan Entity<br/>(from PlanClient)"]
        CUSTOMER["Customer Entity<br/>(from CustomerClient)"]
    end
    
    subgraph "Subscription Lifecycle"
        CREATE["SubscriptionClient.create()"]
        FETCH["SubscriptionClient.fetch()"]
        ADDON["SubscriptionClient.createAddon()"]
        CANCEL["SubscriptionClient.cancel()"]
    end
    
    subgraph "Result Entities"
        SUB_ENT["Subscription Entity"]
        ADDON_ENT["Addon Entity"]
    end
    
    PLAN --> CREATE
    CUSTOMER --> CREATE
    CREATE --> SUB_ENT
    SUB_ENT --> FETCH
    SUB_ENT --> ADDON
    SUB_ENT --> CANCEL
    ADDON --> ADDON_ENT
```

**Sources:** [src/main/java/com/razorpay/SubscriptionClient.java:1-36](), [src/main/java/com/razorpay/Subscription.java:1-11]()

### Error Handling

All `SubscriptionClient` methods can throw `RazorpayException` for API-related errors. The exception handling follows the standard pattern used across all SDK clients.

| Operation | Common Error Scenarios |
|-----------|----------------------|
| `create()` | Invalid plan ID, missing customer details |
| `fetch()` | Subscription not found, invalid ID format |
| `cancel()` | Subscription already cancelled, invalid state |
| `createAddon()` | Subscription not active, invalid addon parameters |

**Sources:** [src/main/java/com/razorpay/SubscriptionClient.java:13-35]()
