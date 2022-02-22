import com.up42.apireusable.TestData;
import com.up42.apireusable.WorkFlowReusable;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class WorkFlowE2ETest extends BaseTest{

    @Test
    @Description("Create workflow and add a task and monitor the job e2e scenario")
    @Severity(SeverityLevel.CRITICAL)
    @Story("POST Request")
    public void createWorkFlowE2ETest(){
        WorkFlowReusable workFlowReusable = new WorkFlowReusable();
        Response response =  workFlowReusable.createWorkFlow(TestData.getWorkFlowData());
        workFlowReusable.createWorkFlowTasks(response.path("data.id"));
        workFlowReusable.createAndRunJob(response.path("data.id"));
        workFlowReusable.waitUnitJobIsExecuted();
    }

}
