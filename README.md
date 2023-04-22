# APRO2_22L_PRO_CZ_Rozliczenie_wspólnych_wydatków

Projekt powstał w ramach przedmiotu Algorytmy i Programowanie 2, który realizowałem podczas 2 semestru studiów "Cyberbezpieczenstwo" na Politechnice Warszawskiej.

## Opis struktury repozytorium
Nasz projekt składa się z dwóch branchy, z których branch o nazwie gui-backend to branch, który przedstawia działanie i współpracę części logicznej programu z GUI i jest on branchem głównym, domyślnym. Druga gałąź o nazwie main służy do przedstawienia działania serwera.  

## Założenia 
Aplikacja miała zawierać następujące komponenty: 
#### - BACKEND - odpowiadający za logiczne działanie programu, który pozwala na: 
- stworzenie grupy i wykonywanie na niej operacji, 
- dodawanie wydatków o różnej charakterystyce podziału i osób, które są z danym wydatkiem związane(Wydatek po równo, po dokładnej kwocie, po procencie) 
- uregulowanie wydatku, sprawdzenia własnych wydatków, które są przypisane do odpowiednich grup, 
- przekazanie(uproszczenie) dotychczasowych wydatków w grupie(metoda Simplify). 

#### - GUI 
- przyjazną prezentację danych o długach i funkcji programu użytkownikowi

#### - Serwer 
- komunikacje z klientem aplikacji
- przechowywanie danych

Program zawiera te komponenty, z tym że nie współpracują one wszystkie ze sobą. Backend współpracuje z GUI, który pozwala na realizacje prawie wszystkich operacji, na które zawiera logiczna część programu. Niestety nie udało się nam zmieścić w deadlinie i dołączyć do tego wszystkiego serwera, więc możliwe jest jedynie sprawdazenie jego działania w osobnej gałęzi. Być może kiedyś spróbuję dokończyć projekt tak, żeby gui oraz backend stanowiły jedność i z aplikacji dało się korzystać w przyjazny sposób.
