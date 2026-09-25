package dao;

import model.ItemCompra;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO para a entidade ItemCompra.
 */
public class ItemCompraDAO {

    public void inserir(ItemCompra item, Connection conn) throws SQLException {
        String sql = "INSERT INTO item_compra (id_compra, id_produto, qtd_comprada, preco_unitario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getIdCompra());
            stmt.setInt(2, item.getIdProduto());
            stmt.setInt(3, item.getQtdComprada());
            stmt.setBigDecimal(4, item.getPrecoUnitario());
            stmt.executeUpdate();
        }
    }

    public List<ItemCompra> listarPorCompra(int idCompra) {
        List<ItemCompra> itens = new ArrayList<>();
        String sql = "SELECT * FROM item_compra WHERE id_compra = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCompra);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ItemCompra item = new ItemCompra();
                    item.setIdItemCompra(rs.getInt("id_item_compra"));
                    item.setIdCompra(rs.getInt("id_compra"));
                    item.setIdProduto(rs.getInt("id_produto"));
                    item.setQtdComprada(rs.getInt("qtd_comprada"));
                    item.setPrecoUnitario(rs.getBigDecimal("preco_unitario"));
                    itens.add(item);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar itens por compra: " + e.getMessage());
        }
        return itens;
    }

    public void excluirPorCompra(int idCompra) {
        String sql = "DELETE FROM item_compra WHERE id_compra = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCompra);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir itens da compra: " + e.getMessage());
        }
    }
}
