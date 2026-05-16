package com.automation.retry;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final int MAX_RETRIES = 2;
    private int attemptCount = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (attemptCount < MAX_RETRIES) {
            attemptCount++;
            System.out.println("Retrying test: " + result.getName() + " | Attempt: " + attemptCount);
            return true;
        }
        return false;
    }
}
