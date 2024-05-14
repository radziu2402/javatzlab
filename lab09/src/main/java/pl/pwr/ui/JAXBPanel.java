package pl.pwr.ui;

import pl.pwr.model.Channel;
import pl.pwr.model.CurrencyRate;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static pl.pwr.model.CurrencyConstants.CURRENCIES;

public class JAXBPanel extends JPanel {
    private final JTextArea textArea;
    private final JComboBox<String> currencyComboBox;

    public JAXBPanel() {
        setLayout(new BorderLayout());
        textArea = new JTextArea("Wyniki będą wyświetlane tutaj...");
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        currencyComboBox = new JComboBox<>(CURRENCIES);
        currencyComboBox.setSelectedIndex(0);

        JButton loadButton = new JButton("Wczytaj dane XML");
        loadButton.addActionListener(this::loadData);

        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.add(currencyComboBox, BorderLayout.CENTER);
        controlPanel.add(loadButton, BorderLayout.EAST);

        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadData(ActionEvent event) {
        try {
            String selectedCurrency = (String) currencyComboBox.getSelectedItem();
            String url = "https://www.floatrates.com/daily/" + selectedCurrency.toLowerCase() + ".xml";
            String xmlData = fetchXmlData(url);
            Channel channel = deserializeXml(xmlData);
            displayData(channel);
        } catch (Exception e) {
            textArea.setText("Błąd przy wczytywaniu danych: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String fetchXmlData(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        java.util.Scanner scanner = new java.util.Scanner(connection.getInputStream()).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    private Channel deserializeXml(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Channel.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Channel) unmarshaller.unmarshal(new StringReader(xml));
    }

    private void displayData(Channel channel) {
        StringBuilder builder = new StringBuilder();
        builder.append("Kanał: ").append(channel.getTitle()).append("\n");
        builder.append("Opis: ").append(channel.getDescription()).append("\n");
        builder.append("Data publikacji: ").append(channel.getPubDate()).append("\n\n");

        for (CurrencyRate rate : channel.getItems()) {
            builder.append("Waluta bazowa: ").append(rate.getBaseCurrency()).append(" -> ");
            builder.append("Waluta docelowa: ").append(rate.getTargetCurrency()).append("\n");
            builder.append("Kurs wymiany: ").append(rate.getExchangeRate()).append("\n");
            builder.append("Opis: ").append(rate.getDescription()).append("\n\n");
        }

        textArea.setText(builder.toString());
    }
}
