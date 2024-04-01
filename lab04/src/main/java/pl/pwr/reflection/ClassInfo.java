package pl.pwr.reflection;

public class ClassInfo {
    private final String className;
    private final String description;

    public ClassInfo(String className, String description) {
        this.className = className;
        this.description = description;
    }

    @Override
    public String toString() {
        return className + " - " + description;
    }

    public String getClassName() {
        return className;
    }

}