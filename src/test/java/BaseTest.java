import com.up42.apireusable.WorkFlowReusable;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;

public class BaseTest {

    @AfterTest
    public void clearAllWorkFlow(){
        WorkFlowReusable workFlowReusable = new WorkFlowReusable();
        workFlowReusable.clearAllWorkFlow();
    }


}
