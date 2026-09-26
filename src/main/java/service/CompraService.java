package service;

import dao.FornecedorDAO;
import dao.ItemCompraDAO;
import dao.ProdutoDAO;
import dao.CompraDAO;
import model.Fornecedor;
import model.ItemCompra;
import model.Produto;
import model.Compra;
import util.ConnectionFactory;
import util.InputUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompraService {

    private FornecedorDAO fornecedorDAO = new FornecedorDAO();
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private CompraDAO compraDAO = new CompraDAO();
    private ItemCompraDAO itemCompraDAO = new ItemCompraDAO();

    public void registrarCompra() {
        System.out.println("--- REGISTRAR COMPRA ---");
        List<Fornecedor> fornecedores = fornecedorDAO.listarTodos();
        if (fornecedores.isEmpty()) {
            System.out.println("Nenhum fornecedor cadastrado. Cadastre um fornecedor primeiro.");
            return;
        }

        System.out.println("Fornecedores disponíveis:");
        for (Fornecedor f : fornecedores) {
            System.out.println(f.getIdFornecedor() + " - " + f.getNome() + " (CNPJ: " + f.getCnpj() + ")");
        }

        int idFornecedor = InputUtil.lerInt("Informe o ID do fornecedor: ");
        Fornecedor fornecedor = fornecedorDAO.buscarPorId(idFornecedor);

        if (fornecedor == null) {
            System.out.println("Fornecedor não encontrado.");
            return;
        }

        List<ItemCompra> itens = new ArrayList<>();
        boolean continuar = true;

        while (continuar) {
            List<Produto> produtos = produtoDAO.listarTodos();
            if (produtos.isEmpty()) {
                System.out.println("Nenhum produto cadastrado no momento.");
                return;
            }

            System.out.println("\nProdutos disponíveis:");
            System.out.println(String.format("%-5s | %-30s | %-10s", "ID", "NOME", "ESTOQUE ATUAL"));
            for (Produto p : produtos) {
                System.out.println(String.format("%-5d | %-30s | %-10d", 
                        p.getIdProduto(), p.getNome(), p.getQuantidadeEstoque()));
            }

            int idProduto = InputUtil.lerInt("\nInforme o ID do produto: ");
            Produto produto = produtoDAO.buscarPorId(idProduto);

            if (produto == null) {
                System.out.println("Produto não encontrado.");
                continue;
            }

            int qtd = InputUtil.lerIntPositivo("Informe a quantidade comprada: ");
            BigDecimal precoUnit = InputUtil.lerBigDecimal("Informe o preço unitário (atacado): ");

            ItemCompra item = new ItemCompra();
            item.setIdProduto(produto.getIdProduto());
            item.setQtdComprada(qtd);
            item.setPrecoUnitario(precoUnit);
            itens.add(item);

            System.out.println("Produto adicionado à compra.");

            String resposta = InputUtil.lerString("Deseja adicionar mais produtos? (s/n): ");
            if (!resposta.equalsIgnoreCase("s")) {
                continuar = false;
            }
        }

        if (itens.isEmpty()) {
            System.out.println("Nenhum produto foi adicionado. Compra cancelada.");
            return;
        }

        System.out.println("\n--- RESUMO DA COMPRA ---");
        System.out.println("Fornecedor: " + fornecedor.getNome());
        System.out.println("Data: " + LocalDate.now());
        System.out.println("Itens:");

        BigDecimal totalGeral = BigDecimal.ZERO;
        for (ItemCompra ic : itens) {
            Produto p = produtoDAO.buscarPorId(ic.getIdProduto());
            BigDecimal subtotal = ic.getPrecoUnitario().multiply(new BigDecimal(ic.getQtdComprada()));
            totalGeral = totalGeral.add(subtotal);
            System.out.println(String.format("- %s | Qtd: %d | Preço Unit: R$ %.2f | Subtotal: R$ %.2f", 
                    p.getNome(), ic.getQtdComprada(), ic.getPrecoUnitario(), subtotal));
        }
        System.out.println("Total da Compra: R$ " + totalGeral);

        String conf = InputUtil.lerString("Confirma a compra? (s/n): ");
        if (conf.equalsIgnoreCase("s")) {
            Connection conn = null;
            try {
                conn = ConnectionFactory.getConnection();
                conn.setAutoCommit(false);

                Compra compra = new Compra();
                compra.setDataCompra(LocalDate.now());
                compra.setValorTotal(BigDecimal.ZERO);
                compra.setIdFornecedor(fornecedor.getIdFornecedor());

                int idCompra = compraDAO.inserir(compra, conn);
                BigDecimal valorTotalCompra = BigDecimal.ZERO;

                for (ItemCompra ic : itens) {
                    ic.setIdCompra(idCompra);
                    itemCompraDAO.inserir(ic, conn);

                    Produto p = produtoDAO.buscarPorId(ic.getIdProduto());
                    int novoEstoque = p.getQuantidadeEstoque() + ic.getQtdComprada();
                    produtoDAO.atualizarEstoque(p.getIdProduto(), novoEstoque, conn);

                    valorTotalCompra = valorTotalCompra.add(ic.getPrecoUnitario().multiply(new BigDecimal(ic.getQtdComprada())));
                }

                compraDAO.atualizarValorTotal(idCompra, valorTotalCompra, conn);
                conn.commit();
                System.out.println("Compra registrada com sucesso! ID da compra: " + idCompra + ", Total: R$ " + valorTotalCompra);

            } catch (SQLException e) {
                if (conn != null) {
                    try {
                        conn.rollback();
                        System.out.println("Erro na transação. Rollback executado.");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                System.out.println("Erro ao registrar a compra: " + e.getMessage());
            } finally {
                if (conn != null) {
                    try {
                        conn.setAutoCommit(true);
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("Compra cancelada.");
        }
    }

    public void consultarCompras() {
        System.out.println("--- CONSULTAR COMPRAS ---");
        List<Compra> compras = compraDAO.listarTodos();
        if (compras.isEmpty()) {
            System.out.println("Nenhuma compra registrada.");
            return;
        }

        System.out.println("1 - Listar todas as compras");
        System.out.println("2 - Buscar compra por ID");
        int op = InputUtil.lerInt("Escolha uma opção: ");

        if (op == 1) {
            System.out.println(String.format("%-5s | %-12s | %-30s | %-15s", "ID", "DATA", "FORNECEDOR", "VALOR TOTAL"));
            for (Compra c : compras) {
                Fornecedor f = fornecedorDAO.buscarPorId(c.getIdFornecedor());
                String nomeFornecedor = (f != null) ? f.getNome() : "Desconhecido";
                System.out.println(String.format("%-5d | %-12s | %-30s | R$ %-12.2f", 
                        c.getIdCompra(), c.getDataCompra(), nomeFornecedor, c.getValorTotal()));
            }
        } else if (op == 2) {
            int idBusca = InputUtil.lerInt("Informe o ID da compra: ");
            Compra c = compraDAO.buscarPorId(idBusca);
            if (c != null) {
                Fornecedor f = fornecedorDAO.buscarPorId(c.getIdFornecedor());
                System.out.println("\n--- DETALHES DA COMPRA ---");
                System.out.println("ID: " + c.getIdCompra());
                System.out.println("Data: " + c.getDataCompra());
                System.out.println("Fornecedor: " + (f != null ? f.getNome() : "Desconhecido"));
                System.out.println("Valor Total: R$ " + c.getValorTotal());
                System.out.println("Itens:");

                List<ItemCompra> itens = itemCompraDAO.listarPorCompra(c.getIdCompra());
                for (ItemCompra ic : itens) {
                    Produto p = produtoDAO.buscarPorId(ic.getIdProduto());
                    System.out.println("- " + (p != null ? p.getNome() : "Produto ID " + ic.getIdProduto()) + 
                            " | Qtd: " + ic.getQtdComprada() + " | Preço Unit: R$ " + ic.getPrecoUnitario());
                }
            } else {
                System.out.println("Compra não encontrada.");
            }
        } else {
            System.out.println("Opção inválida.");
        }
    }
}
