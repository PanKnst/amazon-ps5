package com.amazon.ps5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(Runner.class);

    private final OrderPlacer orderPlacer;

    public Runner(OrderPlacer orderPlacer) {
        this.orderPlacer = orderPlacer;
    }

    @Override
    public void run(String... args) throws Exception {
        orderPlacer.checkDetails();
        orderPlacer.login();
        orderPlacer.navigateToPs5DigitalPage();
        orderPlacer.keepRefreshingUntilOrderPlaced();
    }
}
