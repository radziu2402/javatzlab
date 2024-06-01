package pl.pwr.crypto;

import java.io.File;

public interface CryptoStrategy {
    void encrypt(File inputFile, File outputFile) throws Exception;
    void decrypt(File inputFile, File outputFile) throws Exception;
}