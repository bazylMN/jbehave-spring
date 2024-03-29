package jbehave.spring;

import jbehave.spring.jbehaveSteps.MyStepsOne;
import jbehave.spring.jbehaveSteps.MyStepsTwo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class StepsContext {

    @Autowired
    public MyStepsOne myStepsOne;
    @Autowired
    public MyStepsTwo myStepsTwo;
}
