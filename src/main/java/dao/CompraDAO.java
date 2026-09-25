package dao;

import model.Compra;
import util.ConnectionFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO para a entidade Compra.
 */
public class CompraDAO {

    public void inserir(Compra compra) {
        String sql = "INSERT INTO compra (data_compra, valor_total, id_fornecedor) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(compra.getDataCompra()));
            stmt.setBigDecimal(2, compra.getValorTotal());
            stmt.setInt(3, compra.getIdFornecedor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir compra: " + e.getMessage());
        }
    }

    public Compra buscarPorId(int id) {
        String sql = "SELECT * FROM compra WHERE id_compra = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Compra compra = new Compra();
                    compra.setIdCompra(rs.getInt("id_compra"));
                    compra.setDataCompra(rs.getDate("data_compra").toLocalDate());
                    compra.setValorTotal(rs.getBigDecimal("valor_total"));
                    compra.setIdFornecedor(rs.getInt("id_fornecedor"));
                    return compra;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar compra por ID: " + e.getMessage());
        }
        return null;
    }

    public List<Compra> listarTodos() {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT * FROM compra";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Compra compra = new Compra();
                compra.setIdCompra(rs.getInt("id_compra"));
                compra.setDataCompra(rs.getDate("data_compra").toLocalDate());
                compra.setValorTotal(rs.getBigDecimal("valor_total"));
                compra.setIdFornecedor(rs.getInt("id_fornecedor"));
                compras.add(compra);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar compras: " + e.getMessage());
        }
        return compras;
    }

    public void atualizar(Compra compra) {
        String sql = "UPDATE compra SET data_compra = ?, valor_total = ?, id_fornecedor = ? WHERE id_compra = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(compra.getDataCompra()));
            stmt.setBigDecimal(2, compra.getValorTotal());
            stmt.setInt(3, compra.getIdFornecedor());
            stmt.setInt(4, compra.getIdCompra());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar compra: " + e.getMessage());
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM compra WHERE id_compra = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir compra: " + e.getMessage());
        }
    }

    public int inserir(Compra compra, Connection conn) throws SQLException {
        String sql = "INSERT INTO compra (data_compra, valor_total, id_fornecedor) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, Date.valueOf(compra.getDataCompra()));
            stmt.setBigDecimal(2, compra.getValorTotal());
            stmt.setInt(3, compra.getIdFornecedor());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Falha ao criar compra, nenhum ID obtido.");
    }

    public void atualizarValorTotal(int idCompra, BigDecimal valorTotal, Connection conn) throws SQLException {
        String sql = "UPDATE compra SET valor_total = ? WHERE id_compra = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, valorTotal);
            stmt.setInt(2, idCompra);
            stmt.executeUpdate();
        }
    }
}
