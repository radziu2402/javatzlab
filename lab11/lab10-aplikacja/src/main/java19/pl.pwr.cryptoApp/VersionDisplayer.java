package pl.pwr.cryptoApp;

public class VersionDisplayer {
    public static void showVersion() {
        System.out.println(Runtime.version().toString());
        System.out.println("Tu powinna być Java 19");
    }
}
