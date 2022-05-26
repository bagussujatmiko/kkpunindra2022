
package GUI;


import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import method.koneksi;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger; 


/**
 *
 * @author dinda
 */
public class Master_Siswa extends javax.swing.JFrame {
    
    private final Connection conn = new koneksi().getKoneksi();
    private DefaultTableModel tabmode;
    String tanggal1;
    Connection con = null;
    Statement st = null;
    ResultSet rs = null;

    public Master_Siswa() {
        initComponents();
        dataTable();
        SaveAktif();
    }
    
    public void SaveAktif() {
        btSisHapus.setEnabled(false);
        btSisUbah.setEnabled(false);
        btSisSimpan.setEnabled(true);
        AutoNomor();
        txNmSis.requestFocus();
    }
    
    public void SaveUnAktif(){
        btSisHapus.setEnabled(true);
        btSisUbah.setEnabled(true);
        btSisSimpan.setEnabled(false);
    }
    
    public void AutoNomor(){
         try{
             Statement st;
             ResultSet rs;
             String sql;
             
             sql ="SELECT * FROM siswa order by nik desc";
             st=conn.createStatement();
             rs=st.executeQuery(sql);
             if (rs.next()) {
                 String ids = rs.getString("nik").substring(1);
                 String AN = "" + (Integer.parseInt(ids)+1);
                 String Nol = "";
                 
                 if (AN.length()==1) {
                     Nol ="000";
                 } if (AN.length()==2) {
                     Nol ="00";
                 }if (AN.length()==3) {
                     Nol ="0";
                 } if (AN.length()==4) {
                     Nol="";
                 }
                 txnis.setText("2"+Nol+AN);
                 
             } else {
                 txnis.setText("210001");
             }
         } catch (Exception e){
             JOptionPane.showMessageDialog(rootPane, e);
         }
     }
    
    protected void dataTable(){
        Object [] baris = {"nik","nama_siswa","temp_lahir","tgl_lahir","j_kelamin","agama","alamat","handphone","nama_ayah","nama_ibu","pekerjaan_ayah","pekerjaan_ibu"};
        tabmode = new DefaultTableModel(null, baris);
        tabelSiswa.setModel(tabmode);
        String sql="select*from siswa";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
            String sid = hasil.getString("nik");
            String snama = hasil.getString("nama_siswa");
            String stl = hasil.getString("temp_lahir");
            String sta = hasil.getString("tgl_lahir");
            String sjk = hasil.getString("j_kelamin");
            String sagm = hasil.getString("agama");
            String salm = hasil.getString("alamat");
            String sno = hasil.getString("handphone");
            String sayh = hasil.getString("nama_ayah");
            String sibu = hasil.getString("nama_ibu");
            String spa = hasil.getString("pekerjaan_ayah");
            String sib = hasil.getString("pekerjaan_ibu");
            String[]data = {sid,snama,stl,sta,sjk,sagm,salm,sno,sayh,sibu,spa,sib};
            tabmode.addRow(data);
            }
        }catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public void TabelKlik(){
        int bar = tabelSiswa.getSelectedRow();
        txnis.setText(tabmode.getValueAt(bar, 0).toString());
        txNmSis.setText(tabmode.getValueAt(bar, 1).toString());
        txTmpLahir.setText(tabmode.getValueAt(bar, 2).toString());
        String date = String.valueOf(tabmode.getValueAt(bar, 3).toString());
        Date tanggal = null;
        try {
        tanggal = new SimpleDateFormat("dd MMMM yyyy").parse(date);
        } catch (ParseException ex) {
            System.out.println(ex);
        }
        txTglLahir.setDate(tanggal);
        if ("perempuan".equals(tabmode.getValueAt(bar, 4).toString())){
            radioSisP.setSelected(true);
        } else {
            radioSisL.setSelected(true);
        };
        if ("islam".equals(tabmode.getValueAt(bar, 5).toString())){
            cbSisAgama.setSelectedIndex(0);
        } if ("kristen".equals(tabmode.getValueAt(bar, 5).toString())){
            cbSisAgama.setSelectedIndex(1);
        } if ("hindu".equals(tabmode.getValueAt(bar, 5).toString())){
            cbSisAgama.setSelectedIndex(2);
        }if ("budha".equals(tabmode.getValueAt(bar, 5).toString())){
            cbSisAgama.setSelectedIndex(3);
        }if ("katolik".equals(tabmode.getValueAt(bar, 5).toString())){
            cbSisAgama.setSelectedIndex(4);
        };
        txAlamat.setText(tabmode.getValueAt(bar, 6).toString());
        txSisTlp.setText(tabmode.getValueAt(bar, 7).toString());
        txSisAyah.setText(tabmode.getValueAt(bar, 8).toString());
        txSisIbu.setText(tabmode.getValueAt(bar, 9).toString());
        txSisPAyah.setText(tabmode.getValueAt(bar, 10).toString());
        txPIbu.setText(tabmode.getValueAt(bar, 11).toString());

    }
    
    public void Kosong(){
        txNmSis.setText("");
        txTmpLahir.setText("");
        txTglLahir.setDate(null);
        GrupJenkel.clearSelection();
        cbSisAgama.setSelectedIndex(0);
        txAlamat.setText("");
        txSisTlp.setText("");
        txSisAyah.setText("");
        txSisIbu.setText("");
        txSisPAyah.setText("");
        txPIbu.setText("");

    }
    
    public int Simpan(){
        String sql = "insert into siswa(nik,nama_siswa,temp_lahir,tgl_lahir,j_kelamin,agama,alamat,handphone,nama_ayah,nama_ibu,pekerjaan_ayah,pekerjaan_ibu) "
                + "values (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            if (txnis.getText().equals("")||txNmSis.getText().equals("")||
                    txTmpLahir.getText().equals("")||txTglLahir.getDate()==null||GrupJenkel.isSelected(null)) { 
                JOptionPane.showMessageDialog(rootPane, "Maaf kolom tidak boleh kosong!");
                AutoNomor();
            } else {
            PreparedStatement stat = conn.prepareStatement(sql);
            SimpleDateFormat Format=new SimpleDateFormat("dd MMMM yyyy");
            tanggal1 = Format.format(txTglLahir.getDate());
            stat.setString(1,txnis.getText());
            stat.setString(2,txNmSis.getText());
            stat.setString(3,txTmpLahir.getText());
            stat.setString(4,tanggal1);
            String G = "" ;
            if (radioSisL.isSelected())
                G = "laki-laki";
            else if (radioSisP.isSelected())
                    G = "perempuan";
            stat.setString(5,G);
            stat.setString(6,String.valueOf(cbSisAgama.getSelectedItem()));
            stat.setString(7,txAlamat.getText());
            stat.setString(8,txSisTlp.getText());
            stat.setString(9,txSisAyah.getText());
            stat.setString(10,txSisIbu.getText());
            stat.setString(11,txSisPAyah.getText());
            stat.setString(12,txPIbu.getText());
 
            
            stat.executeUpdate();
            JOptionPane.showMessageDialog(rootPane, "Data berhasil disimpan");
            Kosong();
            dataTable();
            } 
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "Data gagal disimpan "+e);
        }
        return 0;
    }
    
    public void Ubah(){
        String sql ="update siswa set nama_siswa=?, temp_lahir=?, tgl_lahir=?, j_kelamin=?, agama=?, alamat=?, handphone=?, nama_ayah=?, nama_ibu=?, pekerjaan_ayah=?, pekerjaan_ibu=? where nik='"+txnis.getText()+"'";
        try{
            if (txnis.getText().trim().equals("")||txNmSis.getText().trim().equals("")||
                    txTmpLahir.getText().trim().equals("")||txTglLahir.getDate()==null||GrupJenkel.isSelected(null)) { 
                JOptionPane.showMessageDialog(rootPane, "Maaf kolom tidak boleh kosong!");
                AutoNomor();
                return;
            } else {
            PreparedStatement stat = conn.prepareStatement(sql);
            SimpleDateFormat Format=new SimpleDateFormat("dd MMMM yyyy");
            tanggal1 = Format.format(txTglLahir.getDate());
            stat.setString(1,txNmSis.getText());
            stat.setString(2,txTmpLahir.getText());
            stat.setString(3,tanggal1);
            
            String G = "" ;
            if (radioSisL.isSelected())
                G = "laki-laki";
            else if (radioSisP.isSelected())
                    G = "perempuan";
            stat.setString(4,G);
            stat.setString(5,String.valueOf(cbSisAgama.getSelectedItem()));
            stat.setString(6,txAlamat.getText());
            stat.setString(7,txSisTlp.getText());
            stat.setString(8,txSisAyah.getText());
            stat.setString(9,txSisIbu.getText());
            stat.setString(10,txSisPAyah.getText());
            stat.setString(11,txPIbu.getText());
 
            
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
        if (txnis.getText().equals("")||txNmSis.getText().equals("")||
                    txTmpLahir.getText().equals("")||txTglLahir.getDate()==null||GrupJenkel.isSelected(null)) { 
                JOptionPane.showMessageDialog(rootPane, "Maaf kolom tidak boleh kosong!");
                AutoNomor();
                return;
        } else {
            int ok = JOptionPane.showConfirmDialog(rootPane, "Anda yakin ingin menghapus data?","Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql = "delete from siswa where nik='"+txnis.getText()+"'";
            try{
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(rootPane, "Data berhasil di hapus");
                Kosong();
                dataTable();
            }catch (SQLException e){
                JOptionPane.showMessageDialog(rootPane, "Data gagal di hapus"+e);
            }
        } else {
            Kosong();
        }
        }
    } 
    
    public void Cari(){
        int row=tabelSiswa.getRowCount();
        for (int x=0;x<row;x++){
            tabmode.removeRow(0);
        }
        try{
            ResultSet rs=conn.createStatement().executeQuery("Select * from siswa where nama_siswa like '%"+txCari.getText()+"%' or nik like '%"+txCari.getText()+"%' or j_kelamin like '%"+txCari.getText()+"%'");
            while(rs.next()){
                String data[]={rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11),rs.getString(12),rs.getString(13),rs.getString(14),rs.getString(15)};
                tabmode.addRow(data);
            }
        }catch (SQLException ex){
            Logger.getLogger(Master_Siswa.class.getName()).log(Level.SEVERE, null, ex);  
        }
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GrupJenkel = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelSiswa = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        btSisBatal = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txnis = new javax.swing.JTextField();
        txNmSis = new javax.swing.JTextField();
        txTmpLahir = new javax.swing.JTextField();
        txAlamat = new javax.swing.JTextField();
        txSisTlp = new javax.swing.JTextField();
        txSisAyah = new javax.swing.JTextField();
        txSisIbu = new javax.swing.JTextField();
        txSisPAyah = new javax.swing.JTextField();
        txPIbu = new javax.swing.JTextField();
        btSisSimpan = new javax.swing.JButton();
        btSisUbah = new javax.swing.JButton();
        btSisHapus = new javax.swing.JButton();
        radioSisP = new javax.swing.JRadioButton();
        radioSisL = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        cbSisAgama = new javax.swing.JComboBox();
        txTglLahir = new com.toedter.calendar.JDateChooser();
        txCari = new javax.swing.JTextField();
        GsExit = new javax.swing.JButton();
        btCari = new javax.swing.JButton();
        javax.swing.JLabel jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 153), 2));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelSiswa.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelSiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelSiswaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelSiswa);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 600, 770, 160));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setText("Tanggal Lahir");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, 100, -1));

        btSisBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tutup.png"))); // NOI18N
        btSisBatal.setText("Batal");
        btSisBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSisBatalActionPerformed(evt);
            }
        });
        jPanel1.add(btSisBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 510, 100, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setText("NIK Siswa");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 110, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setText("Jenis Kelamin");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 210, 110, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setText("Tempat Lahir");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, 110, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel7.setText("Nama");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 110, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel8.setText("Agama");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 110, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel9.setText("Alamat");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, 110, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel10.setText("No Handphone");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, 110, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel11.setText("Nama Ibu");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 390, 110, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel12.setText("Nama Ayah");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, 110, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel13.setText("Pekerjaan Ayah");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 430, 110, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel14.setText("Pekerjaan Ibu");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 480, 110, 20));
        jPanel1.add(txnis, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 150, -1));
        jPanel1.add(txNmSis, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 150, -1));
        jPanel1.add(txTmpLahir, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 150, -1));
        jPanel1.add(txAlamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 280, 440, -1));
        jPanel1.add(txSisTlp, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, 440, -1));
        jPanel1.add(txSisAyah, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 440, -1));
        jPanel1.add(txSisIbu, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 390, 440, -1));
        jPanel1.add(txSisPAyah, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 430, 440, -1));
        jPanel1.add(txPIbu, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 480, 440, -1));

        btSisSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simpan.png"))); // NOI18N
        btSisSimpan.setText("Simpan");
        btSisSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSisSimpanActionPerformed(evt);
            }
        });
        jPanel1.add(btSisSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 510, 100, 30));

        btSisUbah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        btSisUbah.setText("Ubah");
        btSisUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSisUbahActionPerformed(evt);
            }
        });
        jPanel1.add(btSisUbah, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 510, 100, 30));

        btSisHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hapus.png"))); // NOI18N
        btSisHapus.setText("Hapus");
        btSisHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSisHapusActionPerformed(evt);
            }
        });
        jPanel1.add(btSisHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 510, 100, 30));

        radioSisP.setBackground(new java.awt.Color(255, 255, 255));
        GrupJenkel.add(radioSisP);
        radioSisP.setText("Perempuan");
        jPanel1.add(radioSisP, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, 120, 20));

        radioSisL.setBackground(new java.awt.Color(255, 255, 255));
        GrupJenkel.add(radioSisL);
        radioSisL.setText("Laki-Laki");
        jPanel1.add(radioSisL, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 210, 80, 20));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("PENDAFTARAN MURID");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 270, 40));

        cbSisAgama.setBackground(new java.awt.Color(204, 204, 255));
        cbSisAgama.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Islam", "Kristen", "Hindu", "Budha", "Katolik" }));
        jPanel1.add(cbSisAgama, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 240, 300, 20));

        txTglLahir.setDateFormatString("dd MMMM yyyy");
        jPanel1.add(txTglLahir, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, 110, -1));

        txCari.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txCari.setToolTipText("cari data");
        txCari.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        txCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txCariKeyPressed(evt);
            }
        });
        jPanel1.add(txCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 560, 250, 20));

        GsExit.setBackground(new java.awt.Color(255, 0, 0));
        GsExit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        GsExit.setText("X");
        GsExit.setToolTipText("keluar");
        GsExit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        GsExit.setIconTextGap(1);
        GsExit.setMargin(new java.awt.Insets(2, 2, 2, 2));
        GsExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GsExitActionPerformed(evt);
            }
        });
        jPanel1.add(GsExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 0, 40, -1));

        btCari.setText("cari");
        btCari.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCariActionPerformed(evt);
            }
        });
        jPanel1.add(btCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 560, 70, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pendaftaran_1.png"))); // NOI18N
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 860));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 770));

        setSize(new java.awt.Dimension(802, 779));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void GsExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GsExitActionPerformed
        // Keluar
        new gui.Beranda().setVisible(true);
        dispose();
    }//GEN-LAST:event_GsExitActionPerformed

    int x, y;
    private void btCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCariActionPerformed
        // bt cari
        Cari();
    }//GEN-LAST:event_btCariActionPerformed

    private void txCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txCariKeyPressed
        // cari enter
        if (evt.getKeyCode()== KeyEvent.VK_ENTER) {
            Cari();
        }
    }//GEN-LAST:event_txCariKeyPressed

    private void btSisHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSisHapusActionPerformed
        // Hapus
        Hapus();
        SaveAktif();
    }//GEN-LAST:event_btSisHapusActionPerformed

    private void btSisUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSisUbahActionPerformed
        // Ubah
        Ubah();
        SaveAktif();
    }//GEN-LAST:event_btSisUbahActionPerformed

    private void btSisSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSisSimpanActionPerformed
        // Simpan
        Simpan();
        SaveAktif();
    }//GEN-LAST:event_btSisSimpanActionPerformed

    private void tabelSiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelSiswaMouseClicked
        // Tabel Klik
        TabelKlik();
        SaveUnAktif();
    }//GEN-LAST:event_tabelSiswaMouseClicked

    private void btSisBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSisBatalActionPerformed
        // Batal
        Kosong();
        SaveAktif();
    }//GEN-LAST:event_btSisBatalActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Master_Siswa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup GrupJenkel;
    private javax.swing.JButton GsExit;
    private javax.swing.JButton btCari;
    private javax.swing.JButton btSisBatal;
    private javax.swing.JButton btSisHapus;
    private javax.swing.JButton btSisSimpan;
    private javax.swing.JButton btSisUbah;
    private javax.swing.JComboBox cbSisAgama;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton radioSisL;
    private javax.swing.JRadioButton radioSisP;
    private javax.swing.JTable tabelSiswa;
    private javax.swing.JTextField txAlamat;
    private javax.swing.JTextField txCari;
    private javax.swing.JTextField txNmSis;
    private javax.swing.JTextField txPIbu;
    private javax.swing.JTextField txSisAyah;
    private javax.swing.JTextField txSisIbu;
    private javax.swing.JTextField txSisPAyah;
    private javax.swing.JTextField txSisTlp;
    private com.toedter.calendar.JDateChooser txTglLahir;
    private javax.swing.JTextField txTmpLahir;
    private javax.swing.JTextField txnis;
    // End of variables declaration//GEN-END:variables
}
