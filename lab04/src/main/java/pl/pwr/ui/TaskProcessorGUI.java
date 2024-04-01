package pl.pwr.ui;

import processing.StatusListener;
import pl.pwr.reflection.ClassInfo;
import pl.pwr.reflection.CustomClassLoader;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskProcessorGUI extends JFrame {
    private final DefaultListModel<ClassInfo> classListModel = new DefaultListModel<>();
    private final JList<ClassInfo> classList = new JList<>(classListModel);
    private JTextField taskField;
    private JTextArea resultArea;
    private final Map<String, Class<?>> loadedClasses = new HashMap<>();
    private final Map<String, Object> classInstances = new HashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private static final String basePath = "C:\\Users\\radziu2402\\Desktop\\klasy";

    public TaskProcessorGUI() {
        super("Task Processor GUI");
        initializeUI();
    }

    private void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JScrollPane listScrollPane = new JScrollPane(classList);

        taskField = new JTextField();

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        JPanel topPanel = getjPanel();

        add(topPanel, BorderLayout.NORTH);
        add(listScrollPane, BorderLayout.WEST);
        add(resultScrollPane, BorderLayout.CENTER);
        add(taskField, BorderLayout.SOUTH);


        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private JPanel getjPanel() {
        JButton loadClassButton = new JButton("Load Class");
        loadClassButton.addActionListener(e -> loadClass());

        JButton submitTaskButton = new JButton("Submit Task");
        submitTaskButton.addActionListener(e -> submitTask());

        JButton unloadClassButton = new JButton("Unload Class");
        unloadClassButton.addActionListener(e -> unloadClass());

        JButton cleanButton = new JButton("Clear Results");
        cleanButton.addActionListener(e -> resultArea.setText(""));


        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        topPanel.add(loadClassButton);
        topPanel.add(submitTaskButton);
        topPanel.add(unloadClassButton);
        topPanel.add(cleanButton);
        return topPanel;
    }

    private void loadClass() {
        JFileChooser fileChooser = new JFileChooser(basePath);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            String fullClassName = path.substring(path.lastIndexOf(File.separator) + 1).replace(".class", "");
            try {
                CustomClassLoader classLoader = new CustomClassLoader(basePath);
                Class<?> cls = classLoader.loadClass(fullClassName);
                Object instance = cls.getDeclaredConstructor().newInstance();
                Method getInfoMethod = cls.getMethod("getInfo");
                String classInfo = (String) getInfoMethod.invoke(instance);
                loadedClasses.put(fullClassName, cls);
                classListModel.addElement(new ClassInfo(fullClassName, classInfo));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error loading class: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void submitTask() {
        ClassInfo selectedClassInfo = classList.getSelectedValue();
        String task = taskField.getText();
        if (selectedClassInfo != null && !task.isEmpty()) {
            executor.submit(() -> {
                try {
                    String className = selectedClassInfo.getClassName();
                    Class<?> cls = loadedClasses.get(className);
                    if (!classInstances.containsKey(className)) {
                        Constructor<?> constructor = cls.getConstructor();
                        Object instance = constructor.newInstance();
                        classInstances.put(className, instance);
                    }
                    Object instance = classInstances.get(className);
                    Method submitTaskMethod = cls.getMethod("submitTask", String.class, StatusListener.class);
                    submitTaskMethod.invoke(instance, task, (StatusListener) status -> SwingUtilities.invokeLater(() -> {
                        resultArea.append("Status: " + status.getProgress() + "%\n");
                        if (status.getProgress() == 100) {
                            try {
                                Method getResultMethod = instance.getClass().getMethod("getResult");
                                String result = (String) getResultMethod.invoke(instance);
                                resultArea.append("Result: " + result + "\n\n");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }));
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "Error executing task: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
                }
            });
        }
    }

    private void unloadClass() {
        ClassInfo selectedClassInfo = classList.getSelectedValue();
        if (selectedClassInfo != null) {
            String className = selectedClassInfo.getClassName();
            if (loadedClasses.containsKey(className)) {
                loadedClasses.remove(className);
                classListModel.removeElement(selectedClassInfo);
                classInstances.remove(className);
                System.gc();
                JOptionPane.showMessageDialog(this, "Class unloaded: " + className, "Class Unloaded", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

}
