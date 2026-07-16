# Kazio — Özellik Genişletme Planı (v1.1) — Antigravity İçin Uygulama Promptu

> Bu dosya `00-PROJECT_BRIEF.md`'ye ek olarak, mevcut MVP üzerine eklenecek "ilgi çekici ama sürtünmesiz" özellikleri tanımlar. Amaç: kullanıcıyı elde tutan, offline çalışan, mevcut veri modeline ince katman olarak oturan özellikler. **v1 kapsam dışı bırakılan maddelere (OCR, platform API, bulut senkron, çoklu dil, vergi raporu) hiçbir şekilde dokunulmuyor.**

## 0. Antigravity'ye Talimat

Bu dosyayı `00-PROJECT_BRIEF.md` ile birlikte oku. Aşağıdaki özellikleri mevcut mimariye (Compose + Hilt, Room/local DB, Repository pattern) uygun şekilde, ayrı bir modül/feature paketi olarak planla. Her özellik için:
1. Önce veri modeli değişikliğini uygula (migration dahil),
2. Sonra iş mantığını (calculator/use-case katmanı) yaz,
3. En son UI'ı bağla.

Belirsiz bir nokta olursa (örn. bir eşik değeri, bir metin) varsayım yapıp devam et ve varsayımını yorum olarak kod içinde belirt — durma, sor değil, makul bir varsayımla ilerle. Mevcut ekranların (Özet, Ayarlar, Gelir/Gider modalleri, Widget) görsel dilini ve renk paletini (yeşil/kırmızı, koyu tema) koru.

---

## 1. Kırılma Noktası Göstergesi (Break-Even Indicator)

**Amaç:** Kullanıcının günün hangi anında giderlerini karşılayıp net kâra geçtiğini görsel olarak göstermek.

**Mantık:** `net = toplam gelir − toplam gider` her kayıt girişinde yeniden hesaplanıyor zaten (mevcut). Ek olarak: gider toplamı > 0 iken gelir toplamı gideri henüz karşılamadıysa ana ekrandaki "Bugün net kazancın" kartının rengi/durumu "kırılma öncesi" (turuncu/nötr) olarak işaretlenmeli; gelir gideri geçtiği anda "kırılma sonrası" (yeşil) durumuna geçmeli.

**UI:** Mevcut "Bugün net kazancın" kartının altına ince bir ilerleme çubuğu (gider tutarı = çubuğun başlangıç noktası, gelir arttıkça dolar, sıfırı geçince renk döner). Metin: kırılmadan önce "Giderlerini karşılamana ₺X kaldı", sonra "Bugünkü net kârın başladı 🎉".

**Veri modeli:** Yeni alan gerekmiyor — mevcut `IncomeEntry`/`ExpenseEntry` toplamlarından türetilen bir hesaplanmış (derived) değer.

**Kabul kriteri:** Gün içinde gelir/gider girildikçe gösterge gerçek zamanlı güncellenmeli (FR3 ile aynı prensip). Gece yarısını geçen vardiyalarda gösterge o "gün"e ait kayıtlara göre hesaplanmalı (FR5 ile tutarlı).

---

## 2. Kişisel Rekorlar

**Amaç:** Kullanıcının kendi geçmişiyle yarışması — sosyal kıyaslama yok, sadece kendi verisi.

**Rekor türleri (v1.1 kapsamı):**
- En yüksek günlük net kazanç
- En uzun vardiye (süre)
- En yüksek saatlik ortalama kazanç (tek vardiya bazında)
- En yüksek haftalık net kazanç

**Mantık:** Her yeni kayıt/vardiya kapanışında mevcut rekorla karşılaştırılır; aşılırsa "Yeni Rekor!" bildirimi/rozeti tetiklenir.

**Veri modeli:** Yeni tablo önerisi: `PersonalRecord(id, type, value, achievedAt)`. Alternatif: her sorguda mevcut kayıtlardan `MAX()` ile hesaplanabilir (küçük veri hacminde performans sorunu olmaz) — Antigravity hangisinin mevcut Repository yapısına daha uygun olduğuna karar versin.

**UI:** Ayarlar veya Özet sekmesinde "Rekorların" adında küçük bir kart/liste. Yeni rekor kırıldığında ana ekranda kısa bir toast/snackbar.

**Kabul kriteri:** Rekor kırılması yalnızca kayıt tamamlandığında (kaydet butonuna basıldığında) kontrol edilmeli, gereksiz yere sürekli hesaplama yapmamalı.

---

## 3. Seri (Streak) Takibi

**Amaç:** Alışkanlık oluşturma — "kaç gün üst üste en az bir kayıt girdin".

**Mantık:** Her takvim günü için en az bir `IncomeEntry` veya `ExpenseEntry` varsa o gün seriye dahil. Bir gün boşsa seri sıfırlanır.

**Veri modeli:** Yeni alan gerekmiyor, mevcut kayıtların `occurredAt` tarihlerinden hesaplanabilir. İsteğe bağlı: performans için `UserStats(currentStreak, longestStreak, lastEntryDate)` küçük bir tablo tutulabilir.

**UI:** Ana ekranda küçük bir rozet/ikon: "🔥 5 gün". Ayarlar'da "en uzun serin" gösterimi.

**Kabul kriteri:** Uygulama arka planda çalışmasa da (v1'de bildirim/servis yoksa) kullanıcı uygulamayı her açtığında seri doğru hesaplanmalı — arka plan servisi gerektirmiyor, sadece tarih karşılaştırması.

---

## 4. Km Başına Kâr

**Amaç:** Sürücüye "km başına gerçekten ne kazandığını" göstermek — yakıt/amortisman karşılaştırması için pratik bir metrik.

**Mantık:** `Shift` kaydına opsiyonel `distanceKm` alanı eklenir. Vardiya kapatılırken kullanıcıya "Bu vardiyada kaç km yaptın? (opsiyonel)" sorulur. Girilirse: `km başına net kâr = vardiyanın net kârı / distanceKm`.

**Veri modeli:** `Shift` tablosuna `distanceKm: Double?` (nullable — brief'teki "sürtünmeyi azaltma" ilkesiyle tutarlı, zorunlu olmamalı).

**UI:** Vardiya kapatma ekranında opsiyonel bir input; vardiya özet kartında (varsa) "₺X/km" gösterimi.

**Kabul kriteri:** `distanceKm` girilmezse bu metrik hiçbir yerde gösterilmez, hata vermez, formu tıkamaz (opsiyonel alan zorunluluğu bozmamalı).

---

## 5. En Verimli Saat Dilimi

**Amaç:** Kullanıcının geçmiş verisinden hangi saat aralığında daha çok kazandığını göstermek — bu bir **öneri/tahmin değil**, sadece geçmiş veri sunumu olmalı (kullanıcıya "şu saatte çalış" demiyoruz, "geçmişte şu saatlerde daha çok kazanmışsın" diyoruz).

**Mantık:** `IncomeEntry.occurredAt` alanındaki saat bilgisi 3-4 saatlik dilimlere (ör. 06-10, 10-14, 14-18, 18-22, 22-02, 02-06) gruplanır, her dilim için toplam/ortalama gelir hesaplanır. Son 30 günlük veri baz alınır.

**Veri modeli:** Yeni alan gerekmiyor, mevcut `occurredAt` timestamp'inden türetilir.

**UI:** Haftalık/Aylık özet ekranında (mevcut "Platform Dağılımı" kartının altına) "Saat Dilimi Dağılımı" adında benzer bir liste/çubuk grafik.

**Kabul kriteri:** Veri az olduğunda (örn. ilk hafta) yanıltıcı kesin ifadeler kullanılmamalı — "yeterli veri birikince gösterilecek" gibi bir eşik (örn. min. 5 kayıt) konulmalı.

---

## 6. Platform Verimlilik Karşılaştırması (Saatlik Ortalama)

**Amaç:** Mevcut "Platform Dağılımı" kartı sadece toplam geliri gösteriyor; buna saatlik ortalama eklenerek hangi platformun **saat başına** daha kârlı olduğu gösterilir.

**Mantık:** Bir platforma ait gelir, o platforma bağlı vardiyaların toplam süresine bölünür: `platform saatlik ortalama = o platformdaki toplam gelir / o platforma bağlı vardiyaların toplam süresi (saat)`. Vardiyaya bağlı olmayan (shiftId null) gelirler bu hesaba dahil edilmez (süre bilgisi yok).

**Veri modeli:** Yeni alan gerekmiyor — mevcut `Shift` ve `IncomeEntry.shiftId` ilişkisinden türetilir.

**UI:** Mevcut "Platform Dağılımı" listesindeki her satıra ikinci bir değer olarak "₺X/saat" eklenir.

**Kabul kriteri:** Vardiyasız girilen gelirler hesaba katılmadığı için, kullanıcıya bu metriğin yalnızca vardiyalı kayıtlara dayandığı küçük bir not/ikon ile belirtilmeli (yanlış yorumlanmasın diye).

---

## 7. Son Kayıtlar / Hızlı Tekrar Giriş

**Amaç:** FR1'deki "3 dokunuştan az gelir girişi" hedefini daha da güçlendirmek.

**Mantık:** Gelir/Gider Ekle modalı açıldığında, son 5 kayıttan en sık tekrar edeni (platform/kategori + tutar kombinasyonu) küçük bir "Son Girdiklerin" satırı olarak modalin üstünde gösterilir; dokunulduğunda tutar ve platform/kategori otomatik doldurulur, kullanıcı sadece "Kaydet"e basar.

**Veri modeli:** Yeni alan gerekmiyor, mevcut kayıtlardan sorgu.

**UI:** Mevcut "Gelir Ekle" / "Gider Ekle" bottom sheet'lerinin üst kısmına, platform/kategori chip'lerinin üstünde yatay kaydırılabilir küçük öneri kartları.

**Kabul kriteri:** Öneri hiç kayıt yokken boş, kırık bir UI göstermemeli (empty state).

---

## 8. Widget Aksiyon Kısayolu

**Amaç:** Uygulamayı açmadan widget üzerinden doğrudan gelir/gider ekleyebilmek.

**Mantık:** Mevcut widget'a (Bugünün Kârı) dokunma alanı ekleyerek Android App Widget'ın `PendingIntent` mekanizmasıyla doğrudan "Gelir Ekle" bottom sheet'ini açan bir deep-link/activity tetiklenir. İnternet gerektirmez, mevcut "internet izni gerekmiyor" NFR'ını bozmaz.

**Veri modeli:** Değişiklik yok.

**Kabul kriteri:** Widget'a dokunma, uygulamayı tam açmadan (veya açıp direkt modalı gösterecek şekilde) 1-2 saniye içinde gelir ekleme formuna ulaştırmalı.

---

## 9. Yerel Bildirimler (Vardiya Bitirme + Gün Sonu Hatırlatma)

**Amaç:** Alışkanlık oluşturma, unutulan vardiyaların kapatılmasını sağlama.

**Bildirim türleri:**
- Vardiya "Başlat" dendikten belirli bir süre sonra (örn. 8 saat — makul bir varsayım, ayarlardan değiştirilebilir olsun): "Hâlâ vardiyan açık, unutmadıysan bitirmeyi unutma."
- Gün sonuna doğru (örn. 22:00 — yerel, kullanıcı ayarlanabilir): "Bugünü hâlâ kapatmadın, net kazancını görmek ister misin?"

**Teknik not:** Android 13 (API 33)+ için `POST_NOTIFICATIONS` runtime izni gerekir — brief'teki "minimum izin" ilkesiyle çelişmez çünkü bu izin sadece bildirim gösterimi için, internet/konum gibi hassas bir izin değil. `WorkManager` veya `AlarmManager` ile tamamen yerel, offline planlanabilir.

**Kabul kriteri:** Bildirimler varsayılan olarak açık ama Ayarlar'dan kapatılabilir olmalı; kullanıcı izin vermezse uygulama çökmemeli, sessizce bildirim göndermemeli.

---

## 10. Veri Modeli Değişiklikleri — Özet

```
Shift: + distanceKm: Double? (nullable, opsiyonel)
PersonalRecord(id, type, value, achievedAt)   // opsiyonel yeni tablo, ya da hesaplanmış değer
UserStats(currentStreak, longestStreak, lastEntryDate)  // opsiyonel yeni tablo, ya da hesaplanmış değer
```
Diğer tüm özellikler mevcut `Platform`, `Vehicle`, `Shift`, `IncomeEntry`, `ExpenseEntry` tablolarından türetilen (derived) hesaplamalardır, yeni tablo gerektirmez.

## 11. Teknik Kısıtlar (Değişmeyen İlkeler)

- Tamamen offline çalışmaya devam eder (FR2 korunur) — bu özelliklerin hiçbiri network çağrısı gerektirmez.
- Soğuk başlatma < 1sn hedefi korunmalı; yeni hesaplamalar (saat dilimi, saatlik ortalama vb.) ana thread'i bloklamamalı, gerekirse arka planda hesaplanıp cache'lenmeli.
- TR yerel para/tarih formatı (FR4) tüm yeni metriklerde de uygulanmalı.
- Minimum Android API 26 korunur; bildirim izni (API 33+) geriye dönük uyumlu şekilde ele alınmalı.

## 12. Kapsam Dışı (Bu Promptta Yapılmayacaklar)

- Sosyal/karşılaştırmalı liderlik tablosu (başka kullanıcılarla kıyaslama) — v1 tek kullanıcı ilkesine aykırı.
- Tahmine dayalı öneriler ("şu saatte çalışmalısın" gibi bağlayıcı tavsiye) — sadece geçmiş veri sunumu yapılacak.
- Bulut senkronu gerektiren hiçbir özellik.

## 13. Uygulama Sırası Önerisi

1. Kırılma noktası göstergesi (en düşük efor, en yüksek görünürlük)
2. Km başına kâr + platform saatlik ortalama (aynı `Shift` ilişkisini kullanıyor, birlikte yapılırsa verimli)
3. Son kayıtlar / hızlı tekrar giriş
4. Seri takibi + kişisel rekorlar
5. En verimli saat dilimi
6. Widget aksiyon kısayolu
7. Yerel bildirimler (en son — izin akışı ve zamanlama en çok test gerektiren kısım)
