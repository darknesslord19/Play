# Tavsiyefilmizle - CloudStream eklentisi

Bu proje cloudstream_super.py ile otomatik uretildi.

## .cs3 dosyasini almanin yolu (telefonda en kolayi: GitHub)
1. GitHub'da yeni bir DEPO ac (bos).
2. Bu zip'in icindekileri depoya yukle (web arayuzunden "Upload files" ile, klasor yapisi korunmali).
3. Depoda Actions sekmesi -> "Build" calisir (ya da "Run workflow" ile elle baslat).
4. Bitince Actions -> son calisma -> Artifacts -> "cs3-eklentiler" indir. Icinde Tavsiyefilmizle.cs3 olur.

## Bilgisayarda derlemek icin
Gradle 8.10+ ve JDK 17 kurulu olmali:

    gradle Tavsiyefilmizle:make

Cikti: Tavsiyefilmizle/build/Tavsiyefilmizle.cs3

## Dikkat
- Derleme ayarlari (AGP / Kotlin / CloudStream surumu) CloudStream'in guncel
  "plugin-template" deposuyla ayni olmali. Surum hatasi alirsan o depodaki
  build.gradle.kts degerlerini buradakine kopyala.
- Uretilen .kt dosyasi otomatik uretildi, derleme hatasi verirse hata satirini bana gonder.
