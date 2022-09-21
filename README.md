# APRO2_22L_PRO_CZ_Rozliczenie_wspólnych_wydatków

Projekt powstał w ramach przedmiotu Algorytmy i Programowanie 2, który realizowałem podczas 2 semestru studiów "Cyberbezpieczenstwo" na Politechnice Warszawskiej.

## Opis struktury repozytorium
Nasz projekt składa się z dwóch branchy, z których branch o nazwie gui-backend jest to branch, który przedstawia działanie i współpracę części logicznej programu z GUI i jest on branchem głównym, domyślnym. Druga gałąź o nazwie main służy do przedstawienia działania serwera.  
## Założenia 
Aplikacja miała zawierać następujące komponenty: 
#### BACKEND - odpowiadający za logiczne działanie programu, który pozwala na: 
- stworzenie grupy i wykonywanie na niej operacji, 
- dodawania wydatków o różnej charakterystyce podziału i osób, które są z danym wydatkiem związane(Wydatek po równo, po dokładnej kwocie, po procencie) 
- możliwości uregulowania wydatku, sprawdzenia własnych wydatków, które są przypisane do odpowiednich grup, 
- możliwość przekazania(uproszczenia) dotychczasowych wydatków w grupie(metoda Simplify).  
#### GUI 
#### Serwer 

Program zawiera te komponenty, z tym że nie współpracują one wszystkie ze sobą. Backend współpracuje z GUI, który pozwala na realizacje prawie wszystkich operacji, na które pozwala logiczna część programu. Niestety nie udało się nam zmieścić w deadlinie i dołączyć do tego wszystkiego serwera, więc możliwe jest jedynie sprawdazenie jego działania w osobnej gałęzi.
