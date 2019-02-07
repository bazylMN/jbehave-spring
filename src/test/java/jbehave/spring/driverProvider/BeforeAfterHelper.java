package jbehave.spring.driverProvider;

import org.jbehave.core.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeforeAfterHelper {

    @Autowired
    private DriverProvider driverProvider;
    @Autowired
    private TakesScreenshots takesScreenshots;

    @BeforeStories
    public void before() {
        driverProvider.clearCookies();
    }

    @AfterScenario(uponOutcome = AfterScenario.Outcome.FAILURE)
    public void deleteCookiessWhenFailed() {
        takesScreenshots.saveScreenshotTo();
        driverProvider.clearCookies();
    }

    @AfterStories
    public void afterStories1(){
        driverProvider.end();
    }
}
