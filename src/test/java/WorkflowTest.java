
import com.up42.apireusable.TestData;
import com.up42.apireusable.WorkFlowReusable;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkflowTest extends BaseTest{


    @Test(description = "Test to verify all the response code and a successful create work flow scenario")
    public void createWorkFlow(){
        WorkFlowReusable workFlowReusable = new WorkFlowReusable();
        Response response = workFlowReusable.createWorkFlow(TestData.getWorkFlowData());
        assertThat(response.getStatusCode()).isEqualTo(200);
         response = workFlowReusable.createWorkFlow(TestData.getWorkFlowDataWithEmptyName());
        assertThat(response.getStatusCode()).isEqualTo(400);
         response = workFlowReusable.createWorkFlow(TestData.getWorkFlowDataWithExpiredToken());
        assertThat(response.getStatusCode()).isEqualTo(401);
         response = workFlowReusable.createWorkFlow(TestData.getWorkFlowDataWithIncorrectContentType());
        assertThat(response.getStatusCode()).isEqualTo(415);
    }

    @Test(description = "Test to verify all the response code and a successful create task scenario")
    public void createTask(){
        WorkFlowReusable workFlowReusable = new WorkFlowReusable();
        Response response =  workFlowReusable.createWorkFlow(TestData.getWorkFlowData());
        workFlowReusable.createWorkFlowTasks(response.path("data.id"));
    }

    @Test(description = "Test to verify all the response code and a successful execution of job")
    public void createAndRunJob(){
        WorkFlowReusable workFlowReusable = new WorkFlowReusable();
        Response response =  workFlowReusable.createWorkFlow(TestData.getWorkFlowData());
        workFlowReusable.createWorkFlowTasks(response.path("data.id"));
        workFlowReusable.createAndRunJob(response.path("data.id"));
    }

    @Test(description = "Create workflow and add a task and monitor the job e2e scenario")
    public void createWorkFlowE2ETest(){
        WorkFlowReusable workFlowReusable = new WorkFlowReusable();
        Response response =  workFlowReusable.createWorkFlow(TestData.getWorkFlowData());
        workFlowReusable.createWorkFlowTasks(response.path("data.id"));
        workFlowReusable.createAndRunJob(response.path("data.id"));
        workFlowReusable.waitUnitJobIsExecuted();
    }



}
