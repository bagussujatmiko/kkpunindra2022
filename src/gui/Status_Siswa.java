
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
 * @author dinda
 */
public class Status_Siswa extends javax.swing.JFrame {
    
    private final Connection conn = new koneksi().getKoneksi();
    private DefaultTableModel tabmode;


    public Status_Siswa() {
        initComponents();
        dataTable();
        isiComboNIS();
        isiComboKelas();
        isiComboTA();
        SaveAktif();
    }
    
    protected void dataTable(){
        Object [] baris = {"id status ","nik","Nama","Kelas", "status siswa","Semester","Tahun Ajaran"};
        tabmode = new DefaultTableModel(null, baris);
        tbMapel.setModel(tabmode);
        String sql="select*from  status_siswa";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
            String id = hasil.getString("id_status");
            String ns = hasil.getString("nik");
            String nm = hasil.getString("nama_siswa");
            String kl = hasil.getString("kelas");
            String ss = hasil.getString("status_siswa");
            String sm = hasil.getString("semester");
            String ta = hasil.getString("t_ajaran");
           
            String[]data = {id,ns,nm,kl,ss,sm,ta};
            tabmode.addRow(data);
            }
        }catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public void TabelKlik(){
        int bar = tbMapel.getSelectedRow();
        txid.setText(tabmode.getValueAt(bar, 0).toString());
        String vnis = String.valueOf(tabmode.getValueAt(bar, 1).toString());
        cbNIK.setSelectedItem(vnis);
        txNmSis.setText(tabmode.getValueAt(bar, 2).toString());
        String vkls = String.valueOf(tabmode.getValueAt(bar, 3).toString());
        cbKelas1.setSelectedItem(vkls);
        String vsis = String.valueOf(tabmode.getValueAt(bar, 4).toString());
        cbStatusSiswa.setSelectedItem(vsis);
        String vsms = String.valueOf(tabmode.getValueAt(bar, 5).toString());
        cbSemester.setSelectedItem(vsms);
        String vta = String.valueOf(tabmode.getValueAt(bar, 6).toString());
        cbTa.setSelectedItem(vta);

    }
    
    public void Kosong(){
        cbNIK.setSelectedIndex(0);
        txNmSis.setText("");
        cbKelas1.setSelectedIndex(0);
        cbStatusSiswa.setSelectedIndex(0);
        cbSemester.setSelectedIndex(0);
        cbTa.setSelectedIndex(0);
    }
    
    public void AutoNomor(){
         try{
             Statement st;
             ResultSet rs;
             String sql;
             
             sql ="SELECT * FROM status_siswa order by id_status desc";
             st=conn.createStatement();
             rs=st.executeQuery(sql);
             if (rs.next()) {
                 String nis = rs.getString("id_status").substring(2);
                 String AN = "" + (Integer.parseInt(nis)+1);
                 String Nol = "";
                 
                if (AN.length()==2) {
                     Nol ="00";
                 }if (AN.length()==3) {
                     Nol ="0";
                 } if (AN.length()==4) {
                     Nol="";
                 }
                 txid.setText("ST"+Nol+AN);
                 
             } else {
                 txid.setText("ST1001");
             }
         } catch (Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
     }
    
    public void isiComboNIS(){
        try {
            Statement st;
             ResultSet rs;
             String sql;
             sql ="SELECT * FROM siswa";
             st=conn.createStatement();
             rs=st.executeQuery(sql);
             while(rs.next()){
                 cbNIK.addItem(rs.getString("nik"));
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
                cbKelas1.addItem(rs.getString("kelas"));
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
            sql ="SELECT * FROM tahun_ajaran";
            st=conn.createStatement();
            rs=st.executeQuery(sql);
            while(rs.next()){
                cbTa.addItem(rs.getString("t_ajar"));
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "gagal "+e);
        }
    }
    
    public void SaveAktif() {
        btHapus.setEnabled(false);
        btUbah1.setEnabled(false);
        btSimpan.setEnabled(true);
        AutoNomor();
    }
    
    public void SaveUnAktif(){
        btHapus.setEnabled(true);
        btUbah1.setEnabled(true);
        btSimpan.setEnabled(false);
    }
    
    public void Simpan(){
        String sql = "insert into status_siswa  values (?,?,?,?,?,?,?)";
        try {
            if (txNmSis.getText().equals("")) { 
                JOptionPane.showMessageDialog(rootPane, "Maaf kolom tidak boleh kosong!");
                return;
            } else {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1,txid.getText());
            stat.setString(2,String.valueOf(cbNIK.getSelectedItem()));
            stat.setString(3,txNmSis.getText());
            stat.setString(4,String.valueOf(cbKelas1.getSelectedItem()));
            stat.setString(5,String.valueOf(cbStatusSiswa.getSelectedItem()));
            stat.setString(6,String.valueOf(cbSemester.getSelectedItem()));
            stat.setString(7,String.valueOf(cbTa.getSelectedItem()));
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
            String sql ="update status_siswa set nik=?, nama_siswa=?, kelas=?,status_siswa=?, semester=?, t_ajaran=?  where id_status='"+txid.getText()+"'";
            if (txNmSis.getText().equals("")) { 
                JOptionPane.showMessageDialog(rootPane, "Maaf kolom tidak boleh kosong!");
                return;
            } else {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1,String.valueOf(cbNIK.getSelectedItem()));
            stat.setString(2,txNmSis.getText());
            stat.setString(3,String.valueOf(cbKelas1.getSelectedItem()));
            stat.setString(4,String.valueOf(cbStatusSiswa.getSelectedItem()));
            stat.setString(5,String.valueOf(cbSemester.getSelectedItem()));
            stat.setString(6,String.valueOf(cbTa.getSelectedItem()));
            stat.executeUpdate();
            JOptionPane.showMessageDialog(rootPane, "Data berhasil diubah");
            Kosong();
            dataTable();
            stat.close();
            } 
        }catch (SQLException e){
            JOptionPane.showMessageDialog(rootPane, "Data gagal di ubah "+e);
        }
    }
    
    public void Hapus(){
        if (txNmSis.getText().equals("")) { 
                JOptionPane.showMessageDialog(rootPane, "Maaf kolom tidak boleh kosong!");
                return;
        } else {
            int ok = JOptionPane.showConfirmDialog(rootPane, "Anda yakin ingin menghapus data?","Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql = "delete from status_siswa where id_status='"+txid.getText()+"'";
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
        int row=tbMapel.getRowCount();
        for (int x=0;x<row;x++){
            tabmode.removeRow(0);
        }
        try{
            ResultSet rs=conn.createStatement().executeQuery("Select * from "
                    + "status_siswa where nama_siswa like '%"+txCari.getText()+ 
                    "%' or nik like '%"+txCari.getText()+"%' "
                    + "or kelas like '%"+txCari.getText()+"%' "
                    + "or semester like '%"+txCari.getText()+"%'");
            while(rs.next()){
                String data[]={rs.getString(1),rs.getString(2),rs.getString(3)
                        ,rs.getString(4),rs.getString(5),rs.getString(6)};
                tabmode.addRow(data);
            }
        }catch (SQLException ex){
            Logger.getLogger(Status_Siswa.class.getName()).log(Level.SEVERE, null, ex);  
        }
    }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txNmSis = new javax.swing.JTextField();
        cbTa = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        cbNIK = new javax.swing.JComboBox();
        cbSemester = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        txid = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cbKelas1 = new javax.swing.JComboBox();
        cbStatusSiswa = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbMapel = new javax.swing.JTable();
        txCari = new javax.swing.JTextField();
        btCari = new javax.swing.JButton();
        btBatal = new javax.swing.JButton();
        btSimpan = new javax.swing.JButton();
        btHapus = new javax.swing.JButton();
        btUbah1 = new javax.swing.JButton();
        btKel = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 153), 2));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Andalus", 1, 18)); // NOI18N
        jLabel1.setText("Kelola Status Siswa");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)), "input data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(51, 51, 51))); // NOI18N
        jPanel2.setAutoscrolls(true);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("id");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jLabel3.setText("Nama");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        jLabel5.setText("Tahun Ajaran");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, -1));
        jPanel2.add(txNmSis, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 180, -1));

        cbTa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-Tahun ajaran-" }));
        cbTa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTaActionPerformed(evt);
            }
        });
        jPanel2.add(cbTa, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, 180, -1));

        jLabel7.setText("Semester");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        cbNIK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-NIK Siswa-" }));
        cbNIK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNIKActionPerformed(evt);
            }
        });
        jPanel2.add(cbNIK, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 180, -1));

        cbSemester.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-Semester- ", "satu", "dua" }));
        cbSemester.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSemesterActionPerformed(evt);
            }
        });
        jPanel2.add(cbSemester, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, 180, -1));

        jLabel8.setText("nik siswa");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        txid.setEditable(false);
        jPanel2.add(txid, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 100, -1));

        jLabel6.setText("Kelas");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        jLabel9.setText("Status Siswa");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        cbKelas1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-Kelas-" }));
        cbKelas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbKelas1ActionPerformed(evt);
            }
        });
        jPanel2.add(cbKelas1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 180, -1));

        cbStatusSiswa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Status Siswa -", " Aktif  ", " Lulus ", "Tidak Aktif " }));
        cbStatusSiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatusSiswaActionPerformed(evt);
            }
        });
        jPanel2.add(cbStatusSiswa, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, 180, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 80, 340, 280));

        tbMapel.setModel(new javax.swing.table.DefaultTableModel(
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
        tbMapel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMapelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbMapel);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 360, 290));

        txCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txCariKeyPressed(evt);
            }
        });
        jPanel1.add(txCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 170, -1));

        btCari.setText("cari");
        btCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCariActionPerformed(evt);
            }
        });
        jPanel1.add(btCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 80, 60, -1));

        btBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tutup.png"))); // NOI18N
        btBatal.setText("Batal");
        btBatal.setToolTipText("Batal");
        btBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBatalActionPerformed(evt);
            }
        });
        jPanel1.add(btBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 380, -1, -1));

        btSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simpan.png"))); // NOI18N
        btSimpan.setText("Simpan");
        btSimpan.setToolTipText("Simpan");
        btSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSimpanActionPerformed(evt);
            }
        });
        jPanel1.add(btSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 380, -1, -1));

        btHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hapus.png"))); // NOI18N
        btHapus.setText("Hapus");
        btHapus.setToolTipText("Hapus");
        btHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHapusActionPerformed(evt);
            }
        });
        jPanel1.add(btHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 380, -1, -1));

        btUbah1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        btUbah1.setText("Ubah");
        btUbah1.setToolTipText("Ubah");
        btUbah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btUbah1ActionPerformed(evt);
            }
        });
        jPanel1.add(btUbah1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 380, -1, -1));

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
        jPanel1.add(btKel, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 10, 45, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Pelajaran.png"))); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 470));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 470));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btKelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btKelActionPerformed
        // keluar
        new Beranda().setVisible(true);
        dispose();
    }//GEN-LAST:event_btKelActionPerformed

    private void btSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSimpanActionPerformed
        // Simpan
        Simpan();
        SaveAktif();
    }//GEN-LAST:event_btSimpanActionPerformed

    private void btHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHapusActionPerformed
        // hapus
        Hapus();
        SaveAktif();
    }//GEN-LAST:event_btHapusActionPerformed

    private void btBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBatalActionPerformed
        // batal
        Kosong();
        SaveAktif();
    }//GEN-LAST:event_btBatalActionPerformed

    private void btUbah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btUbah1ActionPerformed
        // Ubah
        Ubah();
        SaveAktif();
    }//GEN-LAST:event_btUbah1ActionPerformed

    private void cbTaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTaActionPerformed
        // kombo get klik
       
    }//GEN-LAST:event_cbTaActionPerformed

    private void tbMapelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMapelMouseClicked
        // tabel klik
        TabelKlik();
        SaveUnAktif();
    }//GEN-LAST:event_tbMapelMouseClicked

    private void btCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCariActionPerformed
        // Cari
        Cari();
    }//GEN-LAST:event_btCariActionPerformed

    private void txCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txCariKeyPressed
        // cari enter
        if (evt.getKeyCode()== KeyEvent.VK_ENTER) {
            Cari();
        }
    }//GEN-LAST:event_txCariKeyPressed

    private void cbNIKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNIKActionPerformed
        // NIK klik
        String ids = String.valueOf(cbNIK.getSelectedItem());
        String vnm = "";
        try {
            Statement st;
             ResultSet rs;
             String sql;
             sql ="SELECT * FROM siswa where nik='"+ids+"'";
             st=conn.createStatement();
             rs=st.executeQuery(sql);
             while(rs.next()){
                 ids = rs.getString("nik");
                 vnm = rs.getString("nama_siswa");
                 cbNIK.setSelectedItem(ids);
                 txNmSis.setText(vnm);
             }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_cbNIKActionPerformed

    private void cbSemesterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSemesterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbSemesterActionPerformed

    private void cbKelas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbKelas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbKelas1ActionPerformed

    private void cbStatusSiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatusSiswaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbStatusSiswaActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Status_Siswa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btBatal;
    private javax.swing.JButton btCari;
    private javax.swing.JButton btHapus;
    private javax.swing.JButton btKel;
    private javax.swing.JButton btSimpan;
    private javax.swing.JButton btUbah1;
    private javax.swing.JComboBox cbKelas1;
    private javax.swing.JComboBox cbNIK;
    private javax.swing.JComboBox cbSemester;
    private javax.swing.JComboBox<String> cbStatusSiswa;
    private javax.swing.JComboBox cbTa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbMapel;
    private javax.swing.JTextField txCari;
    private javax.swing.JTextField txNmSis;
    private javax.swing.JTextField txid;
    // End of variables declaration//GEN-END:variables
}
