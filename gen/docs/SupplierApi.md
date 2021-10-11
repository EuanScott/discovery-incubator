# SupplierApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getSupplierList**](SupplierApi.md#getSupplierList) | **GET** /suppliers | Retrieve a list of Suppliers


<a name="getSupplierList"></a>
# **getSupplierList**
> getSupplierList()

Retrieve a list of Suppliers

Returns a list of all Suppliers

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.SupplierApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    SupplierApi apiInstance = new SupplierApi(defaultClient);
    try {
      apiInstance.getSupplierList();
    } catch (ApiException e) {
      System.err.println("Exception when calling SupplierApi#getSupplierList");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Successful operation |  -  |
**404** | List of Suppliers not found |  -  |

