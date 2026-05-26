# 🎮 Java Swing - Adam Asmaca (Hangman) Oyunu

Bu proje, Java Swing kullanılarak geliştirilmiş, grafik arayüzlü ve dosya işlemleri (File I/O) destekli bir kelime tahmin oyunudur.

## 🚀 Proje Özellikleri

* **Dinamik Kelime Seçimi:** Oyun, kelimeleri `kelimeler.txt` dosyasından okur ve uzunluğu 6 harften büyük olanları rastgele seçer.
* **Görsel Geri Bildirim:** Yanlış tahminlerde adam asmaca görselleri anlık olarak güncellenir.
* **Zamanlayıcı (Timer):** Oyuncunun kelimeyi ne kadar sürede bulduğu saniye cinsinden takip edilir.
* **Skor ve Log Kayıt Sistemi:** * Oynanan her oyunun tarihi, süresi ve sonucu `oyunlar.txt` dosyasına kaydedilir.
  * Sistemdeki önemli olaylar ve hatalı şifre denemeleri `log.txt` dosyasına zaman damgasıyla yazılır.
* **Güvenlikli Veri Temizleme:** Skorları veya logları temizlemek isteyen kullanıcının `sifre.txt` içerisindeki şifreyi doğru girmesi gerekir.
## 📸 Ekran Görüntüleri
* Giriş Menüsü
* 
<img width="399" height="310" alt="image" src="https://github.com/user-attachments/assets/537a68ae-5708-433a-a39e-e00c38a66052" />

* Oyun Ana Menüsü
* 
<img width="1092" height="627" alt="image" src="https://github.com/user-attachments/assets/3209065e-ca00-478f-b6ec-f19f5490824b" />

*Oyun İçi Bildirimler

<img width="405" height="140" alt="image" src="https://github.com/user-attachments/assets/c36d1582-ee12-434e-8a00-8e8531151daf" />
<img width="257" height="141" alt="image" src="https://github.com/user-attachments/assets/63a1f319-787a-4f11-8bdb-4536292dc0fb" />
<img width="256" height="140" alt="image" src="https://github.com/user-attachments/assets/c2545053-cc34-459f-8b0b-5fbfc8d3128c" />
<img width="258" height="143" alt="image" src="https://github.com/user-attachments/assets/3c98b630-5d5f-407f-955f-900a79c37fa6" />
<img width="332" height="142" alt="image" src="https://github.com/user-attachments/assets/759c752e-abba-4dda-8d62-0f81484b7c2c" />




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
