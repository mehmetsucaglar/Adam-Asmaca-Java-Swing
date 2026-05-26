# 🎮 Java Swing - Adam Asmaca (Hangman) Oyunu

Bu proje, Java Swing kullanılarak geliştirilmiş, grafik arayüzlü ve dosya işlemleri (File I/O) destekli bir kelime tahmin oyunudur.

## 🚀 Proje Özellikleri

* **Dinamik Kelime Seçimi:** Oyun, kelimeleri `kelimeler.txt` dosyasından okur ve uzunluğu 6 harften büyük olanları rastgele seçer.
* **Görsel Geri Bildirim:** Yanlış tahminlerde adam asmaca görselleri anlık olarak güncellenir.
* **Zamanlayıcı (Timer):** Oyuncunun kelimeyi ne kadar sürede bulduğu saniye cinsinden takip edilir.
* **Skor ve Log Kayıt Sistemi:** * Oynanan her oyunun tarihi, süresi ve sonucu `oyunlar.txt` dosyasına kaydedilir.
  * Sistemdeki önemli olaylar ve hatalı şifre denemeleri `log.txt` dosyasına zaman damgasıyla yazılır.
* **Güvenlikli Veri Temizleme:** Skorları veya logları temizlemek isteyen kullanıcının `sifre.txt` içerisindeki şifreyi doğru girmesi gerekir.

## 🛠️ Kullanılan Teknolojiler
* Java (JDK 8+)
* Swing (GUI)
* File I/O (BufferedReader, FileWriter)

## ⚙️ Kurulum ve Çalıştırma
Projeyi kendi bilgisayarınızda çalıştırmak için:
1. Bilgisayarınızın `C:\` dizininde `P2Oyun` adında bir klasör oluşturun.
2. İçerisine `TXTDosyalar` ve `Resimler` adında iki klasör açın.
3. Gerekli metin dosyalarını (`kelimeler.txt`, `log.txt`, `oyunlar.txt`, `sifre.txt`) ve oyun görsellerini (`1.jpg` - `11.jpg`) bu klasörlere yerleştirin.
4. Projeyi NetBeans üzerinden derleyip çalıştırın.
