### Sakurai UAT - Automated Functional Test Suite
---
#### Summary:

Functional Automated Test Suite. This uses Selenium Testing Tool for automated testing of Project Sakurai - PrepU ReArchitecture

#### System Requirement:

* JDK 1.8
* Maven 3.1
* Eclipse or IDE of choice in case there is need to update the script. (optional) (Install TestNG PlugIn in it)

#### Script Execution

Open terminal and navigate to the root directory of the scripts. Root directory contains pom.xml file

    mvn clean verify -Dtestngxml=<<TestNG.xml name>> //for complete test-suite
    mvn clean verify -Dtest=<<TestScriptName>> //for single test script

#### Result Files:	
The Test Execution Results will be stored in the following directory once the test has completed

    ./target/test-output/emailable-report.html (for complete test suite)
    ./target/surefire-reports/emailable-report.html (for single test suite)
