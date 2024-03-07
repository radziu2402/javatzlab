package com.labs.archiveutility;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Checksum {

    public static void createMD5ChecksumFile(String filePath) throws IOException, NoSuchAlgorithmException {
        String md5 = getMD5Checksum(filePath);
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + ".md5"));
        writer.write(md5);
        writer.close();
    }

    private static String getMD5Checksum(String filePath) throws IOException, NoSuchAlgorithmException {
        InputStream fis = new FileInputStream(filePath);
        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;
        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);
        fis.close();
        byte[] b = complete.digest();
        StringBuilder result = new StringBuilder();
        for (byte value : b) {
            result.append(Integer.toString((value & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

    public static boolean verifyMD5Checksum(String zipFile, String md5File) throws IOException, NoSuchAlgorithmException {
        String generatedMD5 = getMD5Checksum(zipFile);
        String originalMD5 = new String(Files.readAllBytes(Paths.get(md5File)));
        return generatedMD5.equalsIgnoreCase(originalMD5.trim());
    }
}
