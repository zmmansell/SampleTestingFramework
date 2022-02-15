# SampleTestingFramework
Selenium WebDriver framework containing tests for Login & Products page for
https://www.saucedemo.com/

## Pre requisites for running tests in command line:

- Java
- Chrome driver. Download from https://chromedriver.chromium.org/downloads. Save chromedriver.exe in c:\webdrivers\ or change this path which is hard coded in https://github.com/AngelaCearnau/SampleTestFramework/blob/main/src/main/java/com/one/framework/WebDriverFactory.java

Run 'mvn clean install' to build the project 

## Run tests
In target folder run: 

java -cp PocForOneCom-1.0-SNAPSHOT-tests.jar;PocForOneCom-1.0-SNAPSHOT.jar;libs* -Dwebdriver.baseUrl=https://www.saucedemo.com/ -Dwebdriver.remote=false -Dwebdriver.capabilities.browserName=chrome org.testng.TestNG ../TestNG-XML-Suites/Login/LoginTestSuite.xml 

java -cp PocForOneCom-1.0-SNAPSHOT-tests.jar;PocForOneCom-1.0-SNAPSHOT.jar;libs* -Dwebdriver.baseUrl=https://www.saucedemo.com/ -Dwebdriver.remote=false -Dwebdriver.capabilities.browserName=chrome org.testng.TestNG ../TestNG-XML-Suites/Smoke/SmokeTestSuite.xml

