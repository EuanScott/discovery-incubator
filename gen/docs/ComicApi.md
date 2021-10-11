# ComicApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getComicById**](ComicApi.md#getComicById) | **GET** /issues/{id} | Retrieves Comic by ID


<a name="getComicById"></a>
# **getComicById**
> getComicById(id)

Retrieves Comic by ID

Returns a single Comic

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ComicApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    ComicApi apiInstance = new ComicApi(defaultClient);
    Long id = 56L; // Long | Id of the Comic to return
    try {
      apiInstance.getComicById(id);
    } catch (ApiException e) {
      System.err.println("Exception when calling ComicApi#getComicById");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Long**| Id of the Comic to return |

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
**400** | Invalid ID supplied |  -  |
**404** | Comic not found |  -  |

