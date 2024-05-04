
# Raport z Testowania Wydajności Implementacji Konwolucji w Java i C++

## Wprowadzenie
Projekt zakładał implementację i porównanie dwóch metod obliczania dyskretnej dwuwymiarowej funkcji splotu (2D discrete convolution function): jednej w Javie i drugiej w C++ wykorzystującej Java Native Interface (JNI). Celem testów było ustalenie, która z implementacji jest wydajniejsza przy różnych rozmiarach danych wejściowych.

## Implementacja
Zaimplementowano dwie metody w Javie:
1. Metoda wykonująca obliczenia w Javie.
2. Metoda wykonująca obliczenia w natywnej bibliotece C++.

### Stworzenie i kompilacja kodu natywnego
Stworzenie pliku nagłówkowego:
```bash
javac -h . src\main\java\pl\pwr\ConvolutionCounter.java
```

Kompilacja kodu C++ do biblioteki DLL:
```bash
g++ -m64 -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" -shared -o native.dll pl_pwr_ConvolutionCounter.cpp
```
lub
```bash
g++ -c -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" pl_pwr_ConvolutionCounter.cpp -o pl_pwr_ConvolutionCounter.o
g++ -shared -o native.dll pl_pwr_ConvolutionCounter.o -Wl,--add-stdcall-alias
```

## Wyniki testów
Przeprowadzono testy dla różnych rozmiarów macierzy i kerneli. Wyniki niektórych przedstawiono poniżej:

- **Macierz 10x10 z kernelem 3x3**
    - Java: 219,700 nanosekund
    - C++: 658,700 nanosekund

- **Macierz 100x100 z kernelem 11x11**
    - Java: 28,823,700 nanosekund
    - C++: 674,978,900 nanosekund

- **Macierz 200x200 z kernelem 20x20**
    - Java: 245,635,100 nanosekund
    - C++: 10,964,917,800 nanosekund

## Analiza Wyników
Wyniki pokazują znaczącą różnicę w wydajności na korzyść implementacji w Javie. Implementacja w Javie była znacznie szybsza w każdym z przeprowadzonych testów. Przyczynami mogą być:
- **Optymalizacja JIT (Just-In-Time Compilation)**: Java może korzystać z optymalizacji JIT, która znacznie przyspiesza wykonanie powtarzalnych lub intensywnych obliczeniowo operacji, takich jak konwolucja.
- **Narzut JNI**: Wywołania między Javą a C++ mogą wprowadzać dodatkowy narzut, zwłaszcza przy intensywnym przesyłaniu danych między JVM a kodem natywnym, co może tłumaczyć niższą wydajność implementacji w C++.

## Wnioski
Podsumowując, testy wskazują na większą efektywność implementacji konwolucji w Javie w porównaniu do C++. Zaleca się dalsze badania, aby zrozumieć i zoptymalizować potencjalne wąskie gardła w implementacji C++, szczególnie te związane z użyciem JNI.
