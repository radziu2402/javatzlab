package model;

import java.io.File;

public record FileNode(File file) {

    @Override
    public String toString() {
        return file.getName();
    }
}
