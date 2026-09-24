package dao;

import model.ItemVenda;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO para a entidade ItemVenda.
 */
public class ItemVendaDAO {

    public void inserir(ItemVenda item, Connection conn) throws SQLException {
        String sql = "INSERT INTO item_venda (id_venda, id_produto, qtd_vendida, preco_unitario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getIdVenda());
            stmt.setInt(2, item.getIdProduto());
            stmt.setInt(3, item.getQtdVendida());
            stmt.setBigDecimal(4, item.getPrecoUnitario());
            stmt.executeUpdate();
        }
    }

    public List<ItemVenda> listarPorVenda(int idVenda) {
        List<ItemVenda> itens = new ArrayList<>();
        String sql = "SELECT * FROM item_venda WHERE id_venda = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idVenda);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ItemVenda item = new ItemVenda();
                    item.setIdItemVenda(rs.getInt("id_item_venda"));
                    item.setIdVenda(rs.getInt("id_venda"));
                    item.setIdProduto(rs.getInt("id_produto"));
                    item.setQtdVendida(rs.getInt("qtd_vendida"));
                    item.setPrecoUnitario(rs.getBigDecimal("preco_unitario"));
                    itens.add(item);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar itens por venda: " + e.getMessage());
        }
        return itens;
    }

    public void excluirPorVenda(int idVenda) {
        String sql = "DELETE FROM item_venda WHERE id_venda = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idVenda);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir itens da venda: " + e.getMessage());
        }
    }
}
