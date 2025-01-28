/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Connection.ConnectionFactory;
import Model.comanda;
import Model.func;
import static View.Cadastro_GUI.nome_txt;
import static View.Login_GUI.senha_txt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class func_DAO {
    
    public void cadastrar(func f){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
       
        try {
            
            stmt = con.prepareStatement("SELECT nome_usu FROM func WHERE nome_usu = ?");
            stmt.setString(1, f.getNome());
            rs = stmt.executeQuery();
            
            
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Já existe um funcionário cadastrado com este nome.");
            }else {
                
            stmt = con.prepareStatement("INSERT INTO func (nome_usu, email, senha, telefone) VALUES(?, ?, ?, ?)");
            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getEmail());
            stmt.setString(3, f.getSenha());
            stmt.setString(4, f.getTelefone());
            
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Funcionário Cadastrado");
        }
     
        } catch (SQLException ex) {
            Logger.getLogger(Comanda_DAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao Cadastrar" + ex);
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
    }
    
    public boolean check(String logs, String sen){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt= null;
        ResultSet rs = null;

        boolean log = false;
        
        try {
            stmt = con.prepareStatement("SELECT * FROM func WHERE nome_usu = ? and senha = ?");
            stmt.setString(1, logs);
            stmt.setString(2, sen);            
            rs = stmt.executeQuery();
            
            if(rs.next()){
               log = true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Comanda_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return log;
        
    }
    

    
}
