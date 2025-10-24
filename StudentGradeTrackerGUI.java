import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;


// Student class (Encapsulation)
class Student {
    private String name;
    private int grade;

    public Student(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getName() { return name; }
    public int getGrade() { return grade; }
}


// GradeTracker class (Core Logic)
class GradeTracker {
    private ArrayList<Student> students = new ArrayList<>();

    public void addStudent(String name, int grade) {
        students.add(new Student(name, grade));
    }

    public double getAverage() {
        if (students.isEmpty()) return 0;
        int sum = 0;
        for (Student s : students) sum += s.getGrade();
        return (double) sum / students.size();
    }

    public int getHighest() {
        if (students.isEmpty()) return 0;
        return Collections.max(students, (a, b) -> a.getGrade() - b.getGrade()).getGrade();
    }

    public int getLowest() {
        if (students.isEmpty()) return 0;
        return Collections.min(students, (a, b) -> a.getGrade() - b.getGrade()).getGrade();
    }

    public String getSummary() {
        if (students.isEmpty()) return "No students added!";
        StringBuilder sb = new StringBuilder();
        for (Student s : students)
            sb.append(s.getName()).append(": ").append(s.getGrade()).append("\n");
        sb.append("\nAverage: ").append(getAverage());
        sb.append("\nHighest: ").append(getHighest());
        sb.append("\nLowest: ").append(getLowest());
        return sb.toString();
    }
}


// GUI Class (Swing-based Interface)
public class StudentGradeTrackerGUI extends JFrame {
    private JTextField nameField, gradeField;
    private JTextArea resultArea;
    private GradeTracker tracker;

    public StudentGradeTrackerGUI() {
        tracker = new GradeTracker();
        setTitle("Student Grade Tracker");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout setup
        setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add Student"));

        // Components
        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField();
        inputPanel.add(gradeField);

        JButton addButton = new JButton("Add Grade");
        JButton showButton = new JButton("Show Summary");
        inputPanel.add(addButton);
        inputPanel.add(showButton);

        add(inputPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        // Action Listeners
        addButton.addActionListener(e -> addStudent());
        showButton.addActionListener(e -> showSummary());
    }

    private void addStudent() {
        String name = nameField.getText().trim();
        String gradeText = gradeField.getText().trim();
        if (name.isEmpty() || gradeText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both name and grade!");
            return;
        }
        try {
            int grade = Integer.parseInt(gradeText);
            tracker.addStudent(name, grade);
            JOptionPane.showMessageDialog(this, "Student added successfully!");
            nameField.setText("");
            gradeField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid grade! Please enter a number.");
        }
    }

    private void showSummary() {
        resultArea.setText(tracker.getSummary());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentGradeTrackerGUI().setVisible(true));
    }
}
