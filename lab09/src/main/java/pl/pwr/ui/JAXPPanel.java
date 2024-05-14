package pl.pwr.ui;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static pl.pwr.model.CurrencyConstants.CURRENCIES;

public class JAXPPanel extends JPanel {
    private JTextArea textArea;
    private JComboBox<String> currencyComboBox;
    private JRadioButton domButton, saxButton;

    public JAXPPanel() {
        setLayout(new BorderLayout());
        initializeUI();
    }

    private void initializeUI() {
        textArea = new JTextArea("Wyniki będą wyświetlane tutaj...");
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        currencyComboBox = new JComboBox<>(CURRENCIES);
        currencyComboBox.setSelectedIndex(0);

        JButton loadButton = new JButton("Wczytaj dane XML");
        loadButton.addActionListener(this::loadData);

        domButton = new JRadioButton("DOM", true);
        saxButton = new JRadioButton("SAX");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(domButton);
        buttonGroup.add(saxButton);

        JPanel controlPanel = new JPanel();
        controlPanel.add(currencyComboBox);
        controlPanel.add(loadButton);
        controlPanel.add(domButton);
        controlPanel.add(saxButton);

        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadData(ActionEvent event) {
        textArea.setText("");
        try {
            String selectedCurrency = (String) currencyComboBox.getSelectedItem();
            String url = "https://www.floatrates.com/daily/" + selectedCurrency.toLowerCase() + ".xml";
            String xmlData = fetchXmlData(url);
            if (domButton.isSelected()) {
                Document document = parseXMLWithDOM(xmlData);
                displayDOMData(document);
            } else if (saxButton.isSelected()) {
                parseXMLWithSAX(xmlData);
            }
        } catch (Exception e) {
            textArea.setText("Błąd przy wczytywaniu danych: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String fetchXmlData(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        Scanner scanner = new Scanner(connection.getInputStream()).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    private Document parseXMLWithDOM(String xmlData) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        try (InputStream is = new ByteArrayInputStream(xmlData.getBytes())) {
            return builder.parse(is);
        }
    }

    private void displayDOMData(Document document) {
        StringBuilder builder = new StringBuilder();
        NodeList items = document.getElementsByTagName("item");
        for (int i = 0; i < items.getLength(); i++) {
            Node item = items.item(i);
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) item;
                builder.append("Waluta bazowa: ").append(element.getElementsByTagName("baseCurrency").item(0).getTextContent())
                        .append(", Waluta docelowa: ").append(element.getElementsByTagName("targetCurrency").item(0).getTextContent())
                        .append(", Kurs wymiany: ").append(element.getElementsByTagName("exchangeRate").item(0).getTextContent())
                        .append("\n\n");
            }
        }
        textArea.setText(builder.toString());
    }

    private void parseXMLWithSAX(String xmlData) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        StringBuilder builder = new StringBuilder();
        DefaultHandler handler = new DefaultHandler() {

            private String currentElement = "";

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) {
                currentElement = qName;
            }

            @Override
            public void characters(char[] ch, int start, int length) {
                String value = new String(ch, start, length).trim();
                if (!value.isEmpty()) {
                    switch (currentElement) {
                        case "baseCurrency":
                            builder.append("Waluta bazowa: ").append(value).append(", ");
                            break;
                        case "targetCurrency":
                            builder.append("Waluta docelowa: ").append(value).append(", ");
                            break;
                        case "exchangeRate":
                            builder.append("Kurs wymiany: ").append(value).append("\n");
                            break;
                    }
                }
            }

            @Override
            public void endElement(String uri, String localName, String qName) {
                if (qName.equals("item")) {
                    builder.append("\n");
                }
                currentElement = "";
            }
        };

        try (InputStream is = new ByteArrayInputStream(xmlData.getBytes())) {
            saxParser.parse(is, handler);
        }

        textArea.setText(builder.toString());
    }
}
