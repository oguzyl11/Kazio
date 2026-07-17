# Kazio - Play Store Yayın İçerikleri

Bu dosyada Google Play Store'da uygulamanızı yayınlarken mağaza sayfasında (Store Listing) ve Data Safety (Veri Güvenliği) formunda kullanabileceğiniz metinleri bulabilirsiniz.

## 1. Mağaza Tanıtım Metinleri

### Kısa Açıklama (Short Description) - Maksimum 80 karakter
Kuryeler için tamamen ücretsiz, internetsiz ve en detaylı net kâr takip asistanı.

*(veya alternatif)*
Çoklu platform sürücüleri için gerçek net kâr takibi. Gelir-giderini anında gör!

### Uzun Açıklama (Full Description) - Maksimum 4000 karakter

**Kazio: Ne Kazandığını Değil, Ne Kadar Para Bıraktığını Gör!**

Motor kuryeler ve araç sürücüleri için özel olarak geliştirilmiş Kazio, gün sonundaki net kazancını en kolay yoldan takip etmeni sağlar. Trendyol GO, Getir, Yemeksepeti veya diğer tüm kurye platformlarında aynı anda çalışıyor olsan bile, tüm kazancını ve giderlerini tek bir ekranda toplayabilirsin.

Özellikler:
💸 **Gelir ve Gider Takibi:** Yakıt, yemek veya anlık harcamalarını hızla kaydet.
⏱️ **Vardiya Yönetimi:** Hangi saatler arası çalıştığını kaydet, mola sürelerini düş ve "Saatlik Net Kazancını" anında öğren!
📊 **Platform Karşılaştırması:** Hangi platformdan daha çok kazandığını renkli ve kolay anlaşılır grafiklerle analiz et.
🏆 **Kişisel Rekorlar:** En çok kazandığın günü veya saati gör, kendi rekorlarını kır.
🔒 **Tamamen Çevrimdışı ve Güvenli:** Kazio internetsiz çalışır. Tüm verilerin doğrudan senin telefonunda saklanır. Kimseye gönderilmez!
📄 **Detaylı PDF Raporu (Premium):** Aylık ve haftalık özetlerini tek tıkla resmi formatlı PDF belgelerine dönüştür.
⚡ **Hızlı Ekleme ve Ana Ekran Widget'ı:** Gelir ve giderleri ana ekrandan doğrudan saniyeler içinde ekle.

Hemen Kazio'yu indir ve yollardaki kârını maksimuma çıkar!

## 2. Ekran Görüntüsü Önerileri (Screenshots)
Google Play'e yüklemek için en az 4 ekran görüntüsü tavsiye edilir. Mevcut tasarımlara göre şu sırayı izleyebilirsiniz:
1. **Ana Ekran (Dashboard):** "Günlük Net Kâr"ın ve "Vardiya Başlat" butonunun göründüğü bir an. Üstünde "Net Kazancını Anında Gör" yazabilir.
2. **Hızlı Ekleme Çekmecesi:** Gelir Ekle (AddIncome) modalının açık olduğu, "Bahşiş", "Paket Başı" butonlarının göründüğü an. Üstünde "Hızlı ve Pratik Kayıt" yazabilir.
3. **Özet Ekranı (Summary):** Platform dağılımlarının yüzdelik olarak listelendiği analiz ekranı. Üstünde "Hangi Platform Daha Karlı Analiz Et" yazabilir.
4. **Ana Ekran Widget'ı:** Kazio'nun widget görünümü. Üstünde "Uygulamaya Girmeden Takip Et" yazabilir.

## 3. Data Safety (Veri Güvenliği) Formu Yanıtları
Play Console'da "App Content" -> "Data Safety" formunu doldururken aşağıdaki yanıtları kullanın:

- **Uygulamanız veri topluyor mu veya paylaşıyor mu?** -> Evet (Billing kütüphanesi nedeniyle Play Store ile iletişim kurar).
- **Hangi veri türleri toplanıyor?** -> 
  - *Financial info (Finansal Bilgiler):* Kullanıcının girdiği gelir gider DEĞİL, sadece "Kullanıcının satın alma geçmişi" (Purchase History - Google Play Billing üzerinden).
  - *App activity (Uygulama Aktivitesi):* Uygulama etkileşimleri (Google Play Faturalandırma gereksinimi).
  - *App info and performance (Uygulama bilgileri ve performans):* Çökme logları (Crash logs - eğer eklendiyse, şu an yok ama işaretlenebilir).
- **Veriler şifreleniyor mu?** -> Evet (Transit esnasında şifrelenir).
- **Kullanıcılar verilerinin silinmesini talep edebilir mi?** -> Evet. (Kullanıcılar cihazdan silince veriler kaybolur).

*(Kullanıcının yazdığı kendi verileri (yakıt fişi vs) asla dışarı çıkmaz, bunları "Toplanan Veri" olarak seçmenize gerek YOKTUR. Çünkü "Cihazın dışına çıkan" verilere toplanan veri denir.)*
