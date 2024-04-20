import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import model.Model;
import org.junit.jupiter.api.Test;
import org.testng.Assert;
import org.testng.Reporter;

import java.util.List;

public class RestAssuredTest {
    private static final Integer EXP_VALUE = 3;
    private static final Integer VALUE = 3;

    @Test
    public void sampleRESTTest() {
        JsonPath response = RestAssured.given()
                .when()
                .baseUri("https://jsonplaceholder.typicode.com")
                .get("/posts")
                .then()
                .statusCode(200)
                .extract().body().jsonPath();
        List<Model> posts = response.getList("", Model.class);

        for (Model item : posts) {
            System.out.println("Tytuł(" + item.getId() + "): " + item.getTitle());
        }

        List<Model> searchedItem = posts.stream()
                .filter(i -> i.getId().equals(VALUE))
                .toList();

        Assert.assertEquals(searchedItem.get(0).getId(), EXP_VALUE);
        Reporter.log("Znaleziono id: " + searchedItem.get(0).getId() + ", tytuł: " + searchedItem.get(0).getTitle());
    }
}
