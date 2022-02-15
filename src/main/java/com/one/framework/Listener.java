package com.one.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener implements ITestListener {

    private Logger logger = LoggerFactory.getLogger(Listener.class);

    @Override
    public void onFinish(ITestContext Result)
    {

    }

    @Override
    public void onStart(ITestContext Result)
    {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult Result)
    {

    }

    @Override
    public void onTestFailure(ITestResult result)
    {
        logger.info("Status of " + result.getName() + " test is: FAILED");
    }

    @Override
    public void onTestSkipped(ITestResult result)
    {
        logger.info("Status of " + result.getName() + " test is: SKIPPED");
    }

    @Override
    public void onTestStart(ITestResult result)
    {
        logger.info("Starting " + result.getName() + " test..." );
    }

    @Override
    public void onTestSuccess(ITestResult result)
    {
        logger.info("Status of " + result.getName() + " test is: PASSED");
    }

}
