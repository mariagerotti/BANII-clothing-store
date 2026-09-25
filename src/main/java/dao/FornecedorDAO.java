package dao;

import model.Fornecedor;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO para a entidade Fornecedor.
 */
public class FornecedorDAO {

    public void inserir(Fornecedor fornecedor) {
        String sql = "INSERT INTO fornecedor (nome, cnpj, telefone, email, endereco) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getCnpj());
            stmt.setString(3, fornecedor.getTelefone());
            stmt.setString(4, fornecedor.getEmail());
            stmt.setString(5, fornecedor.getEndereco());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir fornecedor: " + e.getMessage());
        }
    }

    public Fornecedor buscarPorId(int id) {
        String sql = "SELECT * FROM fornecedor WHERE id_fornecedor = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Fornecedor fornecedor = new Fornecedor();
                    fornecedor.setIdFornecedor(rs.getInt("id_fornecedor"));
                    fornecedor.setNome(rs.getString("nome"));
                    fornecedor.setCnpj(rs.getString("cnpj"));
                    fornecedor.setTelefone(rs.getString("telefone"));
                    fornecedor.setEmail(rs.getString("email"));
                    fornecedor.setEndereco(rs.getString("endereco"));
                    return fornecedor;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar fornecedor por ID: " + e.getMessage());
        }
        return null;
    }

    public List<Fornecedor> listarTodos() {
        List<Fornecedor> fornecedores = new ArrayList<>();
        String sql = "SELECT * FROM fornecedor";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Fornecedor fornecedor = new Fornecedor();
                fornecedor.setIdFornecedor(rs.getInt("id_fornecedor"));
                fornecedor.setNome(rs.getString("nome"));
                fornecedor.setCnpj(rs.getString("cnpj"));
                fornecedor.setTelefone(rs.getString("telefone"));
                fornecedor.setEmail(rs.getString("email"));
                fornecedor.setEndereco(rs.getString("endereco"));
                fornecedores.add(fornecedor);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar fornecedores: " + e.getMessage());
        }
        return fornecedores;
    }

    public void atualizar(Fornecedor fornecedor) {
        String sql = "UPDATE fornecedor SET nome = ?, cnpj = ?, telefone = ?, email = ?, endereco = ? WHERE id_fornecedor = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fornecedor.getNome());
            stmt.setString(2, fornecedor.getCnpj());
            stmt.setString(3, fornecedor.getTelefone());
            stmt.setString(4, fornecedor.getEmail());
            stmt.setString(5, fornecedor.getEndereco());
            stmt.setInt(6, fornecedor.getIdFornecedor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar fornecedor: " + e.getMessage());
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM fornecedor WHERE id_fornecedor = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir fornecedor: " + e.getMessage());
        }
    }
}
