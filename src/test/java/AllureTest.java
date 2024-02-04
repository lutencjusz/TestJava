import io.qameta.allure.Allure;
import io.qameta.allure.model.*;
import org.junit.Test;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.UUID;

public class AllureTest {

    public void generateTreeStructureReport() {
        // Tworzenie korzenia drzewa
        TestResultContainer root = new TestResultContainer()
                .setUuid(UUID.randomUUID().toString())
                .setName("Root");

        // Tworzenie węzła potomnego
        TestResultContainer child = new TestResultContainer()
                .setUuid(UUID.randomUUID().toString())
                .setName("Child");

        // Dodawanie węzła potomnego do korzenia
        root.getChildren().add(child.getUuid());

        Label labelOwner = new Label()
                .setName("owner")
                .setValue("Jan Kowalski");

        Label labelTag = new Label()
                .setName("tag")
                .setValue("labelValue2");

        Label labelFlaky = new Label()
                .setName("broken")
                .setValue("true");

        Parameter parameter1 = new Parameter()
                .setName("testResult1.Parametr 1")
                .setValue("testResult1.parameter1.setValue 1");

        Parameter parameter2 = new Parameter()
                .setName("testResult1.Parametr 2")
                .setValue("testResult1.parameter1.setValue 2");

        StatusDetails statusDetailsTestResult = new StatusDetails()
                .setMessage("Wystąpił błąd podczas wykonywania testu 1 - testResult1.statusDetails.setMessage")
                .setTrace("""
                        Nie działa test
                        szczegółowy opis znajduje się w krokach
                        testResult1.setStatusDetails.statusDetailsTestResult.setTrace"""); // Tutaj możesz dodać pełny stack trace błędu

        String attachmentContent = "Treść załącznika";
        byte[] attachmentBytes = attachmentContent.getBytes(StandardCharsets.UTF_8);
        String attachmentUuid = UUID.randomUUID().toString();

        Attachment attachment2 = new Attachment()
                .setName("Załącznik testResult1")
                .setType("text/plain")
                .setSource(attachmentUuid + ".txt");

        // Tworzenie wyników testu
        String testResult2Uuid = UUID.randomUUID().toString();
        TestResult testResult2 = new TestResult()
                .setUuid(testResult2Uuid)
                .setName("Test Result 2 - określony w testResult2.setName")
                .setFullName("Test Result 2 - określony w testResult2.setFullName")
                .setDescriptionHtml("<i style='color:green;'>tekst 1 określony testResult2.setDescriptionHtml</i>")
                .setHistoryId("testResult2.setHistoryId")
                .setStage(Stage.FINISHED)
                .setStart(System.currentTimeMillis())
                .setStop(System.currentTimeMillis() + 1000)
                .setTestCaseId("identyfikator - testResult2.setTestCaseId")
                .setTestCaseName("Przypadek użycia - testResult2.setTestCaseName")
                .setStatus(Status.PASSED);

        testResult2.getLabels().add(labelOwner);
        testResult2.getLabels().add(labelTag);
        testResult2.getLabels().add(labelFlaky);

        String testResult1Uuid = UUID.randomUUID().toString();
        TestResult testResult1 = new TestResult()
                .setUuid(testResult1Uuid)
                .setName("Test Result 1 - określony w testResult1.setName")
                .setFullName("Test Result 1 - określony w testResult1.setFullName")
                .setDescription("Wystąpił błąd podczas wykonywania testu 1 - określony w testResult1.setDescription")
                .setStatusDetails(statusDetailsTestResult)
                .setDescriptionHtml("<i style='color:red;'>tekst 1 określony testResult1.setDescriptionHtml</i>")
                .setHistoryId("testResult1.setHistoryId")
                .setStage(Stage.FINISHED)
                .setStart(System.currentTimeMillis())
                .setStop(System.currentTimeMillis() + 1000)
                .setTestCaseId("identyfikator - testResult1.setTestCaseId")
                .setTestCaseName("Przypadek użycia - testResult1.setTestCaseName")
                .setStatus(Status.FAILED);

        testResult1.getLabels().add(labelOwner);
        testResult1.getLabels().add(labelTag);

        testResult1.getParameters().add(parameter1);
        testResult1.getParameters().add(parameter2);

        testResult1.getAttachments().add(attachment2);

        // Dodawanie kroków do wyniku testu
        String stepUuid1 = UUID.randomUUID().toString();
        StepResult stepResult1 = new StepResult().setName(testResult1.getTestCaseName() + ": Step 1").setStatus(Status.PASSED);

        String stepUuid2 = UUID.randomUUID().toString();
        StepResult stepResult2 = new StepResult().setName(testResult2.getTestCaseName() + ": Step 1").setStatus(Status.PASSED);

        // Dodawanie podkroków do StepResult1
        String substepUuid1 = UUID.randomUUID().toString();
        StepResult substepResult1 = new StepResult().setName("Substep 1").setStatus(Status.PASSED);

        String subsubstepUuid1 = UUID.randomUUID().toString();
        StepResult subsubstepResult1 = new StepResult().setName("Subsubstep 1").setStatus(Status.PASSED);
        Allure.getLifecycle().startStep(substepUuid1, subsubstepUuid1, subsubstepResult1);
        Allure.getLifecycle().stopStep(subsubstepUuid1);

        // Dodawanie kolejnego podkroku do StepResult1
        String substepUuid2 = UUID.randomUUID().toString();
        StepResult substepResult2 = new StepResult().setName("Substep 2").setStatus(Status.SKIPPED);
        Allure.getLifecycle().startStep(stepUuid1, substepUuid2, substepResult2);
        Allure.getLifecycle().stopStep(substepUuid2);

        String subsubstepUuid2 = UUID.randomUUID().toString();
        StepResult subsubstepResult2 = new StepResult()
                .setName("Subsubstep 2")
                .setStatus(Status.FAILED);

        // Dodawanie szczegółów statusu
        StatusDetails statusDetails = new StatusDetails()
                .setMessage("Wystąpił błąd podczas wykonywania subsubstepResult2.statusDetails.setMessage")
                .setTrace("""
                        SLF4J: Class path contains multiple SLF4J providers.
                        SLF4J: Found provider [org.slf4j.simple.SimpleServiceProvider@1176dcec]
                        SLF4J: Found provider [org.slf4j.impl.JBossSlf4jServiceProvider@120d6fe6]
                        SLF4J: See https://www.slf4j.org/codes.html#multiple_bindings for an explanation.
                        SLF4J: Actual provider is of type [org.slf4j.simple.SimpleServiceProvider@1176dcec]"""); // Tutaj możesz dodać pełny stack trace błędu

        subsubstepResult2.setStatusDetails(statusDetails);

        // Dodawanie parametrów do SubsubstepResult2
        Parameter parameterSubsubstepResult1 = new Parameter()
                .setName("SubsubstepResult2.Parametr 1")
                .setValue("Wartość 1 - SubsubstepResult2.Parametr.setValue");

        Parameter parameterSubsubstepResult2 = new Parameter()
                .setName("SubsubstepResult2.Parametr 2")
                .setValue("Wartość 2 - SubsubstepResult2.Parametr.setValue");

        subsubstepResult2.getParameters().add(parameterSubsubstepResult1);
        subsubstepResult2.getParameters().add(parameterSubsubstepResult2);
        subsubstepResult2.setDescriptionHtml("<i style='color:red;'>tekst 2 określony subsubstepResult2.setDescriptionHtml</i>");

        Attachment attachment = new Attachment()
                .setName("Załącznik subsubstepResult2")
                .setType("text/plain")
                .setSource(attachmentUuid + ".txt");

        // Ustawianie etapu dla SubsubstepResult2
        subsubstepResult2.setStage(Stage.FINISHED);
        subsubstepResult2.getAttachments().add(attachment);

        Allure.getLifecycle().startStep(substepUuid1, subsubstepUuid2, subsubstepResult2);
        Allure.getLifecycle().stopStep(subsubstepUuid2);

        substepResult1.getSteps().add(subsubstepResult1);
        substepResult1.getSteps().add(subsubstepResult2);

        Allure.getLifecycle().startStep(stepUuid1, substepUuid1, substepResult1);
        Allure.getLifecycle().stopStep(substepUuid1);

        stepResult1.getSteps().add(substepResult1);
        stepResult1.getSteps().add(substepResult2);

        Allure.getLifecycle().startStep(testResult1Uuid, stepUuid1, stepResult1);
        Allure.getLifecycle().stopStep(stepUuid1);

        testResult1.getSteps().add(stepResult1);

        Allure.getLifecycle().startStep(testResult2Uuid, stepUuid2, stepResult2);
        Allure.getLifecycle().stopStep(stepUuid2);

        testResult2.getSteps().add(stepResult2);

        // Wygenerowanie pliku environment.properties
        Properties properties = new Properties();
        properties.setProperty("os_name", System.getProperty("os.name"));
        properties.setProperty("os_version", System.getProperty("os.version"));
        properties.setProperty("java_version", System.getProperty("java.version"));
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("allure-results/environment.properties");
            properties.store(fileOutputStream, "Allure environment properties");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Tworzenie fixture'a
        String fixtureUuid = UUID.randomUUID().toString();
        FixtureResult fixtureResult = new FixtureResult()
                .setName("Fixture utworzony w FixtureResult.setName")
                .setStatus(Status.PASSED);

        // Zapisywanie wyników do raportu Allure
        Allure.getLifecycle().startTestContainer(root);
        Allure.getLifecycle().startTestContainer(child);
        Allure.getLifecycle().startPrepareFixture(testResult2.getUuid(), fixtureUuid, fixtureResult);
        Allure.getLifecycle().stopFixture(fixtureUuid);
        Allure.getLifecycle().scheduleTestCase(testResult1);
        Allure.getLifecycle().startTestCase(testResult1.getUuid());

        Allure.epic("Epik ustawiony w Allure.epic");
        Allure.feature("Feature ustawiona w Allure.feature");
        Allure.story("Story ustawiona w Allure.story");
        Allure.suite("Suite ustawione w Allure.suite");
        Allure.label("tag", "labelValue1 tag - ustawione w Allure.label");
        Allure.label("lead", "labelValue1 lead - ustawione w Allure.label");
        Allure.parameter("Parametr 1", "Wartość 1 - ustawione w Allure.parameter");
        Allure.parameter("Parametr 2", "Wartość 2 - ustawione w Allure.parameter");
        Allure.issue("issue - ustawione w Allure.issue", "https://www.google.pl/");
        Allure.tms("tms - ustawione w Allure.tms", "https://www.google.pl/");
        Allure.link("link - ustawione w Allure.link", "https://www.google.pl/");

        Allure.getLifecycle().addAttachment(attachment.getName(), "text/plain", ".txt", attachmentBytes);
        Allure.getLifecycle().writeTestCase(testResult1.getUuid());
        Allure.getLifecycle().scheduleTestCase(testResult2);
        Allure.getLifecycle().startTestCase(testResult2.getUuid());

        Allure.epic("Epik ustawiony w Allure.epic");
        Allure.feature("Feature ustawiona w Allure.feature");
        Allure.story("Story ustawiona w Allure.story");
        Allure.suite("Suite ustawione w Allure.suite");
        Allure.label("tag", "labelValue2 tag - ustawione w Allure.label");
        Allure.parameter("Parametr 1", "Wartość 1 - ustawione w Allure.parameter");
        Allure.parameter("Parametr 2", "Wartość 2 - ustawione w Allure.parameter");
        Allure.issue("issue - ustawione w Allure.issue", "https://www.google.pl/");
        Allure.tms("tms - ustawione w Allure.tms", "https://www.google.pl/");
        Allure.link("link - ustawione w Allure.link", "https://www.google.pl/");

        Allure.getLifecycle().writeTestCase(testResult2.getUuid());
        Allure.getLifecycle().stopTestContainer(child.getUuid());
        Allure.getLifecycle().writeTestContainer(child.getUuid());
        Allure.getLifecycle().stopTestContainer(root.getUuid());
        Allure.getLifecycle().writeTestContainer(root.getUuid());


    }


    @Test
    public void testAllure() {

        generateTreeStructureReport();
    }
}
