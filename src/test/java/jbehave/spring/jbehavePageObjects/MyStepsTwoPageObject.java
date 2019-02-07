package jbehave.spring.jbehavePageObjects;

import jbehave.spring.driverProvider.DriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyStepsTwoPageObject {

    @Autowired
    private DriverProvider driverProvider;

    private static final String COOKIES_ACCEPTANCE_TEXT = "PRZECHODZĘ DO SERWISU";
    private final By BUTTON = By.cssSelector("button");


    public void clickCookiesButtonIfExists() {
        List<WebElement> elements = driverProvider.getWebDriver().findElements(BUTTON);

        clickOneFromElementsWithText(elements, COOKIES_ACCEPTANCE_TEXT);
    }

    public String getUrl() {
        return driverProvider.getWebDriver().getCurrentUrl().toLowerCase();
    }

    private void clickOneFromElementsWithText(List<WebElement> elements, String text) {
        for (WebElement element:elements) {
            boolean has = element.getText().contains(text);
            if(has){
                element.click();
            }
        }
    }
}
