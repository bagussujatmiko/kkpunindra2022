/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package method;

import entitas.Entitas_TA;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class TA_Method {
     Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    String sql = null;
    
    public TA_Method(){
        try {
            st = koneksi.getKoneksi().createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan saat Membuat Koneksi pada : \n"+e);
        }
    }
    
    public List tampil(){
        List logData = new ArrayList();
        sql = "select * from tahun_ajaran order by kd_t_ajaran";
        try {
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Entitas_TA eb = new Entitas_TA();
                eb.setKd_t_ajaran(rs.getString("kd_t_ajaran"));
                eb.setT_ajar(rs.getString("t_ajar"));
                logData.add(eb);
            }
        } catch (Exception e) {
            Logger.getLogger(TA_Method.class.getName()).log(Level.SEVERE,null,e);            
        }
        return logData;
    }
    
    public int tambah(Entitas_TA eb){
        sql = "insert into tahun_ajaran (kd_t_ajaran, t_ajar) values ('"+eb.getKd_t_ajaran()
                +"', '"+eb.getT_ajar()+"')";
        int hasil = 0;
        try {
            hasil = st.executeUpdate(sql);
        } catch (Exception e) {
            Logger.getLogger(TA_Method.class.getName()).log(Level.SEVERE,null,e);
        }
        return hasil;
    }
    
    public int hapus(Entitas_TA eb){
        sql = "delete from tahun_ajaran where kd_t_ajaran = '"+eb.getKd_t_ajaran()+"'";
        int hasil = 0;
        try {
            hasil = st.executeUpdate(sql);
        } catch (Exception e) {
            Logger.getLogger(TA_Method.class.getName()).log(Level.SEVERE,null,e);
        }
        return hasil;
    }
    
    public int edit(Entitas_TA eb){
        sql = "update tahun_ajaran set t_ajar = '"+eb.getT_ajar()
                +"' where kd_t_ajaran = '"+eb.getKd_t_ajaran()+"'";
        int hasil = 0;
        try {
            hasil = st.executeUpdate(sql);
        } catch (Exception e) {
            Logger.getLogger(TA_Method.class.getName()).log(Level.SEVERE,null,e);
        }
        return hasil;
    }
    
    public List cari(String caridata){
        List logData = new ArrayList();
        sql = "select * from tahun_ajaran where kd_t_ajaran like '%"+caridata+"%' or t_ajar like '%"+caridata+"%' ";
        try {
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Entitas_TA eb = new Entitas_TA();
                eb.setKd_t_ajaran(rs.getString("kd_t_ajaran"));
                eb.setT_ajar(rs.getString("t_ajar"));
                logData.add(eb);
            }
        } catch (Exception e) {
            Logger.getLogger(TA_Method.class.getName()).log(Level.SEVERE,null,e);            
        }
        return logData;
    }
    public String carikode(String caridata){
        List logData = new ArrayList();
        String kode = null;
        sql = "select * from tahun_ajaran where t_ajar = '"+caridata+"' ";
        try {
            rs = st.executeQuery(sql);
            while (rs.next()) {
                
                kode=(rs.getString("kd_t_ajaran"));
            }
        } catch (Exception e) {
            Logger.getLogger(TA_Method.class.getName()).log(Level.SEVERE,null,e);            
        }
        return kode;
    }
}
