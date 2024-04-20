import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ChatGPT {
    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMalformed()
            .ignoreIfMissing()
            .load();

    private static final String CHAT_GPT_KEY = dotenv.get("CHAT_GPT_KEY");
    private static final String ENDPOINT = "https://api.openai.com/v1/chat/completions";

    public static void main(String[] args) {
        RestAssured.baseURI = ENDPOINT;
        RequestSpecification request = RestAssured.given();

        String requestBody = "{"
                + "\"model\": \"gpt-3.5-turbo\","
                + "\"messages\": [{\"role\": \"user\", \"content\": \"Hello, world!\"}]"
                + "}";

        Response response = request
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + CHAT_GPT_KEY)
                .body(requestBody)
                .post();

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
    }
}
