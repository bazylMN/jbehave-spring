package jbehave.spring.jbehaveSteps;

import jbehave.spring.assertions.StepsAssertions;
import jbehave.spring.jbehavePageObjects.MyStepsTwoPageObject;
import org.jbehave.core.annotations.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyStepsTwo {

    @Autowired
    private MyStepsTwoPageObject myStepsTwoPageObject;
    @Autowired
    private StepsAssertions stepsAssertions;


    @Then("I should be on ${page} page")
    public void givenIShouldBeOnHttplocalhostPage(String page) {
        myStepsTwoPageObject.clickCookiesButtonIfExists();
        String currentPage = myStepsTwoPageObject.getUrl();
        stepsAssertions.asssertPage(currentPage, page);
    }
}
