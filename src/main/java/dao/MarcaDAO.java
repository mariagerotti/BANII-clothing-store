package dao;

import model.Marca;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO para a entidade Marca.
 */
public class MarcaDAO {

    public boolean inserir(Marca marca) {
        String sql = "INSERT INTO marca (nome) VALUES (?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, marca.getNome());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir marca: " + e.getMessage());
            return false;
        }
    }

    public Marca buscarPorId(int id) {
        String sql = "SELECT * FROM marca WHERE id_marca = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Marca marca = new Marca();
                    marca.setIdMarca(rs.getInt("id_marca"));
                    marca.setNome(rs.getString("nome"));
                    return marca;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar marca por ID: " + e.getMessage());
        }
        return null;
    }

    public List<Marca> listarTodos() {
        List<Marca> marcas = new ArrayList<>();
        String sql = "SELECT * FROM marca";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Marca marca = new Marca();
                marca.setIdMarca(rs.getInt("id_marca"));
                marca.setNome(rs.getString("nome"));
                marcas.add(marca);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar marcas: " + e.getMessage());
        }
        return marcas;
    }

    public boolean atualizar(Marca marca) {
        String sql = "UPDATE marca SET nome = ? WHERE id_marca = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, marca.getNome());
            stmt.setInt(2, marca.getIdMarca());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar marca: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM marca WHERE id_marca = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir marca: " + e.getMessage());
            return false;
        }
    }
}
