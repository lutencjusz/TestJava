package utils;

import org.openqa.selenium.WebDriver;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;

public class Pico {
    private final MutablePicoContainer picoContainer = new DefaultPicoContainer();

    public Pico(WebDriver driver) {
        this.picoContainer.addComponent(driver);
    }

    public WebDriver getDriver() {
        return (WebDriver) picoContainer.getComponents().get(0);
    }
}
