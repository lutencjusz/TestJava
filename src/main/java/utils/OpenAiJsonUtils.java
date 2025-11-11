package utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import com.openai.models.responses.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OpenAiJsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new KotlinModule())
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private OpenAiJsonUtils() {}

    public static String toJson(Response response) {
        try {
            return MAPPER.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Nie udało się zserializować Response do JSON", e);
        }
    }

    public static String toPrettyJson(Response response) {
        try {
            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Nie udało się zserializować Response do ładnego JSON", e);
        }
    }

    public static String extractAssistantText(String json) {
        try {
            JsonNode root = MAPPER.readTree(json);

            // 1) Szukaj output[*].content[*].text
            JsonNode output = root.path("output");
            if (output.isArray()) {
                List<String> texts = new ArrayList<>();
                for (JsonNode outElem : output) {
                    JsonNode content = outElem.path("content");
                    if (content.isArray()) {
                        for (JsonNode c : content) {
                            JsonNode t = c.path("text");
                            if (t.isTextual()) {
                                texts.add(t.asText());
                            }
                        }
                    }
                }
                if (!texts.isEmpty()) {
                    return String.join("\n", texts);
                }
            }

            // 2) Fallback: top-level "text"
            JsonNode topText = root.path("text");
            if (topText.isTextual()) {
                return topText.asText();
            } else if (topText.isObject() || topText.isArray()) {
                return MAPPER.writeValueAsString(topText);
            }

            return null;
        } catch (IOException e) {
            throw new RuntimeException("Błąd parsowania JSON", e);
        }
    }
}
