package pl.pwr.ui;

import pl.pwr.quiz.QuizManager;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class QuizApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private ResourceBundle messages;

    public QuizApp() {
        super();
        loadResourceBundle();
        initializeUI();
    }

    private void loadResourceBundle() {
        Locale locale = new Locale("pl", "PL");
        Locale.setDefault(locale);
        messages = ResourceBundle.getBundle("Messages", locale);
        setTitle(messages.getString("app.title"));
    }

    private void initializeUI() {
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel startPanel = new JPanel();
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.PAGE_AXIS));

        JButton startQuizButton = new JButton(messages.getString("button.startQuiz"));
        startQuizButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startQuizButton.setFocusPainted(false);

        startQuizButton.addActionListener(e -> cardLayout.show(cardPanel, "QuizPanel"));

        startPanel.add(Box.createVerticalGlue());
        startPanel.add(startQuizButton);
        startPanel.add(Box.createVerticalGlue());

        cardPanel.add(startPanel, "StartPanel");
        QuizPanel quizPanel = new QuizPanel(new QuizManager());
        cardPanel.add(quizPanel, "QuizPanel");

        getContentPane().add(cardPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(QuizApp::new);
    }
}
