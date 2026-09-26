package dao;

import model.Produto;
import util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe DAO para a entidade Produto.
 */
public class ProdutoDAO {

    public boolean inserir(Produto produto) {
        String sql = "INSERT INTO produto (nome, descricao, preco, tamanho, quantidade_estoque, cor, id_marca, id_categoria) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setBigDecimal(3, produto.getPreco());
            stmt.setString(4, produto.getTamanho());
            stmt.setInt(5, produto.getQuantidadeEstoque());
            stmt.setString(6, produto.getCor());
            stmt.setInt(7, produto.getIdMarca());
            stmt.setInt(8, produto.getIdCategoria());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir produto: " + e.getMessage());
            return false;
        }
    }

    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM produto WHERE id_produto = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Produto produto = new Produto();
                    produto.setIdProduto(rs.getInt("id_produto"));
                    produto.setNome(rs.getString("nome"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setPreco(rs.getBigDecimal("preco"));
                    produto.setTamanho(rs.getString("tamanho"));
                    produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                    produto.setCor(rs.getString("cor"));
                    produto.setIdMarca(rs.getInt("id_marca"));
                    produto.setIdCategoria(rs.getInt("id_categoria"));
                    return produto;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar produto por ID: " + e.getMessage());
        }
        return null;
    }

    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setIdProduto(rs.getInt("id_produto"));
                produto.setNome(rs.getString("nome"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getBigDecimal("preco"));
                produto.setTamanho(rs.getString("tamanho"));
                produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                produto.setCor(rs.getString("cor"));
                produto.setIdMarca(rs.getInt("id_marca"));
                produto.setIdCategoria(rs.getInt("id_categoria"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }
        return produtos;
    }

    public boolean atualizar(Produto produto) {
        String sql = "UPDATE produto SET nome = ?, descricao = ?, preco = ?, tamanho = ?, quantidade_estoque = ?, cor = ?, id_marca = ?, id_categoria = ? WHERE id_produto = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setBigDecimal(3, produto.getPreco());
            stmt.setString(4, produto.getTamanho());
            stmt.setInt(5, produto.getQuantidadeEstoque());
            stmt.setString(6, produto.getCor());
            stmt.setInt(7, produto.getIdMarca());
            stmt.setInt(8, produto.getIdCategoria());
            stmt.setInt(9, produto.getIdProduto());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM produto WHERE id_produto = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir produto: " + e.getMessage());
            return false;
        }
    }

    public void atualizarEstoque(int idProduto, int novaQuantidade, Connection conn) throws SQLException {
        String sql = "UPDATE produto SET quantidade_estoque = ? WHERE id_produto = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, novaQuantidade);
            stmt.setInt(2, idProduto);
            stmt.executeUpdate();
        }
    }
}
