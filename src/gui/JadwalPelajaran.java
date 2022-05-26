/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import method.koneksi;
/**
 *
 * @author bagus
 */
public class JadwalPelajaran extends javax.swing.JFrame {
    
    private final Connection conn = new koneksi().getKoneksi();
    private DefaultTableModel tabmode;
    
    public JadwalPelajaran() {
        initComponents();
        dataTable();
        isiComboNipGuru();
        isiComboKelas();
        isiComboTA();
        isiComboMapel();
        SaveAktif();
    }
    
     public void SaveAktif() {
        btnHapus.setEnabled(false);
        btnEdit.setEnabled(false);
        btnSimpan.setEnabled(true);
        
    }
    
        public void SaveUnAktif(){
        btnHapus.setEnabled(true);
        btnEdit.setEnabled(true);
        btnSimpan.setEnabled(false);
        
    }
  
 
     protected void dataTable(){
        Object [] baris = {"nip guru","nama guru","mapel","hari", "waktu mulai","waktu selesai","kelas","semester","tahun ajaran"};
        tabmode = new DefaultTableModel(null, baris);
        tbjadwalPelajaran.setModel(tabmode);
        String sql="select*from  jadwalpelajaran";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
             String id = hasil.getString("nip_guru");   
             String kl = hasil.getString("nama_guru");   
            String mp = hasil.getString("mapel");
            String hr = hasil.getString("hari");
            String ns = hasil.getString("waktu_mulai");
            String nm = hasil.getString("waktu_selesai");
            String sm = hasil.getString("kelas");
            String ss = hasil.getString("semester");
            String ta = hasil.getString("t_ajaran");
           
            String[]data = {id,kl,mp,hr,ns,nm,sm,ss,ta};
            tabmode.addRow(data);
            }
        }catch (Exception e) {
            System.err.println(e);
        }
    }
     
     public void TabelKlik(){
        int bar = tbjadwalPelajaran.getSelectedRow();
        String vNip = String.valueOf(tabmode.getValueAt(bar, 0).toString());
        cbNip.setSelectedItem(vNip);
        txGuru.setText(tabmode.getValueAt(bar, 1).toString());
        String vMapel = String.valueOf(tabmode.getValueAt(bar, 2).toString());
        cbMapel.setSelectedItem(vMapel);
        String vhari = String.valueOf(tabmode.getValueAt(bar, 3).toString());
        cbHari.setSelectedItem(vhari);
        txmulai.setText(tabmode.getValueAt(bar, 4).toString());
        txselesai.setText(tabmode.getValueAt(bar, 5).toString());
        String vKelas = String.valueOf(tabmode.getValueAt(bar, 6).toString());
        cbKelas.setSelectedItem(vKelas);
        String vSemester = String.valueOf(tabmode.getValueAt(bar, 7).toString());
        cbSemester.setSelectedItem(vSemester);
        String vTa = String.valueOf(tabmode.getValueAt(bar, 8).toString());
        cbTa.setSelectedItem(vTa);
        
        String vnm,vkls,vsms,vta,vid ;
        try {
            Statement st;
             ResultSet rs;
             String sql;
             sql ="SELECT * FROM jadwalPelajaran WHERE nip_guru='"+vNip+"'";
             st=conn.createStatement();
             rs=st.executeQuery(sql);
             while(rs.next()){
                vid = rs.getString("nip_guru");
                vNip = rs.getString("nama_guru");
                vnm = rs.getString("mapel");
                vkls = rs.getString("kelas");
                vsms = rs.getString("semester");
                vta = rs.getString("t_ajaran");
                cbNip.setSelectedItem(vid);
                txGuru.setText(vnm);
                cbMapel.setSelectedItem(vkls);
                cbSemester.setSelectedItem(vsms);
                cbTa.setSelectedItem(vta);
             }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }

    }
     
      public void Kosong(){
        cbNip.setSelectedIndex(0);
        cbHari.setSelectedIndex(0);
        txmulai.setText("");
        txselesai.setText("");
        txGuru.setText("");
        cbMapel.setSelectedIndex(0);
        cbKelas.setSelectedIndex(0);
        cbSemester.setSelectedIndex(0);
        cbTa.setSelectedIndex(0);
    }
      
       public void isiComboNipGuru(){
        try {
            Statement st;
             ResultSet rs;
             String sql;
             sql ="SELECT * FROM tabel_guru";
             st=conn.createStatement();
             rs=st.executeQuery(sql);
             while(rs.next()){
                 cbNip.addItem(rs.getString("nip"));
             }
             rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "gagal "+e);
        }
    }
     
        public void isiComboKelas(){
        try {
            Statement st;
            ResultSet rs;
            String sql;
            sql ="SELECT * FROM mapel";
            st=conn.createStatement();
            rs=st.executeQuery(sql);
            while(rs.next()){
                cbKelas.addItem(rs.getString("kelas"));
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "gagal "+e);
        }
    }
        public void isiComboTA(){
        try {
            Statement st;
            ResultSet rs;
            String sql;
            sql ="SELECT * FROM status_siswa";
            st=conn.createStatement();
            rs=st.executeQuery(sql);
            while(rs.next()){
                cbTa.addItem(rs.getString("t_ajaran"));
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "gagal "+e);
        }
    }

        
        public void isiComboMapel(){
        try {
            Statement st;
            ResultSet rs;
            String sql;
            sql ="SELECT * FROM mapel";
            st=conn.createStatement();
            rs=st.executeQuery(sql);
            while(rs.next()){
               cbMapel.addItem(rs.getString("nama_mapel"));
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "gagal "+e);
        }
    }
    
       
       
        public void Simpan(){
        String sql = "insert into jadwalpelajaran  values (?,?,?,?,?,?,?,?,?)";
        try {
            if (txmulai.getText().equals("")) { 
                JOptionPane.showMessageDialog(rootPane, "Maaf kolom tidak boleh kosong!");
                return;
            } else {
            PreparedStatement stat = conn.prepareStatement(sql);
           
            stat.setString(1,String.valueOf(cbNip.getSelectedItem()));
            stat.setString(2,txGuru.getText());
            stat.setString(3,String.valueOf(cbMapel.getSelectedItem()));
            stat.setString(4,String.valueOf(cbHari.getSelectedItem()));
            stat.setString(5,txmulai.getText());
            stat.setString(6,txselesai.getText());
            stat.setString(7,String.valueOf(cbKelas.getSelectedItem()));
            stat.setString(8,String.valueOf(cbSemester.getSelectedItem()));
            stat.setString(9,String.valueOf(cbTa.getSelectedItem()));
            stat.executeUpdate();
            JOptionPane.showMessageDialog(rootPane, "Data berhasil disimpan");
            Kosong();
            dataTable();
            } 
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "Data gagal disimpan "+e);
        }
    }
        
        public void Ubah(){
        try{
            String sql ="update jadwalpelajaran set nama_guru=?, mapel=?, hari=?, waktu_mulai=?, waktu_selesai=?, kelas=?, semester=?, t_ajaran=?  where nip_guru='"+cbNip.getSelectedItem()+"'";
            if (cbNip.getSelectedItem().equals("") || txGuru.getText().equals("")) { 
                JOptionPane.showMessageDialog(rootPane, "Maaf kolom tidak boleh kosong!");
                return;
            } else {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1,txGuru.getText());
            stat.setString(2,String.valueOf(cbMapel.getSelectedItem()));
            stat.setString(3,String.valueOf(cbHari.getSelectedItem()));
            stat.setString(4,txmulai.getText());
            stat.setString(5,txselesai.getText());
            stat.setString(6,String.valueOf(cbKelas.getSelectedItem()));
            stat.setString(7,String.valueOf(cbSemester.getSelectedItem()));
            stat.setString(8,String.valueOf(cbTa.getSelectedItem()));
            stat.executeUpdate();
            JOptionPane.showMessageDialog(rootPane, "Data berhasil diubah");
            Kosong();
            dataTable();
            } 
        }catch (SQLException e){
            JOptionPane.showMessageDialog(rootPane, "Data gagal di ubah "+e);
        }
    } 
        
        public void Hapus(){
        if (cbNip.getSelectedItem().equals("")) { 
                JOptionPane.showMessageDialog(rootPane, "Maaf kolom tidak boleh kosong!");
                return;
        } else {
            int ok = JOptionPane.showConfirmDialog(rootPane, "Anda yakin ingin menghapus data?","Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql = "delete from jadwalpelajaran where nip_guru='"+cbNip.getSelectedItem()+"'";
            try{
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(rootPane, "Data berhasil di hapus");
                Kosong();
                dataTable();
            }catch (SQLException e){
                JOptionPane.showMessageDialog(rootPane, "Data gagal di hapus "+e);
            }
        } else {
            Kosong();
        }
        }
    } 
        
        public void Cari(){
        int row=tbjadwalPelajaran.getRowCount();
        for (int x=0;x<row;x++){
            tabmode.removeRow(0);
        }
        try{
            ResultSet rs=conn.createStatement().executeQuery("Select * from "
                    + "jadwalpelajaran where nama_guru like '%"+txcari.getText()+ 
                    "%' or mapel like '%"+txcari.getText()+"%' "
                    + "or kelas like '%"+txcari.getText()+"%' "
                    + "or semester like '%"+txcari.getText()+"%'");
            while(rs.next()){
                String data[]={rs.getString(1),rs.getString(2),rs.getString(3)
                        ,rs.getString(4),rs.getString(5),rs.getString(6)};
                tabmode.addRow(data);
            }
        }catch (SQLException ex){
            Logger.getLogger(JadwalPelajaran.class.getName()).log(Level.SEVERE, null, ex);  
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbjadwalPelajaran = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btKel = new javax.swing.JButton();
        cbTa = new javax.swing.JComboBox<>();
        cbHari = new javax.swing.JComboBox<>();
        cbMapel = new javax.swing.JComboBox<>();
        cbKelas = new javax.swing.JComboBox<>();
        txmulai = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txselesai = new javax.swing.JTextField();
        btnEdit = new javax.swing.JButton();
        btSisBatal = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        cbSemester = new javax.swing.JComboBox<>();
        txcari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        mapel = new javax.swing.JButton();
        cbNip = new javax.swing.JComboBox<>();
        txGuru = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbjadwalPelajaran.setModel(new javax.swing.table.DefaultTableModel(
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
        tbjadwalPelajaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbjadwalPelajaranMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbjadwalPelajaran);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 730, 132));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setText("HARI");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 44, 25));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setText("SAMPAI");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 220, 57, 33));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel6.setText("NAMA GURU");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel9.setText("MATA PELAJARAN");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel10.setText("KELAS");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 260, 43, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("Jadwal Mata Pelajaran ");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, -1, 42));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel11.setText("TAHUN AJARAN");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, -1, -1));

        btKel.setBackground(new java.awt.Color(255, 0, 0));
        btKel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btKel.setText("X");
        btKel.setIconTextGap(1);
        btKel.setMargin(new java.awt.Insets(1, 1, 1, 1));
        btKel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btKelActionPerformed(evt);
            }
        });
        jPanel1.add(btKel, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, 50, 30));

        cbTa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- TAHUN AJARAN -" }));
        jPanel1.add(cbTa, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 320, 151, -1));

        cbHari.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- HARI -", "SENIN", "SELASA", "RABU", "KAMIS", "JUMAT" }));
        jPanel1.add(cbHari, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, 148, -1));

        cbMapel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- MATA PELAJARAN -" }));
        jPanel1.add(cbMapel, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, -1, -1));

        cbKelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- KELAS -" }));
        jPanel1.add(cbKelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 260, 148, -1));
        jPanel1.add(txmulai, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, 148, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel7.setText("WAKTU");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, 76, 33));
        jPanel1.add(txselesai, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 220, 136, 30));

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jPanel1.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 370, 85, -1));

        btSisBatal.setText("Batal");
        btSisBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSisBatalActionPerformed(evt);
            }
        });
        jPanel1.add(btSisBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 370, 70, -1));

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });
        jPanel1.add(btnHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 370, 85, -1));

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        jPanel1.add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 369, 85, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel12.setText("SEMESTER");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 290, -1, -1));

        cbSemester.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- SEMESTER -", "SATU", "DUA" }));
        jPanel1.add(cbSemester, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 290, 148, -1));

        txcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txcariKeyPressed(evt);
            }
        });
        jPanel1.add(txcari, new org.netbeans.lib.awtextra.AbsoluteConstraints(249, 419, 271, -1));

        btnCari.setText("cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });
        jPanel1.add(btnCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(538, 418, 85, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel8.setText("NIP GURU");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, -1, -1));

        jLabel13.setText("Input Mata Pelajaran");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 70, 130, -1));

        mapel.setText("Mata Pelajaran");
        mapel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mapelActionPerformed(evt);
            }
        });
        jPanel1.add(mapel, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 90, -1, 40));

        cbNip.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Nip Guru -" }));
        cbNip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNipActionPerformed(evt);
            }
        });
        jPanel1.add(cbNip, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, 148, -1));
        jPanel1.add(txGuru, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, 153, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Pembayaran.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 600));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        Simpan();
        SaveAktif();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
         Hapus();
        SaveAktif();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        Cari();
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
         Ubah();
        SaveAktif();
    }//GEN-LAST:event_btnEditActionPerformed

    private void cbNipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNipActionPerformed
        // TODO add your handling code here:
          String ids = String.valueOf(cbNip.getSelectedItem());
        String vnm = "";
        try {
            Statement st;
             ResultSet rs;
             String sql;
             sql ="SELECT * FROM tabel_guru where nip='"+ids+"'";
             st=conn.createStatement();
             rs=st.executeQuery(sql);
             while(rs.next()){
                 ids = rs.getString("nip");
                 vnm = rs.getString("nama_guru");
                 cbNip.setSelectedItem(ids);
                 txGuru.setText(vnm);
             }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_cbNipActionPerformed

    private void btKelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btKelActionPerformed
        // keluar
        new Beranda().setVisible(true);
        dispose();
    }//GEN-LAST:event_btKelActionPerformed

    private void tbjadwalPelajaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbjadwalPelajaranMouseClicked
        // TODO add your handling code here:
        TabelKlik();
        SaveUnAktif();
    }//GEN-LAST:event_tbjadwalPelajaranMouseClicked

    private void btSisBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSisBatalActionPerformed
        // Batal
        Kosong();
        SaveAktif();
    }//GEN-LAST:event_btSisBatalActionPerformed

    private void txcariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txcariKeyPressed
        // TODO add your handling code here:
         if (evt.getKeyCode()== KeyEvent.VK_ENTER) {
            Cari();
        }
    }//GEN-LAST:event_txcariKeyPressed

    private void mapelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mapelActionPerformed
        // TODO add your handling code here:
        new Mapel_Siswa().setVisible(true);
        dispose();
    }//GEN-LAST:event_mapelActionPerformed

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
            java.util.logging.Logger.getLogger(JadwalPelajaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JadwalPelajaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JadwalPelajaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JadwalPelajaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JadwalPelajaran().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btKel;
    private javax.swing.JButton btSisBatal;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> cbHari;
    private javax.swing.JComboBox<String> cbKelas;
    private javax.swing.JComboBox<String> cbMapel;
    private javax.swing.JComboBox<String> cbNip;
    private javax.swing.JComboBox<String> cbSemester;
    private javax.swing.JComboBox<String> cbTa;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton mapel;
    private javax.swing.JTable tbjadwalPelajaran;
    private javax.swing.JTextField txGuru;
    private javax.swing.JTextField txcari;
    private javax.swing.JTextField txmulai;
    private javax.swing.JTextField txselesai;
    // End of variables declaration//GEN-END:variables
}
