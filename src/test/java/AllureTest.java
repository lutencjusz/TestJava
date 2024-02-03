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
        String testResult1Uuid = UUID.randomUUID().toString();
        TestResult testResult1 = new TestResult()
                .setUuid(testResult1Uuid)
                .setName("Test Result 1 - określony w testResult1.setName")
                .setFullName("Test Result 1 - określony w testResult1.setFullName")
                .setDescription("Wystąpił błąd podczas wykonywania testu 1 - określony w testResult1.setDescription")
                .setStatusDetails(new StatusDetails().setMessage("Wystąpił błąd podczas wykonywania testu 1"))
                .setDescriptionHtml("<i style='color:red;'>tekst 1 określony testResult1.setDescriptionHtml</i>")
                .setStatus(Status.FAILED);

        // Dodawanie kroków do wyniku testu
        String stepUuid1 = UUID.randomUUID().toString();
        StepResult stepResult1 = new StepResult().setName("Step 1").setStatus(Status.PASSED);

        // Dodawanie podkroków do StepResult1
        String substepUuid1 = UUID.randomUUID().toString();
        StepResult substepResult1 = new StepResult().setName("Substep 1").setStatus(Status.PASSED);

        String subsubstepUuid1 = UUID.randomUUID().toString();
        StepResult subsubstepResult1 = new StepResult().setName("Subsubstep 1").setStatus(Status.PASSED);
        Allure.getLifecycle().startStep(substepUuid1, subsubstepUuid1, subsubstepResult1);
        Allure.getLifecycle().stopStep(subsubstepUuid1);

        String subsubstepUuid2 = UUID.randomUUID().toString();
        StepResult subsubstepResult2 = new StepResult()
                .setName("Subsubstep 2")
                .setStatus(Status.FAILED);

        // Dodawanie szczegółów statusu
        StatusDetails statusDetails = new StatusDetails()
                .setMessage("Wystąpił błąd podczas wykonywania Subsubstep 2")
                .setTrace("""
                        SLF4J: Class path contains multiple SLF4J providers.
                        SLF4J: Found provider [org.slf4j.simple.SimpleServiceProvider@1176dcec]
                        SLF4J: Found provider [org.slf4j.impl.JBossSlf4jServiceProvider@120d6fe6]
                        SLF4J: See https://www.slf4j.org/codes.html#multiple_bindings for an explanation.
                        SLF4J: Actual provider is of type [org.slf4j.simple.SimpleServiceProvider@1176dcec]"""); // Tutaj możesz dodać pełny stack trace błędu

        subsubstepResult2.setStatusDetails(statusDetails);

        // Dodawanie parametrów do SubsubstepResult2
        Parameter parameter1 = new Parameter()
                .setName("Parametr 1")
                .setValue("Wartość 1");

        Parameter parameter2 = new Parameter()
                .setName("Parametr 2")
                .setValue("Wartość 2");

        subsubstepResult2.getParameters().add(parameter1);
        subsubstepResult2.getParameters().add(parameter2);

        // Ustawianie etapu dla SubsubstepResult2
        subsubstepResult2.setStage(Stage.FINISHED);

        // Dodawanie załącznika do SubsubstepResult2
        String attachmentContent = "Treść załącznika";
        byte[] attachmentBytes = attachmentContent.getBytes(StandardCharsets.UTF_8);
        String attachmentUuid = UUID.randomUUID().toString();

        Attachment attachment = new Attachment()
                .setName("Załącznik 1")
                .setType("text/plain")
                .setSource(attachmentUuid + ".txt");

        subsubstepResult2.getAttachments().add(attachment);

        Allure.getLifecycle().startStep(substepUuid1, subsubstepUuid2, subsubstepResult2);
        Allure.getLifecycle().stopStep(subsubstepUuid2);

        substepResult1.getSteps().add(subsubstepResult1);
        substepResult1.getSteps().add(subsubstepResult2);

        Allure.getLifecycle().startStep(stepUuid1, substepUuid1, substepResult1);
        Allure.getLifecycle().stopStep(substepUuid1);

        stepResult1.getSteps().add(substepResult1);

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
