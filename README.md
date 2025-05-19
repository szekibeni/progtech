# Vonatmenetrend-kezelő rendszerterv (TrainApp)

## 1. Áttekintés

A TrainApp egy JavaFX-alapú asztali alkalmazás, amely egy vonatmenetrend-kezelő rendszert valósít meg. Az alkalmazás kétféle felhasználót támogat:

- **Állomásfőnök (admin)**: hozzáadhat, törölhet vonatokat.
- **Utas (felhasználó)**: megtekintheti a vonatlistát, kereshet, és kiválaszthat egy járatot, majd egy záróoldalon megjelenik a kiválasztott vonat indulása.

## 2. Funkcionális követelmények

- Bejelentkezés: A felhasználó kiválasztja, hogy állomásfőnökként vagy utasként szeretne belépni.
- **Állomásfőnökként:**
  - Vonatok hozzáadása (név, típus, kapacitás, indulás, érkezés).
  - Vonatok törlése.
- **Utasként:**
  - Vonatok listájának megtekintése.
  - Keresés név vagy típus szerint.
  - Kiválasztott vonat adatait megjelenítő záróoldal.

## 3. Nem-funkcionális követelmények

- Egyszerű, letisztult felhasználói felület (JavaFX).
- Az adatok adatbázisból töltődnek be (TrainRepository).
- A rendszer könnyen bővíthető és karbantartható.
- Az alkalmazás ikonjának minden ablakban a `prog.png` van beállítva.

## 4. Architektúra

- **MVC (Model-View-Controller)** elv mentén épül:
  - **Model**: `Train` osztály
  - **View**: `LoginWindow`, `TrainApp`, `SummaryWindow`
  - **Controller**: eseménykezelők minden ablakban

## 5. Adatmodell

class Train {
    private int trainId;
    private String trainName;
    private String trainType;
    private int capacity;
    private String departureTime;
    private String arrivalTime;
    // Getterek/setterek
}
## 6. Főbb osztályok és feladataik

- `LoginWindow`: belépési képernyő, ahol kiválasztható, hogy a felhasználó utasként vagy állomásfőnökként lép be.
- `TrainApp`: a főablak, amely kétféle működési módot biztosít:
  - **Utas módban**: csak megtekintés és keresés.
  - **Admin módban**: vonatok hozzáadása és törlése is elérhető.
- `TrainRepository`: az adatbázisműveletekért felelős osztály (`getAllTrains()`, `addTrain()`, `deleteTrain()`).
- `SummaryWindow`: utas módban egy záróoldal, ahol megjelenik a kiválasztott vonat száma és indulási ideje.

## 7. Felhasználói felület

- Minden képernyő JavaFX technológiával készült.
- A stílus egységes, `style.css` fájl szabályozza a megjelenést.
- Minden ablakban az alkalmazás ikonja a `prog.png` fájl.
- Layout elrendezések:
  - `VBox` és `HBox` az űrlapokhoz és vezérlőkhöz.
  - `BorderPane` a fő szerkezethez.

## 8. Hibakezelés

- Hibás adatbevitel esetén (pl. szöveg a kapacitás mezőbe) figyelmeztető `Alert` ablak jelenik meg.
- Üres adatbázis esetén dummy vonat kerül megjelenítésre a működés biztosításához.
- Törlés előtt a felhasználónak ki kell jelölnie egy elemet.

## 9. Tesztelés

- Az üzleti logika (pl. `TrainRepository`) tesztelése JUnit keretrendszerrel történik.
- Tesztelhető műveletek:
  - Vonat hozzáadása adatbázisba
  - Vonat törlése adatbázisból
  - Lista lekérése
- Kézi teszteléssel ellenőrzött:
  - Felhasználói felület viselkedése
  - Kereső funkció működése
  - Hibakezelési esetek (pl. üres mezők, hibás formátumok)

## 10. Futtatási követelmények

- **Java 17+**
- **JavaFX 17+**
- **JDBC-kompatibilis adatbázis**, például SQLite vagy MySQL (igény szerint)
- **Fejlesztői környezet**: IntelliJ IDEA, Eclipse vagy NetBeans

## 11. Jövőbeli bővítési lehetőségek

- Vonatok adatainak szerkesztése a felületen
- Felhasználói jogosultságkezelés (pl. saját fiók, jelszavas belépés)
- Jegyfoglalás és vásárlás funkció integrálása
- Állomások és útvonalak kezelése külön modulban

## 12. Képernyőképek (ajánlott hozzáadni a dokumentáció végéhez)

- Bejelentkező képernyő (LoginWindow)
- Vonatok listája admin nézetben
- Vonatok listája utas nézetben
- Záróoldal (SummaryWindow) utas számára

---

**Készítette:** *Szeghalmi Bence, Polatschek Péter*  
**Dátum:** 2025.05.19
