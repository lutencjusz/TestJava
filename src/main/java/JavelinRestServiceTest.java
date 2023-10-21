import com.google.gson.Gson;
import io.javalin.Javalin;

import java.util.HashMap;

public class JavelinRestServiceTest {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);
        Gson gson = new Gson();

        app.get("/hello", ctx -> {
            HashMap<String, String> response = new HashMap<>();
            response.put("message", "witaj hello");

            ctx.result(gson.toJson(response));
            ctx.contentType("application/json");
        });
    }
}
