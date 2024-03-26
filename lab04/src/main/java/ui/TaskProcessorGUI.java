package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class TaskProcessorGUI extends JFrame {
    private DefaultListModel<String> classListModel;
    private JList<String> classList;
    private JTextField taskField;
    private final Map<String, Class<?>> loadedClasses = new HashMap<>();

    public TaskProcessorGUI() {
        super("Task Processor GUI");
        initializeUI();
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setVisible(true);
        setLocationRelativeTo(null);

        classListModel = new DefaultListModel<>();
        classList = new JList<>(classListModel);
        JScrollPane listScrollPane = new JScrollPane(classList);

        taskField = new JTextField();

        JButton loadClassButton = new JButton("Load Class");
        loadClassButton.addActionListener(new LoadClassActionListener());

        JButton submitTaskButton = new JButton("Submit Task");
        submitTaskButton.addActionListener(new SubmitTaskActionListener());

        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.add(loadClassButton);
        topPanel.add(submitTaskButton);

        add(topPanel, BorderLayout.NORTH);
        add(listScrollPane, BorderLayout.CENTER);
        add(taskField, BorderLayout.SOUTH);

        setSize(400, 300);
    }

    private class LoadClassActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Tutaj dodaj logikę do ładowania klasy za pomocą CustomClassLoader
            // Na razie symulujemy załadowanie klasy, dodając jej nazwę do listy
            String className = JOptionPane.showInputDialog("Enter class name to load:");
            if (className != null && !className.isEmpty()) {
                classListModel.addElement(className);
                // Tutaj powinno nastąpić faktyczne ładowanie klasy i dodanie do mapy loadedClasses
                // loadedClasses.put(className, loadedClass);
            }
        }
    }

    private class SubmitTaskActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedClass = classList.getSelectedValue();
            String task = taskField.getText();
            if (selectedClass != null && !task.isEmpty()) {
                // Tutaj dodaj logikę do zlecania zadania wybranej klasie
                JOptionPane.showMessageDialog(TaskProcessorGUI.this,
                        "Task submitted to class: " + selectedClass + "\nTask: " + task,
                        "Task Submitted", JOptionPane.INFORMATION_MESSAGE);
                // Pamiętaj, aby użyć refleksji do wywołania metody submitTask na instancji klasy
            }
        }
    }
}
