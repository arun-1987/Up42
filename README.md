# Up42
Sample exercise project using RestAssured Java for automating a Api.

## Decription
The project is developed to demonstrate test automation of Up42 workflow api. This project has test scenarios to validate individual api's for different status code and one e2e flow. The e2e flow involves the steps to create, monitor and delete the work flow.

### Aritifacts
```
Test - WorkFlowTest
TestData - TestData
ReusableComponents - WorkFlowReusable
```

### Usage
To run in local
  mvn clean test -DsuiteXmlFile=SmokeSuite.xml
  mvn clean test -DsuiteXmlFile=E2ESuite.xml

### Tools Used
```
TestNg - Test Runner
Maven - Build
RestAssured - Api Testing Tool
Awaitility - Async wait
AssertJ - Assertion
AllureReport - Reporting
```
### Language
Java
