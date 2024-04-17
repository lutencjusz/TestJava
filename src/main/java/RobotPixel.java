import org.fusesource.jansi.Ansi;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

public class RobotPixel {
    private static void showColor(int centerX, int centerY, int radius, int x, int y, int color) {
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;
        System.out.print("x: " + (centerX - radius + x) + " y: " + (centerY - radius + y) + " color: " + color + " red: " + red + " green: " + green + " blue: " + blue);
        System.out.println(Ansi.ansi().fgRgb(red, green, blue).a(" █").reset());
    }

    public static int convertRGBtoInt(int red, int green, int blue) {
        // Przyjmujemy, że alfa (przezroczystość) ma wartość maksymalną 255, co oznacza pełną nieprzezroczystość
        int alpha = 255; // Możesz dostosować wartość alfa, jeśli potrzebujesz różnych poziomów przezroczystości

        // Połączenie kanałów RGBA do jednej wartości
        int rgb = (alpha << 24) | (red << 16) | (green << 8) | blue;

        return rgb;
    }


    public static void main(String[] args) throws AWTException {

        final int COLOR_RED = -2150279;
        final int COLOR_BLUE = -16734499;
        final int COLOR_WHITE = -18;

        Robot robot = new Robot();
        int centerX = 1300; // Przykładowy środek okręgu X
        int centerY = 400; // Przykładowy środek okręgu Y
        int radius = 200; // Przykładowy promień

        int reareRgb = convertRGBtoInt(46, 149, 211); //niebieski kolor
//        int reareRgb = convertRGBtoInt(223, 48, 121); //różowy
//        int reareRgb = convertRGBtoInt(112, 28, 34); //czerwony
        //niebieski kolor
        showColor(centerX, centerY, radius, 0, 0, reareRgb);
        // Zdefiniowanie zakresu do analizy
        Rectangle captureRect = new Rectangle(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
        BufferedImage screenCapture = robot.createScreenCapture(captureRect);

        // Przeszukiwanie pikseli w poszukiwaniu koloru czerwonego
        int targetColor = Color.RED.getRGB();
        for (int x = 0; x < screenCapture.getWidth(); x++) {
            for (int y = 0; y < screenCapture.getHeight(); y++) {
                int color = screenCapture.getRGB(x, y);
                if (color != -14606047 // Szary kolor
                ) {
                    robot.mouseMove(centerX - radius + x, centerY - radius + y);
                    int distance = (int) Math.sqrt(Math.pow(x - radius, 2) + Math.pow(y - radius, 2));
                    if (distance == radius){
                        screenCapture.setRGB(x, y, COLOR_RED);
                    }
                    if (color == reareRgb) {
                        if (distance <= radius) {
                            robot.mouseMove(centerX - radius + x, centerY - radius + y);
                            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                            showColor(centerX, centerY, radius, x, y, color);
                        }
                    }
                }
            }
        }
    }
}
