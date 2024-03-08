package model;

import java.io.File;

public class FileNode {
    private File file;

    public FileNode(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return file.getName();
    }

    public File getFile() {
        return file;
    }
}
