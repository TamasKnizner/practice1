#Feladat

Szimulálj egy körökre osztott csatát, két hadsereggel.

##Osztályok

A hadsereg (**Army** osztály) 8 egységből (**Unit** osztály) áll. Az egységeket három különböző szerepkörrel ruházhatod fel:

* **Supporter** (2 db): képes healelni egy friendly unitot, beleérte saját magát
* **Defender** (2 db): képes védeni egy friendly unitot, önmagát kivéve, ez azt jelenti, hogy az adott unitra beérkező damage 20%-át a guard kapja, mindemellett támadni is képes
* **Attacker** (4 db): tud támadni

Ezeknek célszerű külön osztályokat létrehozni, amik a Unit-ból származnak (tetszőlegesen bevezethető pl egy Role enum).

A Unit ősosztály csak életerővel (`hitPoints`) rendelkezik, ami maximum 1000 lehet, valamint egy `isDead()` metódussal, ami igazzal tér vissza, ha a `hitPoints` kisebb vagy egyenlő nullával.
A Unit példányok builder pattern segítségével készüljenek (ez lehet pl az adott objektum belső osztálya is).

Hint: készíts néhány config objektumot (beant), amikbe leírod a unitok tulajdonságait és a buildert azzal az adott objektummal inicializálod és a `build()` metódus lesz az adott Unit bean `factory-method`-ja.

##Interfacek
Unitokhoz:
* **Healable**: egy metódussal rendelkezik -> `void heal(int amount)` az `amount` mennyiségével nő a megcélzott Unit HP-ja, mindegyik Unit implementálja (akár egyből az ősosztály)
* **Guardable**: egy metódussal rendelkezik -> `void guardUnit(Unit guard)`, célszerű eltárolni egy adott unit guardját egy változóban, hogy tudjuk kinek kell továbbítani a damaget, a Defender-en kívül mindegyik unit implementálja, egy unitnak csak egy guardja lehet egyszerre
* **Attackable**: az "attackable" unitot meg lehet támadni -> `void attack(int damage)`, mindegyik unit implementálja

##Mechanizmus
Az, hogy melyik unit egy körben mit csinál strategy-k alapján fog eldőlni (strategy pattern). Adott három interface, ami a három különböző típusú unit strategyjének felel meg és az ő különböző implementációik a unitok döntési stratégiáik (ezek is beanek).

A metódusok praktikusan úgy néznek ki, hogy `Unit get<Heal/Guard/Attact>Target()`.

###HealStrategy
* **LowestHpHealStrategy**: a legkisebb HP-val rendelkező unitot heali
* **GuardHealStrategy**: ha van guardja a Support unitnak, akkor őt részesíti előnyben (persze csak vesztett egyáltalán HP-t)
* **SelfFirstHealStrategy**: önző és bunkó Support, aki mindig magát healeli elsőnek

Heal értéke: Random szám 0 és 200 között

###GuardStrategy
* **SupportGuardStategy**: valamelyik support unitot guardolja
* **LowestHpGuardStrategy**: a legkisebb HP-val rendelkező unitot guardolja

###AttackStrategy
* **SupportAttackStrategy**: az ellenfél supportját támadja
* **LowestHpAttackStrategy**: a legkisebb HP-val rendelkező ellenséges unitot támadja
* **RandomAttackStrategy**: random ellenséges unitot guardol

Attack értéke: Random szám 0 és 400 között

Ezek csak ajánlatok, kitalálhatsz saját strategyket is.

**Na de hogyan használjuk ezeket?**

Minden **Army** objektumnak vannak servicei, amik kiszámolják egy adott strategy alapján, hogy adott unitnak mit kell tennie.
Pl: **HealTargetService** -> `Unit getHealTarget(HealStrategy healStrategy)`. Értelemszerűen három ilyen service van (Heal, Guard, Attack).

##Játékmenet

1. Defenderek kiválasztanak egy unitot akit guardolnak
2. Attackerek támadnak
3. Support healel

Ez is csak ajánlat, célszerű a Unitokat úgy belerakni az Army listájába, hogy olyan sorrendben történjen a unitok cselekvése, ahogy szeretnéd

##Ötletek
* Bármilyen egyéb mechanikát, metódust, osztályt írhatsz.
* Írj egy aspectet, ami véletlenszerű critical healt/damaget indukál. Critical hit 20% valószínűséggel fordul elő, a critical szorzó egy véletlen szám 1 és 2 között.
* Ha egy unit meghal, logold ki, hogy ki ölte meg és mekkora volt a "gyilkos" hit. 

##Használandó technológiák:
* Java
* Spring (xml config, de megírhatod később másmilyennel is)
* Spring AOP
* Design patternek
* Logger
* ...