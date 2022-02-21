package com.up42.apireusable;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.HeaderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Base64;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class RestAssuredExtensions {

    public static String endpoint;
    public static RequestSpecification REQUEST_SPEC;
    public static RequestSpecBuilder REQUEST_SPEC_BUILDER;
    public static ResponseSpecification RESPONSE_SPEC;
    public static ResponseSpecBuilder RESPONSE_SPEC_BUILDER;


    /**
     * Arrange Operations
     */


    public static void setEndPoint(String epoint) {
        endpoint = epoint;
    }


    /**
     * Function to return the global settings for making Request
     * @return request specification reference
     */
    public static RequestSpecification getRequestSpecification() {
        REQUEST_SPEC_BUILDER = new RequestSpecBuilder();
        REQUEST_SPEC_BUILDER.setBaseUri(Constants.BASEURL);
        REQUEST_SPEC_BUILDER.setConfig(RestAssuredConfig.config().headerConfig(HeaderConfig.headerConfig().overwriteHeadersWithName("Authorization")));
        return REQUEST_SPEC = REQUEST_SPEC_BUILDER.build();
    }

    /**
     * Function to return the global settings for making Request
     * @return response specification reference
     */
    public static ResponseSpecification getResponseSpecification() {
        RESPONSE_SPEC_BUILDER = new ResponseSpecBuilder();
        RESPONSE_SPEC_BUILDER.expectStatusCode(200);
        RESPONSE_SPEC_BUILDER.expectResponseTime(lessThan(10L),TimeUnit.SECONDS);
        return RESPONSE_SPEC = RESPONSE_SPEC_BUILDER.build();
    }

    /**
     *
     * @param spec
     * @param queryparam
     * @param value
     * @return
     */
    public static RequestSpecification createQueryParam (RequestSpecification spec,String queryparam,String value) {
        return spec.queryParam(queryparam, value);
    }

    /**
     *
     * @param spec
     * @param params
     * @return
     */
    public static RequestSpecification createQueryParam (RequestSpecification spec,Map<String,String> params) {
        return spec.queryParams(params);
    }

    /**
     *
     * @param spec
     * @param pathparam
     * @param value
     * @return
     */
    public static RequestSpecification createPathParam (RequestSpecification spec,String pathparam,String value) {
        return spec.pathParam(pathparam, value);
    }

    /**
     * @param spec
     * @param params
     * @return
     */
    public static RequestSpecification createPathParam (RequestSpecification spec,Map<String,String> params) {
        return spec.pathParams(params);
    }


    /**
     * Function to get response for all type of request.
     * @param reqspec
     * @param type
     * @return
     */
    public static Response getResponse(RequestSpecification reqspec,String type) {
        REQUEST_SPEC.spec(reqspec);
        Response response = null;
        switch(type){
            case "GET":
                response = given().spec(REQUEST_SPEC).get(endpoint);
                break;
            case "POST":
                response = given().spec(REQUEST_SPEC).post(endpoint);
                break;
            case "PUT":
                response = given().spec(REQUEST_SPEC).put(endpoint);
                break;
            case "DELETE":
                response = given().spec(REQUEST_SPEC).delete(endpoint);
                break;
            default:
                System.err.println("Http request is not as expected"+ type);
        }
        response.then().log().all();
        return response;
    }

    /**
     *
     * @param resp
     * @return
     */
    public static JsonPath jsonpath(Response resp) {
        String jsonPath = resp.asString();
        return new JsonPath(jsonPath);
    }


    /**
     * Function to encode the auth params
     * @param str1
     * @param str2
     * @return
     */
    public static String encode(String str1, String str2) {
        return new String(Base64.getEncoder().encode((str1 + ":" + str2).getBytes()));
    }
}
