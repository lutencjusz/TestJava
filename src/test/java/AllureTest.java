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

        // Tworzenie wyników testu
        String testResultUuid = UUID.randomUUID().toString();
        TestResult testResult = new TestResult()
                .setUuid(testResultUuid)
                .setName("Test Result 1")
                .setStatus(Status.PASSED)
                .setDescription("Opis testu określony w TestResult.setDescription")
                .setDescriptionHtml("<i style='color:red;'>Opis testu określony w TestResult.setDescription</i>")
                .setFullName("Test Result określony w polu testResult.setFullName")
                .setHistoryId("Test Result określony w polu testResult.setHistoryId")
                .setStage(Stage.FINISHED);


        // Dodawanie kroków do wyniku testu
        String stepUuid1 = UUID.randomUUID().toString();
        StepResult stepResult1 = new StepResult().setName("Step 1").setStatus(Status.PASSED);
        Allure.getLifecycle().startStep(testResultUuid, stepUuid1, stepResult1);
        Allure.getLifecycle().stopStep(stepUuid1);

        // Dodawanie podkroków do StepResult1
        String substepUuid1 = UUID.randomUUID().toString();
        StepResult substepResult1 = new StepResult().setName("Substep 1").setStatus(Status.PASSED);
        Allure.getLifecycle().startStep(stepUuid1, substepUuid1, substepResult1);
        Allure.getLifecycle().stopStep(substepUuid1);

        // Dodawanie podkroków do podkroku
        String subsubstepUuid1 = UUID.randomUUID().toString();
        StepResult subsubstepResult1 = new StepResult()
                .setName("Subsubstep 1")
                .setStatus(Status.PASSED);
        Allure.getLifecycle().startStep(substepUuid1, subsubstepUuid1, subsubstepResult1);
        Allure.getLifecycle().stopStep(subsubstepUuid1);

        String subsubstepUuid2 = UUID.randomUUID().toString();
        StepResult subsubstepResult2 = new StepResult()
                .setName("Subsubstep 2")
                .setDescription("Description Subsubstep 2")
                .setStatus(Status.FAILED);
        Allure.getLifecycle().startStep(substepUuid1, subsubstepUuid2, subsubstepResult2);
        Allure.getLifecycle().stopStep(subsubstepUuid2);

        // Dodawanie podkroków do podkroku podkroków
        String subsubsubstepUuid1 = UUID.randomUUID().toString();
        StepResult subsubsubstepResult1 = new StepResult()
                .setName("SubsubsubStep 1")
                .setDescription("Description SubsubsubStep 1")
                .setStatus(Status.PASSED);
        Allure.getLifecycle().startStep(subsubstepUuid1, subsubsubstepUuid1, subsubsubstepResult1);
        Allure.getLifecycle().stopStep(subsubsubstepUuid1);

        // Dodawanie szczegółów statusu do subsubsubstepResult2
        StatusDetails statusDetails = new StatusDetails()
                .setMessage("Test przeszedł z ostrzeżeniami")
                .setTrace("""
                        SLF4J: Class path contains multiple SLF4J providers.
                        SLF4J: Found provider [org.slf4j.simple.SimpleServiceProvider@5606c0b]
                        SLF4J: Found provider [org.slf4j.impl.JBossSlf4jServiceProvider@80ec1f8]
                        SLF4J: See https://www.slf4j.org/codes.html#multiple_bindings for an explanation.
                        SLF4J: Actual provider is of type [org.slf4j.simple.SimpleServiceProvider@5606c0b]
                        [main] ERROR io.qameta.allure.AllureLifecycle - Could not start step: no test case running
                        [main] ERROR io.qameta.allure.AllureLifecycle - Could not stop step: step with uuid 8873de65-78d2-4b0c-b690-575f5879bea0 not found
                        [main] ERROR io.qameta.allure.AllureLifecycle - Could not start step: no test case running
                        [main] ERROR io.qameta.allure.AllureLifecycle - Could not stop step: step with uuid e5fafab4-ad7b-4e73-8e4a-1b2993f7fdb6 not found"""); // Tutaj możesz dodać pełny stack trace błędu

        // Dodawanie parametrów do subsubsubstepResult2
        Parameter parameter1 = new Parameter()
                .setName("Parametr 1")
                .setValue("Wartość 1");

        Parameter parameter2 = new Parameter()
                .setName("Parametr 2")
                .setValue("Wartość 2");

        // Dodawanie załącznika do subsubsubstepResult2
        String attachmentContent = "Treść załącznika";
        byte[] attachmentBytes = attachmentContent.getBytes(StandardCharsets.UTF_8);
        String attachmentUuid = UUID.randomUUID().toString();

        Attachment attachment = new Attachment()
                .setName("Załącznik 1")
                .setType("text/plain")
                .setSource(attachmentUuid + ".txt");

        String subsubsubstepUuid2 = UUID.randomUUID().toString();
        StepResult subsubsubstepResult2 = new StepResult()
                .setName("SubsubsubStep 2")
                .setDescription("Description SubsubsubStep 2")
                .setStatusDetails(statusDetails)
                .setDescriptionHtml("<b>Description SubsubsubStep 2</b>")
                .setStatus(Status.BROKEN)
                .setStage(Stage.INTERRUPTED);

        subsubsubstepResult2.getAttachments().add(attachment);
        subsubsubstepResult2.getParameters().add(parameter1);
        subsubsubstepResult2.getParameters().add(parameter2);

        Allure.getLifecycle().startStep(subsubstepUuid1, subsubsubstepUuid2, subsubsubstepResult2);
        Allure.getLifecycle().stopStep(subsubsubstepUuid2);

        String substepUuid2 = UUID.randomUUID().toString();
        StepResult substepResult2 = new StepResult()
                .setName("Substep 2")
                .setStatus(Status.SKIPPED)
                .setStage(Stage.RUNNING);
        Allure.getLifecycle().startStep(stepUuid1, substepUuid2, substepResult2);
        Allure.getLifecycle().stopStep(substepUuid2);

        String substepUuid3 = UUID.randomUUID().toString();
        StepResult substepResult3 = new StepResult()
                .setName("Substep 3")
                .setStatus(Status.PASSED);
        Allure.getLifecycle().startStep(stepUuid1, substepUuid3, substepResult3);
        Allure.getLifecycle().stopStep(substepUuid3);

        // Dodawanie kroku2 do wyniku testu
        String stepUuid2 = UUID.randomUUID().toString();
        System.out.println("stepUuid2: " + stepUuid2);
        StepResult stepResult2 = new StepResult()
                .setName("Step 2")
                .setStatus(Status.PASSED);
        Allure.getLifecycle().startStep(testResultUuid, stepUuid2, stepResult2);
        Allure.getLifecycle().stopStep(stepUuid2);

        // Dodawanie kroków do wyniku testu
        subsubstepResult1.getSteps().add(subsubsubstepResult1);
        subsubstepResult1.getSteps().add(subsubsubstepResult2);

        substepResult1.getSteps().add(subsubstepResult1);
        substepResult1.getSteps().add(subsubstepResult2);

        stepResult1.getSteps().add(substepResult1);
        stepResult1.getSteps().add(substepResult2);
        stepResult1.getSteps().add(substepResult3);

        testResult.getSteps().add(stepResult1);
        testResult.getSteps().add(stepResult2);

        // Zapisywanie wyników do raportu Allure
        Allure.getLifecycle().startTestContainer(root);
        Allure.getLifecycle().startTestContainer(child);
        Allure.getLifecycle().scheduleTestCase(testResult);
        Allure.getLifecycle().startTestCase(testResultUuid);
        Allure.getLifecycle().scheduleTestCase(testResult);
        Allure.getLifecycle().addAttachment(attachmentUuid, "text/plain", ".txt", attachmentBytes);
        Allure.getLifecycle().writeTestCase(testResultUuid);
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
