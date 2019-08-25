# gl-crash-course

## Zadanie testowe - zajęcia II
* Stworzyć aplikację zawierająca Splash Screen i Activity
* Activity zawiera w sobie 2 Fragmenty
* Pierwszy Fragment zawiera w sobie listę pozycji
* Drugi Fragment zawiera widok szczegółowy do pozycji wybranej z listy Fragmentu
pierwszego
* Pierwszy Fragment korzysta z ViewModel'u zwracającego LiveData z listą elementów, lista
może być mockowana (przykład poniżej),
* Aby zasymulować korzystanie z serwisu REST'owego prosze o dodanie offsetu czasowego
przy wypełnianiu obiektu LiveData (minimum 2s) dla ViewModel listy
* Lista powinna mieć przynajmniej 20 pozycji
* Drugi Fragment korzysta z ViewModel'u zwracającego LiveData ze szczegółowymi
informacjami odnośnie wybranego elementu listy (przykład poniżej)
* Zachować informację o odwiedzonych elementach listy
* Po wybraniu pozycji z listy we Fragmencie I informacja ta powinna być zachowana
* Po powrocie z Fragmentu II do Fragmentu I każdy element listy powinien
pokazywać informacje o ilości odwiedzin (może też to być forma czysto graficznego
wyróżnienia, jeśli pozycja nie była jeszcze wybrana)

Zadanie bonusowe
* Stworzyć widok w orientacji poziomej, który będzie jednocześnie pokazywał zawartość obu
Fragmentów i reagował na wybór pozycji z Fragmentu listy przez pokazanie odpowiednich
informacji we Fragmencie widoku szczegółowego, oraz zmieniał status pozycji z listy
Fragmentu listy

Zadanie bonusowe II
* Zaimplementować mechanizm pozwalający stronicować listę pozycji dla Fragmentu listy, tj.
Jeśli cała lista składa się z 20 elementów, pokazać za pierwszym razem 10 i kolejne 10 np.
Poprzez mechanizm pull to refresh
Zachęcamy do eksperymentowania z LiveData oraz stworzenia odpowiedniej struktury aplikacji
poprzez umieszczenie elementów w logicznych namespacach (package)
