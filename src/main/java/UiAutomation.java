import com.sun.jna.platform.win32.WinDef;
import mmarquee.automation.AutomationException;
import mmarquee.automation.Element;
import mmarquee.automation.UIAutomation;
import mmarquee.automation.controls.Panel;
import mmarquee.automation.controls.Window;
import mmarquee.uiautomation.TreeScope;

import java.util.List;

import static java.lang.Thread.sleep;

public class UiAutomation {

    public static void main(String[] args) throws AutomationException, InterruptedException {
        Thread.sleep(10000);
        UIAutomation automation = UIAutomation.getInstance();
        Element el = automation.getFocusedElement();
        WinDef.POINT p=el.getClickablePoint();
        System.out.println("x: "+p.x+" y: "+p.y);

        List<Window> windows = automation.getDesktopWindows();
        List<Panel> panels = automation.getDesktopObjects();
        Panel panelS = automation.getDesktopObject("Spotify Free");
        for (Window window : windows) {
            System.out.println("window: " + window.getName());
        }
        for (Panel panel : panels) {
            System.out.println("panel:" + panel.getName());
        }
        System.out.println("focused Element: " + el.getName());
        System.out.println("Spotify: " + panelS.getName());
        System.out.println("Spotify window: " + panelS.getAutomation().getDesktopWindows().get(0).getName());
    }


}
