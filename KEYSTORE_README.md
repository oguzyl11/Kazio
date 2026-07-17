# Uygulamayı İmzalamak (Keystore Oluşturma)

Kazio uygulamasını Google Play Store'da yayınlamak (veya Release AAB oluşturmak) için kendi özel anahtarınızla (keystore) imzalamanız gerekmektedir. 
**Güvenlik gereği bu anahtar dosyasını ve parolalarını ASLA GitHub'a veya açık bir yere yüklemeyin.**

Aşağıdaki adımları takip ederek güvenli bir imzalama (signing) ortamı kurabilirsiniz.

## Adım 1: Keystore Dosyası Oluşturun
Terminal (veya Komut İstemcisi) üzerinden aşağıdaki komutu çalıştırarak bir `.jks` (Java KeyStore) dosyası oluşturun:

```bash
keytool -genkey -v -keystore release-key.jks -alias kazio_alias -keyalg RSA -keysize 2048 -validity 10000
```
- Bu komut size bir parola soracaktır. Parolanızı belirleyin ve unutmayın.
- Ardından Ad, Soyad, Organizasyon gibi sorular soracak. Hepsini doldurun ve `yes` diyerek onaylayın.
- Komut çalıştığı dizinde `release-key.jks` isimli bir dosya oluşacaktır. Bu dosyayı güvende tutun.

## Adım 2: local.properties Dosyasını Düzenleyin
Oluşturduğunuz bu anahtarı projeye tanıtmak için projenin KÖK dizinindeki (app klasörünün dışındaki) `local.properties` dosyasına aşağıdaki satırları ekleyin:

```properties
# local.properties (Bu dosya git'e yüklenmez, sadece sizin bilgisayarınızda durur)
storeFile=C:\\Tam\\Yol\\release-key.jks
storePassword=belirlediginiz_parola
keyAlias=kazio_alias
keyPassword=belirlediginiz_parola
```
*(Windows kullanıyorsanız dosya yolunda çift ters eğik çizgi `\\` kullanmaya dikkat edin veya `/` kullanın)*

## Adım 3: Release Build Alın
Artık Android Studio üzerinden veya terminalden güvenle release build alabilirsiniz:
```bash
./gradlew bundleRelease
```
Oluşan `.aab` dosyasını `app/build/outputs/bundle/release/` dizininden alıp doğrudan Google Play Console'a yükleyebilirsiniz.
