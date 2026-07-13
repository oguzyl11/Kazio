# Kazio AI Geliştirme Kiti

Bu klasör, Kazio Android projesini Cursor veya Antigravity gibi AI destekli kod editörlerine vermeden önce hazırlanmış rehber dosyalarını içerir.

## Dosyalar ve Okuma Sırası
1. **`00-PROJECT_BRIEF.md`** — Projenin ne olduğu, MVP kapsamı, veri modeli, kapsam dışı özellikler
2. **`01-ARCHITECTURE.md`** — Mimari (Clean Architecture + MVVM), katman kuralları, klasör yapısı
3. **`02-CLEAN_CODE_STANDARDS.md`** — Kod yazım standartları, SOLID uygulaması, isimlendirme
4. **`03-TESTING_STRATEGY.md`** — Test yaklaşımı, coverage hedefleri, kritik edge case'ler
5. **`04-DEBUG_AND_WORKFLOW.md`** — Hata ayıklama protokolü, git akışı, Definition of Done
6. **`05-DESIGN_PROMPT.md`** — Basit ekranlar için ayrı kullanılacak tasarım prompt'u

## Cursor'da Kullanım
En pratik yol: bu dosyaların içeriğini birleştirip proje köküne `.cursor/rules/kazio.mdc` (veya `AGENTS.md`) olarak koyun — Cursor bunu otomatik context olarak okur. Alternatif olarak, her görevde ilgili dosyayı `@00-PROJECT_BRIEF.md` şeklinde referans verebilirsiniz.

## Antigravity'de Kullanım
Dosyaları proje köküne koyun ve yeni bir konuşma başlatırken şunu yazın:

> "Bu projede çalışırken /00-PROJECT_BRIEF.md, /01-ARCHITECTURE.md, /02-CLEAN_CODE_STANDARDS.md, /03-TESTING_STRATEGY.md ve /04-DEBUG_AND_WORKFLOW.md dosyalarındaki kurallara birebir uy."

Destekliyorsa bu talimatı agent/system config alanına da yapıştırabilirsiniz.

## Önerilen İlk Mesaj (kopyala-yapıştır)
```
Kazio projesini bu klasördeki brief, mimari, kod standartları ve test
stratejisi dosyalarına göre sıfırdan kur.
1) Önce boş proje iskeletini (Gradle, Hilt, Room, klasör yapısı) oluştur.
2) Sonra Dashboard ekranını uçtan uca tamamla: UI (Compose) + ViewModel +
   UseCase + Repository + Room + ilgili testler.
3) Her adımdan sonra hangi dosyaları oluşturduğunu ve hangi testleri
   çalıştırdığını kısaca özetle.
```

## Tasarım için Ayrı Kullanım
`05-DESIGN_PROMPT.md` diğerlerinden bağımsızdır — doğrudan bir tasarım aracına (v0, Antigravity tasarım modu, Figma AI) yapıştırılabilir.
