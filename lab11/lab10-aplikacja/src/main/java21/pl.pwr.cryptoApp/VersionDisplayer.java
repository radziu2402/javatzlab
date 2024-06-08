package pl.pwr.cryptoApp;

import java.util.LinkedHashSet;
import java.util.SequencedSet;

public class VersionDisplayer {
    public static void showVersion() {
        System.out.println(Runtime.version().toString());
        System.out.println("Tu powinna być Java 21");
        SequencedSet<String> sequencedSet = new LinkedHashSet<>();
        sequencedSet.addFirst("Apple");
        sequencedSet.add("Banana");
        sequencedSet.addLast("Cherry");
        System.out.println(sequencedSet);
    }
}
