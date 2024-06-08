package pl.pwr.crypto;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

public class KeyStoreManager {

    private final KeyStore keyStore;

    public KeyStoreManager(String keyStorePath, String keyStorePassword) throws Exception {
        keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(new FileInputStream(keyStorePath), keyStorePassword.toCharArray());
    }

    public PrivateKey getPrivateKey(String alias, String password) throws Exception {
        PrivateKey key = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
        if (key == null) {
            throw new Exception("No private key found for alias: " + alias);
        }
        return key;
    }

    public PublicKey getPublicKey(String alias) throws Exception {
        Certificate cert = keyStore.getCertificate(alias);
        if (cert == null) {
            throw new Exception("No certificate found for alias: " + alias);
        }
        return cert.getPublicKey();
    }
}
