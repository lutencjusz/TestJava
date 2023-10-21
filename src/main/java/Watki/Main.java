package Watki;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    private static String formatMilisecondsString(Duration duration) {
        return duration.getSeconds() + "."
                + String.format("%03d", TimeUnit.MILLISECONDS.convert(duration.getNano(), TimeUnit.NANOSECONDS));
    }

    public static void main(String[] args) {

        boolean result = true;
        final int THREAD_MAX = 10; // liczba wątków
        List<Watki> threads = new ArrayList<>();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
        LocalDateTime beginTime = LocalDateTime.now();
        System.out.println("Uruchamiam wątki o " + dtf.format(beginTime));

        for (int i = 1; i < THREAD_MAX; i++) {
            Watki t = new Watki(i + "");
            threads.add(t);
            t.start();
        }
        for (Watki t : threads) {
            try {
                t.join();
                if (result) result = t.getResult();
            } catch (InterruptedException e) {
                System.out.println("Wystąpił błąd zakończeniu wątka: " + t.getName());
            }
        }
        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(beginTime, endTime);
        System.out.println("Wszystkie wątki zakończone o " + dtf.format(endTime) + " po "
                + formatMilisecondsString(duration) + " sekundach");
        System.out.println("Wynik: " + result);
    }
}
