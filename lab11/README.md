
# Lab11 Aplikacja - Dokumentacja

## Wstęp
Projekt ten bazuje na aplikacji z Lab10, która została rozbudowana o obsługę wielowydaniowego JAR-a (Multi-Release JAR), umożliwiając jej działanie na różnych wersjach platformy Java. Poniżej znajduje się opis wykonanych kroków oraz instrukcje, jak uruchomić aplikację.

## Etapy projektu

### Modyfikacja Biblioteki
1. Dodano `module-info.java` do biblioteki używanej w projekcie, co umożliwia jej użycie w systemie modułów Java (JPMS).

### Modyfikacja Aplikacji
1. Dodano klasę `VersionDisplayer` w pakiecie `pl.pwr.cryptoApp` do sprawdzenia wersji Java w runtime.
   ```java
   package pl.pwr.cryptoApp;

   public class VersionDisplayer {
       public static void showVersion() {
           System.out.println(Runtime.version().toString());
           System.out.println("Tu powinna być Java 17");
       }
   }
   ```
2. Stworzono podkatalogi dla Java 19 i Java 21, gdzie dodano odpowiednie wersje klasy `VersionDisplayer`.


Oczywiście! Oto szczegółowy opis konfiguracji pliku `pom.xml`, używanego w projekcie do zarządzania zależnościami i procesem budowy aplikacji, z szczególnym uwzględnieniem dwóch kluczowych pluginów Maven: `maven-compiler-plugin` i `maven-jar-plugin`.

### Konfiguracja Maven

Użyto poniższej konfiguracji `pom.xml` do zarządzania zależnościami i procesem budowania aplikacji. Plik zawiera definicje dwóch głównych pluginów, które odgrywają kluczową rolę w kompilacji i pakowaniu aplikacji.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>pl.pwr</groupId>
    <artifactId>lab10-aplikacja</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <executions>
                    <execution>
                        <id>default-compile</id>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                        <configuration>
                            <source>17</source>
                            <target>17</target>
                        </configuration>
                    </execution>
                    <execution>
                        <id>compile-java-19</id>
                        <phase>compile</phase>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                        <configuration>
                            <release>19</release>
                            <compileSourceRoots>
                                <compileSourceRoot>${project.basedir}/src/main/java19</compileSourceRoot>
                            </compileSourceRoots>
                            <multiReleaseOutput>true</multiReleaseOutput>
                        </configuration>
                    </execution>
                    <execution>
                        <id>compile-java-21</id>
                        <phase>compile</phase>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                        <configuration>
                            <release>21</release>
                            <compileSourceRoots>
                                <compileSourceRoot>${project.basedir}/src/main/java21</compileSourceRoot>
                            </compileSourceRoots>
                            <multiReleaseOutput>true</multiReleaseOutput>
                        </configuration>
                    </execution>
                </executions>
            </plugin>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.3.0</version>
                <configuration>
                    <archive>
                        <manifestEntries>
                            <Multi-Release>true</Multi-Release>
                            <Main-Class>pl.pwr.cryptoApp.MainApp</Main-Class>
                        </manifestEntries>
                    </archive>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <dependencies>
        <dependency>
            <groupId>crypto</groupId>
            <artifactId>crypto</artifactId>
            <version>1.0</version>
            <scope>system</scope>
            <systemPath>${project.basedir}/src/main/resources/signed-lab10-biblioteka-1.0-SNAPSHOT.jar</systemPath>
        </dependency>
    </dependencies>
</project>
```

#### Opis pluginów:

1. **maven-compiler-plugin**: Używany do kompilacji kodu źródłowego projektu. W tym przypadku jest skonfigurowany do obsługi kodu dla różnych wersji Javy (17, 19, 21), co jest kluczowe dla funkcjonowania wielowydaniowego JAR-a. Każda wykonania (`execution`) konfiguruje inną wersję źródłową, co pozwala na wykorzystanie specyficznych funkcji danej wersji Javy.

2. **maven-jar-plugin**: Używany do pakowania skompilowanego kodu do formatu JAR. W tym ustawieniu, `manifestEntries` z `Multi-Release: true` informuje JVM, że JAR jest wielowydaniowy, co pozwala na korzystanie z różnych klas zależnie od wersji Javy, na której uruchamiana jest aplikacja. `Main-Class` określa główną klasę aplikacji, która jest punktem wejścia.

### Budowanie Aplikacji
1. Wykonano komendę `mvn package`, co skutkowało wygenerowaniem wielowydaniowego JAR-a.
2. Sprawdzono działanie aplikacji na różnych wersjach JDK (17, 19, 21).

### Tworzenie Instalatora
1. Użyto narzędzia `jpackage` do stworzenia instalatora `.exe`:
   ```bash
   jpackage --type exe --input . --name Lab11Aplikacja --main-jar lab10-aplikacja-1.0-SNAPSHOT.jar --dest output
   ```
2. Instalator umożliwił łatwą instalację i uruchomienie aplikacji na systemie Windows.

## Uruchomienie Aplikacji
Aplikacja została zainstalowana w katalogu `Program Files` i może być uruchomiona zarówno przez skrót na pulpicie, jak i przez plik wykonywalny w folderze instalacyjnym.

## Dodatkowe Uwagi
Podczas projektu zainstalowano również narzędzie WiX Toolset, które było niezbędne do stworzenia instalatora Windows.

