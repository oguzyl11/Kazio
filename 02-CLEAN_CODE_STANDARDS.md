# Kazio — Temiz Kod Standartları

> **Ajana not:** Kod yazarken bu kuralları otomatik uygula. Bir değişiklik bu kurallardan birini ihlal ediyorsa, teslim etmeden önce kendini durdurup düzelt. Bu dosya `01-ARCHITECTURE.md` ile birlikte okunmalıdır.

## İsimlendirme
- Sınıf/arayüz: `PascalCase` (`AddIncomeUseCase`)
- Fonksiyon/değişken: `camelCase`, fiil ile başlar (`calculateDailyNet()`, `isShiftActive`)
- Sabitler: `UPPER_SNAKE_CASE`, ayrı bir global `Constants.kt` yerine ait olduğu sınıfın `companion object`inde tanımlanır
- Boolean isimleri soru cümlesi gibi olmalı: `isActive`, `hasExpense`, `canEdit`

## Fonksiyon ve Sınıf Boyutu
- Bir fonksiyon 25 satırı geçmemeli; geçiyorsa anlamlı alt fonksiyonlara bölünmeli
- Bir sınıf tek bir sorumluluğa sahip olmalı (SRP); "Manager", "Helper", "Util" gibi belirsiz isimler yasak — sınıf ne yapıyorsa onu söyleyen isim verilir

## Null Safety
- `!!` kullanımı yasak (test kodu dışında; testte de tercih edilmez)
- Nullable dönen fonksiyonlarda `?:` ile açık varsayılan davranış belirtilir

## Hata Yönetimi
- Exception fırlatıp yakalamak yerine sealed sonuç tipi kullanılır:
```kotlin
sealed interface AddIncomeResult {
    data class Success(val id: Long) : AddIncomeResult
    data class Error(val reason: IncomeError) : AddIncomeResult
}
```
- Kullanıcıya gösterilecek hata metinleri domain katmanında değil, presentation katmanında (string resource ile) üretilir; domain sadece hata *türünü* döndürür

## Immutability
- `var` yerine mümkün olduğunca `val` kullanılır
- Domain modelleri `data class` ve immutable olmalı; güncelleme `copy()` ile yapılır, alan doğrudan değiştirilmez

## Compose Kuralları
- Composable fonksiyonlar **stateless** olmalı: state ve event lambda'ları parametre olarak gelir (state hoisting)
- Composable, ViewModel'i doğrudan constructor parametresi olarak almaz; `hiltViewModel()` yalnızca `XScreen` (route) seviyesinde çağrılır, alt composable'lara sade state/lambda geçilir
- Ana ekranlar için en az bir `@Preview` composable eklenir
- Composable içinde hesaplama/formatlama iş kuralı yazılmaz; ViewModel'den hazır, gösterime uygun veri gelir

## Sayı ve Para Formatlama
- Tüm para gösterimleri tek bir `CurrencyFormatter` üzerinden geçer; her yerde manuel `String.format` kullanımı yasak
- Yerelleştirme sabiti: `Locale("tr", "TR")` — ondalık ayırıcı virgül, binlik ayırıcı nokta (₺1.234,50)

## Yorum Politikası
- Kod "ne yaptığını" değil "neden öyle yapıldığını" açıklamalı
- Yorum satırı, kötü isimlendirmenin telafisi olarak kullanılmaz — önce isim düzeltilir, yorum sonra düşünülür

## Commit Mesajı Formatı (Conventional Commits)
```
feat(income): add quick income entry bottom sheet
fix(shift): correct hourly net calculation across midnight
test(usecase): add unit tests for CalculateDailyNetUseCase
refactor(repository): split DataRepository into Income/Expense repos
```

## Kod İnceleme Kontrol Listesi (teslimden önce AI'nin kendine sorması gerekenler)
- [ ] Bu değişiklik tek bir amaca mı hizmet ediyor?
- [ ] Yeni bir sınıf/fonksiyon SRP'yi ihlal ediyor mu?
- [ ] Bu davranış için test eklendi mi / güncellendi mi?
- [ ] Magic number/string var mı?
- [ ] Presentation katmanına iş kuralı sızdı mı?
- [ ] `!!` veya yakalanmamış exception var mı?
