# Play - CloudStream CS3

Bu depo `Tavsiyefilmizle` CloudStream eklentisini GitHub Actions ile derler.

## Otomatik akış

1. `main` dalına push edilir.
2. GitHub Actions JDK 17 ile `./gradlew make` çalıştırır.
3. Üretilen `.cs3` dosyası artifact olarak yüklenir.
4. Aynı dosya `builds` dalına gönderilir.
5. `builds/plugins.json` yalnızca güncel eklenti için otomatik oluşturulur.

Repo: https://github.com/darknesslord19/Play
