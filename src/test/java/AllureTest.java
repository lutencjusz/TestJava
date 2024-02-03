import io.qameta.allure.Allure;
import io.qameta.allure.model.*;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
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

        Label label1 = new Label()
                .setName("issue")
                .setValue("labelValue1");

        Label label2 = new Label()
                .setName("issue")
                .setValue("labelValue2");

        Parameter parameter1 = new Parameter()
                .setName("testResult1.Parametr 1")
                .setValue("testResult1.Wartość 1");

        Parameter parameter2 = new Parameter()
                .setName("testResult1.Parametr 2")
                .setValue("testResult1.Wartość 2");

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

        testResult1.getLabels().add(label1);
        testResult1.getLabels().add(label2);

        testResult1.getParameters().add(parameter1);
        testResult1.getParameters().add(parameter2);

        testResult1.getAttachments().add(attachment2);

        // Dodawanie kroków do wyniku testu
        String stepUuid1 = UUID.randomUUID().toString();
        StepResult stepResult1 = new StepResult().setName(testResult1.getTestCaseName() + ": Step 1").setStatus(Status.PASSED);

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
                .setName("Parametr 1")
                .setValue("Wartość 1");

        Parameter parameterSubsubstepResult2 = new Parameter()
                .setName("Parametr 2")
                .setValue("Wartość 2");

        subsubstepResult2.getParameters().add(parameterSubsubstepResult1);
        subsubstepResult2.getParameters().add(parameterSubsubstepResult2);

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

        // Zapisywanie wyników do raportu Allure
        Allure.getLifecycle().startTestContainer(root);
        Allure.getLifecycle().startTestContainer(child);
        Allure.getLifecycle().scheduleTestCase(testResult1);
        Allure.getLifecycle().startTestCase(testResult1.getUuid());
        Allure.getLifecycle().addAttachment(attachment.getName(), "text/plain", ".txt", attachmentBytes);
        Allure.getLifecycle().writeTestCase(testResult1.getUuid());
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
