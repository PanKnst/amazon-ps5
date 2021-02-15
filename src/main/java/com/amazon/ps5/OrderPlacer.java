package com.amazon.ps5;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class OrderPlacer {
    private final AmazonDetails amazonDetails;
    private final Logger logger = LoggerFactory.getLogger(OrderPlacer.class);
    private boolean inStock = false;
    private final SafariDriver safariDriver = new SafariDriver();
    private final WebDriverWait wait = new WebDriverWait(safariDriver, 10);

    public OrderPlacer(AmazonDetails amazonDetails) {
        this.amazonDetails = amazonDetails;
    }

    public void checkDetails() {
        logger.info(amazonDetails.getEmailAddress());
        logger.info(amazonDetails.getPassword());
    }

    public void login() {
        safariDriver.navigate().to(amazonDetails.getLoginPage());
        WebElement email = safariDriver.findElementById("ap_email");
        email.sendKeys(amazonDetails.getEmailAddress());
        WebElement emailNext = safariDriver.findElementById("continue");
        emailNext.click();
        WebElement password = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("ap_password")));
        password.sendKeys(amazonDetails.getPassword());
        WebElement passwordNext = safariDriver.findElementById("signInSubmit");
        passwordNext.click();
        logger.info("Logged in to amazon account");
    }

    public void navigateToPs5Page() {
        //wait for page to load
        safariDriver.navigate().to("https://www.amazon.co.uk"); //TODO: DELETE THIS when using credentials
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("nav-logo-sprites")));
        safariDriver.navigate().to(amazonDetails.getLink());
        logger.info("Navigated to PS5 Page");

    }

    private void checkStock() {
        WebElement ps5Digital = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("edition_1")));
        ps5Digital.click();
        logger.info("Navigated to PS5 Digital");
        List<WebElement> buyButtonList = safariDriver.findElements(By.id("add-to-cart-button"));
        inStock = buyButtonList.size() > 0;
        logger.info("Stock Found: {}", inStock);
    }

    private void placeOrder() {
        WebElement buyButton = safariDriver.findElementById("add-to-cart-button");
        buyButton.click();
        logger.info("Order placed!");
    }

    public void keepRefreshingUntilOrderPlaced() {
//        while (!inStock) {
//            checkStock();
//            safariDriver.navigate().refresh();
//        }
        checkStock();
//        placeOrder();
    }
}
