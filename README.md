# Android Retrofit

Ta projekt prikazuje osnovno uporabo knjižnice Retrofit v Android aplikaciji. Retrofit je ena izmed najbolj priljubljenih knjižnic za izvajanje HTTP zahtevkov in delo z API-ji. Uporablja se predvsem v Android projektih, zato spada med platformno odvisne tehnologije.

## Zakaj sem izbral Retrofit?

Retrofit sem izbral zato, ker močno poenostavi komunikacijo s splet­nimi API-ji. Večina sodobnih mobilnih aplikacij potrebuje pridobivanje podatkov iz interneta, zato je takšna knjižnica zelo uporabna in praktična. Delo je veliko bolj pregledno kot pri ročni uporabi `HttpUrlConnection`, hkrati pa lepo sodeluje s Kotlin coroutines.

## Prednosti

- enostavna definicija API klicev z anotacijami (`@GET`, `@POST`, …)
- samodejna pretvorba JSON podatkov v Kotlin razrede
- dobra integracija s coroutines (asinhrono izvajanje)
- enostavnejše obravnavanje napak
- zelo razširjena in dobro dokumentirana knjižnica

## Slabosti

- vezana je na Android/JVM okolje, zato ni uporabna na drugih platformah
- zahteva dodatne knjižnice (npr. Gson ali Moshi za pretvorbo JSON)
- ni primerna za realnočasovno komunikacijo (WebSocketi ipd.)

## Licenca

Retrofit spada pod Android Open Source Project ki uporablja **Apache License 2.0**, kar pomeni, da je dovoljena uporaba tako v odprtokodnih kot tudi komercialnih projektih.
- [Link do licenca](https://source.android.com/license)

## Priljubljenost in uporaba
Podatki za leto 2025:

Retrofit je ena najbolj znanih knjižnic v Android svetu.  
Na GitHubu ima več kot 42.000 GitHub zvezdic, okrog 7.000 forkov, uporablja ga več milijonov Android aplikacij. Zaradi velike skupnosti je dobro podprt in redno posodobljen.

## Časovna in prostorska zahtevnost

- čas izvajanja je odvisen od omrežja in velikosti JSON podatkov (približno O(n))
- pomnilniška poraba je majhna, knjižnica pa ne poveča aplikacije za veliko (okoli nekaj sto KB)

## Razvoj in vzdrževanje

Retrofit razvija podjetje Square. Knjižnica je aktivno vzdrževana, redno posodabljana in ima 20+ aktivnih razvijalcev, posodobitve so redne. Zaradi velike uporabe v industriji je razvoj stabilen in dolgoročno zagotovljen.
