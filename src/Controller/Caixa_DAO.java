package Controller;

import Connection.ConnectionFactory;
import Model.Caixa;
import Model.comanda;
import Model.produto;
import View.Caixa_GUI;
import static View.Caixa_GUI.pdds_table;
import static View.Caixa_GUI.statuscx_txt;
import static View.Pedido_GUI.nomepdd_txt;
import static View.Pedido_GUI.precopdd_txt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Caixa_DAO {

    public void pedidosComanda(comanda c) {

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(
                    "SELECT "
                    + "    pe.id AS pdd_id, "
                    + "    pr.nome AS pdt_nome, "
                    + "    pe.quantidade AS qtd_pedido, "
                    + "    pr.preco AS pdt_preco, "
                    + "    pe.obs AS observacao, "
                    + "    cm.num_mesa AS num_mesa, "
                    + "    cm.id AS cma_id "
                    + "FROM pedido pe "
                    + "JOIN produto pr ON pe.pdt_id = pr.id "
                    + "JOIN comanda cm ON pe.cma_id = cm.id "
                    + "WHERE cm.id = ? "
            );

            stmt.setInt(1, c.getId());
            rs = stmt.executeQuery();

            DefaultTableModel modelo = (DefaultTableModel) pdds_table.getModel();
            modelo.setRowCount(0);//isso aqui serve para remover os os registro anteriores

            while (rs.next()) {
//            
                Object[] linha = {
                    rs.getInt("pdd_id"),
                    rs.getString("pdt_nome"),
                    rs.getInt("qtd_pedido"),
                    rs.getDouble("pdt_preco"),
                    rs.getString("observacao"),
                    rs.getInt("num_mesa"),
                    rs.getInt("cma_id")
                };
                modelo.addRow(linha);
                //o new serve pra colocar um array diretamente no vetor
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

    }

    public void fecharComanda(comanda c) {

        Caixa rel = new Caixa();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = con.prepareStatement("SELECT status FROM comanda WHERE id = ?");
            stmt.setInt(1, c.getId());
            rs = stmt.executeQuery();

            if (rs.next()) {
                String stts = rs.getString("status");

                if (stts.equalsIgnoreCase("fechada")) {
                    JOptionPane.showMessageDialog(null, "Comanda já foi fechada.");
                    return;
                }
            }
            stmt = con.prepareStatement("UPDATE comanda SET valor_total = ?, status = ? WHERE id = ?");
            stmt.setDouble(1, c.getValor_total());
            stmt.setString(2, c.getStatus());
            stmt.setInt(3, c.getId());

            stmt.executeUpdate();
//            JOptionPane.showMessageDialog(null, "Comanda " + c.getId() + " fechada com sucesso.");
            try {
                rel.Relatorio(c);
            } catch (Exception ex) {
                Logger.getLogger(Caixa_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Comanda_DAO.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao Atualizar" + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
    }

    public void consul(comanda c) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement("SELECT status FROM comanda WHERE id = ?");
            stmt.setInt(1, c.getId());
            rs = stmt.executeQuery();

            String status = "";
            double preco = 0;

            while (rs.next()) {
                status = (rs.getString("status"));
            }

            statuscx_txt.setText(status);

        } catch (SQLException ex) {
            Logger.getLogger(Pedido_DAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

    }
    
    public void abrirPdf(){
        
    }

}
