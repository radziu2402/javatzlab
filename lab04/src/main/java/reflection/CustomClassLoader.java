package reflection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CustomClassLoader extends ClassLoader {
    private final String classesPath;

    public CustomClassLoader(String classesPath) {
        this.classesPath = classesPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] classData = loadClassData(name);
            return defineClass(name, classData, 0, classData.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Nie można znaleźć klasy: " + name, e);
        }
    }

    private byte[] loadClassData(String name) throws IOException {
        Path path = Paths.get(classesPath, name.replace('.', '/') + ".class");
        return Files.readAllBytes(path);
    }
}
