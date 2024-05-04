import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class MarkdownAWTDisplay extends JFrame implements ActionListener {
    final static double MARGIN_IN_PERCENT = 0.1;
    final int WIDTH = 800;
    final int HEIGHT = 700;
    private JTextPane textPane;
    private TextField questionTextField;
    private Button sendButton;
    private Parser parser;
    private HtmlRenderer renderer;
    private String model = "gpt-3.5-turbo";

    public MarkdownAWTDisplay() {
        setTitle("Chat GPT Markdown Display");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        Insets insets = new Insets(10, 10, 10, 10);

        Panel marginLeft = new Panel();
        marginLeft.setPreferredSize(new Dimension((int) (WIDTH * MARGIN_IN_PERCENT), HEIGHT));

        Panel marginRight = new Panel();
        marginRight.setPreferredSize(new Dimension((int) (WIDTH * MARGIN_IN_PERCENT), HEIGHT));

        Panel marginDown = new Panel();
        marginDown.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * MARGIN_IN_PERCENT)));

        Panel marginRadioDown = new Panel();
        marginDown.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * MARGIN_IN_PERCENT)));

        CheckboxGroup cbg = new CheckboxGroup();
        Checkbox radio1 = new Checkbox("ChatGPT 3.5", cbg, true);
        Checkbox radio2 = new Checkbox("ChatGPT 4", cbg, false);
        Checkbox radio3 = new Checkbox("ChatGPT 4-turbo", cbg, false);
        Container radioContainer = new Container();
        radioContainer.setLayout(new BoxLayout(radioContainer, BoxLayout.Y_AXIS));
        radioContainer.add(radio1);
        radioContainer.add(radio2);
        radioContainer.add(radio3);
        radioContainer.add(marginRadioDown);
        radio1.addItemListener(radioListener);
        radio2.addItemListener(radioListener);
        radio3.addItemListener(radioListener);

        Label questionLabel = new Label("Wpisz treść pytania:");
        questionLabel.setSize(200, 30);

        Container questionRatioContainer = new Container();
        questionRatioContainer.setLayout(new FlowLayout(FlowLayout.LEFT));
        questionRatioContainer.add(Box.createRigidArea(new Dimension((int) (HEIGHT * 0.7), 0)));
        questionRatioContainer.add(radioContainer);

        questionTextField = new TextField();
        questionTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        questionTextField.setPreferredSize(
                new Dimension((int) (getWidth() * (100 - MARGIN_IN_PERCENT)), getHeight() * 2 / 10));

        sendButton = new Button("Wyślij");
        sendButton.setMaximumSize(new Dimension(100, 30));
        sendButton.addActionListener(this);

        textPane = new JTextPane();
        textPane.setFont(new Font("Arial", Font.PLAIN, 14));
        textPane.setContentType("text/html");
        textPane.setEditable(false);
        textPane.setPreferredSize(new Dimension(WIDTH, HEIGHT * 3 / 5));
        textPane.setMargin(insets);
        JScrollPane scrollPane = new JScrollPane(textPane);

        Container iframe = new Container();
        iframe.setPreferredSize(new Dimension((int) (WIDTH * 0.8), HEIGHT));
        iframe.setLayout(new BoxLayout(iframe, BoxLayout.Y_AXIS));
        iframe.add(questionRatioContainer);
        iframe.add(questionLabel);
        iframe.add(questionTextField);
        iframe.add(Box.createRigidArea(new Dimension(0, 10)));
        iframe.add(sendButton);
        iframe.add(Box.createRigidArea(new Dimension(0, 10)));
//        add(Box.createVerticalGlue());
        iframe.add(scrollPane);
        iframe.add(marginDown);

        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(marginLeft);
        add(iframe);
        add(marginRight);

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

    ItemListener radioListener = e -> {
        Checkbox checked = (Checkbox) e.getSource();
        String selectedRadio = checked.getLabel();
        switch (selectedRadio) {
            case "ChatGPT 3.5":
                model = "gpt-3.5-turbo";
                break;
            case "ChatGPT 4":
                model = "gpt-4";
                break;
            case "ChatGPT 4-turbo":
                model = "gpt-4-turbo";
                break;
        }
    };

    @Override
    public void actionPerformed(ActionEvent e) {
        String question = questionTextField.getText();
        questionTextField.setEditable(false);
        sendButton.setEnabled(false);
        String markdownText = ChatGPT.getChatGPTMessage(question, model, false);
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
