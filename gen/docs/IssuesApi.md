# IssuesApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getIssueList**](IssuesApi.md#getIssueList) | **GET** /issues | Retrieves a list of Comics


<a name="getIssueList"></a>
# **getIssueList**
> getIssueList()

Retrieves a list of Comics

Returns a list of 6 Comics

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.IssuesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8080");

    IssuesApi apiInstance = new IssuesApi(defaultClient);
    try {
      apiInstance.getIssueList();
    } catch (ApiException e) {
      System.err.println("Exception when calling IssuesApi#getIssueList");
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
**404** | List of Comics not found |  -  |

