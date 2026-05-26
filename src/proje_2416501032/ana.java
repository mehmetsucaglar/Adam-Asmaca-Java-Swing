/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package proje_2416501032;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mehme
 */
public class ana extends javax.swing.JFrame {
    
    private String secilenKelime = "";
    private int yanlisTahmin = 0;
    private int saniye = 0;
    
    private Timer oyunSayaci;
    private List<JLabel> harfEtiketleri = new ArrayList<>();
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ana.class.getName());

 private void oyunuBaslat() {
        File dosya = new File("C:\\P2Oyun\\TXTDosyalar\\kelimeler.txt");
        List<String> kelimeler = new ArrayList<>();

        try {
            if (!dosya.exists()) {
                javax.swing.JOptionPane.showMessageDialog(this, "kelimeler.txt dosyası bulunamadı!");
                return;
            }
            BufferedReader reader = new BufferedReader(new FileReader(dosya));
            String satir;
            while ((satir = reader.readLine()) != null) {
                satir = satir.trim();
                if(satir.length() >= 6) { 
                    kelimeler.add(satir.toUpperCase());
                }
            }
            reader.close();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Kelime okunurken hata: " + e.getMessage());
            return;
        }

        if (kelimeler.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Dosyada uygun kelime yok!");
            return;
        }

       
        secilenKelime = kelimeler.get(new Random().nextInt(kelimeler.size()));
        yanlisTahmin = 0;
        saniye = 0;
        harfEtiketleri.clear();

       
        pnlYildizlar.removeAll(); 
        pnlYildizlar.setLayout(new java.awt.FlowLayout()); 

        for (int i = 0; i < secilenKelime.length(); i++) {
            JLabel harfLbl = new JLabel("*");
            harfLbl.setFont(new java.awt.Font("Tahoma", 1, 24));
            harfEtiketleri.add(harfLbl);
            pnlYildizlar.add(harfLbl);
        }

       
        lblGorsel.setIcon(new javax.swing.ImageIcon(new javax.swing.ImageIcon("C:\\P2Oyun\\Resimler\\1.jpg").getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)));

        
        if (oyunSayaci != null) oyunSayaci.stop();
        oyunSayaci = new Timer(1000, e -> {saniye++;lblSüre.setText("Süre: " + saniye + " sn");
        });
        oyunSayaci.start();
    }
    
    private void tahminKontrol(boolean dogruMu) {
        if (!dogruMu) {
            yanlisTahmin++;
            int resimNo = yanlisTahmin + 1; 
            if (resimNo > 11) resimNo = 11;
            
            
            lblGorsel.setIcon(new javax.swing.ImageIcon(new javax.swing.ImageIcon("C:\\P2Oyun\\Resimler\\" + resimNo + ".jpg").getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)));
        }

        boolean kazandiMi = true;
        for (javax.swing.JLabel lbl : harfEtiketleri) {
            if (lbl.getText().equals("*")) {
                kazandiMi = false;
                break;
            }
        }

        if (kazandiMi) {
            oyunSayaci.stop(); 
            javax.swing.JOptionPane.showMessageDialog(this, "Tebrikler Kazandınız! Süre: " + saniye + " saniye");
            oyunKayit("Kazandı");
        } else if (yanlisTahmin >= 10) { 
            oyunSayaci.stop(); 
            javax.swing.JOptionPane.showMessageDialog(this, "Oyun Bitti! Kaybettiniz. Kelime: " + secilenKelime);
            oyunKayit("Kaybetti");
        }
    }

    private void oyunKayit(String sonuc) {
        try {
            String tarih = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            
            FileWriter writer = new FileWriter("C:\\P2Oyun\\TXTDosyalar\\oyunlar.txt", true);
            writer.write(tarih + "," + saniye + " sn," + sonuc + "\n");
            writer.close();
            
            FileWriter writerLog = new FileWriter("C:\\P2Oyun\\TXTDosyalar\\log.txt", true);
            writerLog.write(tarih + " - Etiket: Oyun Bitti (" + sonuc + ")\n");
            writerLog.close();
            
            skorKayit();
            logKayit();
            
        } catch (Exception e) {
            System.out.println("Oyun skoru kaydedilemedi.");
        }
    }
    
    private void skorKayit() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Tarih/Saat", "Süre", "Sonuç"}, 0);
        try {
           File dosya = new File("C:\\P2Oyun\\TXTDosyalar\\oyunlar.txt");
            if (dosya.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(dosya));
                String satir;
                while ((satir = reader.readLine()) != null) {
                    model.addRow(satir.split(","));
                }
                reader.close();
            }
        } catch (Exception e) {
            System.out.println("Skorlar okunamadı.");
        }
        tblSkorlar.setModel(model);
    }

    
    private void logKayit() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"İşlem Geçmişi"}, 0);
        try {
            File dosya = new File("C:\\P2Oyun\\TXTDosyalar\\log.txt");
            if (dosya.exists()) {
                BufferedReader readerLog = new BufferedReader(new FileReader(dosya));
                String satir;
                while ((satir = readerLog.readLine()) != null) {
                    model.addRow(new Object[]{satir});
                }
                readerLog.close();
            }
        } catch (Exception e) {
            System.out.println("Loglar okunamadı.");
        }
        tblLoglar.setModel(model);
    }
    
    
    /**
     * Creates new form ana
     */
    public ana() {
        initComponents();
        
        jtextField1.setText("");
        jtextField2.setText("");
        
        skorKayit();
        logKayit();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLoglar = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSkorlar = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblSüre = new javax.swing.JLabel();
        lblGorsel = new javax.swing.JLabel();
        btnHarfTahmin = new javax.swing.JButton();
        btnKlmTahmin = new javax.swing.JButton();
        jtextField1 = new java.awt.TextField();
        jtextField2 = new java.awt.TextField();
        pnlYildizlar = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        oyunYBasla = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1080, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblLoglar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "İşlem  Geçmişi"
            }
        ));
        jScrollPane2.setViewportView(tblLoglar);

        jButton4.setText("Temizle");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(210, 210, 210)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(120, 120, 120)
                .addComponent(jButton4)
                .addContainerGap(226, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(217, 217, 217)
                        .addComponent(jButton4)))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Loglar", jPanel4);

        tblSkorlar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Tarih/Saat", "Süre", "Sonuç"
            }
        ));
        jScrollPane1.setViewportView(tblSkorlar);

        jButton3.setText("Temizle");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(243, 243, 243)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(153, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(203, 203, 203)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Eski Skorları Görüntüle", jPanel2);

        lblSüre.setText("Süre:0 sn");

        btnHarfTahmin.setText("Harf Tahmin");
        btnHarfTahmin.addActionListener(this::btnHarfTahminActionPerformed);

        btnKlmTahmin.setText("Kelime Tahmin");
        btnKlmTahmin.addActionListener(this::btnKlmTahminActionPerformed);

        jtextField1.setText("textField1");

        jtextField2.setText("textField2");

        javax.swing.GroupLayout pnlYildizlarLayout = new javax.swing.GroupLayout(pnlYildizlar);
        pnlYildizlar.setLayout(pnlYildizlarLayout);
        pnlYildizlarLayout.setHorizontalGroup(
            pnlYildizlarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 301, Short.MAX_VALUE)
        );
        pnlYildizlarLayout.setVerticalGroup(
            pnlYildizlarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 62, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnHarfTahmin, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnKlmTahmin))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(76, 76, 76)
                        .addComponent(lblSüre, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(pnlYildizlar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)))
                .addGap(64, 64, 64)
                .addComponent(lblGorsel, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(245, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblGorsel, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(pnlYildizlar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnHarfTahmin)
                                    .addComponent(jtextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnKlmTahmin)
                                    .addComponent(jtextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblSüre, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)))))
                .addContainerGap(203, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Oyun Oyna", jPanel1);

        oyunYBasla.setText("Oyun Seçenekleri");

        jMenuItem2.setText("Oyun Başla");
        jMenuItem2.addActionListener(this::jMenuItem2ActionPerformed);
        oyunYBasla.add(jMenuItem2);

        jMenuItem3.setText("Oyun Yeniden Başla");
        jMenuItem3.addActionListener(this::jMenuItem3ActionPerformed);
        oyunYBasla.add(jMenuItem3);

        jMenuBar1.add(oyunYBasla);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(85, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        oyunuBaslat();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void btnHarfTahminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHarfTahminActionPerformed
String tahmin = jtextField1.getText().toUpperCase().trim();
        jtextField1.setText(""); 
        
        if (tahmin.isEmpty() || secilenKelime.isEmpty()) return;
        
        char harf = tahmin.charAt(0);
        boolean dogruMu = false;
        
        for (int i = 0; i < secilenKelime.length(); i++) {
            if (secilenKelime.charAt(i) == harf) {
                harfEtiketleri.get(i).setText(String.valueOf(harf));
                dogruMu = true;
            }
        }
        tahminKontrol(dogruMu);
    }//GEN-LAST:event_btnHarfTahminActionPerformed

    private void btnKlmTahminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKlmTahminActionPerformed
        // TODO add your handling code here:
        String tahmin = jtextField2.getText().toUpperCase().trim();
        jtextField2.setText(""); 
        
        if (tahmin.isEmpty() || secilenKelime.isEmpty()) return;
        
        boolean dogruMu = tahmin.equals(secilenKelime);
        
        if (dogruMu) {
            for (int i = 0; i < secilenKelime.length(); i++) {
                harfEtiketleri.get(i).setText(String.valueOf(secilenKelime.charAt(i)));
            }
        }
        tahminKontrol(dogruMu);
    }//GEN-LAST:event_btnKlmTahminActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        try {
            String girilen = javax.swing.JOptionPane.showInputDialog(this, "Silmek için şifrenizi giriniz:");
            if (girilen == null) return; 
            
            
            BufferedReader reader = new BufferedReader(new FileReader("C:\\P2Oyun\\TXTDosyalar\\sifre.txt"));
            String gercekSifre = reader.readLine();
            reader.close();
            
            if (girilen.equals(gercekSifre)) {
                
                new FileWriter("C:\\P2Oyun\\TXTDosyalar\\oyunlar.txt", false).close();
                skorKayit(); 
                javax.swing.JOptionPane.showMessageDialog(this, "Skorlar temizlendi!");
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Hatalı şifre!");
                
                
                String tarih = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
                FileWriter writerLog = new FileWriter("C:\\P2Oyun\\TXTDosyalar\\log.txt", true);
                writerLog.write(tarih + " - Etiket: Skorlar Silinirken Hatalı Şifre Denemesi (Girilen: " + girilen + ")\n");
                writerLog.close();
                logKayit(); 
                
            }
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Hata oluştu.");
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        try {
            String girilen = javax.swing.JOptionPane.showInputDialog(this, "Silmek için şifrenizi giriniz:");
            if (girilen == null) return; 
            
            BufferedReader reader = new BufferedReader(new FileReader("C:\\P2Oyun\\TXTDosyalar\\sifre.txt"));
            String gercekSifre = reader.readLine();
            reader.close();
            
            if (girilen.equals(gercekSifre)) {
                new FileWriter("C:\\P2Oyun\\TXTDosyalar\\log.txt", false).close();
                logKayit(); 
                javax.swing.JOptionPane.showMessageDialog(this, "Loglar temizlendi!");
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Hatalı şifre!");
                
               
                String tarih = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
                FileWriter writerLog = new FileWriter("C:\\P2Oyun\\TXTDosyalar\\log.txt", true);
                writerLog.write(tarih + " - Etiket: Loglar Silinirken Hatalı Şifre Denemesi (Girilen: " + girilen + ")\n");
                writerLog.close();
                logKayit(); 
               
            }
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Hata oluştu.");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        oyunuBaslat();
                
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new ana().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHarfTahmin;
    private javax.swing.JButton btnKlmTahmin;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private java.awt.TextField jtextField1;
    private java.awt.TextField jtextField2;
    private javax.swing.JLabel lblGorsel;
    private javax.swing.JLabel lblSüre;
    private javax.swing.JMenu oyunYBasla;
    private javax.swing.JPanel pnlYildizlar;
    private javax.swing.JTable tblLoglar;
    private javax.swing.JTable tblSkorlar;
    // End of variables declaration//GEN-END:variables
}
