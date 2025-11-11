import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import utils.OpenAiJsonUtils;

public class OpenAi {

// do uruchomienia metody main konieczny jest ustawiony klucz API w zmiennej środowiskowej OPENAI_API_KEY;

    public static void main(String[] args) {
        OpenAIClient client = OpenAIOkHttpClient.fromEnv();

        ResponseCreateParams params = ResponseCreateParams.builder()
                .input("Kiedy nastąpił wybuch drugiej wojny światowej w Polsce?")
                .model("o4-mini")
                .build();

        Response response = client.responses().create(params);
        System.out.println("Odpowiedź: " + OpenAiJsonUtils.extractAssistantText(OpenAiJsonUtils.toJson(response)));
    }
}
