# Additional Features

<details>
<summary>Relevant source files</summary>

The following files were used as context for generating this wiki page:

- [src/main/java/com/razorpay/InvoiceClient.java](src/main/java/com/razorpay/InvoiceClient.java)
- [src/main/java/com/razorpay/Reversal.java](src/main/java/com/razorpay/Reversal.java)
- [src/main/java/com/razorpay/Transfer.java](src/main/java/com/razorpay/Transfer.java)
- [src/main/java/com/razorpay/TransferClient.java](src/main/java/com/razorpay/TransferClient.java)

</details>



## Purpose and Scope

This document covers the less commonly used but important features of the Razorpay Java SDK, specifically focusing on transfers, invoices, and bank transfers. These features enable advanced payment scenarios including marketplace operations, billing management, and alternative payment processing workflows.

For core payment operations, see [Payment Operations](#4). For subscription-based billing, see [Subscription & Billing](#7). For customer and account management features, see [Customer & Account Management](#6).

## Overview

The Razorpay Java SDK provides specialized clients for handling advanced payment scenarios beyond basic payment processing. These additional features are accessed through dedicated client classes that follow the same architectural patterns as the core payment operations.

```mermaid
graph TB
    subgraph "RazorpayClient"
        RC["RazorpayClient"]
    end
    
    subgraph "Additional Feature Clients"
        TC["TransferClient"]
        IC["InvoiceClient"]
        BTC["BankTransferClient"]
    end
    
    subgraph "Core Infrastructure"
        AC["ApiClient"]
        AU["ApiUtils"]
    end
    
    subgraph "Entity Models"
        TE["Transfer"]
        RE["Reversal"]
        IE["Invoice"]
        BTE["BankTransfer"]
    end
    
    RC --> TC
    RC --> IC
    RC --> BTC
    
    TC --> AC
    IC --> AC
    BTC --> AC
    
    AC --> AU
    
    TC --> TE
    TC --> RE
    IC --> IE
    BTC --> BTE
```

**Additional Features in SDK Architecture**

Sources: [src/main/java/com/razorpay/TransferClient.java:1-36](), [src/main/java/com/razorpay/InvoiceClient.java:1-32]()

## Transfers

The `TransferClient` enables marketplace scenarios where payments need to be distributed to multiple parties. Transfers allow splitting payment amounts between the main merchant account and linked accounts, with support for editing and reversal operations.

### Transfer Operations

The `TransferClient` provides comprehensive transfer management capabilities:

| Operation | Method | Purpose |
|-----------|--------|---------|
| Create | `create(JSONObject)` | Create a new transfer |
| Edit | `edit(String id, JSONObject)` | Modify an existing transfer |
| Fetch | `fetch(String id)` | Retrieve a specific transfer |
| List | `fetchAll()` / `fetchAll(JSONObject)` | Retrieve multiple transfers |
| Reverse | `reversal(String id, JSONObject)` | Create a reversal for a transfer |

```mermaid
sequenceDiagram
    participant App as "Application"
    participant RC as "RazorpayClient"
    participant TC as "TransferClient"
    participant AC as "ApiClient"
    participant API as "Razorpay API"
    
    App->>RC: "razorpayClient.Transfers"
    RC->>TC: "new TransferClient(auth)"
    
    App->>TC: "create(transferData)"
    TC->>AC: "post(TRANSFER_CREATE, request)"
    AC->>API: "POST /v1/transfers"
    API-->>AC: "Transfer JSON"
    AC-->>TC: "Transfer entity"
    TC-->>App: "Transfer object"
    
    App->>TC: "reversal(transferId, reversalData)"
    TC->>AC: "post(TRANSFER_REVERSAL_CREATE, request)"
    AC->>API: "POST /v1/transfers/{id}/reversals"
    API-->>AC: "Reversal JSON"
    AC-->>TC: "Reversal entity"
    TC-->>App: "Reversal object"
```

**Transfer Operation Flow**

### Transfer and Reversal Entities

Both `Transfer` and `Reversal` entities extend the base `Entity` class, providing JSON-based data access through the inherited methods:

- `Transfer` represents a payment distribution to a linked account
- `Reversal` represents the reversal of a previously created transfer

```mermaid
classDiagram
    class Entity {
        <<abstract>>
        +get(key) Object
        +has(key) boolean
        +toJson() JSONObject
    }
    
    class Transfer {
        +Transfer(JSONObject)
    }
    
    class Reversal {
        +Reversal(JSONObject)
    }
    
    Entity <|-- Transfer
    Entity <|-- Reversal
    
    note for Transfer "Created via TransferClient.create()"
    note for Reversal "Created via TransferClient.reversal()"
```

**Transfer Entity Relationships**

Sources: [src/main/java/com/razorpay/TransferClient.java:13-35](), [src/main/java/com/razorpay/Transfer.java:1-10](), [src/main/java/com/razorpay/Reversal.java:1-10]()

## Invoices

The `InvoiceClient` provides invoice management capabilities for billing scenarios where formal invoice generation and tracking is required. This is distinct from the subscription billing system and focuses on one-time or ad-hoc billing needs.

### Invoice Operations

The `InvoiceClient` supports the complete invoice lifecycle:

| Operation | Method | API Endpoint Constant | Purpose |
|-----------|--------|----------------------|---------|
| Create | `create(JSONObject)` | `INVOICE_CREATE` | Generate a new invoice |
| Fetch | `fetch(String id)` | `INVOICE_GET` | Retrieve a specific invoice |
| List | `fetchAll()` / `fetchAll(JSONObject)` | `INVOICE_LIST` | Retrieve multiple invoices |
| Cancel | `cancel(String id)` | `INVOICE_CANCEL` | Cancel an existing invoice |

```mermaid
stateDiagram-v2
    [*] --> Created : "InvoiceClient.create()"
    Created --> Sent : "Invoice sent to customer"
    Created --> Cancelled : "InvoiceClient.cancel()"
    Sent --> Paid : "Customer payment"
    Sent --> Cancelled : "InvoiceClient.cancel()"
    Paid --> [*]
    Cancelled --> [*]
    
    note right of Created : "fetchAll() and fetch() available at all states"
```

**Invoice State Management**

### Invoice Entity

The `Invoice` entity follows the same pattern as other SDK entities, extending `Entity` to provide JSON-based data access. Invoice data includes details such as amount, customer information, due dates, and payment status.

Sources: [src/main/java/com/razorpay/InvoiceClient.java:13-31]()

## Bank Transfers

Bank transfer functionality provides access to bank transfer data and processing capabilities. This feature will be covered in detail in [Bank Transfers](#8.3), including data retrieval and processing workflows for bank-based payment methods.

## Integration Patterns

All additional features follow consistent integration patterns within the SDK:

```mermaid
graph LR
    subgraph "Client Pattern"
        CLIENT["FeatureClient extends ApiClient"]
    end
    
    subgraph "API Constants"
        CONST["Constants.FEATURE_*"]
    end
    
    subgraph "HTTP Methods"
        POST["post() - Create operations"]
        GET["get() - Fetch operations"] 
        PATCH["patch() - Edit operations"]
        COLLECTION["getCollection() - List operations"]
    end
    
    subgraph "Entity Creation"
        ENTITY["Feature extends Entity"]
    end
    
    CLIENT --> CONST
    CLIENT --> POST
    CLIENT --> GET
    CLIENT --> PATCH
    CLIENT --> COLLECTION
    
    POST --> ENTITY
    GET --> ENTITY
    PATCH --> ENTITY
    COLLECTION --> ENTITY
```

**Common Integration Pattern for Additional Features**

These additional features integrate seamlessly with the core SDK architecture, providing specialized functionality while maintaining consistency with the overall design patterns and error handling mechanisms.

Sources: [src/main/java/com/razorpay/TransferClient.java:7-11](), [src/main/java/com/razorpay/InvoiceClient.java:7-11]()
