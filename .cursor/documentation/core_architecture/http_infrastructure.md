# HTTP Infrastructure

<details>
<summary>Relevant source files</summary>

The following files were used as context for generating this wiki page:

- [pom.xml](pom.xml)
- [src/main/java/com/razorpay/ApiClient.java](src/main/java/com/razorpay/ApiClient.java)
- [src/main/java/com/razorpay/ApiUtils.java](src/main/java/com/razorpay/ApiUtils.java)
- [src/main/java/com/razorpay/CustomTLSSocketFactory.java](src/main/java/com/razorpay/CustomTLSSocketFactory.java)

</details>



This document covers the HTTP communication layer of the Razorpay Java SDK, including HTTP client management, request/response processing, and security configuration. This infrastructure provides the foundation for all API communication between the SDK and Razorpay's REST API endpoints.

For authentication mechanisms and signature verification, see [Security & Authentication](#3.4). For API endpoint configuration and constants, see [Configuration](#3.5).

## Overview

The HTTP infrastructure consists of two primary components: `ApiUtils` for low-level HTTP operations and `ApiClient` for higher-level request processing. This layer handles HTTP client initialization, request construction, response parsing, and secure TLS communication.

```mermaid
graph TB
    subgraph "Resource Clients"
        PC[PaymentClient]
        OC[OrderClient]
        RC[RefundClient]
    end
    
    subgraph "HTTP Infrastructure"
        AC[ApiClient]
        AU[ApiUtils]
        OKHTTP[OkHttpClient]
        CTLS[CustomTLSSocketFactory]
    end
    
    subgraph "External Dependencies"
        JSON[org.json]
        CODEC[commons-codec]
    end
    
    PC --> AC
    OC --> AC
    RC --> AC
    
    AC --> AU
    AU --> OKHTTP
    AU --> CTLS
    AU --> JSON
    
    OKHTTP --> CTLS
```

Sources: [src/main/java/com/razorpay/ApiUtils.java:1-179](), [src/main/java/com/razorpay/ApiClient.java:1-194](), [pom.xml:43-75]()

## HTTP Client Configuration

The `ApiUtils` class manages a singleton `OkHttpClient` instance configured with specific timeouts, logging, and security settings.

```mermaid
sequenceDiagram
    participant App as "Application"
    participant RC as "RazorpayClient"
    participant AU as "ApiUtils"
    participant CTLS as "CustomTLSSocketFactory"
    participant OKHTTP as "OkHttpClient"
    
    App->>RC: "new RazorpayClient(key, secret)"
    RC->>AU: "createHttpClientInstance(enableLogging)"
    AU->>CTLS: "new CustomTLSSocketFactory()"
    CTLS-->>AU: "socket factory"
    AU->>OKHTTP: "new OkHttpClient.Builder()"
    Note over AU,OKHTTP: "60s read/write timeouts<br/>Logging interceptor<br/>Custom TLS config"
    OKHTTP-->>AU: "configured client"
    AU-->>RC: "client ready"
    RC-->>App: "initialized"
```

The HTTP client is configured with the following settings:

| Configuration | Value | Purpose |
|---------------|-------|---------|
| Read Timeout | 60 seconds | Prevents hanging on slow responses |
| Write Timeout | 60 seconds | Prevents hanging on slow uploads |
| SSL Socket Factory | `CustomTLSSocketFactory` | Enforces TLS 1.1/1.2 protocols |
| Logging Level | BASIC or NONE | Configurable request/response logging |

Sources: [src/main/java/com/razorpay/ApiUtils.java:34-62](), [src/main/java/com/razorpay/ApiUtils.java:44-49]()

## Request Processing Flow

The HTTP infrastructure supports five HTTP methods through a consistent interface pattern:

```mermaid
graph LR
    subgraph "HTTP Methods"
        GET[getRequest]
        POST[postRequest]
        PUT[putRequest]
        PATCH[patchRequest]
        DELETE[deleteRequest]
    end
    
    subgraph "Request Building"
        BUILDER[getBuilder]
        PARAMS[addQueryParams]
        REQ[createRequest]
    end
    
    subgraph "Processing"
        PROC[processRequest]
        RESP[Response]
    end
    
    GET --> BUILDER
    POST --> BUILDER
    PUT --> BUILDER
    PATCH --> BUILDER
    DELETE --> BUILDER
    
    BUILDER --> PARAMS
    PARAMS --> REQ
    REQ --> PROC
    PROC --> RESP
```

Each HTTP method follows this pattern:
1. **URL Construction**: Build URL using `Constants` for scheme, hostname, port, and version
2. **Parameter Handling**: Add query parameters for GET/DELETE, JSON body for POST/PUT/PATCH
3. **Authentication**: Add Basic Auth header with API credentials
4. **User Agent**: Include SDK version and Java version information
5. **Execution**: Process request through OkHttp and handle responses

Sources: [src/main/java/com/razorpay/ApiUtils.java:68-125](), [src/main/java/com/razorpay/ApiUtils.java:127-144]()

## Response Processing Architecture

The `ApiClient` class provides typed response processing with automatic entity instantiation:

```mermaid
graph TD
    subgraph "ApiClient Methods"
        GET[get]
        POST[post]
        PUT[put]
        PATCH[patch]
        DELETE[delete]
        GETCOLL[getCollection]
    end
    
    subgraph "Response Processing"
        PROCRESP[processResponse]
        PROCCOLL[processCollectionResponse]
        PARSE[parseResponse]
        PARSECOLL[parseCollectionResponse]
    end
    
    subgraph "Entity Creation"
        GETCLASS[getClass]
        REFLECT[Reflection Constructor]
        ENTITY[Entity Instance]
    end
    
    GET --> PROCRESP
    POST --> PROCRESP
    PUT --> PROCRESP
    PATCH --> PROCRESP
    DELETE --> PROCRESP
    GETCOLL --> PROCCOLL
    
    PROCRESP --> PARSE
    PROCCOLL --> PARSECOLL
    
    PARSE --> GETCLASS
    GETCLASS --> REFLECT
    REFLECT --> ENTITY
```

The response processing includes:
- **Status Code Validation**: Success range 200-299
- **Entity Recognition**: Uses `entity` field in JSON to determine class type
- **Dynamic Instantiation**: Creates appropriate entity objects using reflection
- **Collection Handling**: Processes arrays of entities in collection responses
- **Error Handling**: Structured error response parsing and exception throwing

Sources: [src/main/java/com/razorpay/ApiClient.java:99-146](), [src/main/java/com/razorpay/ApiClient.java:65-96](), [src/main/java/com/razorpay/ApiClient.java:186-193]()

## TLS Security Configuration

The `CustomTLSSocketFactory` enforces modern TLS protocols for secure communication:

```mermaid
graph TB
    subgraph "TLS Configuration"
        SSL[SSLContext.getInstance]
        FACTORY[SSLSocketFactory]
        CUSTOM[CustomTLSSocketFactory]
    end
    
    subgraph "Socket Creation"
        SOCKET[createSocket]
        ENABLE[enableTLSOnSocket]
        PROTOCOLS["TLSv1.1, TLSv1.2"]
    end
    
    SSL --> FACTORY
    FACTORY --> CUSTOM
    CUSTOM --> SOCKET
    SOCKET --> ENABLE
    ENABLE --> PROTOCOLS
```

The custom TLS implementation:
- **Protocol Enforcement**: Restricts to TLS 1.1 and TLS 1.2 only
- **Socket Wrapping**: Overrides all socket creation methods
- **Security Compliance**: Ensures encrypted communication with Razorpay API
- **Default Trust Manager**: Uses system's default certificate validation

Sources: [src/main/java/com/razorpay/CustomTLSSocketFactory.java:14-75](), [src/main/java/com/razorpay/CustomTLSSocketFactory.java:69-74]()

## Error Handling Mechanism

The HTTP infrastructure provides structured error handling with multiple fallback mechanisms:

| Error Type | Status Code | Handling Strategy |
|------------|-------------|-------------------|
| API Errors | 400-499 | Parse `error` object with `code` and `description` |
| Server Errors | 500+ | Include full response body and status code |
| Network Errors | N/A | Wrap IOException in RazorpayException |
| Parse Errors | N/A | Include original exception message |

```mermaid
flowchart TD
    RESP[Response] --> CHECK{Status Code}
    CHECK -->|200-299| SUCCESS[Parse Entity]
    CHECK -->|300+| ERROR[Check Error Object]
    
    ERROR --> HASERROR{Has Error Field?}
    HASERROR -->|Yes| STRUCTURED[Structured Error]
    HASERROR -->|No| GENERIC[Server Error]
    
    STRUCTURED --> THROW1[RazorpayException with code:description]
    GENERIC --> THROW2[RazorpayException with status and body]
    
    SUCCESS --> ENTITY[Return Entity]
```

Sources: [src/main/java/com/razorpay/ApiClient.java:169-184](), [src/main/java/com/razorpay/ApiUtils.java:157-163]()

## Dependencies and External Libraries

The HTTP infrastructure relies on these key dependencies:

| Dependency | Version | Purpose |
|------------|---------|---------|
| OkHttp | 3.10.0 | HTTP client and request/response handling |
| OkHttp Logging Interceptor | 3.10.0 | Request/response logging for debugging |
| org.json | 20180130 | JSON parsing and serialization |
| commons-codec | 1.11 | Base64 encoding for authentication |

Sources: [pom.xml:45-67]()
