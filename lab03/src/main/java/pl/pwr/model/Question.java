package pl.pwr.model;

public class Question {
    private final String questionText;
    private final String correctAnswer;
    private final QuestionType questionType;

    public Question(String questionText, String correctAnswer, QuestionType questionType) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.questionType = questionType;
    }

    public String getQuestionText() {
        return questionText;
    }


    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }
}
