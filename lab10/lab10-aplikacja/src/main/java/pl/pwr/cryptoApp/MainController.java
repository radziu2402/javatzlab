package pl.pwr.cryptoApp;

import pl.pwr.crypto.AESCryptoStrategy;
import pl.pwr.crypto.BlowfishCryptoStrategy;
import pl.pwr.crypto.HybridCryptoStrategy;
import pl.pwr.crypto.KeyStoreManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

public class MainController {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;

    // Fields for AES/Blowfish Tab
    private JTextField inputFilePathFieldAESBlowfish;
    private JTextField outputFileNameFieldAESBlowfish;
    private JTextField keyFileNameFieldAESBlowfish;
    private JTextField keyFilePathFieldAESBlowfish;
    private JButton chooseFileButtonAESBlowfish;
    private JButton chooseKeyFileButtonAESBlowfish;
    private JButton encryptButtonAESBlowfish;
    private JButton decryptButtonAESBlowfish;
    private JComboBox<String> algorithmComboBoxAESBlowfish;

    // Fields for Hybrid Tab
    private JTextField inputFilePathFieldHybrid;
    private JTextField outputFileNameFieldHybrid;
    private JTextField keystorePathFieldHybrid;
    private JTextField keystorePasswordFieldHybrid;
    private JTextField keyAliasFieldHybrid;
    private JTextField keyPasswordFieldHybrid;
    private JButton chooseFileButtonHybrid;
    private JButton chooseKeystoreButtonHybrid;
    private JButton encryptButtonHybrid;
    private JButton decryptButtonHybrid;

    private File selectedFile;
    private File keyFile;
    private File keystoreFile;

    public MainController() {
        mainPanel = new JPanel(new BorderLayout());
        tabbedPane = new JTabbedPane();

        // Zakładka AES/Blowfish
        JPanel aesBlowfishPanel = new JPanel();
        aesBlowfishPanel.setLayout(new BoxLayout(aesBlowfishPanel, BoxLayout.Y_AXIS));
        inputFilePathFieldAESBlowfish = new JTextField(30);
        inputFilePathFieldAESBlowfish.setEditable(false);
        outputFileNameFieldAESBlowfish = new JTextField(30);
        keyFileNameFieldAESBlowfish = new JTextField(30);
        keyFilePathFieldAESBlowfish = new JTextField(30);
        keyFilePathFieldAESBlowfish.setEditable(false);
        chooseFileButtonAESBlowfish = new JButton("Wybierz plik wejściowy");
        chooseKeyFileButtonAESBlowfish = new JButton("Wybierz plik klucza");
        algorithmComboBoxAESBlowfish = new JComboBox<>(new String[]{"AES", "Blowfish"});
        encryptButtonAESBlowfish = new JButton("Szyfruj");
        decryptButtonAESBlowfish = new JButton("Deszyfruj");

        chooseFileButtonAESBlowfish.addActionListener(e -> chooseFile(inputFilePathFieldAESBlowfish));
        chooseKeyFileButtonAESBlowfish.addActionListener(e -> chooseKeyFile(keyFilePathFieldAESBlowfish));
        encryptButtonAESBlowfish.addActionListener(e -> encryptFileAESBlowfish());
        decryptButtonAESBlowfish.addActionListener(e -> decryptFileAESBlowfish());

        aesBlowfishPanel.add(createRow("Plik wejściowy:", inputFilePathFieldAESBlowfish, chooseFileButtonAESBlowfish));
        aesBlowfishPanel.add(createRow("Nazwa pliku wyjściowego:", outputFileNameFieldAESBlowfish));
        aesBlowfishPanel.add(createRow("Nazwa pliku klucza (do szyfrowania):", keyFileNameFieldAESBlowfish));
        aesBlowfishPanel.add(createRow("Ścieżka pliku klucza (do deszyfrowania):", keyFilePathFieldAESBlowfish, chooseKeyFileButtonAESBlowfish));
        aesBlowfishPanel.add(createRow("Algorytm:", algorithmComboBoxAESBlowfish));
        aesBlowfishPanel.add(createButtonRow(encryptButtonAESBlowfish, decryptButtonAESBlowfish));
        tabbedPane.addTab("AES/Blowfish", aesBlowfishPanel);

        // Zakładka Hybrydowa
        JPanel hybridPanel = new JPanel();
        hybridPanel.setLayout(new BoxLayout(hybridPanel, BoxLayout.Y_AXIS));
        inputFilePathFieldHybrid = new JTextField(30);
        inputFilePathFieldHybrid.setEditable(false);
        outputFileNameFieldHybrid = new JTextField(30);
        keystorePathFieldHybrid = new JTextField(30);
        keystorePathFieldHybrid.setEditable(false);
        keystorePasswordFieldHybrid = new JTextField(30);
        keyAliasFieldHybrid = new JTextField(30);
        keyPasswordFieldHybrid = new JTextField(30);
        chooseFileButtonHybrid = new JButton("Wybierz plik wejściowy");
        chooseKeystoreButtonHybrid = new JButton("Wybierz magazyn kluczy");
        encryptButtonHybrid = new JButton("Szyfruj");
        decryptButtonHybrid = new JButton("Deszyfruj");

        chooseFileButtonHybrid.addActionListener(e -> chooseFile(inputFilePathFieldHybrid));
        chooseKeystoreButtonHybrid.addActionListener(e -> chooseKeystoreFile());
        encryptButtonHybrid.addActionListener(e -> encryptFileHybrid());
        decryptButtonHybrid.addActionListener(e -> decryptFileHybrid());

        hybridPanel.add(createRow("Plik wejściowy:", inputFilePathFieldHybrid, chooseFileButtonHybrid));
        hybridPanel.add(createRow("Nazwa pliku wyjściowego:", outputFileNameFieldHybrid));
        hybridPanel.add(createRow("Ścieżka magazynu kluczy:", keystorePathFieldHybrid, chooseKeystoreButtonHybrid));
        hybridPanel.add(createRow("Hasło magazynu kluczy:", keystorePasswordFieldHybrid));
        hybridPanel.add(createRow("Alias klucza:", keyAliasFieldHybrid));
        hybridPanel.add(createRow("Hasło klucza:", keyPasswordFieldHybrid));
        hybridPanel.add(createButtonRow(encryptButtonHybrid, decryptButtonHybrid));
        tabbedPane.addTab("Hybrydowa", hybridPanel);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createRow(String label, JComponent component, JButton... buttons) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        row.add(new JLabel(label));
        row.add(component);
        for (JButton button : buttons) {
            row.add(button);
        }
        return row;
    }

    private JPanel createButtonRow(JButton... buttons) {
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        for (JButton button : buttons) {
            buttonRow.add(button);
        }
        return buttonRow;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void chooseFile(JTextField inputFilePathField) {
        JFileChooser fileChooser = new JFileChooser(".");
        if (fileChooser.showOpenDialog(mainPanel) == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            inputFilePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    private void chooseKeyFile(JTextField keyFilePathField) {
        JFileChooser fileChooser = new JFileChooser(".");
        if (fileChooser.showOpenDialog(mainPanel) == JFileChooser.APPROVE_OPTION) {
            keyFile = fileChooser.getSelectedFile();
            keyFilePathField.setText(keyFile.getAbsolutePath());
        }
    }

    private void chooseKeystoreFile() {
        JFileChooser fileChooser = new JFileChooser(".");
        if (fileChooser.showOpenDialog(mainPanel) == JFileChooser.APPROVE_OPTION) {
            keystoreFile = fileChooser.getSelectedFile();
            keystorePathFieldHybrid.setText(keystoreFile.getAbsolutePath());
        }
    }

    private void encryptFileAESBlowfish() {
        if (validateInputsAESBlowfish()) {
            String algorithm = (String) algorithmComboBoxAESBlowfish.getSelectedItem();
            try {
                String outputFilePath = outputFileNameFieldAESBlowfish.getText();
                String keyFilePath = keyFileNameFieldAESBlowfish.getText();

                if ("AES".equals(algorithm)) {
                    AESCryptoStrategy aesStrategy = new AESCryptoStrategy();
                    aesStrategy.setKeyFilePath(keyFilePath);
                    aesStrategy.encrypt(selectedFile, new File(outputFilePath));
                } else if ("Blowfish".equals(algorithm)) {
                    BlowfishCryptoStrategy blowfishStrategy = new BlowfishCryptoStrategy();
                    blowfishStrategy.setKeyFilePath(keyFilePath);
                    blowfishStrategy.encrypt(selectedFile, new File(outputFilePath));
                }

                JOptionPane.showMessageDialog(mainPanel, "File encrypted successfully!\nSaved as: " + outputFilePath, "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(mainPanel, "Error during encryption: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void decryptFileAESBlowfish() {
        if (validateInputsAESBlowfish()) {
            String algorithm = (String) algorithmComboBoxAESBlowfish.getSelectedItem();
            try {
                String outputFilePath = outputFileNameFieldAESBlowfish.getText();
                String keyFilePath = keyFilePathFieldAESBlowfish.getText();

                if ("AES".equals(algorithm)) {
                    AESCryptoStrategy aesStrategy = new AESCryptoStrategy();
                    aesStrategy.setKeyFilePath(keyFilePath);
                    aesStrategy.decrypt(selectedFile, new File(outputFilePath));
                } else if ("Blowfish".equals(algorithm)) {
                    BlowfishCryptoStrategy blowfishStrategy = new BlowfishCryptoStrategy();
                    blowfishStrategy.setKeyFilePath(keyFilePath);
                    blowfishStrategy.decrypt(selectedFile, new File(outputFilePath));
                }

                JOptionPane.showMessageDialog(mainPanel, "File decrypted successfully!\nSaved as: " + outputFilePath, "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(mainPanel, "Error during decryption: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void encryptFileHybrid() {
        if (validateInputsHybrid()) {
            try {
                String outputFilePath = outputFileNameFieldHybrid.getText();

                KeyStoreManager keyStoreManager = new KeyStoreManager(keystorePathFieldHybrid.getText(), keystorePasswordFieldHybrid.getText());
                PrivateKey privateKey = keyStoreManager.getPrivateKey(keyAliasFieldHybrid.getText(), keyPasswordFieldHybrid.getText());
                PublicKey publicKey = keyStoreManager.getPublicKey(keyAliasFieldHybrid.getText());
                HybridCryptoStrategy hybridStrategy = new HybridCryptoStrategy(privateKey, publicKey);
                hybridStrategy.encrypt(selectedFile, new File(outputFilePath));

                JOptionPane.showMessageDialog(mainPanel, "File encrypted successfully!\nSaved as: " + outputFilePath, "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(mainPanel, "Error during encryption: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void decryptFileHybrid() {
        if (validateInputsHybrid()) {
            try {
                String outputFilePath = outputFileNameFieldHybrid.getText();

                KeyStoreManager keyStoreManager = new KeyStoreManager(keystorePathFieldHybrid.getText(), keystorePasswordFieldHybrid.getText());
                PrivateKey privateKey = keyStoreManager.getPrivateKey(keyAliasFieldHybrid.getText(), keyPasswordFieldHybrid.getText());
                PublicKey publicKey = keyStoreManager.getPublicKey(keyAliasFieldHybrid.getText());
                HybridCryptoStrategy hybridStrategy = new HybridCryptoStrategy(privateKey, publicKey);
                hybridStrategy.decrypt(selectedFile, new File(outputFilePath));

                JOptionPane.showMessageDialog(mainPanel, "File decrypted successfully!\nSaved as: " + outputFilePath, "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(mainPanel, "Error during decryption: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validateInputsAESBlowfish() {
        if (selectedFile == null || outputFileNameFieldAESBlowfish.getText().isEmpty() || (encryptButtonAESBlowfish.getModel().isPressed() && keyFileNameFieldAESBlowfish.getText().isEmpty()) || (decryptButtonAESBlowfish.getModel().isPressed() && keyFilePathFieldAESBlowfish.getText().isEmpty())) {
            JOptionPane.showMessageDialog(mainPanel, "Please provide all necessary files and paths.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean validateInputsHybrid() {
        if (selectedFile == null || outputFileNameFieldHybrid.getText().isEmpty() || keystoreFile == null || keystorePathFieldHybrid.getText().isEmpty() || keystorePasswordFieldHybrid.getText().isEmpty()
                || keyAliasFieldHybrid.getText().isEmpty() || keyPasswordFieldHybrid.getText().isEmpty()) {
            JOptionPane.showMessageDialog(mainPanel, "Please provide all necessary files and paths.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
