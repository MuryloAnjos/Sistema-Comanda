package Controller;

import Connection.ConnectionFactory;
import Model.produto;
import static View.Cardapio_GUI.idpdt_txt;
import static View.Cardapio_GUI.nomepdt_txt;
import static View.Cardapio_GUI.precopdt_txt;
import static View.Cardapio_GUI.produtos_table;
import static View.Cardapio_GUI.tipopdt_txt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Produto_DAO {

    public void create(produto p) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO produto (nome, preco, tipo) VALUES(?, ?, ?)");
            stmt.setString(1, p.getNome());
            stmt.setDouble(2, p.getPreco());
            stmt.setString(3, p.getTipo());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto Adicionado");
        } catch (SQLException ex) {
            Logger.getLogger(Produto_DAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao Salvar" + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

    public List<produto> read() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<produto> produtos = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM produto");
            rs = stmt.executeQuery();

            while (rs.next()) {
                produto produto = new produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setTipo(rs.getString("tipo"));
                produtos.add(produto);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Produto_DAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return produtos;

    }

    public void consul(produto p) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT nome, preco, tipo FROM produto WHERE id = ?");
            stmt.setInt(1, p.getId());
            rs = stmt.executeQuery();

            String tipo = "";
            String nome = "";
            double preco = 0;

            while (rs.next()) {
                nome = (rs.getString("nome"));
                preco = rs.getDouble("preco");
                tipo = (rs.getString("tipo"));
            }

            nomepdt_txt.setText(nome);
            precopdt_txt.setText(String.valueOf(preco));
            tipopdt_txt.setText(tipo);
            
//            int id = Integer.parseInt(idpdt_txt.getText());
//            
//            produtos_table.setRowSelectionAllowed(true);
//            for(int k = 0; k < produtos_table.getRowCount(); k++){
//                
//                if(id == Integer.parseInt(produtos_table.getValueAt(k, 0).toString())){
//                    produtos_table.setRowSelectionInterval(k ,k);
//                }
//                
//            }

        } catch (SQLException ex) {
            Logger.getLogger(Produto_DAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

    }

    public void update(produto p) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE produto SET nome = ?, preco = ?, tipo = ? WHERE id = ?");
            stmt.setString(1, p.getNome());
            stmt.setDouble(2, p.getPreco());
            stmt.setString(3, p.getTipo());
            stmt.setInt(4, p.getId());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto Atualizado");
        } catch (SQLException ex) {
            Logger.getLogger(Produto_DAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao Atualizar" + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

    public void delete(produto p) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM produto WHERE id = ?");
            stmt.setInt(1, p.getId());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto Deletado");
        } catch (SQLException ex) {
            Logger.getLogger(Produto_DAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao Excluir" + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

}
