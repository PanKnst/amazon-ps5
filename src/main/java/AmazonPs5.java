import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;

import java.util.List;

public class AmazonPs5 {

    static SafariDriver safariDriver = new SafariDriver();

    public static void main(String[] args) {
        String account = args[0];
        String password = args[1];
        System.out.println("account: " + account + "pass: " + password);
        boolean found = false;
        safariDriver.navigate().to("https://www.amazon.co.uk/PlayStation-9395003-5-Console/dp/B08H97NYGP/ref=nav_signin?dchild=1&keywords=ps5&qid=1609447873&sr=8-1&th=1");
        System.out.print("At ps5 page");
        int refreshCount = 10;
        List<WebElement> element = safariDriver.findElements(By.id("add-to-cart-button"));
        System.out.println("Found add to cart button");
        for (int i = 0; i < refreshCount; i++) {
            if (element.size() > 0) {

                // Do the operation here on the element
            } else {
                safariDriver.navigate().refresh();
                System.out.println("Refresh count: " + i);
            }
        }
    }
}
