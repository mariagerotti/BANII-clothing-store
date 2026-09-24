package dao;

import model.Venda;
import util.ConnectionFactory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO para a entidade Venda.
 */
public class VendaDAO {

    public void inserir(Venda venda) {
        String sql = "INSERT INTO venda (data_venda, valor_total, id_cliente) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(venda.getDataVenda()));
            stmt.setBigDecimal(2, venda.getValorTotal());
            stmt.setInt(3, venda.getIdCliente());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir venda: " + e.getMessage());
        }
    }

    public Venda buscarPorId(int id) {
        String sql = "SELECT * FROM venda WHERE id_venda = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Venda venda = new Venda();
                    venda.setIdVenda(rs.getInt("id_venda"));
                    venda.setDataVenda(rs.getDate("data_venda").toLocalDate());
                    venda.setValorTotal(rs.getBigDecimal("valor_total"));
                    venda.setIdCliente(rs.getInt("id_cliente"));
                    return venda;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar venda por ID: " + e.getMessage());
        }
        return null;
    }

    public List<Venda> listarTodos() {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT * FROM venda";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Venda venda = new Venda();
                venda.setIdVenda(rs.getInt("id_venda"));
                venda.setDataVenda(rs.getDate("data_venda").toLocalDate());
                venda.setValorTotal(rs.getBigDecimal("valor_total"));
                venda.setIdCliente(rs.getInt("id_cliente"));
                vendas.add(venda);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar vendas: " + e.getMessage());
        }
        return vendas;
    }

    public void atualizar(Venda venda) {
        String sql = "UPDATE venda SET data_venda = ?, valor_total = ?, id_cliente = ? WHERE id_venda = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(venda.getDataVenda()));
            stmt.setBigDecimal(2, venda.getValorTotal());
            stmt.setInt(3, venda.getIdCliente());
            stmt.setInt(4, venda.getIdVenda());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar venda: " + e.getMessage());
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM venda WHERE id_venda = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir venda: " + e.getMessage());
        }
    }

    public int inserir(Venda venda, Connection conn) throws SQLException {
        String sql = "INSERT INTO venda (data_venda, valor_total, id_cliente) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, Date.valueOf(venda.getDataVenda()));
            stmt.setBigDecimal(2, venda.getValorTotal());
            stmt.setInt(3, venda.getIdCliente());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Falha ao criar venda, nenhum ID obtido.");
    }

    public void atualizarValorTotal(int idVenda, BigDecimal valorTotal, Connection conn) throws SQLException {
        String sql = "UPDATE venda SET valor_total = ? WHERE id_venda = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, valorTotal);
            stmt.setInt(2, idVenda);
            stmt.executeUpdate();
        }
    }
}
