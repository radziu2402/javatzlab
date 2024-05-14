package pl.pwr.ui;

import javax.swing.*;
import java.awt.*;

public class XMLProcessingApp extends JFrame {

    public XMLProcessingApp() {
        setTitle("XML Data Processing App");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        JAXBPanel jaxbPanel = new JAXBPanel();
        JAXPPanel jaxpPanel = new JAXPPanel();
        XSLTPanel xsltPanel = new XSLTPanel();

        tabbedPane.addTab("JAXB", jaxbPanel);
        tabbedPane.addTab("JAXP", jaxpPanel);
        tabbedPane.addTab("XSLT", xsltPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            XMLProcessingApp app = new XMLProcessingApp();
            app.setVisible(true);
        });
    }
}
