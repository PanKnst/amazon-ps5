package com.amazon.ps5;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Runner implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(Runner.class);

    private final AmazonDetails amazonDetails;
    private boolean found = false;

    public Runner(AmazonDetails amazonDetails) {
        this.amazonDetails = amazonDetails;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Email address: {}", amazonDetails.getEmailAddress());
        logger.info("pass: {}", amazonDetails.getPassword());

        //Need to log into amazon here using email and pass

        SafariDriver safariDriver = new SafariDriver();
        safariDriver.navigate().to(amazonDetails.getLink());

        logger.info("At ps5 page");

        //Need to keep refreshing until buy it now button is found and then buy it.

        int refreshCount = 10;
        List<WebElement> element = safariDriver.findElements(By.id("add-to-cart-button"));
        logger.info("found add to cart button");
        for (int i = 0; i < refreshCount; i++) {
            if (element.size() > 0) {
            } else {
                safariDriver.navigate().refresh();
                logger.info("refresh count: {} ", i);
            }
        }
    }
}
