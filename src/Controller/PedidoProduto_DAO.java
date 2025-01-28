package Controller;

import Connection.ConnectionFactory;
import Model.PedidoProduto;
import Model.comanda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PedidoProduto_DAO {

    public List<PedidoProduto> read() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<PedidoProduto> pedidoProduto = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT pe.id AS pedido_id, pr.nome AS produto_nome, pr.preco AS produto_preco, pe.quantidade, pe.cma_id, pr.id AS pdt_id "
                    + "FROM pedido pe "
                    + "JOIN produto pr ON pe.pdt_id = pr.id");
            rs = stmt.executeQuery();

            while (rs.next()) {
                PedidoProduto pdspdt = new PedidoProduto();
                pdspdt.setPddId(rs.getInt("pedido_id"));
                pdspdt.setPdtNome(rs.getString("produto_nome"));
                pdspdt.setPdtPreco(rs.getDouble("produto_preco"));
                pdspdt.setQuantidade(rs.getInt("quantidade"));
                pdspdt.setCmaId(rs.getInt("cma_id"));
                pdspdt.setPdtId(rs.getInt("pdt_id"));
                pedidoProduto.add(pdspdt);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PedidoProduto_DAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return pedidoProduto;

    }}
