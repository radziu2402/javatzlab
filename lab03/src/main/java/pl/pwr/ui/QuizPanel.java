package pl.pwr.ui;

import pl.pwr.model.Question;
import pl.pwr.quiz.QuizManager;

import javax.swing.*;
import java.awt.*;
import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class QuizPanel extends JPanel {
    private final JTextArea questionArea;
    private final JTextField answerField;
    private final JButton nextQuestionButton;
    private final QuizManager quizManager;
    private ResourceBundle messages;

    public QuizPanel(QuizManager quizManager) {
        this.quizManager = quizManager;
        loadResourceBundle();
        setLayout(new BorderLayout());

        questionArea = new JTextArea();
        questionArea.setWrapStyleWord(true);
        questionArea.setLineWrap(true);
        questionArea.setEditable(false);
        questionArea.setBackground(null);
        questionArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        JScrollPane questionScrollPane = new JScrollPane(questionArea);
        questionScrollPane.setBorder(null);
        questionScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        answerField = new JTextField();
        answerField.setMaximumSize(new Dimension(Integer.MAX_VALUE, answerField.getPreferredSize().height));

        nextQuestionButton = new JButton(messages.getString("button.nextQuestion"));
        nextQuestionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextQuestionButton.setFocusPainted(false);
        nextQuestionButton.addActionListener(e -> handleNextQuestion());

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(questionScrollPane);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(answerField);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(nextQuestionButton);

        add(centerPanel, BorderLayout.CENTER);

        loadNextQuestion();
    }

    private void loadResourceBundle() {
        Locale locale = Locale.getDefault();
        messages = ResourceBundle.getBundle("Messages", locale);
    }

    public void showEndOfQuizDialog() {
        ResourceBundle messages = ResourceBundle.getBundle("Messages", Locale.getDefault());

        int score = quizManager.getScore();

        String patternPoints = messages.getString("points.format");
        ChoiceFormat choicePoints = new ChoiceFormat(patternPoints);
        String pointsWord = choicePoints.format(score);

        String feedbackPattern = messages.getString("score.feedback");
        ChoiceFormat choiceFeedback = new ChoiceFormat(feedbackPattern);
        String feedback = choiceFeedback.format(score);

        String summary = MessageFormat.format(messages.getString("score.summary"), score, pointsWord, feedback);

        JOptionPane.showMessageDialog(null, summary, messages.getString("dialog.endOfQuizTitle"), JOptionPane.INFORMATION_MESSAGE);
    }


    private void handleNextQuestion() {
        if (!quizManager.hasMoreQuestions()) {
            questionArea.setText("");
            answerField.setText("");
            showEndOfQuizDialog();
            String[] options = {messages.getString("option.startNewQuiz"), messages.getString("option.exit")};
            int response = JOptionPane.showOptionDialog(this, messages.getString("dialog.whatToDoNext"), messages.getString("dialog.endOfQuizTitle"),
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);

            if (response == 0) {
                quizManager.resetQuiz();
                loadNextQuestion();
            } else {
                System.exit(0);
            }
            return;
        }

        String userAnswer = answerField.getText().trim();

        if (!userAnswer.isEmpty()) {
            boolean isCorrect = quizManager.checkAnswer(userAnswer);
            Question currentQuestion = quizManager.getCurrentQuestion();

            if (isCorrect) {
                JOptionPane.showMessageDialog(this, generateCorrectAnswerMessage(currentQuestion), messages.getString("dialog.answer"), JOptionPane.INFORMATION_MESSAGE);
                quizManager.setScore(quizManager.getScore() + 1);
            } else {
                JOptionPane.showMessageDialog(this, generateIncorrectAnswerMessage(currentQuestion), messages.getString("dialog.answer"), JOptionPane.ERROR_MESSAGE);
            }
            loadNextQuestion();
        } else {
            JOptionPane.showMessageDialog(this, messages.getString("prompt.enterAnswer"), messages.getString("dialog.noAnswer"), JOptionPane.WARNING_MESSAGE);
        }
    }

    private String generateCorrectAnswerMessage(Question question) {
        return switch (question.getQuestionType()) {
            case AUTHOR ->
                    String.format(messages.getString("prompt.correctAnswerAuthor"), question.getQuestionText().split("\"")[1], question.getCorrectAnswer());
            case PAGES -> {
                int pages = Integer.parseInt(question.getCorrectAnswer());
                String pagesWord = formatPagesWord(pages);
                yield String.format(messages.getString("prompt.correctAnswerPages"), question.getQuestionText().split("\"")[1], pages, pagesWord);
            }
            case TITLE_BY_AUTHOR ->
                    String.format(messages.getString("prompt.correctAnswerTitleByAuthor"), question.getCorrectAnswer(), question.getQuestionText().split(messages.getString("question.separator"))[1].split("\\?")[0].trim());
        };
    }

    private String generateIncorrectAnswerMessage(Question question) {
        return switch (question.getQuestionType()) {
            case AUTHOR ->
                    String.format(messages.getString("prompt.incorrectAnswerAuthor"), question.getQuestionText().split("\"")[1], question.getCorrectAnswer());
            case PAGES -> {
                int pages = Integer.parseInt(question.getCorrectAnswer());
                String pagesWord = formatPagesWord(pages);
                yield String.format(messages.getString("prompt.incorrectAnswerPages"), question.getQuestionText().split("\"")[1], pages, pagesWord);
            }
            case TITLE_BY_AUTHOR ->
                    String.format(messages.getString("prompt.incorrectAnswerTitleByAuthor"), question.getQuestionText().split(messages.getString("question.separator"))[1].split("\\?")[0].trim(), question.getCorrectAnswer());
        };
    }

    public String formatPagesWord(int pages) {
        int lastDigit = pages % 10;
        int lastTwoDigits = pages % 100;

        if (pages == 1) {
            return messages.getString("page.single");
        } else if (lastDigit >= 2 && lastDigit <= 4 && !(lastTwoDigits >= 12 && lastTwoDigits <= 14)) {
            return messages.getString("page.few");
        } else {
            return messages.getString("page.many");
        }
    }

    private void loadNextQuestion() {
        Question question = quizManager.nextQuestion();
        if (question != null) {
            questionArea.setText(question.getQuestionText());
            answerField.setText("");
        } else {
            nextQuestionButton.setEnabled(false);
        }
    }
}
