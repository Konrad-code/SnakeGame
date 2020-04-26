# SnakeGame

************** POLISH EDITION ******************

*************** POLSKA WERSJA ******************

Projekt "Snake Game":
Twórcy:

Konrad Żołyński
https://github.com/Konrad-code
konrad.zolynski@gmail.com
+48 533 683 168

Janek Misiórski
https://github.com/janekjmf
janekjmf@gmail.com
+48 883 483 807

Projekt gry Snake zostal zrealizowany w jezyku Java w oparciu o narzędzia: 
Maven, Swing oraz z wykorzystaniem polaczenia z baza danych JDBC na platformie PostgreSQL.

KONFIGURACJA:

Aby uruchomić projekt należy go zaimportować jako plik Maven oraz mieć zainstalowane środowisko 
PostgreSQL - aplikacja pgAdmin 4. Do celow testowych aplikacji podstawa jest zainstalowanie aplikacji
pgAdmin 4 na komputerze, a nastepnie zalozenie bazy, gdyz obecna wersja gry jest wersja Beta do testow offline
z baza danych wylacznie na swojej jednostce.

W pliku „sql\Snake_properties” nalezy zmienić pozycje „login” oraz „password” na login i haslo
wykorzystane przez uzytkownika przy zalozeniu bazy danych PostgreSQL. Pozycja "URL" stanowi sciezke 
do lokalnej bazy danych PostgreSQL i nalezy jej adres ustawic w pliku zgodnie z uzytkownika.
Ostatni element sciezki to nazwa bazy danych utworzonej przez uzytkownika. Zachowujac domyslne sciezki instalacji
i tworzac baze o nazwie "Snake" uzytkownik ominie koniecznosc edycji tej pozycji w pliku konfiguracyjnym bazy.

Zawartość pliku „sql\build” stanowi zbior komend przydatnych do wykonania testow na bazie danych w pgAdmin 4.
Szczegolnie wygodne bedzie inicjalizacyjne stworzenie tabeli za pomoca bloku `CREATE TABLE players`,
natomiast alternatywne korzystanie z aplikacji do testow zostanie omowione ponizej.

Jeśli IDE z którego korzysta użytkownik nie dokonuje autolinkowania, to należy plik postgresql-42.2.11.jar
(stanowiacy sterownik JDBC dla bazy danych) z folderu źródłowego src kliknąć prawym przyciskiem myszy
i dodać do "Build path", po czym plik w drzewku powinien przenieść się do "Referenced libraries".

Jako projekt Maven należy na początku uruchomić go z opcja run as -> clean, następnie tak samo tylko z opcja run as -> install. 
Użytkownik chcący dokonać testów może wykonać uruchomienie run as -> test.
Projekt byl pisany z przeznaczeniem dla uruchomienia w javie 1.8 wiec zaleca się kliknąć PPM na "JRE System Library" 
a następnie "Properties" i zmienić "Execution environment" na JaveSE-1.8".

Wstepnie projekt jest uruchamiany bez tabel uzytkownikow i punktacji. Dane sa zaciagane do zdalnego logowania 
admina ze specjalnego pliku "admin credentials" na wypadek takiej potrzeby w przyszlosci po wdrozeniu aplikacji
kiedy admin bedzie mial jako jedyny dostep do pliku. Nalezy wejsc do gry, zalogowac sie jako admin, utworzyc 
tabele w panelu administratorai mozna cieszyc sie gra oraz dodawaniem uzytkownikow.
















