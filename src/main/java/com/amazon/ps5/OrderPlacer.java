package com.amazon.ps5;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class OrderPlacer {
    private final AmazonDetails amazonDetails;
    private final Logger logger = LoggerFactory.getLogger(OrderPlacer.class);
    private boolean found = false;
    private final SafariDriver safariDriver = new SafariDriver();

    public OrderPlacer(AmazonDetails amazonDetails) {
        this.amazonDetails = amazonDetails;
    }

    public void checkDetails(){
        logger.info(amazonDetails.getEmailAddress());
        logger.info(amazonDetails.getPassword());
    }

    public void login(){
        safariDriver.navigate().to(amazonDetails.getLoginPage());
        WebElement email = safariDriver.findElementById("ap_email");
        email.sendKeys(amazonDetails.getEmailAddress());
        WebElement emailNext = safariDriver.findElementById("continue");
        emailNext.click();
        WebDriverWait wait = new WebDriverWait(safariDriver, 10);
        WebElement password = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("ap_password")));
        password.sendKeys(amazonDetails.getPassword());
        WebElement passwordNext = safariDriver.findElementById("signInSubmit");
        passwordNext.click();
        //Check if successful first
        logger.info("Logged in to amazon account");
    }

    public void navigateToPs5DigitalPage(){
        safariDriver.navigate().to(amazonDetails.getLink());
        logger.info("Navigated to PS5 Page");
    }

    private void checkStock(){
        //set found here
//        logger.info("No stock");
    }

    private void placeOrder(){

        logger.info("Order placed!");
    }

    public void keepRefreshingUntilOrderPlaced(){
        while(!found){
            checkStock();
            //refresh page
        }
        //call placeOrder here
        placeOrder();
    }
}
