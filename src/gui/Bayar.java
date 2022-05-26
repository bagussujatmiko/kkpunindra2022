/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author miko
 */
import method.koneksi;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import net.proteanit.sql.DbUtils;

public class Bayar extends javax.swing.JFrame {

    private Connection conn = koneksi.getKoneksi();
    private DefaultTableModel tabmode;
    private boolean kondisiCari = false;   
    public String tgl1;
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * Creates new form Bayar
     */
    
    protected void kosong() {
        //cbcari.setSelectedItem(0);
        no_pembayaran.setText("");//1
       // no_pembayaran.setEnabled(false);
        tanggal_bayar.setDate(null);//2
        txtnis.setText("");//3
        nama.setText("");//4
        kelas.setText("");//5
        //cbjenis_pem.setSelectedItem(0);//6
        cbjenis_pem.setSelectedIndex(0);
        txtbiaya.setText("");//7
        txtbayar.setText("");//8
        txtkurang.setText("");
        ket.setText("");//9
        //cbcetak.setSelectedItem(0);
        tabelpembayaran.tableChanged(null);
    }
    
      protected void datatable(String dtcari) {
        Object[] Baris = {"No Pembayaran", "Tanggal Pembayaran", "NIK","Nama","Kelas", "Jenis Pembayaran", "Biaya","kurang", "Total", "Keterangan"};
        tabmode = new DefaultTableModel(null, Baris);
        tabelpembayaran.setModel(tabmode);
        String kondisi = "";
        if(!dtcari.isEmpty()){
            kondisiCari = true;
            kondisi = " where nama='"+dtcari+"'";
        }
        String sql = "select * from andministrasi" +kondisi;
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while(hasil.next()){
                String no = hasil.getString("no_pem");
                String tgl = hasil.getString("tgl_pem");
                String Nik = hasil.getString("nik");
                 String Nii = hasil.getString("nama");
                 String kel = hasil.getString("kelas");
                  String Niss = hasil.getString("jns_pem");
                   String Nisss = hasil.getString("biaya");
                    String Nissss = hasil.getString("total");
                    String z = hasil.getString("kurang");
                    String Niis = hasil.getString("ket");
                String[] data = {no,tgl,Nik,Nii,kel,Niss,Nisss,Nissss,z,Niis};
                tabmode.addRow(data);
            }
            }catch (Exception e) {
            }
            }
      
      protected void carinama() {
     //protected void carinama() {
        Object[] Baris = {"NIk", "NAMA", "KELAS", "STATUS SISWA","SEMESTER", "TAHUN AJARAN"};
        tabmode = new DefaultTableModel(null, Baris);
        datasiswa.setModel(tabmode); 
        String sql = "select * from status_siswa";
       
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()) {
                String no = hasil.getString("nik");
                String tgl = hasil.getString("nama_siswa");
                String c = hasil.getString("kelas");
                String angkatan = hasil.getString("status_siswa");
                String Nik = hasil.getString("semester");
                String Nii = hasil.getString("t_ajaran");
                String[] data = {no, tgl, c, angkatan, Nik, Nii};
                tabmode.addRow(data);
            }
        } catch (Exception e) {
        }
    }
       
        private void cetak(){
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app_kkp","root","");
            String sql = "select * from administrasi";
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next())
            {
                String a = hasil.getString("nama");
                cbcetak.addItem(a);
                //String b = hasil.getString("harga");
                //biaya.setText(b);
            }
        }catch (Exception ex)
        {
        JOptionPane.showMessageDialog(null, ex);    
        }
    }
         public void auto() {
           try {
               String sql = "select max(right(no_pem,3)) from administrasi";
               java.sql.Statement stat = conn.createStatement();
               ResultSet hasil = stat.executeQuery(sql);
               while(hasil.next()) {
                   if(hasil.first()==false) {
                       no_pembayaran.setText("001");
                   } else {
                       hasil.last();
                       int code = hasil.getInt(1)+1;
                       String Nomor = String.valueOf(code);
                       int noLong = Nomor.length();
                       
                       for (int a=0; a<3-noLong; a++){
                           Nomor = "0" + Nomor;
                       }
                       no_pembayaran.setText("P-"+ Nomor);
                       
                   }
               }
           }catch (Exception e){
               
           }}
      
    public Bayar() {
        initComponents();
         carinama();
        datatable("");
        auto();
        //tampilnama();
        d_pembayaran.setLocationRelativeTo(this);
        AutoCompleteDecorator.decorate(cbcetak);
        AutoCompleteDecorator.decorate(cbjenis_pem);
        //AutoCompleteDecorator.decorate(cbnama);
        cetak();
        //comboboxcari();
      // comboboxcarinama();
       // comboboxcarinis();
        no_pembayaran.setEnabled(false);
        

    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        d_pembayaran = new javax.swing.JDialog();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtcari1 = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        datasiswa = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        no_pembayaran = new javax.swing.JTextField();
        nama = new javax.swing.JTextField();
        txtbiaya = new javax.swing.JTextField();
        txtbayar = new javax.swing.JTextField();
        ket = new javax.swing.JTextField();
        tanggal_bayar = new com.toedter.calendar.JDateChooser();
        cbjenis_pem = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        kelas = new javax.swing.JTextField();
        txtkurang = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtnis = new javax.swing.JTextField();
        btncarii = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelpembayaran = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        bsimpan = new javax.swing.JButton();
        bubah = new javax.swing.JButton();
        bhapus = new javax.swing.JButton();
        btnfres = new javax.swing.JButton();
        cbcetak = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        btncetak = new javax.swing.JButton();
        bkeluar = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        btKel = new javax.swing.JButton();

        d_pembayaran.setMinimumSize(new java.awt.Dimension(901, 546));

        jPanel9.setBackground(new java.awt.Color(0, 232, 176));

        jPanel10.setBackground(new java.awt.Color(0, 232, 176));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel20.setText("Cari Berdasarkan Nama :");

        txtcari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcari1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtcari1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtcari1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel11.setBackground(new java.awt.Color(0, 232, 176));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        datasiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        datasiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                datasiswaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(datasiswa);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 851, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout d_pembayaranLayout = new javax.swing.GroupLayout(d_pembayaran.getContentPane());
        d_pembayaran.getContentPane().setLayout(d_pembayaranLayout);
        d_pembayaranLayout.setHorizontalGroup(
            d_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        d_pembayaranLayout.setVerticalGroup(
            d_pembayaranLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 232, 176));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setText("No. Pembayaran");

        jLabel2.setText("Tanggal Pembayaran");

        jLabel3.setText("NIk Siswa");

        jLabel4.setText("NAMA");

        jLabel5.setText("JENIS PEMBAYARAN");

        jLabel6.setText("BIAYA");

        jLabel7.setText("BAYAR");

        jLabel8.setText("KETERANGAN");

        no_pembayaran.setMaximumSize(new java.awt.Dimension(4, 19));

        nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                namaActionPerformed(evt);
            }
        });

        txtbayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtbayarKeyReleased(evt);
            }
        });

        tanggal_bayar.setDateFormatString("MM/dd/yyyy");
        tanggal_bayar.setMaximumSize(new java.awt.Dimension(90, 19));
        tanggal_bayar.setMinimumSize(new java.awt.Dimension(90, 19));
        tanggal_bayar.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tanggal_bayarPropertyChange(evt);
            }
        });

        cbjenis_pem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Silahkan Pilih-", "BAYAR SPP", "BAYAR BUKU", "BAYAR BAJU", "BAYAR LAIN LAIN", "BAYAR UJIAN" }));
        cbjenis_pem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbjenis_pemMouseClicked(evt);
            }
        });
        cbjenis_pem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbjenis_pemActionPerformed(evt);
            }
        });

        jLabel9.setText("KELAS");

        kelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kelasActionPerformed(evt);
            }
        });

        txtkurang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtkurangKeyReleased(evt);
            }
        });

        jLabel21.setText("KURANG");

        txtnis.setMaximumSize(new java.awt.Dimension(4, 19));
        txtnis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtnisKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtnisKeyReleased(evt);
            }
        });

        btncarii.setText("Pilih");
        btncarii.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncariiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtnis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btncarii, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(no_pembayaran, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nama)
                            .addComponent(tanggal_bayar, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(kelas, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel21)
                            .addComponent(jLabel8)
                            .addComponent(jLabel5))
                        .addGap(51, 51, 51)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ket)
                            .addComponent(txtbiaya)
                            .addComponent(txtbayar)
                            .addComponent(txtkurang)
                            .addComponent(cbjenis_pem, 0, 240, Short.MAX_VALUE))))
                .addGap(21, 21, 21))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(no_pembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(tanggal_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtnis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btncarii)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(kelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbjenis_pem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtbiaya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtbayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtkurang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(ket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(0, 232, 176));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tabelpembayaran.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelpembayaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelpembayaranMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelpembayaran);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 833, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        bsimpan.setText("Simpan");
        bsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpanActionPerformed(evt);
            }
        });

        bubah.setText("Ubah");
        bubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bubahActionPerformed(evt);
            }
        });

        bhapus.setText("Hapus");
        bhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhapusActionPerformed(evt);
            }
        });

        btnfres.setText("Refresh");
        btnfres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfresActionPerformed(evt);
            }
        });

        cbcetak.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cari Berdasarkan Nama" }));
        cbcetak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cbcetakKeyReleased(evt);
            }
        });

        jButton1.setText("Cari");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btncetak.setText("Cetak");
        btncetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncetakActionPerformed(evt);
            }
        });

        bkeluar.setText("Keluar");
        bkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bkeluarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bsimpan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bubah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bhapus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnfres)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 534, Short.MAX_VALUE)
                .addComponent(cbcetak, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btncetak)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bkeluar)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bsimpan)
                    .addComponent(bubah)
                    .addComponent(bhapus)
                    .addComponent(bkeluar)
                    .addComponent(btnfres)
                    .addComponent(btncetak)
                    .addComponent(cbcetak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 40)); // NOI18N
        jLabel19.setText("PEMBAYARAN ADMINISTRASI");

        btKel.setBackground(new java.awt.Color(255, 0, 0));
        btKel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btKel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button home.png"))); // NOI18N
        btKel.setIconTextGap(1);
        btKel.setMargin(new java.awt.Insets(1, 1, 1, 1));
        btKel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btKelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(390, 390, 390)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btKel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btKel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 546, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(100, 100, 100)))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1334, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtcari1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcari1KeyReleased
        // TODO add your handling code here:
        String tf = txtcari1.getText();
        try{
            String sql = "SELECT * FROM status_siswa WHERE nama_siswa LIKE '%"+tf+"%'";
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            datasiswa.setModel(DbUtils.resultSetToTableModel(hasil));
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }/*finally {
            try {

            }
            catch (Exception e){

            }
        }*/
    }//GEN-LAST:event_txtcari1KeyReleased

    private void datasiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datasiswaMouseClicked
        // TODO add your handling code here:
        try {
            int row = datasiswa.getSelectedRow();
            String tabel_klik = (datasiswa.getModel().getValueAt(row, 0).toString());
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet sql = stm.executeQuery("select * from status_siswa where nik='"+tabel_klik+"'");
            if(sql.next()) {
                String a = sql.getString("nik");//1 a
                txtnis.setText(a);
                String b = sql.getString("nama_siswa");//2 b
                nama.setText(b);
                String c = sql.getString("kelas");//3
                kelas.setText(c);
                
               String d = sql.getString("status_siswa");
                
                String e = sql.getString("t_ajaran");//4

                String f = sql.getString("semester");//5

                /*Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse((String)tabmode.getValueAt(row,5));
                } catch (ParseException ex) {
                    Logger.getLogger(DataSiswa.class.getName()).log(Level.SEVERE, null, ex);
                }*/

                
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Tidak ada data yang dipilih");
        }
        d_pembayaran.setVisible(false);
    }//GEN-LAST:event_datasiswaMouseClicked

    private void namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_namaActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
    }//GEN-LAST:event_namaActionPerformed

    private void txtbayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbayarKeyReleased
        // TODO add your handling code here:
        int biaya = Integer.parseInt(txtbiaya.getText());
        int bayar = Integer.parseInt(txtbayar.getText());
        int kurang = biaya-bayar;
        txtkurang.setText(""+kurang);
        if (kurang == 0){
            ket.setText("Lunas");
        } else if (kurang <= bayar ){
            ket.setText(" lunas ada kembalian ");
        }else{
             ket.setText(" Belum Lunas ");
        }
    }//GEN-LAST:event_txtbayarKeyReleased

    private void tanggal_bayarPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tanggal_bayarPropertyChange
        // TODO add your handling code here:
        if (tanggal_bayar.getDate() != null) {
            tgl1 = format1.format(tanggal_bayar.getDate());
        }
    }//GEN-LAST:event_tanggal_bayarPropertyChange

    private void cbjenis_pemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbjenis_pemMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_cbjenis_pemMouseClicked

    private void cbjenis_pemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbjenis_pemActionPerformed
        // TODO add your handling code here:
         int harga=0;
        if (cbjenis_pem.getSelectedItem() == "BAYAR SPP"){
            harga=400000;
        } else if (cbjenis_pem.getSelectedItem() == "BAYAR BUKU"){
            harga=350000;
        } else if (cbjenis_pem.getSelectedItem() == "BAYAR BAJU"){
            harga=200000;
        } else if (cbjenis_pem.getSelectedItem() == "BAYAR LAIN LAIN"){
            harga=100000;
        } else if (cbjenis_pem.getSelectedItem() == "BAYAR UJIAN"){
            harga=50000;
        }  else {
            harga=0;
        }
        txtbiaya.setText(String.valueOf(harga));

    }//GEN-LAST:event_cbjenis_pemActionPerformed

    private void kelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kelasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kelasActionPerformed

    private void txtkurangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtkurangKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtkurangKeyReleased

    private void txtnisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnisKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtnisKeyPressed

    private void txtnisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnisKeyReleased

    }//GEN-LAST:event_txtnisKeyReleased

    private void btncariiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncariiActionPerformed
        // TODO add your handling code here:
        d_pembayaran.setVisible(true);
    }//GEN-LAST:event_btncariiActionPerformed

    private void tabelpembayaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelpembayaranMouseClicked
        // TODO add your handling code here:
        try {
            int row = tabelpembayaran.getSelectedRow();
            String tabel_klik = (tabelpembayaran.getModel().getValueAt(row, 0).toString());
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet sql = stm.executeQuery("select * from administrasi where no_pem='"+tabel_klik+"'");
            if(sql.next()) {
                String a = sql.getString("no_pem");
                no_pembayaran.setText(a);
                String b = sql.getString("tgl_pem");
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse((String)tabmode.getValueAt(row,1));
                } catch (ParseException ex) {
                    Logger.getLogger(Bayar.class.getName()).log(Level.SEVERE, null, ex);
                }
                tanggal_bayar.setDate(date);

                String c = sql.getString("nik");
                txtnis.setText(c);
                String d = sql.getString("nama");
                nama.setText(d);
                String e = sql.getString("kelas");
                kelas.setText(e);
                String f = sql.getString("jns_pem");
                cbjenis_pem.setSelectedItem(f);
                String g = sql.getString("biaya");
                txtbiaya.setText(g);
                String h = sql.getString("total");
                txtbayar.setText(h);
                String i = sql.getString("kurang");
                txtkurang.setText(i);
                String j = sql.getString("ket");
                ket.setText(j);
            }
        }catch (Exception e) {

        }


    }//GEN-LAST:event_tabelpembayaranMouseClicked

    private void bsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanActionPerformed
        // TODO add your handling code here:
        String sql = "insert into administrasi values (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, no_pembayaran.getText());
            stat.setString(2, tgl1);
            stat.setString(3, txtnis.getText());
            stat.setString(4, nama.getText());
            stat.setString(5, kelas.getText());
            stat.setString(6, cbjenis_pem.getSelectedItem().toString());
            stat.setString(7, txtbiaya.getText());
            stat.setString(8, txtbayar.getText());
            stat.setString(9, txtkurang.getText());
            stat.setString(10, ket.getText());

            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            kosong();
            no_pembayaran.requestFocus();
            datatable("");
            auto();
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal disimpan"+e);
        }
    }//GEN-LAST:event_bsimpanActionPerformed

    private void bubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bubahActionPerformed
        // TODO add your handling code here:
        String sql = "update administrasi set tgl_pem=?, nik=?, nama=?, kelas=?, jns_pem=?, biaya=?, total=?,kurang=?, ket=? where  no_pem='"+no_pembayaran.getText()+"'";
        try {
            PreparedStatement stat = conn.prepareStatement(sql);

            stat.setString(1, tgl1);
            stat.setString(2, txtnis.getText());
            stat.setString(3, nama.getText());
            stat.setString(4, kelas.getText());
            stat.setString(5, cbjenis_pem.getSelectedItem().toString());
            stat.setString(6, txtbiaya.getText());
            stat.setString(7, txtbayar.getText());
            stat.setString(8, txtkurang.getText());
            stat.setString(9, ket.getText());

            stat.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
            kosong();
            no_pembayaran.requestFocus();
            auto();
            datatable("");
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal diubah"+e);
        }
    }//GEN-LAST:event_bubahActionPerformed

    private void bhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapusActionPerformed
        // TODO add your handling code here:
        int ok = JOptionPane.showConfirmDialog(null, "hapus","Konfirmasi Dialog", JOptionPane.YES_NO_CANCEL_OPTION);
        if (ok==0) {
            String sql = "delete from administrasi where no_pem='"+no_pembayaran.getText()+"'";
            try {
                PreparedStatement stat = conn.prepareStatement (sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
                kosong();
                no_pembayaran.requestFocus();
                datatable("");
                auto();
            } catch(SQLException e) {
                JOptionPane.showMessageDialog(null, "Data gagal dihapus");
            }
        }
    }//GEN-LAST:event_bhapusActionPerformed

    private void btnfresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfresActionPerformed
        // TODO add your handling code here:
        kosong();
        datatable("");
        cetak();
        auto();
    }//GEN-LAST:event_btnfresActionPerformed

    private void cbcetakKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cbcetakKeyReleased
        // TODO add your handling code here:
        String tf = (String) cbcetak.getSelectedItem();
        try{
            String sql = "SELECT * FROM administrasi WHERE nama LIKE '%"+tf+"%'";
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            datasiswa.setModel(DbUtils.resultSetToTableModel(hasil));
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_cbcetakKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        datatable(cbcetak.getSelectedItem().toString());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btncetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncetakActionPerformed
        // TODO add your handling code here:
        try {
            HashMap data = new HashMap();
            String logo = ("lib/");
            data.put("logo", logo);
            data.put("nama",cbcetak.getSelectedItem());
            String buatLaporan=("./src/laporan/BuktiPembayaran.jasper");
            JasperPrint cetak_laporan = JasperFillManager.fillReport(buatLaporan, data, conn);
            JasperViewer LaporanData = new JasperViewer(cetak_laporan, false);
            LaporanData.setVisible(true);
        }catch (Exception e){
            javax.swing.JOptionPane.showMessageDialog(rootPane, "Gagal Menampilkan Bukti Pembayaran");
        }
    }//GEN-LAST:event_btncetakActionPerformed

    private void bkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bkeluarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_bkeluarActionPerformed

    private void btKelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btKelActionPerformed
        // keluar
        new Beranda().setVisible(true);
        dispose();
    }//GEN-LAST:event_btKelActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Bayar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Bayar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Bayar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Bayar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Bayar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bhapus;
    private javax.swing.JButton bkeluar;
    private javax.swing.JButton bsimpan;
    private javax.swing.JButton btKel;
    private javax.swing.JButton btncarii;
    private javax.swing.JButton btncetak;
    private javax.swing.JButton btnfres;
    private javax.swing.JButton bubah;
    private javax.swing.JComboBox<String> cbcetak;
    public javax.swing.JComboBox<String> cbjenis_pem;
    private javax.swing.JDialog d_pembayaran;
    private javax.swing.JTable datasiswa;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField kelas;
    private javax.swing.JTextField ket;
    private javax.swing.JTextField nama;
    private javax.swing.JTextField no_pembayaran;
    public javax.swing.JTable tabelpembayaran;
    private com.toedter.calendar.JDateChooser tanggal_bayar;
    private javax.swing.JTextField txtbayar;
    private javax.swing.JTextField txtbiaya;
    private javax.swing.JTextField txtcari1;
    private javax.swing.JTextField txtkurang;
    private javax.swing.JTextField txtnis;
    // End of variables declaration//GEN-END:variables
}
