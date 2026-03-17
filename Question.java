public class Question {
    String question, optionA, optionB, optionC, optionD;
    char correctOption;

    public Question(String question, String a, String b, String c, String d, char correct) {
        this.question = question;
        this.optionA = a;
        this.optionB = b;
        this.optionC = c;
        this.optionD = d;
        this.correctOption = correct;
    }
}