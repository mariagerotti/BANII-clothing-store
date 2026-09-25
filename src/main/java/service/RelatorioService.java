package service;

import dao.CategoriaDAO;
import dao.FornecedorDAO;
import dao.MarcaDAO;
import model.Categoria;
import model.Fornecedor;
import model.Marca;
import util.ConnectionFactory;
import util.InputUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class RelatorioService {

    public void relatorioVendasPorPeriodo() {
        System.out.println("\n--- RELATÓRIO: VENDAS POR PERÍODO ---");
        LocalDate dtInicio = InputUtil.lerData("Informe a data de início (dd/MM/yyyy): ");
        LocalDate dtFim = InputUtil.lerData("Informe a data de fim (dd/MM/yyyy): ");

        String sql = "SELECT v.id_venda, v.data_venda, c.nome AS cliente, p.nome AS produto, " +
                     "iv.qtd_vendida, iv.preco_unitario, " +
                     "(iv.qtd_vendida * iv.preco_unitario) AS subtotal, v.valor_total " +
                     "FROM venda v " +
                     "JOIN cliente c ON v.id_cliente = c.id_cliente " +
                     "JOIN item_venda iv ON iv.id_venda = v.id_venda " +
                     "JOIN produto p ON iv.id_produto = p.id_produto " +
                     "WHERE v.data_venda BETWEEN ? AND ? " +
                     "ORDER BY v.data_venda, v.id_venda";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setObject(1, dtInicio);
            stmt.setObject(2, dtFim);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    System.out.println("Nenhuma venda encontrada no período.");
                    return;
                }

                System.out.println(String.format("\n%-5s | %-12s | %-20s | %-20s | %-4s | %-12s | %-12s", 
                        "ID", "DATA", "CLIENTE", "PRODUTO", "QTD", "PREÇO UNIT.", "SUBTOTAL"));
                System.out.println("------------------------------------------------------------------------------------------------------");

                BigDecimal totalGeral = BigDecimal.ZERO;
                int ultimaVendaId = -1;

                while (rs.next()) {
                    int idVenda = rs.getInt("id_venda");
                    LocalDate dataVenda = rs.getDate("data_venda").toLocalDate();
                    String cliente = rs.getString("cliente");
                    String produto = rs.getString("produto");
                    int qtd = rs.getInt("qtd_vendida");
                    BigDecimal precoUnit = rs.getBigDecimal("preco_unitario");
                    BigDecimal subtotal = rs.getBigDecimal("subtotal");
                    BigDecimal valorTotalVenda = rs.getBigDecimal("valor_total");

                    System.out.println(String.format("%-5d | %-12s | %-20s | %-20s | %-4d | R$ %-9.2f | R$ %-9.2f",
                            idVenda, dataVenda, cliente, produto, qtd, precoUnit, subtotal));

                    if (idVenda != ultimaVendaId) {
                        totalGeral = totalGeral.add(valorTotalVenda);
                        ultimaVendaId = idVenda;
                    }
                }
                System.out.println("------------------------------------------------------------------------------------------------------");
                System.out.println("Total Geral (Soma das vendas): R$ " + totalGeral);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao gerar relatório de vendas: " + e.getMessage());
        }
    }

    public void relatorioProdutosEstoque() {
        System.out.println("\n--- RELATÓRIO: PRODUTOS E ESTOQUE ---");
        System.out.println("1 - Todos os produtos");
        System.out.println("2 - Filtrar por categoria");
        System.out.println("3 - Filtrar por marca");
        System.out.println("4 - Produtos com estoque abaixo de...");
        System.out.println("0 - Voltar");
        
        int opcao = InputUtil.lerInt("Escolha o filtro: ");
        if (opcao == 0) return;

        String sqlBase = "SELECT p.id_produto, p.nome, m.nome AS marca, cat.nome AS categoria, " +
                         "p.tamanho, p.cor, p.preco, p.quantidade_estoque " +
                         "FROM produto p " +
                         "JOIN marca m ON p.id_marca = m.id_marca " +
                         "JOIN categoria cat ON p.id_categoria = cat.id_categoria ";
        String sqlOrder = " ORDER BY cat.nome, p.nome";

        int paramId = -1;
        int paramQtd = -1;

        if (opcao == 2) {
            CategoriaDAO cDao = new CategoriaDAO();
            List<Categoria> cats = cDao.listarTodos();
            for (Categoria c : cats) System.out.println(c.getIdCategoria() + " - " + c.getNome());
            paramId = InputUtil.lerInt("Informe o ID da categoria: ");
            sqlBase += "WHERE p.id_categoria = ?";
        } else if (opcao == 3) {
            MarcaDAO mDao = new MarcaDAO();
            List<Marca> marcas = mDao.listarTodos();
            for (Marca m : marcas) System.out.println(m.getIdMarca() + " - " + m.getNome());
            paramId = InputUtil.lerInt("Informe o ID da marca: ");
            sqlBase += "WHERE p.id_marca = ?";
        } else if (opcao == 4) {
            paramQtd = InputUtil.lerInt("Informe a quantidade mínima: ");
            sqlBase += "WHERE p.quantidade_estoque < ?";
        }

        String sql = sqlBase + sqlOrder;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (opcao == 2 || opcao == 3) {
                stmt.setInt(1, paramId);
            } else if (opcao == 4) {
                stmt.setInt(1, paramQtd);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    System.out.println("Nenhum produto encontrado com os filtros informados.");
                    return;
                }

                System.out.println(String.format("\n%-5s | %-25s | %-15s | %-15s | %-8s | %-10s | %-10s | %-8s", 
                        "ID", "PRODUTO", "MARCA", "CATEGORIA", "TAMANHO", "COR", "PREÇO", "ESTOQUE"));
                System.out.println("-----------------------------------------------------------------------------------------------------------------");

                while (rs.next()) {
                    System.out.println(String.format("%-5d | %-25s | %-15s | %-15s | %-8s | %-10s | R$ %-7.2f | %-8d",
                            rs.getInt("id_produto"), rs.getString("nome"), rs.getString("marca"),
                            rs.getString("categoria"), rs.getString("tamanho"), rs.getString("cor"),
                            rs.getBigDecimal("preco"), rs.getInt("quantidade_estoque")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao gerar relatório de estoque: " + e.getMessage());
        }
    }

    public void relatorioComprasPorFornecedor() {
        System.out.println("\n--- RELATÓRIO: COMPRAS POR FORNECEDOR ---");
        System.out.println("1 - Por período");
        System.out.println("2 - Por fornecedor");
        System.out.println("0 - Voltar");
        
        int opcao = InputUtil.lerInt("Escolha o filtro: ");
        if (opcao == 0) return;

        String sql = "SELECT f.nome AS fornecedor, co.id_compra, co.data_compra, " +
                     "p.nome AS produto, ic.qtd_comprada, ic.preco_unitario, " +
                     "(ic.qtd_comprada * ic.preco_unitario) AS subtotal, co.valor_total " +
                     "FROM compra co " +
                     "JOIN fornecedor f ON co.id_fornecedor = f.id_fornecedor " +
                     "JOIN item_compra ic ON ic.id_compra = co.id_compra " +
                     "JOIN produto p ON ic.id_produto = p.id_produto ";

        LocalDate dtInicio = null, dtFim = null;
        int idFornecedor = -1;

        if (opcao == 1) {
            dtInicio = InputUtil.lerData("Data de início (dd/MM/yyyy): ");
            dtFim = InputUtil.lerData("Data de fim (dd/MM/yyyy): ");
            sql += "WHERE co.data_compra BETWEEN ? AND ? ";
        } else if (opcao == 2) {
            FornecedorDAO fDao = new FornecedorDAO();
            List<Fornecedor> forns = fDao.listarTodos();
            for (Fornecedor f : forns) System.out.println(f.getIdFornecedor() + " - " + f.getNome());
            idFornecedor = InputUtil.lerInt("Informe o ID do fornecedor: ");
            sql += "WHERE co.id_fornecedor = ? ";
        }

        sql += "ORDER BY f.nome, co.data_compra";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (opcao == 1) {
                stmt.setObject(1, dtInicio);
                stmt.setObject(2, dtFim);
            } else if (opcao == 2) {
                stmt.setInt(1, idFornecedor);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    System.out.println("Nenhuma compra encontrada.");
                    return;
                }

                System.out.println(String.format("\n%-20s | %-10s | %-12s | %-20s | %-4s | %-12s | %-12s", 
                        "FORNECEDOR", "ID COMPRA", "DATA", "PRODUTO", "QTD", "PREÇO UNIT.", "SUBTOTAL"));
                System.out.println("------------------------------------------------------------------------------------------------------");

                while (rs.next()) {
                    System.out.println(String.format("%-20s | %-10d | %-12s | %-20s | %-4d | R$ %-9.2f | R$ %-9.2f",
                            rs.getString("fornecedor"), rs.getInt("id_compra"), rs.getDate("data_compra").toLocalDate(),
                            rs.getString("produto"), rs.getInt("qtd_comprada"), rs.getBigDecimal("preco_unitario"),
                            rs.getBigDecimal("subtotal")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao gerar relatório de compras: " + e.getMessage());
        }
    }
}
