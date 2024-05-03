import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MarkdownAWTDisplay extends JFrame implements ActionListener {
    private JTextPane textPane;
    private TextField questionTextField;
    private Parser parser;
    private HtmlRenderer renderer;

    public MarkdownAWTDisplay() {
        setTitle("Chat GPT Markdown Display");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        Label questionLabel = new Label("Wpisz treść pytania:");
        questionTextField = new TextField();
        questionTextField.setColumns(50);

        Button sendButton = new Button("Wyślij");
        sendButton.setPreferredSize(new Dimension(100, 30));
        sendButton.addActionListener(this);

        textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textPane);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(questionLabel);
        add(questionTextField);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(sendButton);
        add(Box.createVerticalGlue());
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

    public void displayQuestion(String question) {
        questionTextField.setText(question);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String question = questionTextField.getText();
        String markdownText = ChatGPT.getChatGPTMessage(question, "gpt-3.5-turbo-0125", false);
        displayMarkdown(markdownText);
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
