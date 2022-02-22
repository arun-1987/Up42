package com.up42.apireusable;

import io.qameta.allure.Step;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.MatcherAssert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MINUTES;
import static org.awaitility.Awaitility.*;
import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.is;

public class WorkFlowReusable {


    public String getAccessToken(String projectId,String apiKey){
        RequestSpecification requestSpec = RestAssuredExtensions.getRequestSpecification();
        RestAssuredExtensions.setEndPoint("/oauth/token");
        String auth = RestAssuredExtensions.encode(projectId,apiKey);
        requestSpec.header("Authorization", "Basic " + auth)
                .header("Content-Type","application/x-www-form-urlencoded")
                .formParam("grant_type","client_credentials");
        Response response = RestAssuredExtensions.getResponse(requestSpec, "POST");
        assertThat(response.getStatusCode()).isEqualTo(200);
        return response.path("data.accessToken");
    }


    public void clearAllWorkFlow(){
        String accessToken = getAccessToken(Constants.PROJECTID,Constants.APIKEY);
        RequestSpecification requestSpec = RestAssuredExtensions.getRequestSpecification();
        RestAssuredExtensions.setEndPoint("/projects/"+Constants.PROJECTID+"/workflows");
        requestSpec.header("Authorization", "Bearer "+accessToken)
                .header("Content-Type","application/json");
        Response response = RestAssuredExtensions.getResponse(requestSpec, "GET");
        assertThat(response.getStatusCode()).isEqualTo(200);
        List<String> idList = response.jsonPath().get("data.id");
       if(idList.size()>0) {
           for (String id : idList) {
               deleteWorkFlowById(id, accessToken);
           }
       }
    }

    public Response createWorkFlow(String testData){
        String accessToken = getAccessToken(Constants.PROJECTID,Constants.APIKEY);
        RequestSpecification requestSpec = RestAssuredExtensions.getRequestSpecification();
        RestAssuredExtensions.setEndPoint("/projects/"+Constants.PROJECTID+"/workflows");
        if(testData.contains("Expired")) {
            requestSpec.header("Authorization", "Bearer " + "erwsdfafdfafafdfasfdfadgdfsfwerwger")
                    .header("Content-Type", "application/json")
                    .body(testData);
        }else if(testData.contains("IncorrectContent")) {
            requestSpec.header("Authorization", "Bearer " + accessToken)
                    .header("Content-Type", "application/xml")
                    .body(testData);
        }
        else{
            requestSpec.header("Authorization", "Bearer " + accessToken)
                    .header("Content-Type", "application/json")
                    .body(testData);
        }
       return RestAssuredExtensions.getResponse(requestSpec, "POST");
    }

    public Response createWorkFlowTasks(String workFlowId){
        String accessToken = getAccessToken(Constants.PROJECTID,Constants.APIKEY);
        RequestSpecification requestSpec = RestAssuredExtensions.getRequestSpecification();
        RestAssuredExtensions.setEndPoint("/projects/"+Constants.PROJECTID+"/workflows/"+workFlowId+"/tasks");
        requestSpec.header("Authorization", "Bearer "+accessToken)
                .header("Content-Type","application/json")
                .body(TestData.getWorkFlowTaskData());
        return RestAssuredExtensions.getResponse(requestSpec, "POST");

    }

    public void verifyCreateTaskResponse(Response response){
        response.then().assertThat().statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Create_Task_Schema.json"));
        assertThat(response.path("data.createdBy").toString().equalsIgnoreCase(Constants.PROJECTID));
        assertThat(response.path("data.updatedBy").toString().equalsIgnoreCase(Constants.PROJECTID));
        assertThat(response.body().jsonPath().getList("data.id").size()==2);
    }


    public void deleteWorkFlowById(String id,String accessToken){
        RequestSpecification requestSpec = RestAssuredExtensions.getRequestSpecification();
        RestAssuredExtensions.setEndPoint("/projects/"+Constants.PROJECTID+"/workflows/"+id+"");
        requestSpec.header("Authorization", "Bearer "+accessToken)
                .header("Content-Type","application/json");
        Response response = RestAssuredExtensions.getResponse(requestSpec, "DELETE");
        assertThat(response.getStatusCode()).isEqualTo(204);
    }


    public Response createAndRunJob(String workFlowId){
        String accessToken = getAccessToken(Constants.PROJECTID,Constants.APIKEY);
        RequestSpecification requestSpec = RestAssuredExtensions.getRequestSpecification();
        RestAssuredExtensions.setEndPoint("/projects/"+Constants.PROJECTID+"/workflows/"+workFlowId+"/jobs");
        requestSpec.header("Authorization", "Bearer "+accessToken)
                .header("Content-Type","application/json")
                .body(TestData.getWorkFlowConfigData());
        return   RestAssuredExtensions.getResponse(requestSpec, "POST");
    }

    private Callable getJobStatusAndValidate() {
        return new Callable() {
            public Boolean call()  {
                boolean flag = false;
                String accessToken = getAccessToken(Constants.PROJECTID,Constants.APIKEY);
                RequestSpecification requestSpec = RestAssuredExtensions.getRequestSpecification();
                RestAssuredExtensions.setEndPoint("/projects/"+Constants.PROJECTID+"/jobs");
                requestSpec.header("Authorization", "Bearer "+accessToken)
                        .header("Content-Type","application/json");
                Response response = RestAssuredExtensions.getResponse(requestSpec, "GET");
                if(response.path("data[0].status").toString().equalsIgnoreCase("SUCCEEDED")){
                    flag = true;
                }
                return flag;
            }
        };
    }

    public void waitUnitJobIsExecuted(){
            await().atMost(5, MINUTES).pollInterval(5, TimeUnit.SECONDS).
                    until(getJobStatusAndValidate(), is(true));
    }

    public void getJobs(){
        String accessToken = getAccessToken(Constants.PROJECTID,Constants.APIKEY);
        RequestSpecification requestSpec = RestAssuredExtensions.getRequestSpecification();
        RestAssuredExtensions.setEndPoint("/projects/"+Constants.PROJECTID+"/jobs");
        requestSpec.header("Authorization", "Bearer "+accessToken)
                .header("Content-Type","application/json");
        Response response = RestAssuredExtensions.getResponse(requestSpec, "GET");
    }

}
