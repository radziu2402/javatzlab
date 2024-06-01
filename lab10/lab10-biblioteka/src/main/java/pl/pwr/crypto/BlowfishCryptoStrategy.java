package pl.pwr.crypto;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.SecureRandom;

public class BlowfishCryptoStrategy implements CryptoStrategy {
    private SecretKey secretKey;
    private String keyFilePath;

    @Override
    public void encrypt(File inputFile, File outputFile) throws Exception {
        generateSecretKey();
        saveSecretKey();

        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            byte[] inputBytes = new byte[1024];
            int readBytes;
            while ((readBytes = inputStream.read(inputBytes)) != -1) {
                byte[] outputBytes = cipher.update(inputBytes, 0, readBytes);
                if (outputBytes != null) {
                    outputStream.write(outputBytes);
                }
            }
            byte[] finalBytes = cipher.doFinal();
            if (finalBytes != null) {
                outputStream.write(finalBytes);
            }
        }
    }

    @Override
    public void decrypt(File inputFile, File outputFile) throws Exception {
        loadSecretKey();

        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            byte[] inputBytes = new byte[1024];
            int readBytes;
            while ((readBytes = inputStream.read(inputBytes)) != -1) {
                byte[] outputBytes = cipher.update(inputBytes, 0, readBytes);
                if (outputBytes != null) {
                    outputStream.write(outputBytes);
                }
            }
            byte[] finalBytes = cipher.doFinal();
            if (finalBytes != null) {
                outputStream.write(finalBytes);
            }
        }
    }

    private void generateSecretKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("Blowfish");
        keyGen.init(128, new SecureRandom());
        this.secretKey = keyGen.generateKey();
    }

    private void saveSecretKey() throws Exception {
        try (FileOutputStream fos = new FileOutputStream(keyFilePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(secretKey);
        }
    }

    private void loadSecretKey() throws Exception {
        try (FileInputStream fis = new FileInputStream(keyFilePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            this.secretKey = (SecretKey) ois.readObject();
        }
    }

    public void setKeyFilePath(String keyFilePath) {
        this.keyFilePath = keyFilePath;
    }
}
