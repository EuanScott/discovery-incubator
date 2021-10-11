/*
 * Spring Playground
 * A small playground project to learn what Java Spring is all about.
 *
 * The version of the OpenAPI document: 0.0.1
 * Contact: euan.scott@entelect.co.za
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.ApiException;
import org.junit.Test;
import org.junit.Ignore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for TodoApi
 */
@Ignore
public class TodoApiTest {

    private final TodoApi api = new TodoApi();

    
    /**
     * Retrieves a list of Todos
     *
     * Returns a list of all Todos
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getTodoListTest() throws ApiException {
        api.getTodoList();

        // TODO: test validations
    }
    
}
