package com.qait.sakurai.automation.tests.smokechecklist;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.qait.automation.utils.BrowserStackTestNGTest;

public class BrowserStackTest extends BrowserStackTestNGTest {

    @Test
    public void test() throws Exception {
        driver.get("https://www.google.com/ncr");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("BrowserStack");
        element.submit();
        Thread.sleep(5000);

        Assert.assertEquals("BrowserStack - Google Search", driver.getTitle());
    }
}