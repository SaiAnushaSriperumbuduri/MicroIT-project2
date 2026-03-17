import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class QuizGame extends JFrame implements ActionListener {
    ArrayList<Question> questions = new ArrayList<>();
    int current = 0, score = 0;

    JLabel qLabel;
    JRadioButton a, b, c, d;
    ButtonGroup options;
    JButton next;

    public QuizGame() {
        selectSubject();
        buildGUI();
        showQuestion();
    }

    void selectSubject() {
        String[] subjects = { "Java", "Math" };
        String subject = (String) JOptionPane.showInputDialog(
                null, "Select Subject:", "Subject",
                JOptionPane.QUESTION_MESSAGE, null,
                subjects, subjects[0]);

        String fileName = subject.toLowerCase() + "_questions.txt";
        loadQuestionsFromFile(fileName);
    }

    void loadQuestionsFromFile(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 6) {
                    questions.add(new Question(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5].charAt(0)));
                }
            }
            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Could not load questions from file: " + fileName);
            System.exit(1);
        }
    }

    void buildGUI() {
        setTitle("Quiz Game");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        qLabel = new JLabel();
        a = new JRadioButton();
        b = new JRadioButton();
        c = new JRadioButton();
        d = new JRadioButton();

        options = new ButtonGroup();
        options.add(a); options.add(b); options.add(c); options.add(d);

        next = new JButton("Next");
        next.addActionListener(this);

        add(qLabel); add(a); add(b); add(c); add(d); add(next);
        setVisible(true);
    }

    void showQuestion() {
        options.clearSelection();
        if (current < questions.size()) {
            Question q = questions.get(current);
            qLabel.setText((current + 1) + ". " + q.question);
            a.setText("A. " + q.optionA);
            b.setText("B. " + q.optionB);
            c.setText("C. " + q.optionC);
            d.setText("D. " + q.optionD);
        } else {
            showResult();
        }
    }

    void showResult() {
        JOptionPane.showMessageDialog(this, "Your Score: " + score + "/" + questions.size());
        System.exit(0);
    }

    public void actionPerformed(ActionEvent e) {
        char selected = 'X';
        if (a.isSelected()) selected = 'A';
        else if (b.isSelected()) selected = 'B';
        else if (c.isSelected()) selected = 'C';
        else if (d.isSelected()) selected = 'D';

        if (selected == questions.get(current).correctOption) score++;
        current++;
        showQuestion();
    }

    public static void main(String[] args) {
        new QuizGame();
    }
}