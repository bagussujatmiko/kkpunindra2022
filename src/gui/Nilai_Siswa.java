
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
public class Nilai_Siswa extends javax.swing.JFrame {
    
    private final Connection conn = new koneksi().getKoneksi();
    private DefaultTableModel tabmode;


    public Nilai_Siswa() {
        initComponents();
        dataTable();
        isiComboKelas();
        isiComboMapel();
        AutoNomor();
        AllUnAktif();
        
    }
      public void SaveAktif() {
        btHapus.setEnabled(false);
        btUbah1.setEnabled(false);
        btSimpan.setEnabled(true);
    }
    
    public void SaveUnAktif(){
        btHapus.setEnabled(true);
        btUbah1.setEnabled(true);
        btSimpan.setEnabled(false);
    }
    
    public void AllUnAktif(){
        cbNIK.setEnabled(false);
        btBatal.setEnabled(false);
        btHapus.setEnabled(false);
        btUbah1.setEnabled(false);
        btSimpan.setEnabled(false);
        tbNilai.setVisible(false);
        cbKelas.requestFocus();
    }
    
    public void AllAktif(){
        cbNIK.setEnabled(true);
        btBatal.setEnabled(true);
        btHapus.setEnabled(false);
        btUbah1.setEnabled(false);
        btSimpan.setEnabled(true);
        tbNilai.setVisible(true);
        cbNIK.requestFocus();
    }
    
    protected void dataTable(){
        Object [] baris = {"kd nilai mapel","status siswa","nama","nik","kelas","semester","tahun ajaran","nama mapel","Nilai Perkembangan"};
        tabmode = new DefaultTableModel(null, baris);
        tbNilai.setModel(tabmode);
        String sql="select*from nilai_siswa";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
            String idn = hasil.getString("kd_nilai_mapel");
            String ids = hasil.getString("status_siswa");
            String ns = hasil.getString("nama_siswa");
            String idss = hasil.getString("nik");
            String ks = hasil.getString("kelas");
            String sm = hasil.getString("semester");
            String ta = hasil.getString("tahun_ajaran");
            String idm = hasil.getString("mapel");
            String np = hasil.getString("n_perkembangan");

           
            String[]data = {idn,ids,ns,idss,ks,sm,ta,idm,np};
            tabmode.addRow(data);
            }
        }catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public void TabelKlik(){
        int bar = tbNilai.getSelectedRow();
        txid.setText(tabmode.getValueAt(bar, 0).toString());
        txidSts.setText(tabmode.getValueAt(bar, 1).toString());
        String vnis = String.valueOf(tabmode.getValueAt(bar, 2).toString());
        cbNIK.setSelectedItem(vnis);
        txNmSis.setText(tabmode.getValueAt(bar, 3).toString());
        String vkelas = String.valueOf(tabmode.getValueAt(bar, 4).toString());
        cbKelas.setSelectedItem(vkelas);
        txSemester.setText(tabmode.getValueAt(bar, 5).toString());
        txTA.setText(tabmode.getValueAt(bar, 6).toString());
        String vmp = String.valueOf(tabmode.getValueAt(bar, 7).toString());
        cbMapel.setSelectedItem(vmp); 
        txUH.setText(tabmode.getValueAt(bar, 8).toString());
       
         String vnm,vsms,vta,ids ;
        try {
            Statement st;
             ResultSet rs;
             String sql;
             sql ="SELECT * FROM status_siswa WHERE nik='"+vnis+"'";
             st=conn.createStatement();
             rs=st.executeQuery(sql);
             while(rs.next()){
                ids = rs.getString("status_siswa");
                vnis = rs.getString("nik");
                vnm = rs.getString("nama_siswa");
                vsms = rs.getString("semester");
                vta = rs.getString("t_ajaran");
                txidSts.setText(ids);
                txNmSis.setText(vnm);
                txSemester.setText(vsms);
                txTA.setText(vta);
             }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }
    
    public void Kosong(){
        txidSts.setText("");
        cbNIK.setSelectedIndex(0);
        txNmSis.setText("");
        txSemester.setText("");
        txTA.setText("");
        cbMapel.setSelectedIndex(0);
        txUH.setText("");

    }
    
    public void AutoNomor(){
         try{
             Statement st;
             ResultSet rs;
             String sql;
             
             sql ="SELECT * FROM nilai_siswa order by kd_nilai_mapel desc";
             st=conn.createStatement();
             rs=st.executeQuery(sql);
             if (rs.next()) {
                 String nis = rs.getString("kd_nilai_mapel").substring(2);
                 String AN = "" + (Integer.parseInt(nis)+1);
                 String Nol = "";
                 
                if (AN.length()==2) {
                     Nol ="00";
                 }if (AN.length()==3) {
                     Nol ="0";
                 } if (AN.length()==4) {
                     Nol="";
                 }
                 txid.setText("NM"+Nol+AN);
                 
             } else {
                 txid.setText("NM10001");
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
             sql ="SELECT * FROM status_siswa where kelas='"
                     +String.valueOf(cbKelas.getSelectedItem())+"'";
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
             sql ="SELECT * FROM status_siswa";
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
        String sql = "insert into nilai_siswa values (?,?,?,?,?,?,?,?,?)";
        try {
            if (txidSts.getText().equals("")||txNmSis.getText().equals("")||
                    txUH.getText().equals("")) { 
                JOptionPane.showMessageDialog(rootPane, "Maaf kolom tidak boleh kosong!");
                return;
            } else {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1,txid.getText());
            stat.setString(2,txidSts.getText());
            stat.setString(3,String.valueOf(cbNIK.getSelectedItem()));
            stat.setString(4,txNmSis.getText());
            stat.setString(5,String.valueOf(cbKelas.getSelectedItem()));
            stat.setString(6,txSemester.getText());
            stat.setString(7,txTA.getText());
            stat.setString(8,String.valueOf(cbMapel.getSelectedItem()));
            stat.setString(9,txUH.getText());
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
            String sql ="update nilai_siswa set status_siswa=?, nik=?, "
                    + "nama_siswa=?,kelas=?,semester=?,tahun_ajaran=?, mapel=?, n_perkembangan=? where kd_nilai_mapel='"+txid.getText()+"'";
            if (txidSts.getText().equals("")||txNmSis.getText().equals("")||
                    txUH.getText().equals("")) { 
                JOptionPane.showMessageDialog(rootPane, "Maaf kolom tidak boleh kosong!");
                return;
            } else {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1,txidSts.getText());
             stat.setString(2,String.valueOf(cbNIK.getSelectedItem()));
            stat.setString(3,txNmSis.getText());
             stat.setString(4,String.valueOf(cbKelas.getSelectedItem()));
            stat.setString(5,txSemester.getText());
            stat.setString(6,txTA.getText());
            stat.setString(7,String.valueOf(cbMapel.getSelectedItem()));
            stat.setString(8,txUH.getText());
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
        if (txidSts.getText().equals("")||txNmSis.getText().equals("")||
                    txUH.getText().equals("")) { 
            JOptionPane.showMessageDialog(rootPane, "Maaf kolom tidak boleh kosong!");
            return;
        } else {
            int ok = JOptionPane.showConfirmDialog(rootPane, "Anda yakin ingin menghapus data?","Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql = "delete from nilai_siswa where kd_nilai_mapel='"+txid.getText()+"'";
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
        int row=tbNilai.getRowCount();
        for (int x=0;x<row;x++){
            tabmode.removeRow(0);
        }
        try{
            ResultSet rs=conn.createStatement().executeQuery("Select * from "
                    + "nilai_siswa where nama_siswa like '%"+txCari.getText()+ 
                    "%' or nik like '%"+txCari.getText()+"%' "
                    + "or status_siswa like '%"+txCari.getText()+"%' "
                    + "or nilai_mapel like '%"+txCari.getText()+"%'");
            while(rs.next()){
                String data[]={rs.getString(1),rs.getString(2),rs.getString(3)
                        ,rs.getString(4),rs.getString(5),rs.getString(6)};
                tabmode.addRow(data);
            }
        }catch (SQLException ex){
            Logger.getLogger(Nilai_Siswa.class.getName()).log(Level.SEVERE, null, ex);  
        }
    }
    

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        window = new javax.swing.JPanel();
        btKel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbNIK = new javax.swing.JComboBox();
        txNmSis = new javax.swing.JTextField();
        txSemester = new javax.swing.JTextField();
        txTA = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txidSts = new javax.swing.JTextField();
        txid = new javax.swing.JTextField();
        cbKelas = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cbMapel = new javax.swing.JComboBox();
        txUH = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbNilai = new javax.swing.JTable();
        txCari = new javax.swing.JTextField();
        btCari = new javax.swing.JButton();
        btBatal = new javax.swing.JButton();
        btSimpan = new javax.swing.JButton();
        btHapus = new javax.swing.JButton();
        btUbah1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 153), 2));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        window.setBackground(new java.awt.Color(0, 102, 153));
        window.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                windowMouseDragged(evt);
            }
        });
        window.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                windowMousePressed(evt);
            }
        });
        window.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        window.add(btKel, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, 80, 60));

        jLabel1.setFont(new java.awt.Font("Andalus", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 255, 51));
        jLabel1.setText("Kelola Data Nilai Mapel Siswa");
        window.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 360, 40));

        jPanel1.add(window, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 60));

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)), "input data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(51, 51, 51))); // NOI18N
        jPanel2.setAutoscrolls(true);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("id nilai Mapel");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel3.setText("Nama");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        jLabel7.setText("Semester");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        cbNIK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-nik siswa-" }));
        cbNIK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNIKActionPerformed(evt);
            }
        });
        jPanel2.add(cbNIK, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 180, -1));

        txNmSis.setEditable(false);
        jPanel2.add(txNmSis, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 120, 180, -1));

        txSemester.setEditable(false);
        jPanel2.add(txSemester, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 180, -1));

        txTA.setEditable(false);
        jPanel2.add(txTA, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, 180, -1));

        jLabel8.setText("Tahun Ajaran");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, -1, -1));

        jLabel9.setText("Nama Mapel");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, -1, -1));

        jLabel12.setText("nik siswa");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        jLabel13.setText("status siswa");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        txidSts.setEditable(false);
        jPanel2.add(txidSts, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 100, -1));

        txid.setEditable(false);
        jPanel2.add(txid, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 100, -1));

        cbKelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "- pilih kelas -" }));
        cbKelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cbKelasMouseClicked(evt);
            }
        });
        cbKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbKelasActionPerformed(evt);
            }
        });
        jPanel2.add(cbKelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, 70, -1));

        jLabel15.setText("Kelas");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, -1, -1));

        jLabel11.setText("Nilai Mata pelajaran");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, -1));

        cbMapel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "- pilih mapel -" }));
        jPanel2.add(cbMapel, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, 180, -1));
        jPanel2.add(txUH, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, 50, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 370, 300));

        tbNilai.setModel(new javax.swing.table.DefaultTableModel(
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
        tbNilai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbNilaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbNilai);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 120, 470, 240));

        txCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txCariKeyPressed(evt);
            }
        });
        jPanel1.add(txCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, 200, 30));

        btCari.setText("cari");
        btCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCariActionPerformed(evt);
            }
        });
        jPanel1.add(btCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 80, 80, 30));

        btBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tutup.png"))); // NOI18N
        btBatal.setText("Batal");
        btBatal.setToolTipText("Batal");
        btBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBatalActionPerformed(evt);
            }
        });
        jPanel1.add(btBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 410, 100, 30));

        btSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/simpan.png"))); // NOI18N
        btSimpan.setText("Simpan");
        btSimpan.setToolTipText("Simpan");
        btSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSimpanActionPerformed(evt);
            }
        });
        jPanel1.add(btSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 370, 100, 30));

        btHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/hapus.png"))); // NOI18N
        btHapus.setText("Hapus");
        btHapus.setToolTipText("Hapus");
        btHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHapusActionPerformed(evt);
            }
        });
        jPanel1.add(btHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 410, 100, 30));

        btUbah1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/edit.png"))); // NOI18N
        btUbah1.setText("Ubah");
        btUbah1.setToolTipText("Ubah");
        btUbah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btUbah1ActionPerformed(evt);
            }
        });
        jPanel1.add(btUbah1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 370, 100, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 880, 450));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btKelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btKelActionPerformed
        // keluar
        new BerandaGuru().setVisible(true);
        dispose();
    }//GEN-LAST:event_btKelActionPerformed

    int x, y;
    private void windowMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_windowMousePressed
        // window hold
        x=evt.getX();
        y=evt.getY();
    }//GEN-LAST:event_windowMousePressed

    private void windowMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_windowMouseDragged
        // mouse drag
        int xx = evt.getXOnScreen();
        int yy = evt.getYOnScreen();
        this.setLocation(xx-x, yy-y);
    }//GEN-LAST:event_windowMouseDragged

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

    private void tbNilaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNilaiMouseClicked
        // tabel klik
        TabelKlik();
        SaveUnAktif();
    }//GEN-LAST:event_tbNilaiMouseClicked

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
        // NIS klik
        String vnis = String.valueOf(cbNIK.getSelectedItem());
        String ids,vnm,vsms,vta ;
        try {
            Statement st;
            ResultSet rs;
            String sql ="SELECT * FROM status_siswa where nik='"+vnis+"'";
            st=conn.createStatement();
            rs=st.executeQuery(sql);
            while(rs.next()){
                ids = rs.getString("status_siswa");
                vnm = rs.getString("nama_siswa");
                vsms = rs.getString("semester");
                vta = rs.getString("t_ajaran");
                txidSts.setText(ids);
                txNmSis.setText(vnm);
                txSemester.setText(vsms);
                txTA.setText(vta);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_cbNIKActionPerformed

    private void cbKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbKelasActionPerformed
        // cb kelas
        int n=cbNIK.getItemCount();
        int i=0;
        try {
            while(i<=n) {
                cbNIK.removeItemAt(i+1);
                i++;
            }
        } catch (Exception e) {
        }
        isiComboNIS();
        AllAktif();
        Kosong();
    }//GEN-LAST:event_cbKelasActionPerformed

    private void cbKelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbKelasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cbKelasMouseClicked

    public static void main(String args[]) {
  
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Nilai_Siswa().setVisible(true);
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
    private javax.swing.JComboBox cbKelas;
    private javax.swing.JComboBox cbMapel;
    private javax.swing.JComboBox cbNIK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbNilai;
    private javax.swing.JTextField txCari;
    private javax.swing.JTextField txNmSis;
    private javax.swing.JTextField txSemester;
    private javax.swing.JTextField txTA;
    private javax.swing.JTextField txUH;
    private javax.swing.JTextField txid;
    private javax.swing.JTextField txidSts;
    private javax.swing.JPanel window;
    // End of variables declaration//GEN-END:variables
}
