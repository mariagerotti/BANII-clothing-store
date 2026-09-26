package service;

import dao.ClienteDAO;
import dao.ItemVendaDAO;
import dao.ProdutoDAO;
import dao.VendaDAO;
import model.Cliente;
import model.ItemVenda;
import model.Produto;
import model.Venda;
import util.ConnectionFactory;
import util.InputUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VendaService {
    
    private ClienteDAO clienteDAO = new ClienteDAO();
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private VendaDAO vendaDAO = new VendaDAO();
    private ItemVendaDAO itemVendaDAO = new ItemVendaDAO();

    public void realizarVenda() {
        System.out.println("--- REALIZAR VENDA ---");
        List<Cliente> clientes = clienteDAO.listarTodos();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado. Cadastre um cliente primeiro.");
            return;
        }

        System.out.println("Clientes disponíveis:");
        for (Cliente c : clientes) {
            System.out.println(c.getIdCliente() + " - " + c.getNome());
        }

        int idCliente = InputUtil.lerInt("Informe o ID do cliente: ");
        Cliente cliente = clienteDAO.buscarPorId(idCliente);
        
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        List<ItemVenda> itens = new ArrayList<>();
        boolean continuar = true;

        while (continuar) {
            List<Produto> produtos = produtoDAO.listarTodos();
            if (produtos.isEmpty()) {
                System.out.println("Nenhum produto cadastrado no momento.");
                return;
            }

            System.out.println("\nProdutos disponíveis:");
            System.out.println(String.format("%-5s | %-30s | %-10s | %-10s", "ID", "NOME", "PREÇO", "ESTOQUE"));
            for (Produto p : produtos) {
                System.out.println(String.format("%-5d | %-30s | R$ %-7.2f | %-10d", 
                        p.getIdProduto(), p.getNome(), p.getPreco(), p.getQuantidadeEstoque()));
            }

            int idProduto = InputUtil.lerInt("\nInforme o ID do produto: ");
            Produto produto = produtoDAO.buscarPorId(idProduto);

            if (produto == null) {
                System.out.println("Produto não encontrado.");
                continue;
            }

            int qtd = InputUtil.lerIntPositivo("Informe a quantidade: ");
            if (produto.getQuantidadeEstoque() < qtd) {
                System.out.println("Estoque insuficiente. Quantidade disponível: " + produto.getQuantidadeEstoque());
                continue;
            }

            ItemVenda item = new ItemVenda();
            item.setIdProduto(produto.getIdProduto());
            item.setQtdVendida(qtd);
            item.setPrecoUnitario(produto.getPreco());
            itens.add(item);
            
            System.out.println("Produto adicionado à venda.");

            String resposta = InputUtil.lerString("Deseja adicionar mais produtos? (s/n): ");
            if (!resposta.equalsIgnoreCase("s")) {
                continuar = false;
            }
        }

        if (itens.isEmpty()) {
            System.out.println("Nenhum produto foi adicionado. Venda cancelada.");
            return;
        }

        System.out.println("\n--- RESUMO DA VENDA ---");
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Data: " + LocalDate.now());
        System.out.println("Itens:");
        
        BigDecimal totalGeral = BigDecimal.ZERO;
        for (ItemVenda iv : itens) {
            Produto p = produtoDAO.buscarPorId(iv.getIdProduto());
            BigDecimal subtotal = iv.getPrecoUnitario().multiply(new BigDecimal(iv.getQtdVendida()));
            totalGeral = totalGeral.add(subtotal);
            System.out.println(String.format("- %s | Qtd: %d | Preço Unit: R$ %.2f | Subtotal: R$ %.2f", 
                    p.getNome(), iv.getQtdVendida(), iv.getPrecoUnitario(), subtotal));
        }
        System.out.println("Total da Venda: R$ " + totalGeral);

        String conf = InputUtil.lerString("Confirma a venda? (s/n): ");
        if (conf.equalsIgnoreCase("s")) {
            Connection conn = null;
            try {
                conn = ConnectionFactory.getConnection();
                conn.setAutoCommit(false);

                Venda venda = new Venda();
                venda.setDataVenda(LocalDate.now());
                venda.setValorTotal(BigDecimal.ZERO);
                venda.setIdCliente(cliente.getIdCliente());

                int idVenda = vendaDAO.inserir(venda, conn);
                BigDecimal valorTotalVenda = BigDecimal.ZERO;

                for (ItemVenda iv : itens) {
                    iv.setIdVenda(idVenda);
                    itemVendaDAO.inserir(iv, conn);

                    Produto p = produtoDAO.buscarPorId(iv.getIdProduto());
                    int novoEstoque = p.getQuantidadeEstoque() - iv.getQtdVendida();
                    produtoDAO.atualizarEstoque(p.getIdProduto(), novoEstoque, conn);

                    valorTotalVenda = valorTotalVenda.add(iv.getPrecoUnitario().multiply(new BigDecimal(iv.getQtdVendida())));
                }

                vendaDAO.atualizarValorTotal(idVenda, valorTotalVenda, conn);
                conn.commit();
                System.out.println("Venda registrada com sucesso! ID da venda: " + idVenda + ", Total: R$ " + valorTotalVenda);

            } catch (SQLException e) {
                if (conn != null) {
                    try {
                        conn.rollback();
                        System.out.println("Erro na transação. Rollback executado.");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                System.out.println("Erro ao registrar a venda: " + e.getMessage());
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
            System.out.println("Venda cancelada.");
        }
    }

    public void consultarVendas() {
        System.out.println("--- CONSULTAR VENDAS ---");
        List<Venda> vendas = vendaDAO.listarTodos();
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }

        System.out.println("1 - Listar todas as vendas");
        System.out.println("2 - Buscar venda por ID");
        int op = InputUtil.lerInt("Escolha uma opção: ");

        if (op == 1) {
            System.out.println(String.format("%-5s | %-12s | %-30s | %-15s", "ID", "DATA", "CLIENTE", "VALOR TOTAL"));
            for (Venda v : vendas) {
                Cliente c = clienteDAO.buscarPorId(v.getIdCliente());
                String nomeCliente = (c != null) ? c.getNome() : "Desconhecido";
                System.out.println(String.format("%-5d | %-12s | %-30s | R$ %-12.2f", 
                        v.getIdVenda(), v.getDataVenda(), nomeCliente, v.getValorTotal()));
            }
        } else if (op == 2) {
            int idBusca = InputUtil.lerInt("Informe o ID da venda: ");
            Venda v = vendaDAO.buscarPorId(idBusca);
            if (v != null) {
                Cliente c = clienteDAO.buscarPorId(v.getIdCliente());
                System.out.println("\n--- DETALHES DA VENDA ---");
                System.out.println("ID: " + v.getIdVenda());
                System.out.println("Data: " + v.getDataVenda());
                System.out.println("Cliente: " + (c != null ? c.getNome() : "Desconhecido"));
                System.out.println("Valor Total: R$ " + v.getValorTotal());
                System.out.println("Itens:");
                
                List<ItemVenda> itens = itemVendaDAO.listarPorVenda(v.getIdVenda());
                for (ItemVenda iv : itens) {
                    Produto p = produtoDAO.buscarPorId(iv.getIdProduto());
                    System.out.println("- " + (p != null ? p.getNome() : "Produto ID " + iv.getIdProduto()) + 
                            " | Qtd: " + iv.getQtdVendida() + " | Preço Unit: R$ " + iv.getPrecoUnitario());
                }
            } else {
                System.out.println("Venda não encontrada.");
            }
        } else {
            System.out.println("Opção inválida.");
        }
    }
}
