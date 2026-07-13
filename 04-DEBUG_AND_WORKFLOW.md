# Kazio — Debug ve Çalışma Akışı Rehberi

> **Ajana not:** Bir hata bildirildiğinde aşağıdaki sırayı izle. Anlamadan rastgele kod değiştirmek yasak.

## Bug Çözme Protokolü
1. **Reproduce et** — hatayı yeniden üretecek somut adımları veya test senaryosunu tanımla
2. **Failing test yaz** — mümkünse hatayı gösteren bir unit test ekle; bu, düzeltmenin gerçekten çalıştığını kanıtlar
3. **Kök nedeni bul** — sadece belirtiyi değil, kaynağı düzelt
4. **Minimal fix uygula** — alakasız refactor'ları aynı değişikliğe karıştırma
5. **Regresyon kontrolü** — ilgili diğer testleri çalıştır
6. **Özetle** — ne bozulmuştu, neden bozulmuştu, nasıl düzeltildi; kısa ve net anlat

## Loglama
- `println` kullanımı yasak; `Timber` kullanılır
- Log seviyeleri: geliştirme sırasında `Timber.d`, gerçek hata durumunda `Timber.e`
- Kullanıcıya ait veriler (tutar, kişisel not) log'a asla ham/açık şekilde yazılmaz

## Git Akışı
- Branch isimlendirme: `feature/add-income-entry`, `fix/shift-midnight-bug`, `test/usecase-coverage`
- Her commit tek bir mantıksal değişiklik içerir
- `main` branch'e doğrudan commit yapılmaz; küçük, gözden geçirilebilir değişiklikler tercih edilir

## Definition of Done (bir görev bunlar sağlanmadan bitmiş sayılmaz)
- [ ] Kod doğru katmana yerleştirildi (bkz. `01-ARCHITECTURE.md`)
- [ ] Yeni/değişen davranış için test yazıldı ve geçti
- [ ] Lint/format kontrolünden geçti (ktlint/detekt varsa)
- [ ] Magic number/string, `!!`, god-class yok
- [ ] Kısa bir değişiklik özeti verildi

## AI Ajanına Genel Talimatlar
- Belirsiz bir gereksinimle karşılaşınca makul bir varsayım yapıp ilerle; varsayımı commit mesajında veya kısa bir notta belirt
- Aynı anda birden fazla sorumluluğu olan büyük bir değişiklik yapma; küçük, test edilebilir adımlarla ilerle
- `01-ARCHITECTURE.md`'de tanımlı bir mimari kararı kendi başına değiştirme; gerekçeli bir öneri olarak sun, kullanıcı onaylamadan uygulama
