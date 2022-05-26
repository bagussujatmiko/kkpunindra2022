
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
public class Kepribadian extends javax.swing.JFrame {
    
    private final Connection conn = new koneksi().getKoneksi();
    private DefaultTableModel tabmode;


    public Kepribadian() {
        initComponents();
        dataTable();
        isiComboNIS();
        isiComboKelas();
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
        tbKepri.setVisible(false);
        txCatatan.setEnabled(false);
        cbKelas.requestFocus();
    }
    
    public void AllAktif(){
        cbNIK.setEnabled(true);
        btBatal.setEnabled(true);
        btHapus.setEnabled(false);
        btUbah1.setEnabled(false);
        btSimpan.setEnabled(true);
        tbKepri.setVisible(true);
        txCatatan.setEnabled(true);
        cbNIK.requestFocus();
    }
    
    protected void dataTable(){
        Object [] baris = {"kd akhlak","id status","nik","nama","Pecaya diri","Tanggung Jawab","Mandiri","displin","Catatan"};
        tabmode = new DefaultTableModel(null, baris);
        tbKepri.setModel(tabmode);
        String sql="select*from akhlak_siswa";
        try {
            java.sql.Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
            String id = hasil.getString("kd_akhlak");
            String idss = hasil.getString("id_status");
            String ids = hasil.getString("nik");
            String nm = hasil.getString("nama_siswa");
            String Npd = hasil.getString("percaya_diri");
            String Ntj = hasil.getString("t_jawab");
            String Nm = hasil.getString("mandiri");
            String Nd = hasil.getString("disiplin");
            String Nc = hasil.getString("catatan");
           
            String[]data = {id,idss,ids,nm,Npd,Ntj,Nm,Nd,Nc};
            tabmode.addRow(data);
            }
        }catch (Exception e) {
            System.err.println(e);
        }
    }
    
    public void AutoNomor(){
         try{
             Statement st;
             ResultSet rs;
             String sql;
             
             sql ="SELECT * FROM akhlak_siswa order by kd_akhlak desc";
             st=conn.createStatement();
             rs=st.executeQuery(sql);
             if (rs.next()) {
                 String nis = rs.getString("kd_akhlak").substring(2);
                 String AN = "" + (Integer.parseInt(nis)+1);
                 String Nol = "";
                 
                if (AN.length()==2) {
                     Nol ="00";
                 }if (AN.length()==3) {
                     Nol ="0";
                 } if (AN.length()==4) {
                     Nol="";
                 }
                 txid.setText("KP"+Nol+AN);
                 
             } else {
                 txid.setText("KP10001");
             }
         } catch (Exception e){
             JOptionPane.showMessageDialog(null, e);
         }
     }
    
    public void TabelKlik(){
        int bar = tbKepri.getSelectedRow();
        txid.setText(tabmode.getValueAt(bar, 0).toString());
        txidSts.setText(tabmode.getValueAt(bar, 1).toString());
        String vnis = String.valueOf(tabmode.getValueAt(bar, 2).toString());
        cbNIK.setSelectedItem(vnis);
        txNmSis.setText(tabmode.getValueAt(bar, 3).toString());
         String vpd = String.valueOf(tabmode.getValueAt(bar, 4).toString());
        cbPecayaDiri.setSelectedItem(vpd); 
        String vtj = String.valueOf(tabmode.getValueAt(bar, 5).toString());
        cbT_jawab.setSelectedItem(vtj); 
        String vm = String.valueOf(tabmode.getValueAt(bar, 6).toString());
        cbMandiri.setSelectedItem(vm); 
        String vd = String.valueOf(tabmode.getValueAt(bar, 7).toString());
        cbDisiplin.setSelectedItem(vd);
        txCatatan.setText(tabmode.getValueAt(bar, 8).toString());
        
         String vnm,vkls,vsms,vta,ids ;
        try {
            Statement st;
             ResultSet rs;
             String sql;
             sql ="SELECT * FROM status_siswa WHERE nik='"+vnis+"'";
             st=conn.createStatement();
             rs=st.executeQuery(sql);
             while(rs.next()){
                ids = rs.getString("id_status");
                vnis = rs.getString("nik");
                vnm = rs.getString("nama_siswa");
                vkls = rs.getString("kelas");
                vsms = rs.getString("semester");
                vta = rs.getString("t_ajaran");
                txidSts.setText(ids);
                txNmSis.setText(vnm);
                txKelas.setText(vkls);
                txSemester.setText(vsms);
                txTA.setText(vta);
             }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }
    
    public void Kosong(){
        txid.setText("");
        txidSts.setText("");
        cbNIK.setSelectedIndex(0);
        txNmSis.setText("");
        txKelas.setText("");
        txSemester.setText("");
        txTA.setText("");
        cbPecayaDiri.setSelectedIndex(0);
        cbT_jawab.setSelectedIndex(0);
        cbMandiri.setSelectedIndex(0);
        cbDisiplin.setSelectedIndex(0);
        txCatatan.setText("");
        AutoNomor();
    }
    
      public void isiComboNIS(){
        try {
            Statement st;
             ResultSet rs;
             String sql;
             sql ="SELECT * FROM status_siswa where kelas='"+String.valueOf(cbKelas.getSelectedItem())+"'";
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
                cbKelas.addItem(rs.getString("kelas"));
             }
             rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "gagal "+e);
        }
    }
    
    public void Simpan(){
        String sql = "insert into akhlak_siswa values (?,?,?,?,?,?,?,?,?)";
        try {
            if (txidSts.getText().equals("")||txNmSis.getText().equals("")||txKelas.getText().equals("")) { 
                JOptionPane.showMessageDialog(rootPane, "Maaf kolom tidak boleh kosong!");
                return;
            } else {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1,txid.getText());
            stat.setString(2,txidSts.getText());
            stat.setString(3,String.valueOf(cbNIK.getSelectedItem()));
            stat.setString(4,txNmSis.getText());
            stat.setString(5,String.valueOf(cbPecayaDiri.getSelectedItem()));
            stat.setString(6,String.valueOf(cbT_jawab.getSelectedItem()));
            stat.setString(7,String.valueOf(cbMandiri.getSelectedItem()));
            stat.setString(8,String.valueOf(cbDisiplin.getSelectedItem()));
            stat.setString(9,txCatatan.getText());
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
            String sql ="update akhlak_siswa set id_status=?, nik=?, nama_siswa=?, percaya_diri=?, t_jawab=?, mandiri=?,disiplin=?, catatan=? where kd_akhlak='"+txid.getText()+"'";
            if (txidSts.getText().equals("")||txNmSis.getText().equals("")||txKelas.getText().equals("")) { 
                JOptionPane.showMessageDialog(rootPane, "Maaf kolom tidak boleh kosong!");
                return;
            } else {
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1,txidSts.getText());
            stat.setString(2,String.valueOf(cbNIK.getSelectedItem()));
            stat.setString(3,txNmSis.getText());
            stat.setString(4,String.valueOf(cbPecayaDiri.getSelectedItem()));
            stat.setString(5,String.valueOf(cbT_jawab.getSelectedItem()));
            stat.setString(6,String.valueOf(cbMandiri.getSelectedItem()));
            stat.setString(7,String.valueOf(cbDisiplin.getSelectedItem()));
            stat.setString(8,txCatatan.getText());
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
        if (txidSts.getText().equals("")||txNmSis.getText().equals("")||txKelas.getText().equals("")) { 
                JOptionPane.showMessageDialog(rootPane, "Maaf kolom tidak boleh kosong!");
                return;
        } else {
            int ok = JOptionPane.showConfirmDialog(rootPane, "Anda yakin ingin menghapus data?","Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql = "delete from akhlak_siswa where kd_akhlak='"+txid.getText()+"'";
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
        int row=tbKepri.getRowCount();
        for (int x=0;x<row;x++){
            tabmode.removeRow(0);
        }
        try{
            ResultSet rs=conn.createStatement().executeQuery("Select * from "
                    + "akhlak_siswa where nama_siswa like '%"+txCari.getText()+ 
                    "%' or nik like '%"+txCari.getText()+"%' "
                    + "or id_status like '%"+txCari.getText()+"%' ");
            while(rs.next()){
                String data[]={rs.getString(1),rs.getString(2),rs.getString(3)
                        ,rs.getString(4),rs.getString(5),rs.getString(6),
                        rs.getString(7),rs.getString(8)};
                tabmode.addRow(data);
            }
        }catch (SQLException ex){
            Logger.getLogger(Kepribadian.class.getName()).log(Level.SEVERE, null, ex);  
        }
    }

 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btKel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbNIK = new javax.swing.JComboBox();
        txNmSis = new javax.swing.JTextField();
        txKelas = new javax.swing.JTextField();
        txSemester = new javax.swing.JTextField();
        txTA = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cbDisiplin = new javax.swing.JComboBox();
        cbT_jawab = new javax.swing.JComboBox();
        cbMandiri = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        txCatatan = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txid = new javax.swing.JTextField();
        txidSts = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        cbKelas = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cbPecayaDiri = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbKepri = new javax.swing.JTable();
        txCari = new javax.swing.JTextField();
        btCari = new javax.swing.JButton();
        btBatal = new javax.swing.JButton();
        btSimpan = new javax.swing.JButton();
        btHapus = new javax.swing.JButton();
        btUbah1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 153), 2));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel1.add(btKel, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 10, 45, -1));

        jLabel1.setFont(new java.awt.Font("Andalus", 1, 18)); // NOI18N
        jLabel1.setText("Kelola Data Perkembangan Siswa");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)), "input data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(51, 51, 51))); // NOI18N
        jPanel2.setAutoscrolls(true);
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Nik Siswa");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        jLabel3.setText("Nama");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        jLabel4.setText("Kelas");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        jLabel5.setText("Catatan");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, -1, -1));

        jLabel7.setText("Semester");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));

        cbNIK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "- Nik Siswa -" }));
        cbNIK.setToolTipText("Pilih NIk");
        cbNIK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNIKActionPerformed(evt);
            }
        });
        jPanel2.add(cbNIK, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 110, -1));

        txNmSis.setEditable(false);
        jPanel2.add(txNmSis, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 180, -1));

        txKelas.setEditable(false);
        jPanel2.add(txKelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 180, -1));

        txSemester.setEditable(false);
        jPanel2.add(txSemester, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 180, -1));

        txTA.setEditable(false);
        jPanel2.add(txTA, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 210, 180, -1));

        jLabel8.setText("Tahun Ajaran");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        cbDisiplin.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-ISI NILAI-", "A", "B", "C", "D", "E" }));
        cbDisiplin.setToolTipText("Nilai Kerapian");
        jPanel2.add(cbDisiplin, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, 120, -1));

        cbT_jawab.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-ISI NILAI-", "A", "B", "C", "D", "E" }));
        cbT_jawab.setToolTipText("Nilai kerajinan");
        jPanel2.add(cbT_jawab, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 270, 120, -1));

        cbMandiri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-ISI NILAI-", "A", "B", "C", "D", "E" }));
        cbMandiri.setToolTipText("Nilai Kelakuan");
        jPanel2.add(cbMandiri, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, 120, -1));

        txCatatan.setColumns(1);
        txCatatan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txCatatan.setLineWrap(true);
        txCatatan.setRows(10);
        txCatatan.setTabSize(2);
        txCatatan.setToolTipText("catatan siswa");
        txCatatan.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txCatatan);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 360, 180, 90));

        jLabel13.setText("id_akhlak");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel14.setText("id status");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        txid.setEditable(false);
        txid.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.add(txid, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 80, -1));

        txidSts.setEditable(false);
        txidSts.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.add(txidSts, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 80, -1));

        jLabel15.setText("Kelas");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, -1, -1));

        cbKelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "- pilih kelas -" }));
        cbKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbKelasActionPerformed(evt);
            }
        });
        jPanel2.add(cbKelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 50, 100, -1));

        jLabel19.setText("tanggang jawab ");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, -1, -1));

        jLabel17.setText("mandiri");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, -1, -1));

        jLabel18.setText("disiplin ");
        jPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, -1, -1));

        jLabel16.setText("percaya diri");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        cbPecayaDiri.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-ISI NILAI-", "A", "B", "C", "D", "E" }));
        jPanel2.add(cbPecayaDiri, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, 120, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 50, 320, 470));

        tbKepri.setModel(new javax.swing.table.DefaultTableModel(
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
        tbKepri.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKepriMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbKepri);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 610, 350));

        txCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txCariActionPerformed(evt);
            }
        });
        txCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txCariKeyPressed(evt);
            }
        });
        jPanel1.add(txCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 190, -1));

        btCari.setText("cari");
        btCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCariActionPerformed(evt);
            }
        });
        jPanel1.add(btCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, 60, -1));

        btBatal.setText("Batal");
        btBatal.setToolTipText("Batal");
        btBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBatalActionPerformed(evt);
            }
        });
        jPanel1.add(btBatal, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 540, -1, -1));

        btSimpan.setText("Simpan");
        btSimpan.setToolTipText("Simpan");
        btSimpan.setMargin(new java.awt.Insets(2, 10, 2, 10));
        btSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSimpanActionPerformed(evt);
            }
        });
        jPanel1.add(btSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 540, -1, -1));

        btHapus.setText("Hapus");
        btHapus.setToolTipText("Hapus");
        btHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHapusActionPerformed(evt);
            }
        });
        jPanel1.add(btHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 540, -1, -1));

        btUbah1.setText("Ubah");
        btUbah1.setToolTipText("Ubah");
        btUbah1.setMargin(new java.awt.Insets(2, 15, 2, 15));
        btUbah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btUbah1ActionPerformed(evt);
            }
        });
        jPanel1.add(btUbah1, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 540, -1, -1));
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 510, -1, -1));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pendaftaran beranda.png"))); // NOI18N
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -120, 1060, 960));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, 600));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btKelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btKelActionPerformed
        // keluar
        dispose();
    }//GEN-LAST:event_btKelActionPerformed

    int x, y;
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

    private void tbKepriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKepriMouseClicked
        // tabel klik
        TabelKlik();
        SaveUnAktif();
    }//GEN-LAST:event_tbKepriMouseClicked

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
        String ids,vnm,vkls,vsms,vta ;
        try {
            Statement st;
            ResultSet rs;
            String sql ="SELECT * FROM status_siswa where nik='"+vnis+"'";
            st=conn.createStatement();
            rs=st.executeQuery(sql);
            while(rs.next()){
                ids = rs.getString("id_status");
                vnm = rs.getString("nama_siswa");
                vkls = rs.getString("kelas");
                vsms = rs.getString("semester");
                vta = rs.getString("t_ajaran");
                txidSts.setText(ids);
                txNmSis.setText(vnm);
                txKelas.setText(vkls);
                txSemester.setText(vsms);
                txTA.setText(vta);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_cbNIKActionPerformed

    private void cbKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbKelasActionPerformed
        // cb kelas
        try {
            for(int i=cbNIK.getItemCount()-1;i>0;i++){
                cbNIK.removeItemAt(i);
            }
        } catch (Exception e) {
        }
        isiComboNIS();
        AllAktif();
    }//GEN-LAST:event_cbKelasActionPerformed

    private void txCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txCariActionPerformed


    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Kepribadian().setVisible(true);
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
    private javax.swing.JComboBox cbDisiplin;
    private javax.swing.JComboBox cbKelas;
    private javax.swing.JComboBox cbMandiri;
    private javax.swing.JComboBox cbNIK;
    private javax.swing.JComboBox<String> cbPecayaDiri;
    private javax.swing.JComboBox cbT_jawab;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbKepri;
    private javax.swing.JTextField txCari;
    private javax.swing.JTextArea txCatatan;
    private javax.swing.JTextField txKelas;
    private javax.swing.JTextField txNmSis;
    private javax.swing.JTextField txSemester;
    private javax.swing.JTextField txTA;
    private javax.swing.JTextField txid;
    private javax.swing.JTextField txidSts;
    // End of variables declaration//GEN-END:variables
}
