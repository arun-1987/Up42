import com.up42.apireusable.TestData;
import com.up42.apireusable.WorkFlowReusable;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class WorkFlowE2ETest extends BaseTest{

    @Test(description = "Create workflow and add a task and monitor the job e2e scenario")
    public void createWorkFlowE2ETest(){
        WorkFlowReusable workFlowReusable = new WorkFlowReusable();
        Response response =  workFlowReusable.createWorkFlow(TestData.getWorkFlowData());
        workFlowReusable.createWorkFlowTasks(response.path("data.id"));
        workFlowReusable.createAndRunJob(response.path("data.id"));
        workFlowReusable.waitUnitJobIsExecuted();
    }

}
