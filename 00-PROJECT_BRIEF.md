# Kazio — Mühendislik Brief'i (v1.0)

> Bu dosya projenin **ne** ve **neden**ini tanımlar. Kod yazan AI ajanına (Cursor/Antigravity/Claude Code) verilecek ilk dosyadır. Diğer tüm kararlar (mimari, kod standardı, test) bu belgeye dayanır.

## 1. Ürün Tanımı
Kazio, Türkiye'de birden fazla platformda (Uber, Bolt, BiTaksi, Trendyol Go, Getir, Yemeksepeti) çalışan sürücü ve kuryeler için **günlük gerçek net kârlılığı** gösteren, tamamen çevrimdışı çalışan bir Android finans asistanıdır.

Vaat: *"Ne kazandığını değil, ne kadar para bıraktığını göster."*

## 2. Hedef Kullanıcı
- Aynı gün birden fazla platformda çalışan sürücü/kurye
- Genelde araç içinde, kısa sürede, tek elle uygulamayı kullanan kişi
- Teknik olmayan, hızlı sonuç isteyen kullanıcı

## 3. MVP Kapsamı (v1 — bunların dışına çıkılmaz)
1. Platform tanımlama (varsayılan liste hazır gelir + kullanıcı yeni platform ekleyebilir)
2. **Gelir girişi**: platform, tutar, tarih/saat, opsiyonel not
3. **Gider girişi**: kategori (Yakıt, Bakım, Ceza, Park, Yıkama, Diğer), tutar, tarih
4. **Vardiya (shift) kaydı** — başlangıç/bitiş saati. 🔧 *Pitch deck'te yoktu; saatlik kazanç hesabı bu olmadan anlamsızlaşıyor, bu yüzden MVP'ye eklendi.*
5. Günlük net kazanç paneli (toplam gelir − toplam gider)
6. Haftalık/aylık özet (basit toplamlar — karmaşık grafik yok)
7. Platform bazlı kârlılık karşılaştırması (basit sıralı liste)
8. Android widget: bugünün net kazancı

## 4. v1 Dışı (bilinçli olarak kapsam dışı bırakıldı)
- OCR ile fiş/ekran görüntüsü okuma
- Platform API entegrasyonu (otomatik gelir çekme) — hiçbir platform buna resmi izin vermiyor, hukuki/teknik risk yüksek
- Bulut yedekleme / çoklu cihaz senkronizasyonu
- Vergi raporu / resmi mali belge üretimi
- Çoklu kullanıcı hesabı
- Çoklu dil desteği (v1 yalnızca Türkçe, TRY)

> Ajana not: Bu kapsam dışı maddeler "yapılmayacak" değil, "v1'de yapılmayacak" demektir. Kodda bu özellikler için yer bırakılabilir (ör. Repository interface'i genişlemeye açık olmalı) ama implementasyon yazılmaz.

## 5. Veri Modeli (öneri)
```
Platform(id, name, colorTag, isCustom)
Vehicle(id, name, type[CAR|MOTORCYCLE|BICYCLE])   // 🔧 yeni: yakıt/bakım gideri araç tipine göre değişir
Shift(id, vehicleId, startAt, endAt, note)
IncomeEntry(id, shiftId?, platformId, amount, occurredAt, note)
ExpenseEntry(id, shiftId?, category, amount, occurredAt, note)
```
`DailySummary` ayrı bir tablo değildir — gelir/gider kayıtlarından anlık hesaplanan bir view/aggregate'tir.

🔧 Not: `shiftId` opsiyonel olmalı. Kullanıcı vardiya açmadan da hızlıca gelir/gider girebilmeli (sürtünmeyi azaltır); vardiya açıksa saatlik kazanç ayrıca hesaplanır.

## 6. Fonksiyonel Gereksinimler (test edilebilir olmalı)
- **FR1**: Kullanıcı 3 dokunuştan az ile bir gelir kaydı girebilmeli
- **FR2**: Uygulama internet olmadan tamamen çalışmalı
- **FR3**: Günlük net kazanç gerçek zamanlı güncellenmeli
- **FR4**: Tutarlar TR yerel biçiminde gösterilmeli (₺1.234,50)
- **FR5**: Bir vardiya açıkken saatlik net kazanç canlı hesaplanmalı, gece yarısını geçen vardiyalarda da doğru olmalı
- **FR6**: Widget, en son güncellenen günün net kazancını göstermeli

## 7. Fonksiyonel Olmayan Gereksinimler
- Soğuk başlatma < 1sn (tamamen yerel veri, network çağrısı yok)
- Minimum Android API 26 (Oreo) — Compose + güncel Hilt için makul alt sınır
- Uygulama izinleri minimum tutulmalı (internet izni v1'de gerekmiyor)

## 8. İş Modeli — Paywall Noktaları (Premium)
- 30 günden eski kayıt görüntüleme
- PDF/Excel dışa aktarma
- Bulut yedekleme
- Widget özelleştirme
- Reklamsız kullanım (ücretsiz sürümde banner reklam olabilir ama kayıt girişini asla engellememeli)

## 9. Açık Varsayımlar (gerekirse değiştirilebilir)
- Tek para birimi: TRY
- Tek kullanıcı, tek cihaz (v1)
- Reklam SDK'sı henüz seçilmedi — v1 reklamsız başlayıp sonradan eklenmesi önerilir (teknik borç riski düşük)
- Vardiya kaydı zorunlu değil, opsiyonel bir "gelişmiş kullanım" özelliği olarak sunulmalı
