
import com.up42.apireusable.TestData;
import com.up42.apireusable.WorkFlowReusable;
import io.qameta.allure.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkflowTest extends BaseTest{


    @Test
    @Description("Test to verify all the response code and a successful create work flow scenario")
    @Severity(SeverityLevel.CRITICAL)
    @Story("POST Request")
    public void createWorkFlow(){
        WorkFlowReusable workFlowReusable = new WorkFlowReusable();
        Response response = workFlowReusable.createWorkFlow(TestData.getWorkFlowData());
        assertThat(response.getStatusCode()).isEqualTo(200);
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Work_Flow_Schema.json"));
         response = workFlowReusable.createWorkFlow(TestData.getWorkFlowDataWithEmptyName());
        assertThat(response.getStatusCode()).isEqualTo(400);
         response = workFlowReusable.createWorkFlow(TestData.getWorkFlowDataWithExpiredToken());
        assertThat(response.getStatusCode()).isEqualTo(401);
         response = workFlowReusable.createWorkFlow(TestData.getWorkFlowDataWithIncorrectContentType());
        assertThat(response.getStatusCode()).isEqualTo(415);
    }

    @Test
    @Description("Test to verify all the response code and a successful create task scenario")
    @Severity(SeverityLevel.CRITICAL)
    @Story("POST Request")
    public void createTask(){
        WorkFlowReusable workFlowReusable = new WorkFlowReusable();
        Response response =  workFlowReusable.createWorkFlow(TestData.getWorkFlowData());
        response = workFlowReusable.createWorkFlowTasks(response.path("data.id"));
        workFlowReusable.verifyCreateTaskResponse(response);
    }

    @Test
    @Description("Test to verify all the response code and a successful execution of job")
    @Severity(SeverityLevel.CRITICAL)
    @Story("POST Request")
    public void createAndRunJob(){
        WorkFlowReusable workFlowReusable = new WorkFlowReusable();
        Response response =  workFlowReusable.createWorkFlow(TestData.getWorkFlowData());
        workFlowReusable.createWorkFlowTasks(response.path("data.id"));
        Response createJobResponse = workFlowReusable.createAndRunJob(response.path("data.id"));
        createJobResponse.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Create_Job_Run_Schema.json"));
    }





}
