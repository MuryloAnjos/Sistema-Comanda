
package Controller;

import Connection.ConnectionFactory;
import Model.comanda;
import Model.pedido;
import Model.produto;
import static View.Pedido_GUI.nomepdd_txt;
import static View.Pedido_GUI.precopdd_txt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Pedido_DAO {
    
     public void create(pedido p, comanda c) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            
            stmt = con.prepareStatement("SELECT status FROM comanda WHERE id = ? AND status = 'fechada'");
            stmt.setInt(1, c.getId());
            rs = stmt.executeQuery();
            
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Comanda já foi fechada");
            }else {
            
            stmt = con.prepareStatement("INSERT INTO pedido (cma_id, pdt_id, obs, quantidade) VALUES(?, ?, ?, ?)");
            stmt.setInt(1, p.getCma_id());
            stmt.setInt(2, p.getPdt_id());
            stmt.setString(3, p.getObs());
            stmt.setInt(4, p.getQuantidade());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Pedido Adicionado");
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Pedido_DAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao Salvar" + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }
     
     
     public void consul(produto p) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT nome, preco FROM produto WHERE id = ?");
            stmt.setInt(1, p.getId());
            rs = stmt.executeQuery();

            String nome = "";
            double preco = 0;

            while (rs.next()) {
                nome = (rs.getString("nome"));
                preco = rs.getDouble("preco");
            }

            nomepdd_txt.setText(nome);
            precopdd_txt.setText(String.valueOf(preco));


        } catch (SQLException ex) {
            Logger.getLogger(Pedido_DAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

    }

     
     public void update(pedido p) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE pedido SET cma_id = ?, pdt_id = ?, obs = ?, quantidade = ? WHERE id = ?;");
            stmt.setInt(1, p.getCma_id());
            stmt.setInt(2, p.getPdt_id());
            stmt.setString(3, p.getObs());
            stmt.setInt(4, p.getQuantidade());
            stmt.setInt(5, p.getId());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Pedido Atualizado");
        } catch (SQLException ex) {
            Logger.getLogger(Pedido_DAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao Atualizar" + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }
     
      public void delete(pedido p) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM pedido WHERE id = ?");
            stmt.setInt(1, p.getId());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Pedido Deletado");
        } catch (SQLException ex) {
            Logger.getLogger(Pedido_DAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao Excluir" + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }
    
}
