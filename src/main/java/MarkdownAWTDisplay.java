import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MarkdownAWTDisplay extends JFrame implements ActionListener {
    final int WIDTH = 800;
    final int HEIGHT = 700;
    private JTextPane textPane;
    private TextField questionTextField;
    private Button sendButton;
    private Parser parser;
    private HtmlRenderer renderer;

    public MarkdownAWTDisplay() {
        setTitle("Chat GPT Markdown Display");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        Insets insets = new Insets(10, 10, 10, 10);

        Label questionLabel = new Label("Wpisz treść pytania:");
        questionLabel.setSize(200, 30);
        questionTextField = new TextField();
        questionTextField.setPreferredSize(
                new Dimension(getWidth() * 9 / 10, getHeight() * 2 / 10));

        sendButton = new Button("Wyślij");
        sendButton.setMaximumSize(new Dimension(100, 30));
        sendButton.addActionListener(this);

        textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setEditable(false);
        textPane.setPreferredSize(new Dimension(WIDTH, HEIGHT * 3 / 5));
        textPane.setMargin(insets);
        JScrollPane scrollPane = new JScrollPane(textPane);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(questionLabel);
        add(questionTextField);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(sendButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
//        add(Box.createVerticalGlue());
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
        questionTextField.setEditable(false);
        sendButton.setEnabled(false);
        String markdownText = ChatGPT.getChatGPTMessage(question, "gpt-3.5-turbo", false);
        displayMarkdown(markdownText);
        questionTextField.setEditable(true);
        sendButton.setEnabled(true);
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
