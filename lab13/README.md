
# Generator Życzeń

Generator Życzeń to aplikacja JavaFX, która umożliwia generowanie spersonalizowanych życzeń dla różnych okazji, opierając się na szablonach JSON. Cała logika aplikacji jest realizowana za pomocą skryptów JavaScript Nashorn, zapisanych bezpośrednio w plikach FXML, co stanowi niestandardowe podejście w implementacji aplikacji JavaFX.

## Technologie i struktura projektu

Aplikacja korzysta z JavaFX oraz Nashorn JavaScript Engine dla obsługi logiki interakcji i przetwarzania danych. Poniżej przedstawiono kluczowe aspekty techniczne oraz strukturalne projektu:

### `module-info.java`

Projekt jest aplikacją modułową Java, co wymaga zdefiniowania pliku `module-info.java`. W tym pliku określono moduły, które są wymagane do działania aplikacji, jak również moduły, do których aplikacja musi mieć dostęp:

```java
module pl.pwr {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.openjdk.nashorn.core;

    opens pl.pwr to javafx.fxml;
    exports pl.pwr;
}
```

### Konfiguracja Mavena

Projekt wykorzystuje Maven do zarządzania zależnościami i budowania aplikacji. W pliku `pom.xml` skonfigurowano wtyczki niezbędne do kompilacji oraz uruchomienia projektu:

- **Maven Compiler Plugin**: Skonfigurowano użycie Javy w wersji 17.
- **JavaFX Maven Plugin**: Umożliwia łatwe kompilowanie i uruchamianie aplikacji JavaFX.
- **Maven Jar Plugin**: Konfiguracja pakowania aplikacji do formatu JAR.

### Fragmenty kodu

Kod źródłowy aplikacji zawiera zarówno klasy Java, jak i skrypty JavaScript Nashorn. Oto fragment metody `start` w klasie `Main`, która ładuje główny interfejs użytkownika z pliku FXML:

```java
public void start(Stage stage) {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("style.css")).toExternalForm());
        stage.setTitle("Generator życzeń");
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
        throw new RuntimeException(e);
    }
}
```

## Przykładowe szablony

Szablony życzeń są przechowywane w plikach JSON i mogą być łatwo modyfikowane lub rozszerzane. Oto przykład szablonu:
```json
{
  "template": "Gratulacje z okazji przejścia na emeryturę, {name}! Życzymy Ci wspaniałych lat pełnych odpoczynku i radości. Data emerytury: {date}."
}
```

## Problemy i wsparcie

W przypadku problemów z konfiguracją lub pytań dotyczących aplikacji prosimy o kontakt. Możesz również zgłaszać problemy w sekcji "Issues" w repozytorium GitHub projektu.

Oczywiście! Rozbuduję sekcję dotyczącą uruchamiania aplikacji, uwzględniając szczegółowe informacje na temat używania wtyczki Maven do JavaFX. Poniżej znajduje się rozbudowana wersja tej sekcji z pliku `README.md`:

---

## Uruchamianie aplikacji

Aplikację można uruchomić na kilka sposobów, w zależności od preferowanego środowiska i konfiguracji. Dla ułatwienia procesu uruchamiania, projekt wykorzystuje **JavaFX Maven Plugin**, który pozwala na łatwe kompilowanie i uruchamianie aplikacji JavaFX bezpośrednio z linii komend.

### Używanie JavaFX Maven Plugin

1. **Przygotowanie środowiska**: Upewnij się, że masz zainstalowaną odpowiednią wersję Javy oraz Mavena. Projekt jest skonfigurowany do używania Java 17, więc twoje środowisko powinno wspierać tę wersję.

2. **Uruchomienie aplikacji z linii komend**:

   Otwórz terminal lub wiersz poleceń w katalogu głównym projektu, gdzie znajduje się plik `pom.xml`. Użyj poniższego polecenia, aby uruchomić aplikację za pomocą Mavena:

   ```bash
   mvn javafx:run
   ```

   To polecenie uruchomi zdefiniowaną klasę główną, która została skonfigurowana w wtyczce `javafx-maven-plugin`. Wtyczka automatycznie skonfiguruje środowisko uruchomieniowe, dodając wszystkie potrzebne moduły JavaFX oraz ustawienia, które są wymagane do poprawnego działania aplikacji.

### Uruchomienie z IDE

Możesz również uruchomić aplikację bezpośrednio z Twojego zintegrowanego środowiska programistycznego (IDE), takiego jak IntelliJ IDEA, Eclipse, czy NetBeans:

- **IntelliJ IDEA**: Importuj projekt jako projekt Maven, a następnie użyj konfiguracji wykonania programu Java z `Main` jako klasą startową.
- **Eclipse**: Użyj funkcji 'Import Existing Maven Projects', a następnie skonfiguruj i uruchom aplikację korzystając z 'Run As Java Application'.
- **NetBeans**: Importuj projekt jako projekt Maven, następnie kliknij prawym przyciskiem myszy na projekcie i wybierz 'Run'.

### Uruchomienie jara

   Żeby uruchomić plik jar wygenerowany przez maven-jar-plugin musimy użyć jdk 11, ponieważ w poźniejszych dystrybucjach nie ma juz silnika nashorn. Do ścieżki modułów musimy dodać też JaveFX pobraną osobno. Polecenie uruchamiające wygląda następująco:

   ```bash
   C:\Users\radziu2402\.jdks\corretto-11.0.23\bin\java --module-path "C:\Program Files\Java\javafx-sdk-11\lib" --add-modules javafx.controls,javafx.fxml -jar lab13-1.0-SNAPSHOT.jar
   ```

### Debugowanie i rozwiązywanie problemów

W przypadku problemów z uruchamianiem aplikacji:

- Upewnij się, że wszystkie zależności Maven zostały poprawnie pobrane.
- Sprawdź, czy środowisko JDK/JRE jest ustawione na wersję 17 lub nowszą.
- W razie problemów z wersją JavaFX lub konfliktów zależności, skonsultuj się z dokumentacją JavaFX oraz Maven.

