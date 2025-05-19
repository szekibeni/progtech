# Rendszerterv – Vonatközlekedés Menetrendszerűségének Kezelése

**Készítők:** Polatschek Péter, Szeghalmi Bence

## 1. Bevezetés

A projekt célja egy informatikai rendszer létrehozása, amely lehetővé teszi a vonatok menetrendszerűségének nyomon követését, a késések rögzítését, elemzését és riportálását. A rendszer hasznos lehet mind a közlekedési vállalatoknak, mind az utasok számára.

---

## 2. Követelmények

### 2.1 Funkcionális követelmények
- Menetrend importálása (pl. CSV, API)
- Vonatok valós idejű helyzetének követése
- Késések rögzítése és naplózása
- Statisztikák és jelentések generálása (pl. menetrendszerűségi arány)
- Felhasználói jogosultságkezelés (admin, operátor, utas)
- Keresés vonatszámra, útvonalra vagy dátumra
- Értesítés késésekről (email/push)

### 2.2 Nem funkcionális követelmények
- Reszponzív webes felület
- Adatbiztonság és naplózás
- Skálázhatóság több állomás és járat kezelésére
- Minimum 99%-os rendelkezésre állás

---

## 3. Rendszerarchitektúra

### 3.1 Áttekintés

A rendszer három fő komponensből áll:

1. **Frontend:** Webes felhasználói felület React vagy Angular alapokon.
2. **Backend:** REST API Node.js vagy Django használatával.
3. **Adatbázis:** PostgreSQL vagy MongoDB az adatok tárolására.

## 4. Adatmodell

### 4.1 Fő entitások
- **Vonat**
  - ID
  - Járatszám
  - Útvonal
  - Indulási és érkezési idő
- **Állomás**
  - ID
  - Név
  - Koordináták
- **Menetrend**
  - Vonat_ID
  - Állomás_ID
  - Tervezett indulás / érkezés
- **Valós idejű adat**
  - Vonat_ID
  - Aktuális pozíció
  - Aktuális idő
- **Késés**
  - Vonat_ID
  - Dátum
  - Állomás
  - Percek száma

---

## 5. Felhasználói szerepkörök

| Szerepkör   | Jogosultságok |
|-------------|----------------|
| Admin       | Mindenhez hozzáfér, statisztikák |
| Operátor    | Késések rögzítése, menetrend frissítése |
| Utas        | Menetrend és késések lekérdezése |

---

## 6. Interfészek

- **Felhasználói felület:** Mobilbarát webapp
- **Backend API:** RESTful JSON válaszok
- **Külső API kapcsolat:** Valós idejű pozíciók (pl. GPS szolgáltatásból)
- **Admin felület:** Menetrend és késéskezelés

---

## 7. Tesztelési terv

- Egységtesztek minden backend funkcióhoz
- Integrációs tesztek az API és az adatbázis között
- UI tesztek (pl. Cypress)
- Teljesítményteszt nagy adathalmazon
- Felhasználói tesztelés (pl. utas felület usability)

---

## 8. Fejlesztési és üzemeltetési környezet

- Fejlesztés: VS Code, Git, Docker
- Üzemeltetés: Heroku / Vercel (frontend), AWS / DigitalOcean (backend)
- CI/CD: GitHub Actions vagy GitLab CI

---

## 9. Kockázatok

- Valós idejű adatkapcsolat instabilitása
- Nagy mennyiségű adat tárolása hosszú távon
- API-k hibakezelése

---

## 10. Jövőbeli bővítési lehetőségek

- Mobil alkalmazás (Android / iOS)
- Térképes megjelenítés
- Gépi tanulás alapú késés-előrejelzés
- Utas visszajelzési rendszer

---

## 11. Összefoglalás

A rendszer célja a vonatközlekedés átláthatóságának javítása, a késések hatékony nyilvántartása és a döntéstámogatás segítése. A projekt hosszú távon akár országos szintű közlekedésmonitorozást is támogathat.
