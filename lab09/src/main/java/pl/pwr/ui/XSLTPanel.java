package pl.pwr.ui;

import javax.swing.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

import static pl.pwr.model.CurrencyConstants.CURRENCIES;

public class XSLTPanel extends JPanel {
    private JComboBox<String> currencyChoices;
    private JButton transformButton, loadButton, selectXsltButton;
    private JEditorPane htmlPane;
    private JTextArea xmlTextArea;
    private File xsltFile;  // To store the selected XSLT file

    public XSLTPanel() {
        setLayout(new BorderLayout());
        initializeUI();
        setupActions();
    }

    private void initializeUI() {
        currencyChoices = new JComboBox<>(CURRENCIES);
        transformButton = new JButton("Transform XML");
        loadButton = new JButton("Load XML");
        selectXsltButton = new JButton("Select XSLT");

        htmlPane = new JEditorPane();
        htmlPane.setContentType("text/html");
        htmlPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(htmlPane);
        add(scrollPane, BorderLayout.CENTER);

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Currency:"));
        topPanel.add(currencyChoices);
        topPanel.add(loadButton);
        topPanel.add(selectXsltButton);
        topPanel.add(transformButton);
        add(topPanel, BorderLayout.NORTH);

        xmlTextArea = new JTextArea(10, 40);
        xmlTextArea.setEditable(false);
        add(new JScrollPane(xmlTextArea), BorderLayout.SOUTH);
    }

    private void setupActions() {
        loadButton.addActionListener(this::loadXmlAction);
        transformButton.addActionListener(this::transformXmlAction);
        selectXsltButton.addActionListener(this::selectXsltAction);
    }

    private void loadXmlAction(ActionEvent event) {
        try {
            String selectedCurrency = Objects.requireNonNull(currencyChoices.getSelectedItem()).toString().toLowerCase();
            URL url = new URL("https://www.floatrates.com/daily/" + selectedCurrency + ".xml");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\A");
            String xmlData = scanner.hasNext() ? scanner.next() : "";
            xmlTextArea.setText(xmlData);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load XML: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void selectXsltAction(ActionEvent event) {
        JFileChooser chooser = new JFileChooser(".");
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            xsltFile = chooser.getSelectedFile();
        }
    }

    private void transformXmlAction(ActionEvent event) {
        if (xsltFile == null) {
            JOptionPane.showMessageDialog(this, "Please select an XSLT file first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            StreamSource styleSource = new StreamSource(xsltFile);
            Transformer transformer = factory.newTransformer(styleSource);

            StringReader reader = new StringReader(xmlTextArea.getText());
            StringWriter writer = new StringWriter();
            transformer.transform(new StreamSource(reader), new StreamResult(writer));

            htmlPane.setText(writer.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error during transformation: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
