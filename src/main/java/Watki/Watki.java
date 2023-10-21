package Watki;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class Watki extends Thread {

    String title;
    boolean result;

    public Watki(@NotNull String title) {
        super();
        this.title = title;
    }

    private String formatMilisecondsString(Duration duration) {
        return duration.getSeconds() + "."
                + String.format("%03d", TimeUnit.MILLISECONDS.convert(duration.getNano(), TimeUnit.NANOSECONDS));
    }

    public void run() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
        LocalDateTime beginTime = LocalDateTime.now();
        System.out.println("Zapytanie z wątku " + title + " uruchomione o " + dtf.format(beginTime));
        Response response = RestAssured.given().get("https://jsonplaceholder.typicode.com/todos/" + title);
        System.out.println("Wątek: " + title + " pobrał odpowiedź: " + response.jsonPath().getString("title"));
        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(beginTime, endTime);
        System.out.println("Zapytanie z wątku  " + title + " (" + Thread.currentThread().threadId() + ") zakończone o " + dtf.format(endTime) + " po "
                + formatMilisecondsString(duration) + " sekundach");
        result = response.getStatusCode() == 200;
    }

    public boolean getResult() {
        return result;
    }
}