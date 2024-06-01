package pl.pwr.crypto;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

public class HybridCryptoStrategy implements CryptoStrategy {
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public HybridCryptoStrategy(PrivateKey privateKey, PublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    @Override
    public void encrypt(File inputFile, File outputFile) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey aesKey = keyGen.generateKey();

        Cipher rsaCipher = Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedAesKey = rsaCipher.doFinal(aesKey.getEncoded());

        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(encryptedAesKey);

            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);

            try (FileInputStream inputStream = new FileInputStream(inputFile)) {
                byte[] inputBytes = new byte[1024];
                int readBytes;
                while ((readBytes = inputStream.read(inputBytes)) != -1) {
                    byte[] outputBytes = aesCipher.update(inputBytes, 0, readBytes);
                    if (outputBytes != null) {
                        outputStream.write(outputBytes);
                    }
                }
                byte[] finalBytes = aesCipher.doFinal();
                if (finalBytes != null) {
                    outputStream.write(finalBytes);
                }
            }
        }
    }

    @Override
    public void decrypt(File inputFile, File outputFile) throws Exception {
        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            byte[] encryptedAesKey = new byte[256];
            inputStream.read(encryptedAesKey);

            Cipher rsaCipher = Cipher.getInstance("RSA");
            rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] aesKeyBytes = rsaCipher.doFinal(encryptedAesKey);
            SecretKeySpec aesKey = new SecretKeySpec(aesKeyBytes, "AES");

            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.DECRYPT_MODE, aesKey);

            byte[] inputBytes = new byte[1024];
            int readBytes;
            while ((readBytes = inputStream.read(inputBytes)) != -1) {
                byte[] outputBytes = aesCipher.update(inputBytes, 0, readBytes);
                if (outputBytes != null) {
                    outputStream.write(outputBytes);
                }
            }
            byte[] finalBytes = aesCipher.doFinal();
            if (finalBytes != null) {
                outputStream.write(finalBytes);
            }
        }
    }
}
