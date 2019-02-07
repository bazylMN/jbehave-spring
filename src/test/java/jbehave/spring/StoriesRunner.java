package jbehave.spring;

import org.jbehave.core.InjectableEmbedder;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.spring.UsingSpring;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.configuration.spring.SpringParameterControls;
import org.jbehave.core.configuration.spring.SpringStoryControls;
import org.jbehave.core.configuration.spring.SpringStoryReporterBuilder;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.jbehave.core.junit.spring.SpringAnnotatedEmbedderRunner;
import org.jbehave.core.parsers.RegexPrefixCapturingPatternParser;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.SilentStepMonitor;
import org.jbehave.core.steps.spring.SpringApplicationContextFactory;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.jbehave.core.reporters.Format.*;

@RunWith(SpringAnnotatedEmbedderRunner.class)
@Configure(storyControls = StoriesRunner.MyStoryControls.class,
        storyReporterBuilder = StoriesRunner.MyReportBuilder.class,
        parameterConverters = { StoriesRunner.MyDateConverter.class },
        stepPatternParser = StoriesRunner.MyRegexPrefixCapturingPatternParser.class)
@UsingEmbedder(generateViewAfterStories = true,
        ignoreFailureInStories = true,
        ignoreFailureInView = true,
        verboseFailures = true)
@UsingSpring(resources = {"configuration.xml"})
public class StoriesRunner extends InjectableEmbedder {

    @Autowired
    private ApplicationContext applicationContext;
    private final CrossReference xref = new CrossReference();

    public StoriesRunner() {}

    public static class MyStoryControls extends SpringStoryControls {
        public MyStoryControls() {
            doDryRun(false);
            doSkipScenariosAfterFailure(true);
            doResetStateBeforeScenario(true);
            doResetStateBeforeStory(true);
        }
    }

    public static class MyReportBuilder extends SpringStoryReporterBuilder {
        public MyReportBuilder() {
            this.withFormats(CONSOLE, TXT, HTML, XML).withDefaultFormats();
            this.withCodeLocation(CodeLocations.codeLocationFromPath("reports/jbehave"));
        }
    }

    public static class MyRegexPrefixCapturingPatternParser extends RegexPrefixCapturingPatternParser {
        public MyRegexPrefixCapturingPatternParser() {
            super("$");
        }
    }

    public static class MyDateConverter extends ParameterConverters.DateConverter {
        public MyDateConverter() {
            super(new SimpleDateFormat("yyyy-MM-dd"));
        }
    }



    public Configuration configuration() {
        return new MostUsefulConfiguration()
                .useStoryPathResolver(new UnderscoredCamelCaseResolver())
                .useStepMonitor(new SilentStepMonitor())
                .useParameterControls(new SpringParameterControls());
    }

    private List<String> storyPaths() {
        return new StoryFinder()
                .findPaths(org.jbehave.core.io.CodeLocations
                        .codeLocationFromPath("src/test/resources"),"stories/*.story","");

    }

    public InjectableStepsFactory stepsFactory() {
        applicationContext = new SpringApplicationContextFactory("classpath:StepsContext")
                .createApplicationContext();
        return new SpringStepsFactory(configuration(), applicationContext);
    }

    @Test
    public void run() {
        injectedEmbedder().runStoriesAsPaths(storyPaths());
    }
}
