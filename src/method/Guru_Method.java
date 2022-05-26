/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package method;

import entitas.Entitas_Guru;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class Guru_Method {
      Connection con = null;
    Statement st = null;
    ResultSet rs = null;
    String sql = null;
    
      
    
    public  Guru_Method(){
        try {
            st = koneksi.getKoneksi().createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan saat Membuat Koneksi pada : \n"+e);
        }
    }
    
    public List tampil(){
        List logData = new ArrayList();
        sql = "select * from tabel_guru order by nip asc";
        try {
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Entitas_Guru eg = new Entitas_Guru();
                eg.setNip(rs.getString("nip"));
                eg.setNama_guru(rs.getString("nama_guru"));
                eg.setTemp_lahir(rs.getString("temp_lahir"));
                eg.setTgl_lahir(rs.getString("tanggal_lahir"));
                eg.setJ_kelamin(rs.getString("j_kelamin"));
                eg.setJabatan(rs.getString("jabatan"));
                eg.setAlamat(rs.getString("alamat"));
                eg.setTelephone(rs.getString("telephone"));
                eg.setUsername(rs.getString("username"));
                eg.setPassword(rs.getString("password"));
                logData.add(eg);
            }
        } catch (Exception e) {
            Logger.getLogger(Guru_Method.class.getName()).log(Level.SEVERE,null,e);            
        }
        return logData;
    }
    
    public int tambah(Entitas_Guru eg){
        sql = "insert into tabel_guru (nip, nama_guru, temp_lahir,tanggal_lahir,j_kelamin, jabatan, alamat, telephone, username, password) values ('"+eg.getNip()
                +"', '"+eg.getNama_guru()+"', '"+eg.getTemp_lahir()+"' , '"+eg.getTgl_lahir()+"', '"+eg.getJ_kelamin()+"','"+eg.getJabatan()
                +"', '"+eg.getAlamat()+"', '"+eg.getTelephone()+"',  '"+eg.getUsername()+"', '"+eg.getPassword()+"')";
        int hasil = 0;
        try {
            hasil = st.executeUpdate(sql);
        } catch (Exception e) {
            Logger.getLogger(Guru_Method.class.getName()).log(Level.SEVERE,null,e);
        }
        return hasil;
    }
    
    public int hapus(Entitas_Guru eb){
        sql = "delete from guru where nip = '"+eb.getNip()+"'";
        int hasil = 0;
        try {
            hasil = st.executeUpdate(sql);
        } catch (Exception e) {
            Logger.getLogger(Guru_Method.class.getName()).log(Level.SEVERE,null,e);
        }
        return hasil;
    }
    
    public int edit(Entitas_Guru eg){
        sql = "update tabel_guru set nama_guru = '"+eg.getNama_guru()+"',temp_lahir = '"+eg.getTemp_lahir()+"' , tanggal_lahir = '"+eg.getTgl_lahir()+"', j_kelamin ='"+eg.getJ_kelamin()+"',jabatan='"+eg.getJabatan()
                +"', alamat = '"+eg.getAlamat()+"', telephone = '"+eg.getTelephone()+"', username =  '"+eg.getUsername()+"', password = '"+eg.getPassword()+"' where nip = '"+eg.getNip()+"'";
        int hasil = 0;
        try {
            hasil = st.executeUpdate(sql);
        } catch (SQLException e) {
            Logger.getLogger(Guru_Method.class.getName()).log(Level.SEVERE,null,e);
        }
        return hasil;
    }
    
    public List cari(String caridata){
        List logData = new ArrayList();
        sql = "select * from tabel_guru where nip like '%"+caridata+"%' order by nip asc";
        try {
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Entitas_Guru eb = new Entitas_Guru();
                eb.setNip(rs.getString("nip"));
                eb.setNama_guru(rs.getString("nama_guru"));
                eb.setTemp_lahir(rs.getString("temp_lahir"));
                eb.setTgl_lahir(rs.getString("tanggal_lahir"));
                eb.setJ_kelamin(rs.getString("j_kelamin"));
                eb.setJabatan(rs.getString("Jabatan"));
                eb.setAlamat(rs.getString("alamat"));
                eb.setTelephone(rs.getString("telephone"));
                eb.setUsername(rs.getString("username"));
                eb.setPassword(rs.getString("password"));
                logData.add(eb);
            }
        } catch (Exception e) {
            Logger.getLogger(Guru_Method.class.getName()).log(Level.SEVERE,null,e);            
        }
        return logData;
    }
    
    public Entitas_Guru cariLogin(String username,String password){
        List logLogin = new ArrayList();
        int result;
        sql="select username, password,nip,nama_guru from tabel_guru where username='"+username+"' and password='"+password+"'";
         Entitas_Guru eg = new Entitas_Guru();
        try{
            rs=st.executeQuery(sql);
            while(rs.next()){
               
                eg.setUsername(rs.getString("username"));
                eg.setPassword(rs.getString("password"));
                eg.setNama_guru(rs.getString("nama_guru"));
                eg.setNip(rs.getString("nip"));
                logLogin.add(eg);
            }
        } catch(SQLException a){
        }
        return eg;
    }
    public Entitas_Guru cariuser(String nip){
        List logLogin = new ArrayList();
        int result;
        sql="select username, password,nip,nama_guru from tabel_guru where nip='"+nip+"'";
         Entitas_Guru eg = new Entitas_Guru();
        try{
            rs=st.executeQuery(sql);
            while(rs.next()){
               
                eg.setUsername(rs.getString("username"));
                eg.setPassword(rs.getString("password"));
                eg.setNama_guru(rs.getString("nama_guru"));
                eg.setNip(rs.getString("nip"));
                logLogin.add(eg);
            }
        } catch(SQLException a){
        }
        return eg;
    }
    
    public List tampilIdUser(String username){
    List logNamaUser = new ArrayList();
    sql="select nip from tabel_guru where username='"+username+"'";
    try{
        rs=st.executeQuery(sql);        
        while(rs.next()){
            Entitas_Guru es = new Entitas_Guru();
            es.setNip(rs.getString("nip"));
            logNamaUser.add(es);            
       }
    } catch (SQLException a){
        JOptionPane.showMessageDialog(null,"Terjadi kesalahan tampil, pada : \n"+a);
    }
    return logNamaUser;
    }
}
