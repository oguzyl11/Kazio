# Kazio — Tasarım Prompt'u (Basit, Karmaşık Olmayan Ekranlar)

> Bu metni doğrudan bir tasarım aracına (Antigravity tasarım modu, v0, Claude, Figma AI vb.) yapıştırabilirsiniz.

---

**Prompt:**

Türkiye'de çalışan sürücü/kurye kullanıcılar için bir Android finans uygulamasının (Kazio) arayüzünü tasarla. Kullanıcı çoğunlukla araç içinde, kısa sürede, tek elle telefonu kullanıyor — bu yüzden **maksimum sadelik** zorunlu.

**Kesin kısıtlar:**
- Toplam en fazla 5 ekran: Ana Panel (Dashboard), Gelir Ekle, Gider Ekle, Özet (Haftalık/Aylık), Ayarlar
- Her ekranda **tek bir ana aksiyon** olsun; ikincil aksiyonlar küçük/az görünür kalsın
- Gelir/Gider ekleme tam ekran değil, **bottom sheet (alt panel)** olarak açılsın; 2-3 dokunuştan fazla adım olmasın
- Ana Panel'in en üstünde, en büyük tipografiyle **"Bugün net kazancın"** rakamı yer alsın (₺ formatında, TR yerel biçim: ₺1.234,50)
- Karmaşık grafik/chart **YOK** — sadece büyük sayılar, sade liste, istenirse tek renkli yatay bar (eksen/legend yok)
- Renk dili basit olsun: net kazanç pozitifse yeşil vurgu, gider gelirden fazlaysa kırmızı/turuncu vurgu
- Koyu tema (dark mode) öncelikli düşünülsün — gece sürüşünde göz yormamalı
- Material 3 tasarım dili; büyük dokunma alanları (min 48dp); tek elle ulaşılabilir, sağ altta FAB ile hızlı gelir/gider girişi
- Onboarding varsa tek ekran ve atlanabilir olsun; zorunlu çok adımlı sihirbaz **YOK**

**Yapılmaması gerekenler:**
- Yoğun tablo/veri ızgarası yok
- Gereksiz animasyon/geçiş efekti yok
- Çok adımlı form/sihirbaz yok
- Küçük punto, düşük kontrast yok

**Çıktı formatı:** Her ekranı ayrı ayrı; bileşen hiyerarşisi ve önemli spacing/tipografi notlarıyla birlikte sade bir mockup/açıklama olarak üret.

---

## Ekran Bazlı Notlar

### 1. Dashboard (Ana Panel)
- Üstte: "Bugün net kazancın: ₺XXX" (en büyük punto)
- Altında: aktif vardiya durumu varsa küçük satır ("Vardiya: 2s 15dk")
- Ortada: bugünkü gelir toplamı / gider toplamı (2 küçük kart, yan yana)
- Alt: platform bazlı mini sıralama (en fazla 3 satır, "tümünü gör" → Özet ekranına gider)
- Sağ alt FAB: "+" → Gelir/Gider seçim menüsü

### 2. Gelir Ekle (Bottom Sheet)
- Platform seç (chip'ler, tek dokunuş)
- Tutar gir (büyük numeric input, otomatik açılan sayısal klavye)
- Kaydet butonu (tam genişlik, tek buton)

### 3. Gider Ekle (Bottom Sheet)
- Kategori seç (chip'ler: Yakıt, Bakım, Ceza, Park, Yıkama, Diğer)
- Tutar gir
- Kaydet butonu

### 4. Özet
- Basit toggle: Haftalık / Aylık (2 seçenek)
- Toplam net kazanç (büyük punto)
- Platform bazlı liste (sıralı; oran göstermek için basit dikdörtgen bar, chart kütüphanesi gerekmez)

### 5. Ayarlar
- Basit liste: Platformları Yönet, Araç Bilgisi, Premium, Dışa Aktar (premium), Hakkında
