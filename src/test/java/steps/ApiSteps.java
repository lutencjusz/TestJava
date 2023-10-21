package steps;

import io.cucumber.java.en.Given;
import pageobjects.ApiPageObjects;

public class ApiSteps {

    ApiPageObjects apiPageObjects = new ApiPageObjects();
    @Given("[API] Użytkownik pobiera dane")
    public void getData(){
        apiPageObjects.getIdComments();
        apiPageObjects.getIdPosts();
    }
}
