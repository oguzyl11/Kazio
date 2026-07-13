# Kazio — Test Stratejisi

> **Ajana not:** Yeni bir UseCase veya ViewModel yazıldığında, aynı görev kapsamında karşılık gelen testler de yazılır. Test yazılmadan bir görev "tamamlandı" olarak işaretlenmez.

## Test Piramidi
1. **Unit testler (çoğunluk)** — domain (UseCase) ve ViewModel
2. **Entegrasyon testleri (az)** — Room DAO, Repository implementasyonları
3. **UI testleri (en az)** — yalnızca kritik akışlar (gelir ekleme, vardiya başlatma)

## Araçlar
- JUnit5 veya Kotest
- MockK (relaxed mock)
- Turbine (Flow/StateFlow test etmek için)
- Room in-memory database (`androidTest`, gerçek SQLite davranışını doğrulamak için)
- Compose UI Test (`createComposeRule`)

## Kurallar
- Her `UseCase` için en az: 1 happy path + 1 edge case + 1 hata durumu testi yazılır
- Test isimlendirme backtick ile açıklayıcı cümle şeklinde olur:
```kotlin
@Test
fun `daily net calculation subtracts all expense categories from income`() { ... }

@Test
fun `hourly net returns zero when shift duration is zero`() { ... }

@Test
fun `shift crossing midnight calculates duration correctly`() { ... }
```

## Kritik Edge Case Listesi (mutlaka test edilmeli)
- Gece yarısını geçen vardiya (örn. 23:30 – 01:15) → saatlik hesap yanlış olmamalı
- Gelir sıfır, gider var → net kazanç negatif gösterilmeli, uygulama çökmemeli
- Ondalıklı tutar girişi (12,50 ₺) → virgül/nokta karışıklığı olmamalı
- Aynı anda iki açık vardiya başlatma denemesi → engellenmeli, kullanıcıya net mesaj verilmeli
- Silinen bir platforma bağlı eski gelir kaydı → uygulama çökmeden, "silinmiş platform" olarak gösterilmeli
- Çok büyük tutar girişi (ör. yanlışlıkla fazladan sıfır) → mantıklı bir üst sınır kontrolü olmalı

## Coverage Hedefi
- Domain katmanı: ≥ %85
- Data (Repository) katmanı: ≥ %70
- Presentation (ViewModel): ≥ %70
- UI (Composable): kritik akışlar için smoke test yeterli, %100 hedeflenmez

## Görev Akışı Şablonu (her yeni özellik için)
1. Önce başarısız olan (failing) testi yaz
2. Testi geçirecek minimum kodu yaz
3. Refactor et (testler yeşil kalmalı)
4. `./gradlew test` çalıştır, sonucu kısaca özetle (kaç test, geçen/kalan)
