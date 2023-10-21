import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class OpenSaveWindow extends JFrame {

    public OpenSaveWindow() {
        this.setTitle("Okno zapisu/odczytu");
        this.setBounds(250, 250, 300, 100);

        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        jFileChooser.setMultiSelectionEnabled(true);

        JButton buttonLoad = new JButton("Otwórz");
        buttonLoad.addActionListener(e -> {
            jFileChooser.setDialogTitle("Otwórz");
            jFileChooser.setFileFilter(new FileNameExtensionFilter("Pliki tekstowe", "txt", "xml"));
            jFileChooser.setFileFilter(new FileNameExtensionFilter("Pliki konfiguracyjne",  "properties"));
            if (jFileChooser.showDialog(rootPane, "Wybierz pliki do ZIP") == 0)
                createZipFiles(jFileChooser.getSelectedFiles());
        });

        JButton buttonSave = new JButton("Zapisz");
        buttonSave.addActionListener(e -> {
            jFileChooser.setDialogTitle("Zapisz");
            int result = jFileChooser.showSaveDialog(rootPane);
            System.out.println("Wynik zapisu: " + result);
        });

        JPanel jPanel = new JPanel();
        jPanel.add(buttonLoad);
        jPanel.add(buttonSave);
        this.getContentPane().add(jPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void createZipFiles(File[] files) {
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
        }
    }

    public static void main(String[] args) {
        new OpenSaveWindow().setVisible(true);
    }
}
