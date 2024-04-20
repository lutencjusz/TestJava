package utils;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

import javax.swing.*;
import java.awt.*;

public class MarkdownAWTDisplay extends JFrame {
    private JTextPane textPane;
    private Parser parser;
    private HtmlRenderer renderer;

    public MarkdownAWTDisplay() {
        setTitle("Markdown Display with AWT");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textPane);
        add(scrollPane);

        MutableDataSet options = new MutableDataSet();
        parser = Parser.builder(options).build();
        renderer = HtmlRenderer.builder(options).build();
    }

    public void displayMarkdown(String markdown) {
        Node document = parser.parse(markdown);
        String html = renderer.render(document);
        textPane.setText(html);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MarkdownAWTDisplay ex = new MarkdownAWTDisplay();
            ex.setVisible(true);
            String markdownText = "# Welcome to OpenAI GPT Results\n" +
                    "Here is some **bold** text and some _italic_ text.";
            ex.displayMarkdown(markdownText);
        });
    }
}
