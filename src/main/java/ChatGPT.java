import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.fusesource.jansi.Ansi;

import java.awt.*;

public class ChatGPT {
    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMalformed()
            .ignoreIfMissing()
            .load();

    private static final String CHAT_GPT_KEY = dotenv.get("CHAT_GPT_KEY");
    private static final String CHAT_GPT_ENDPOINT = "https://api.openai.com/v1/chat/completions";
    private static final String JAVELIN_ENDPOINT = "http://localhost:7000";

    /**
     * Demo Javelin REST service, do uruchomienia wymagane jest uruchomienie projektu JavelinRestServiceTest
     * (C:\Data\Java\TestJava\src\main\java\JavelinRestServiceTest.java)
     */
    public static void demoJavelin() {
        RestAssured.baseURI = JAVELIN_ENDPOINT;
        RequestSpecification request = RestAssured.given();

        Response response = request
                .header("Content-Type", "application/json")
                .get("/hello");

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("message: " + response.jsonPath().get("message"));
    }

    public static String getChatGPTMessage(String message, String model, boolean isStrongerLogging) {
        RestAssured.baseURI = CHAT_GPT_ENDPOINT;
        RequestSpecification request = RestAssured.given();

        String requestBody = "{"
                + "\"model\": \"" + model + "\","
                + "\"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]"
                + "}";

        Response response = request
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + CHAT_GPT_KEY)
                .body(requestBody)
                .post();

        System.out.println(Ansi.ansi().fg(Ansi.Color.BLUE).a(model).reset() + " Response Status Code: "
                + Ansi.ansi().fg(response.getStatusCode() == 200 ? Ansi.Color.GREEN : Ansi.Color.RED).a(response.getStatusCode()).reset());
        if (isStrongerLogging) {
            System.out.println("Response Body: " + response.getBody().asString());
        }
        return response.jsonPath().getString("choices[0].message.content");
    }

    public static void main(String[] args) {
        String markdownText="";
        EventQueue.invokeLater(() -> {
            MarkdownAWTDisplay ex = new MarkdownAWTDisplay();
            ex.setVisible(true);
            String model = "gpt-3.5-turbo-0125";
            String message = "Podaj przykład panelu AWT w Javie z pytaniem, odpowiedzią i przyciskiem 'Wyślij'";
            ex.displayQuestion(message);
//            markdownText = getChatGPTMessage(message, model, false);
            ex.displayMarkdown(markdownText);
        });
    }
}
