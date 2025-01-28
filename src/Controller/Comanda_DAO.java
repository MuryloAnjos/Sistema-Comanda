
package Controller;

import Connection.ConnectionFactory;
import Model.comanda;
import Model.produto;
import static View.Comanda_GUI.nummesa_txt;
import static View.Comanda_GUI.status_txt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public  class Comanda_DAO {
    
    public void create(comanda c){
        
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
       
        try {
            
            stmt = con.prepareStatement("SELECT num_mesa, status FROM comanda WHERE num_mesa = ? AND status = 'aberta'");
            stmt.setInt(1, c.getNum_mesa());
            rs = stmt.executeQuery();
            
            
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Esta mesa está ocupada no momento");
            }else {
                
            stmt = con.prepareStatement("INSERT INTO comanda (num_mesa) VALUES(?)");
            stmt.setInt(1, c.getNum_mesa());
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Comanda Criada");
            }
     
        } catch (SQLException ex) {
            Logger.getLogger(Comanda_DAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao Salvar" + ex);
        }finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
        
    }
    
    
    public List<comanda> read(){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt= null;
        ResultSet rs = null;
        
        List<comanda> comandas = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM comanda");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                comanda cmd = new comanda();
                cmd.setId(rs.getInt("id"));
                cmd.setStatus(rs.getString("status"));
                cmd.setNum_mesa(rs.getInt("num_mesa"));
                cmd.setValor_total(rs.getDouble("valor_total"));
//                cmd.setValor_total(rs.getDouble("valor_total"));
                comandas.add(cmd);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Comanda_DAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return comandas;
        
    }
    
    
    public void consul(comanda c) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT status, num_mesa FROM comanda WHERE id = ?");
            stmt.setInt(1, c.getId());
            rs = stmt.executeQuery();

            String status = "";
            int num_mesa = 0;

            while (rs.next()) {
                status = (rs.getString("status"));
                num_mesa = rs.getInt("num_mesa");
            }

            status_txt.setText(status);
            nummesa_txt.setText(String.valueOf(num_mesa));


        } catch (SQLException ex) {
            Logger.getLogger(Comanda_DAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

    }

    public void update(comanda c) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE comanda SET num_mesa = ?, status = ? WHERE id = ?");
            stmt.setInt(1, c.getNum_mesa());
            stmt.setString(2, c.getStatus());
            stmt.setInt(3, c.getId());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Comanda Atualizada");
        } catch (SQLException ex) {
            Logger.getLogger(Comanda_DAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao Atualizar" + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

    public void delete(comanda c) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM comanda WHERE id = ?");
            stmt.setInt(1, c.getId());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Comanda Deletada");
        } catch (SQLException ex) {
            Logger.getLogger(Comanda_DAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao Excluir" + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

        
    
    
}
