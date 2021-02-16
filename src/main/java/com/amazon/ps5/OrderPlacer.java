package com.amazon.ps5;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
//    private final SafariDriver safariDriver = new SafariDriver();
    private final ChromeDriver chromeDriver = new ChromeDriver();
    private final WebDriverWait wait = new WebDriverWait(chromeDriver, 15);

    public OrderPlacer(AmazonDetails amazonDetails) {
        this.amazonDetails = amazonDetails;
    }

    public void checkDetails() {
        logger.info(amazonDetails.getEmailAddress());
        logger.info(amazonDetails.getPassword());
    }

    public void login() {
        chromeDriver.navigate().to(amazonDetails.getLoginPage());
        WebElement email = chromeDriver.findElementById("ap_email");
        email.sendKeys(amazonDetails.getEmailAddress());
        WebElement emailNext = chromeDriver.findElementById("continue");
        emailNext.click();
        WebElement password = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("ap_password")));
        password.sendKeys(amazonDetails.getPassword());
        WebElement passwordNext = chromeDriver.findElementById("signInSubmit");
        passwordNext.click();
        //If 2FA is needed then wait for manual input here
        WebDriverWait webDriverWait = new WebDriverWait(chromeDriver, 100);
        webDriverWait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("nav-logo-sprites")));
        logger.info("Logged in to amazon account");
    }

    public void navigateToPs5Page() {
        maximize();
//        chromeDriver.navigate().to("https://www.amazon.co.uk"); //TODO: DELETE THIS when using credentials
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("nav-logo-sprites")));
        chromeDriver.navigate().to(amazonDetails.getLink());
        logger.info("Navigated to PS5 Page");
    }

    private void checkStock() {
        chromeDriver.navigate().refresh();
        logger.info("Searching for Digital");
        WebElement ps5Digital = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("edition_1")));
        ps5Digital.click();
        logger.info("Navigated to PS5 Digital");

        try{
            wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart-button")));
        } catch (Exception e) {
            logger.info("Timed out");
        }

        List<WebElement> buyButtonList = chromeDriver.findElements(By.id("add-to-cart-button"));
        inStock = buyButtonList.size() > 0;
        logger.info("Stock Found: {}", inStock);
    }

    private void placeOrder() {
        WebElement buyButton = chromeDriver.findElementById("buy-now-button");
        buyButton.click();
        WebDriverWait waitForFrame = new WebDriverWait(chromeDriver, 60);
        waitForFrame.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("turbo-checkout-iframe")));
        chromeDriver.switchTo().frame("turbo-checkout-iframe");
        WebElement buyItNowConfirmation = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("turbo-checkout-pyo-button")));
        buyItNowConfirmation.click();
        logger.info("Order placed!");
    }

    public void keepRefreshingUntilOrderPlaced() {
        while (!inStock) {
            checkStock();
        }
        placeOrder();
    }

    public void maximize(){
        chromeDriver.manage().window().maximize();
    }
}
