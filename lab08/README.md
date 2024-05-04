Napisz program, w którym wykorzystane zostanie JNI. Zadanie do wykonania polegać ma na zadeklarowaniu klasy Java 
z dwiema metodami służącymi do obliczania dyskretnej dwuwymiarowej funkcji splotu (ang. 2D discrete convolution function): 
natywną oraz normalną, a następnie przetestowaniu ich działania.
Metoda do obliczania splotu powinna przyjmować na wejście dwie tablice dwuwymiarowe 
(jądro splotu oraz macierz przetwarzaną) oraz produkować na wyjściu wynik (macierz wyników). 
O tym, czym jest funkcji splotu, poczytać sobie można w wielu tutorialach, np. w dokumencie 
https://staff.uz.zgora.pl/agramack/files/conv/Splot.pdf
Podczas testowania należy wygenerować odpowiednio duży problem obliczeniowy, 
a potem spróbować go rozwiązać korzystając z obu zaimplementowanych metod. 
Testowanie powinno dać odpowiedź na pytanie, która z implementacji jest wydajniejsza. 
Stąd uruchamianiu metod powinno towarzyszyć mierzenie czasu ich wykonania (czyli coś na kształt testów wydajnościowych). 
Sposób wykonania takich testów jest dowolny, jednak warto spróbować wykorzystać do tego np. framework Mockito.
Jako wynik zadania, oprócz kodów źródłowych Java oraz kodu zaimplementowanej natywnej biblioteki, należy dostarczyć 
również raport z omówieniem wyników testowania. Proszę zwrócić uwagę na to, czy podczas testów nie uwidoczniło się działanie JIT.

javac -h . src\main\java\pl\pwr\ConvolutionCounter.java

potem:

g++ -c -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" pl_pwr_ConvolutionCounter.cpp -o pl_pwr_ConvolutionCounter.o
g++ -shared -o native.dll pl_pwr_ConvolutionCounter.o -Wl,--add-stdcall-alias

lub
g++ -m64 -I"%JAVA_HOME%\include" -I"%JAVA_HOME%\include\win32" -shared -o native.dll pl_pwr_ConvolutionCounter.cpp



Raport z Testowania Wydajności Implementacji Konwolucji w Java i C++
W ramach testowania dwóch implementacji dyskretnej dwuwymiarowej funkcji splotu — jednej w Javie, drugiej w C++ (przy użyciu Java Native Interface) — przeprowadzono szereg eksperymentów mających na celu porównanie ich wydajności. Celem testów była ocena, która z metod jest szybsza przy różnych rozmiarach danych wejściowych.

Wyniki Testów
Macierz wejściowa 10x10 z kernelem 3x3
Java: 219,700 nanosekund
C++: 658,700 nanosekund
Macierz wejściowa 100x100 z kernelem 11x11
Java: 28,823,700 nanosekund
C++: 674,978,900 nanosekund
Macierz wejściowa 200x200 z kernelem 20x20 (dwa testy dla tej samej konfiguracji)
Java: 245,635,100 nanosekund
C++: 10,964,917,800 nanosekund

Analiza wyników pokazuje znaczącą różnicę w wydajności na korzyść implementacji w Javie w każdym z przeprowadzonych testów. Wydaje się, że implementacja w Javie jest lepiej zoptymalizowana lub mniej obciążona dodatkowymi kosztami wywołania, które mogą występować w implementacji C++ przy użyciu JNI.

Wydajność Javy: Java może korzystać z optymalizacji JIT (Just-In-Time Compilation), która znacznie przyspiesza wykonanie powtarzalnych lub intensywnych obliczeniowo operacji, takich jak konwolucja.
Narzut JNI: Wywołania między Javą a C++ mogą wprowadzać dodatkowy narzut, zwłaszcza przy intensywnym przesyłaniu danych między JVM a kodem natywnym, co może tłumaczyć niższą wydajność implementacji w C++.
Podsumowując, testy wskazują na większą efektywność implementacji konwolucji w Javie w porównaniu do C++. Zaleca się dalsze badania, aby zrozumieć i zoptymalizować potencjalne wąskie gardła w implementacji C++, szczególnie te związane z użyciem JNI.