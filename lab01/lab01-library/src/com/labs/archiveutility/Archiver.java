package com.labs.archiveutility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Archiver {

    public static void pack(List<String> sourcePaths, String zipFilePath) throws IOException {
        FileOutputStream fos = new FileOutputStream(zipFilePath);
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        for (String srcPath : sourcePaths) {
            File srcFile = new File(srcPath);
            if (srcFile.isDirectory()) {
                // Rekurencyjnie pakuj zawartość katalogu
                packDirectory(srcFile, srcFile.getName(), zipOut);
            } else {
                // Pakuj pojedynczy plik
                packFile(srcFile, srcFile.getName(), zipOut);
            }
        }

        zipOut.close();
        fos.close();
    }

    private static void packDirectory(File folderToZip, String baseName, ZipOutputStream zipOut) throws IOException {
        File[] children = folderToZip.listFiles();
        if (children == null) {
            return;
        }
        for (File childFile : children) {
            if (childFile.isDirectory()) {
                packDirectory(childFile, baseName + "/" + childFile.getName(), zipOut);
            } else {
                packFile(childFile, baseName + "/" + childFile.getName(), zipOut);
            }
        }
    }

    private static void packFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
        zipOut.closeEntry();
    }
}
