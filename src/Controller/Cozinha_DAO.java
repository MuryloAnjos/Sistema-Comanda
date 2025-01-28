package Controller;

import Connection.ConnectionFactory;
import Model.Cozinha;
import Model.PedidoProduto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Cozinha_DAO {
    
    public List<Cozinha> read() {
    Connection con = ConnectionFactory.getConnection();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    List<Cozinha> cz = new ArrayList<>();

    try {
        stmt = con.prepareStatement(
            "SELECT " +
            "    pe.id AS pdd_id, " +
            "    pr.nome AS pdt_nome, " +
            "    pe.quantidade AS qtd_pedido, " +
            "    pe.obs AS observacao, " +
            "    cm.num_mesa AS num_mesa, " +
            "    cm.id AS cma_id " +
            "FROM pedido pe " +
            "JOIN produto pr ON pe.pdt_id = pr.id " +
            "JOIN comanda cm ON pe.cma_id = cm.id"
        );
        rs = stmt.executeQuery();

        while (rs.next()) {
//            
            Cozinha cz1 = new Cozinha();
                cz1.setPddId(rs.getInt("pdd_id"));
                cz1.setPdtNome(rs.getString("pdt_nome"));
                cz1.setPdtQtd(rs.getInt("qtd_pedido"));
                cz1.setPddObs(rs.getString("observacao"));
                cz1.setNum_mesa(rs.getInt("num_mesa"));
                cz1.setCmaId(rs.getInt("cma_id"));
                cz.add(cz1);
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        ConnectionFactory.closeConnection(con, stmt, rs);
    }

    return cz;
}
    
//     public void setarStatus(pedido p) {
//
//        Caixa rel = new Caixa();
//        Connection con = ConnectionFactory.getConnection();
//        PreparedStatement stmt = null;
//        ResultSet rs = null;
//
//        try {
//
//            stmt = con.prepareStatement("SELECT status FROM comanda WHERE id = ?");
//            stmt.setInt(1, p.getId());
//            rs = stmt.executeQuery();
//
//            if (rs.next()) {
//                String stts = rs.getString("status");
//
//                if (stts.equalsIgnoreCase("fechada")) {
//                    JOptionPane.showMessageDialog(null, "Comanda já foi fechada.");
//                    return;
//                }
//            }
//            stmt = con.prepareStatement("UPDATE comanda SET status = ? WHERE id = ?");
//            stmt.setDouble(1, p.getValor_total());
//            stmt.setString(2, p.getStatus());
//            stmt.setInt(3, p.getId());
//
//            stmt.executeUpdate();
//            JOptionPane.showMessageDialog(null, "Comanda " + c.getId() + " fechada com sucesso.");
//            
//
//        } catch (SQLException ex) {
//            Logger.getLogger(Comanda_DAO.class.getName()).log(Level.SEVERE, null, ex);
//            JOptionPane.showMessageDialog(null, "Erro ao Atualizar" + ex);
//        } finally {
//            ConnectionFactory.closeConnection(con, stmt, rs);
//        }
//    }
//    
}
