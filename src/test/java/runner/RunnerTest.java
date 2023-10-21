package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@CucumberOptions(
        features="src/test/resources/Features/testAPI.feature",
        glue={"steps"},
        publish = true
)
@RunWith(Cucumber.class)
public class RunnerTest {
}
