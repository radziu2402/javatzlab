package pl.pwr.quiz;

import pl.pwr.api.BooksApiClient;
import pl.pwr.model.BookInfo;
import pl.pwr.model.Question;

import java.util.*;

import static pl.pwr.model.QuestionType.*;

public class QuizManager {
    private final List<Question> questions = new ArrayList<>();
    private int currentQuestionIndex = -1;
    private int score = 0;
    private final BooksApiClient booksApiClient = new BooksApiClient();
    private ResourceBundle messages;

    public QuizManager() {
        loadResourceBundle();
        loadQuestions();
    }

    private void loadResourceBundle() {
        Locale locale = Locale.getDefault();
        messages = ResourceBundle.getBundle("Messages", locale);
    }
    public void setScore(int score) {
        this.score = score;
    }

    private void loadQuestions() {
        List<BookInfo> books = booksApiClient.fetchRandomBooks(5);

        for (BookInfo book : books) {
            String authorQuestion = String.format(messages.getString("question.author"), book.getTitle());
            questions.add(new Question(authorQuestion, book.getAuthorFirstName() + " " + book.getAuthorLastName(), AUTHOR));

            String pagesQuestion = String.format(messages.getString("question.pages"), book.getTitle());
            questions.add(new Question(pagesQuestion, String.valueOf(book.getPages()), PAGES));

            List<String> titles = new ArrayList<>();
            for (BookInfo otherBook : books) {
                if (!otherBook.getTitle().equals(book.getTitle())) {
                    titles.add(otherBook.getTitle());
                }
            }

            Collections.shuffle(titles);
            titles = titles.subList(0, 3);
            titles.add(book.getTitle());
            Collections.shuffle(titles);

            String questionText = String.format(messages.getString("question.titleByAuthor"), book.getAuthorFirstName() + " " + book.getAuthorLastName()) + "\n";
            char correctLetter = 0;
            StringBuilder optionsDisplay = new StringBuilder();
            String[] optionLetters = {"a", "b", "c", "d"};

            for (int i = 0; i < titles.size(); i++) {
                optionsDisplay.append(optionLetters[i]).append(") ").append(titles.get(i)).append("\n");
                if (titles.get(i).equals(book.getTitle())) {
                    correctLetter = optionLetters[i].charAt(0);
                }
            }

            questionText += optionsDisplay.toString();
            questions.add(new Question(questionText, String.valueOf(correctLetter), TITLE_BY_AUTHOR));
        }
    }


    public void resetQuiz() {
        currentQuestionIndex = -1;
        score = 0;
        loadQuestions();
    }

    public Question nextQuestion() {
        if (hasMoreQuestions()) {
            currentQuestionIndex++;
            return questions.get(currentQuestionIndex);
        }
        return null;
    }

    public boolean hasMoreQuestions() {
        return currentQuestionIndex < questions.size() - 1;
    }

    public boolean checkAnswer(String userAnswer) {
        Question currentQuestion = getCurrentQuestion();
        return currentQuestion != null && currentQuestion.getCorrectAnswer().equalsIgnoreCase(userAnswer.trim());
    }

    public Question getCurrentQuestion() {
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        }
        return null;
    }

    public int getScore() {
        return score;
    }
}
