import dao.*;
import model.*;
import service.CompraService;
import service.RelatorioService;
import service.VendaService;
import util.InputUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {

    private static ClienteDAO clienteDAO = new ClienteDAO();
    private static ProdutoDAO produtoDAO = new ProdutoDAO();
    private static MarcaDAO marcaDAO = new MarcaDAO();
    private static CategoriaDAO categoriaDAO = new CategoriaDAO();
    private static FornecedorDAO fornecedorDAO = new FornecedorDAO();
    private static VendaDAO vendaDAO = new VendaDAO();
    private static CompraDAO compraDAO = new CompraDAO();

    private static VendaService vendaService = new VendaService();
    private static CompraService compraService = new CompraService();
    private static RelatorioService relatorioService = new RelatorioService();

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n========================================");
            System.out.println("       LOJA DE ROUPAS FEMININAS       ");
            System.out.println("========================================");
            System.out.println("1 - Clientes");
            System.out.println("2 - Produtos");
            System.out.println("3 - Marcas");
            System.out.println("4 - Categorias");
            System.out.println("5 - Fornecedores");
            System.out.println("6 - Compras");
            System.out.println("7 - Vendas");
            System.out.println("8 - Relatórios");
            System.out.println("0 - Sair");
            
            opcao = InputUtil.lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1: menuClientes(); break;
                case 2: menuProdutos(); break;
                case 3: menuMarcas(); break;
                case 4: menuCategorias(); break;
                case 5: menuFornecedores(); break;
                case 6: menuCompras(); break;
                case 7: menuVendas(); break;
                case 8: menuRelatorios(); break;
                case 0: System.out.println("Saindo do sistema..."); break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void menuClientes() {
        int opcao;
        do {
            System.out.println("\n--- CLIENTES ---");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Listar clientes");
            System.out.println("3 - Buscar cliente por ID");
            System.out.println("4 - Atualizar cliente");
            System.out.println("5 - Excluir cliente");
            System.out.println("0 - Voltar");
            
            opcao = InputUtil.lerInt("Escolha: ");
            switch (opcao) {
                case 1:
                    Cliente cNovo = new Cliente();
                    cNovo.setNome(InputUtil.lerString("Nome: "));
                    cNovo.setCpf(InputUtil.lerString("CPF: "));
                    cNovo.setTelefone(InputUtil.lerString("Telefone: "));
                    cNovo.setEmail(InputUtil.lerString("Email: "));
                    cNovo.setEndereco(InputUtil.lerString("Endereço: "));
                    clienteDAO.inserir(cNovo);
                    System.out.println("Cliente cadastrado com sucesso!");
                    break;
                case 2:
                    List<Cliente> clientes = clienteDAO.listarTodos();
                    if (clientes.isEmpty()) {
                        System.out.println("Nenhum cliente cadastrado.");
                    } else {
                        System.out.println("ID | Nome | CPF");
                        for (Cliente c : clientes) {
                            System.out.println(c.getIdCliente() + " | " + c.getNome() + " | " + c.getCpf());
                        }
                    }
                    break;
                case 3:
                    int idBusca = InputUtil.lerInt("ID do cliente: ");
                    Cliente c = clienteDAO.buscarPorId(idBusca);
                    if (c != null) {
                        System.out.println("ID: " + c.getIdCliente() + ", Nome: " + c.getNome() + 
                                ", CPF: " + c.getCpf() + ", Tel: " + c.getTelefone() + 
                                ", Email: " + c.getEmail() + ", Endereço: " + c.getEndereco());
                    } else {
                        System.out.println("Cliente não encontrado.");
                    }
                    break;
                case 4:
                    int idAtualizar = InputUtil.lerInt("ID do cliente a atualizar: ");
                    Cliente cAtualizar = clienteDAO.buscarPorId(idAtualizar);
                    if (cAtualizar != null) {
                        System.out.println("Deixe em branco para manter o valor atual.");
                        
                        String nome = InputUtil.lerStringOpcional("Nome [" + cAtualizar.getNome() + "]: ");
                        if (!nome.isEmpty()) cAtualizar.setNome(nome);
                        
                        String cpf = InputUtil.lerStringOpcional("CPF [" + cAtualizar.getCpf() + "]: ");
                        if (!cpf.isEmpty()) cAtualizar.setCpf(cpf);
                        
                        String telefone = InputUtil.lerStringOpcional("Telefone [" + cAtualizar.getTelefone() + "]: ");
                        if (!telefone.isEmpty()) cAtualizar.setTelefone(telefone);
                        
                        String email = InputUtil.lerStringOpcional("Email [" + cAtualizar.getEmail() + "]: ");
                        if (!email.isEmpty()) cAtualizar.setEmail(email);
                        
                        String endereco = InputUtil.lerStringOpcional("Endereço [" + cAtualizar.getEndereco() + "]: ");
                        if (!endereco.isEmpty()) cAtualizar.setEndereco(endereco);
                        
                        clienteDAO.atualizar(cAtualizar);
                        System.out.println("Cliente atualizado com sucesso!");
                    } else {
                        System.out.println("Cliente não encontrado.");
                    }
                    break;
                case 5:
                    int idExcluir = InputUtil.lerInt("ID do cliente a excluir: ");
                    Cliente cExcluir = clienteDAO.buscarPorId(idExcluir);
                    if (cExcluir != null) {
                        System.out.println("Cliente: " + cExcluir.getNome());
                        String conf = InputUtil.lerString("Confirma exclusão? (s/n): ");
                        if (conf.equalsIgnoreCase("s")) {
                            clienteDAO.excluir(idExcluir);
                            System.out.println("Cliente excluído.");
                        } else {
                            System.out.println("Cancelado.");
                        }
                    } else {
                        System.out.println("Cliente não encontrado.");
                    }
                    break;
                case 0: break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void menuProdutos() {
        int opcao;
        do {
            System.out.println("\n--- PRODUTOS ---");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Listar produtos");
            System.out.println("3 - Buscar produto por ID");
            System.out.println("4 - Atualizar produto");
            System.out.println("5 - Excluir produto");
            System.out.println("0 - Voltar");
            
            opcao = InputUtil.lerInt("Escolha: ");
            switch (opcao) {
                case 1:
                    Produto pNovo = new Produto();
                    pNovo.setNome(InputUtil.lerString("Nome: "));
                    pNovo.setDescricao(InputUtil.lerString("Descrição: "));
                    pNovo.setPreco(InputUtil.lerBigDecimal("Preço: "));
                    pNovo.setTamanho(InputUtil.lerString("Tamanho: "));
                    pNovo.setQuantidadeEstoque(InputUtil.lerInt("Qtd Estoque Inicial: "));
                    pNovo.setCor(InputUtil.lerString("Cor: "));
                    
                    List<Marca> marcas = marcaDAO.listarTodos();
                    if (marcas.isEmpty()) { System.out.println("Cadastre uma marca primeiro."); break; }
                    for (Marca m : marcas) System.out.println(m.getIdMarca() + " - " + m.getNome());
                    pNovo.setIdMarca(InputUtil.lerInt("ID da Marca: "));
                    
                    List<Categoria> categorias = categoriaDAO.listarTodos();
                    if (categorias.isEmpty()) { System.out.println("Cadastre uma categoria primeiro."); break; }
                    for (Categoria c : categorias) System.out.println(c.getIdCategoria() + " - " + c.getNome());
                    pNovo.setIdCategoria(InputUtil.lerInt("ID da Categoria: "));

                    produtoDAO.inserir(pNovo);
                    System.out.println("Produto cadastrado com sucesso!");
                    break;
                case 2:
                    List<Produto> produtos = produtoDAO.listarTodos();
                    if (produtos.isEmpty()) {
                        System.out.println("Nenhum produto cadastrado.");
                    } else {
                        System.out.println(String.format("%-5s | %-25s | %-15s | %-15s | %-10s", "ID", "NOME", "MARCA", "CATEGORIA", "PREÇO"));
                        for (Produto p : produtos) {
                            Marca m = marcaDAO.buscarPorId(p.getIdMarca());
                            Categoria c = categoriaDAO.buscarPorId(p.getIdCategoria());
                            System.out.println(String.format("%-5d | %-25s | %-15s | %-15s | R$ %-7.2f",
                                    p.getIdProduto(), p.getNome(), 
                                    (m!=null?m.getNome():""), (c!=null?c.getNome():""), p.getPreco()));
                        }
                    }
                    break;
                case 3:
                    int idBusca = InputUtil.lerInt("ID do produto: ");
                    Produto p = produtoDAO.buscarPorId(idBusca);
                    if (p != null) {
                        System.out.println("ID: " + p.getIdProduto() + ", Nome: " + p.getNome() + 
                                "\nDescrição: " + p.getDescricao() + "\nPreço: " + p.getPreco() +
                                ", Tam: " + p.getTamanho() + ", Cor: " + p.getCor() + ", Estoque: " + p.getQuantidadeEstoque() +
                                "\nID Marca: " + p.getIdMarca() + ", ID Categoria: " + p.getIdCategoria());
                    } else {
                        System.out.println("Produto não encontrado.");
                    }
                    break;
                case 4:
                    int idAtualizar = InputUtil.lerInt("ID do produto a atualizar: ");
                    Produto pAtualizar = produtoDAO.buscarPorId(idAtualizar);
                    if (pAtualizar != null) {
                        System.out.println("Deixe em branco/0 para manter o valor atual.");
                        
                        String nome = InputUtil.lerStringOpcional("Nome [" + pAtualizar.getNome() + "]: ");
                        if (!nome.isEmpty()) pAtualizar.setNome(nome);
                        
                        String desc = InputUtil.lerStringOpcional("Descrição [" + pAtualizar.getDescricao() + "]: ");
                        if (!desc.isEmpty()) pAtualizar.setDescricao(desc);
                        
                        System.out.println("Preço atual: " + pAtualizar.getPreco());
                        BigDecimal preco = InputUtil.lerBigDecimal("Novo preço (ou digite 0 para manter): ");
                        if (preco.compareTo(BigDecimal.ZERO) > 0) pAtualizar.setPreco(preco);
                        
                        String tam = InputUtil.lerStringOpcional("Tamanho [" + pAtualizar.getTamanho() + "]: ");
                        if (!tam.isEmpty()) pAtualizar.setTamanho(tam);
                        
                        String cor = InputUtil.lerStringOpcional("Cor [" + pAtualizar.getCor() + "]: ");
                        if (!cor.isEmpty()) pAtualizar.setCor(cor);
                        
                        produtoDAO.atualizar(pAtualizar);
                        System.out.println("Produto atualizado com sucesso!");
                    } else {
                        System.out.println("Produto não encontrado.");
                    }
                    break;
                case 5:
                    int idExcluir = InputUtil.lerInt("ID do produto a excluir: ");
                    Produto pExcluir = produtoDAO.buscarPorId(idExcluir);
                    if (pExcluir != null) {
                        System.out.println("Produto: " + pExcluir.getNome());
                        String conf = InputUtil.lerString("Confirma exclusão? (s/n): ");
                        if (conf.equalsIgnoreCase("s")) {
                            produtoDAO.excluir(idExcluir);
                            System.out.println("Produto excluído.");
                        } else {
                            System.out.println("Cancelado.");
                        }
                    } else {
                        System.out.println("Produto não encontrado.");
                    }
                    break;
                case 0: break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void menuMarcas() {
        int opcao;
        do {
            System.out.println("\n--- MARCAS ---");
            System.out.println("1 - Cadastrar marca");
            System.out.println("2 - Listar marcas");
            System.out.println("3 - Buscar marca por ID");
            System.out.println("4 - Atualizar marca");
            System.out.println("5 - Excluir marca");
            System.out.println("0 - Voltar");
            
            opcao = InputUtil.lerInt("Escolha: ");
            switch (opcao) {
                case 1:
                    Marca m = new Marca();
                    m.setNome(InputUtil.lerString("Nome da marca: "));
                    marcaDAO.inserir(m);
                    System.out.println("Marca cadastrada!");
                    break;
                case 2:
                    for (Marca marca : marcaDAO.listarTodos()) {
                        System.out.println(marca.getIdMarca() + " - " + marca.getNome());
                    }
                    break;
                case 3:
                    Marca mBusca = marcaDAO.buscarPorId(InputUtil.lerInt("ID da marca: "));
                    if (mBusca != null) System.out.println("ID: " + mBusca.getIdMarca() + ", Nome: " + mBusca.getNome());
                    else System.out.println("Marca não encontrada.");
                    break;
                case 4:
                    Marca mAt = marcaDAO.buscarPorId(InputUtil.lerInt("ID da marca a atualizar: "));
                    if (mAt != null) {
                        String nNome = InputUtil.lerStringOpcional("Nome [" + mAt.getNome() + "]: ");
                        if (!nNome.isEmpty()) mAt.setNome(nNome);
                        marcaDAO.atualizar(mAt);
                        System.out.println("Marca atualizada.");
                    } else System.out.println("Não encontrada.");
                    break;
                case 5:
                    int idDel = InputUtil.lerInt("ID a excluir: ");
                    Marca mDel = marcaDAO.buscarPorId(idDel);
                    if (mDel != null) {
                        if (InputUtil.lerString("Confirma exclusão? (s/n): ").equalsIgnoreCase("s")) {
                            marcaDAO.excluir(idDel);
                            System.out.println("Excluída.");
                        }
                    } else System.out.println("Não encontrada.");
                    break;
                case 0: break;
                default: System.out.println("Inválido.");
            }
        } while (opcao != 0);
    }

    private static void menuCategorias() {
        int opcao;
        do {
            System.out.println("\n--- CATEGORIAS ---");
            System.out.println("1 - Cadastrar categoria");
            System.out.println("2 - Listar categorias");
            System.out.println("3 - Buscar categoria por ID");
            System.out.println("4 - Atualizar categoria");
            System.out.println("5 - Excluir categoria");
            System.out.println("0 - Voltar");
            
            opcao = InputUtil.lerInt("Escolha: ");
            switch (opcao) {
                case 1:
                    Categoria c = new Categoria();
                    c.setNome(InputUtil.lerString("Nome: "));
                    c.setDescricao(InputUtil.lerString("Descrição: "));
                    categoriaDAO.inserir(c);
                    System.out.println("Cadastrada!");
                    break;
                case 2:
                    for (Categoria cat : categoriaDAO.listarTodos()) {
                        System.out.println(cat.getIdCategoria() + " - " + cat.getNome() + " - " + cat.getDescricao());
                    }
                    break;
                case 3:
                    Categoria cBusca = categoriaDAO.buscarPorId(InputUtil.lerInt("ID: "));
                    if (cBusca != null) System.out.println(cBusca.getIdCategoria() + " - " + cBusca.getNome() + " - " + cBusca.getDescricao());
                    else System.out.println("Não encontrada.");
                    break;
                case 4:
                    Categoria cAt = categoriaDAO.buscarPorId(InputUtil.lerInt("ID a atualizar: "));
                    if (cAt != null) {
                        String nn = InputUtil.lerStringOpcional("Nome [" + cAt.getNome() + "]: ");
                        if (!nn.isEmpty()) cAt.setNome(nn);
                        String nd = InputUtil.lerStringOpcional("Descrição [" + cAt.getDescricao() + "]: ");
                        if (!nd.isEmpty()) cAt.setDescricao(nd);
                        categoriaDAO.atualizar(cAt);
                        System.out.println("Atualizada.");
                    } else System.out.println("Não encontrada.");
                    break;
                case 5:
                    int idDel = InputUtil.lerInt("ID a excluir: ");
                    Categoria cDel = categoriaDAO.buscarPorId(idDel);
                    if (cDel != null) {
                        if (InputUtil.lerString("Confirma exclusão? (s/n): ").equalsIgnoreCase("s")) {
                            categoriaDAO.excluir(idDel);
                            System.out.println("Excluída.");
                        }
                    } else System.out.println("Não encontrada.");
                    break;
                case 0: break;
                default: System.out.println("Inválido.");
            }
        } while (opcao != 0);
    }

    private static void menuFornecedores() {
        int opcao;
        do {
            System.out.println("\n--- FORNECEDORES ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Buscar por ID");
            System.out.println("4 - Atualizar");
            System.out.println("5 - Excluir");
            System.out.println("0 - Voltar");
            
            opcao = InputUtil.lerInt("Escolha: ");
            switch (opcao) {
                case 1:
                    Fornecedor f = new Fornecedor();
                    f.setNome(InputUtil.lerString("Nome: "));
                    f.setCnpj(InputUtil.lerString("CNPJ: "));
                    f.setTelefone(InputUtil.lerString("Telefone: "));
                    f.setEmail(InputUtil.lerString("Email: "));
                    f.setEndereco(InputUtil.lerString("Endereço: "));
                    fornecedorDAO.inserir(f);
                    System.out.println("Cadastrado!");
                    break;
                case 2:
                    for (Fornecedor forn : fornecedorDAO.listarTodos()) {
                        System.out.println(forn.getIdFornecedor() + " | " + forn.getNome() + " | " + forn.getCnpj());
                    }
                    break;
                case 3:
                    Fornecedor fBusca = fornecedorDAO.buscarPorId(InputUtil.lerInt("ID: "));
                    if (fBusca != null) System.out.println(fBusca.getIdFornecedor() + " - " + fBusca.getNome() + " - " + fBusca.getCnpj() + " - " + fBusca.getTelefone());
                    else System.out.println("Não encontrado.");
                    break;
                case 4:
                    Fornecedor fAt = fornecedorDAO.buscarPorId(InputUtil.lerInt("ID a atualizar: "));
                    if (fAt != null) {
                        String n = InputUtil.lerStringOpcional("Nome [" + fAt.getNome() + "]: ");
                        if (!n.isEmpty()) fAt.setNome(n);
                        String c = InputUtil.lerStringOpcional("CNPJ [" + fAt.getCnpj() + "]: ");
                        if (!c.isEmpty()) fAt.setCnpj(c);
                        String t = InputUtil.lerStringOpcional("Telefone [" + fAt.getTelefone() + "]: ");
                        if (!t.isEmpty()) fAt.setTelefone(t);
                        String e = InputUtil.lerStringOpcional("Email [" + fAt.getEmail() + "]: ");
                        if (!e.isEmpty()) fAt.setEmail(e);
                        String end = InputUtil.lerStringOpcional("Endereço [" + fAt.getEndereco() + "]: ");
                        if (!end.isEmpty()) fAt.setEndereco(end);
                        fornecedorDAO.atualizar(fAt);
                        System.out.println("Atualizado.");
                    } else System.out.println("Não encontrado.");
                    break;
                case 5:
                    int idDel = InputUtil.lerInt("ID a excluir: ");
                    Fornecedor fDel = fornecedorDAO.buscarPorId(idDel);
                    if (fDel != null) {
                        if (InputUtil.lerString("Confirma exclusão? (s/n): ").equalsIgnoreCase("s")) {
                            fornecedorDAO.excluir(idDel);
                            System.out.println("Excluído.");
                        }
                    } else System.out.println("Não encontrado.");
                    break;
                case 0: break;
                default: System.out.println("Inválido.");
            }
        } while (opcao != 0);
    }

    private static void menuCompras() {
        int opcao;
        do {
            System.out.println("\n--- COMPRAS ---");
            System.out.println("1 - Registrar compra");
            System.out.println("2 - Consultar compras");
            System.out.println("3 - Atualizar compra");
            System.out.println("4 - Excluir compra");
            System.out.println("0 - Voltar");
            
            opcao = InputUtil.lerInt("Escolha: ");
            switch (opcao) {
                case 1: compraService.registrarCompra(); break;
                case 2: compraService.consultarCompras(); break;
                case 3: 
                    int id = InputUtil.lerInt("ID da compra a atualizar: ");
                    Compra c = compraDAO.buscarPorId(id);
                    if (c != null) {
                        System.out.println("Data atual: " + c.getDataCompra());
                        LocalDate novaData = InputUtil.lerData("Nova data (dd/MM/yyyy) ou erro para manter: ");
                        if (novaData != null) c.setDataCompra(novaData);
                        System.out.println("Fornecedor atual: " + c.getIdFornecedor());
                        int fId = InputUtil.lerInt("Novo ID Fornecedor (0 p/ manter): ");
                        if (fId > 0) c.setIdFornecedor(fId);
                        compraDAO.atualizar(c);
                        System.out.println("Atualizada.");
                    } else System.out.println("Não encontrada.");
                    break;
                case 4:
                    int idExcluir = InputUtil.lerInt("ID da compra a excluir: ");
                    if (compraDAO.buscarPorId(idExcluir) != null) {
                        if (InputUtil.lerString("Confirma exclusão (excluirá os itens)? (s/n): ").equalsIgnoreCase("s")) {
                            compraDAO.excluir(idExcluir);
                            System.out.println("Excluída.");
                        }
                    } else System.out.println("Não encontrada.");
                    break;
                case 0: break;
                default: System.out.println("Inválido.");
            }
        } while (opcao != 0);
    }

    private static void menuVendas() {
        int opcao;
        do {
            System.out.println("\n--- VENDAS ---");
            System.out.println("1 - Realizar venda");
            System.out.println("2 - Consultar vendas");
            System.out.println("3 - Atualizar venda");
            System.out.println("4 - Excluir venda");
            System.out.println("0 - Voltar");
            
            opcao = InputUtil.lerInt("Escolha: ");
            switch (opcao) {
                case 1: vendaService.realizarVenda(); break;
                case 2: vendaService.consultarVendas(); break;
                case 3:
                    int id = InputUtil.lerInt("ID da venda a atualizar: ");
                    Venda v = vendaDAO.buscarPorId(id);
                    if (v != null) {
                        System.out.println("Data atual: " + v.getDataVenda());
                        LocalDate novaData = InputUtil.lerData("Nova data (dd/MM/yyyy) ou erro para manter: ");
                        if (novaData != null) v.setDataVenda(novaData);
                        System.out.println("Cliente atual: " + v.getIdCliente());
                        int cId = InputUtil.lerInt("Novo ID Cliente (0 p/ manter): ");
                        if (cId > 0) v.setIdCliente(cId);
                        vendaDAO.atualizar(v);
                        System.out.println("Atualizada.");
                    } else System.out.println("Não encontrada.");
                    break;
                case 4:
                    int idExcluir = InputUtil.lerInt("ID da venda a excluir: ");
                    if (vendaDAO.buscarPorId(idExcluir) != null) {
                        if (InputUtil.lerString("Confirma exclusão (excluirá os itens)? (s/n): ").equalsIgnoreCase("s")) {
                            vendaDAO.excluir(idExcluir);
                            System.out.println("Excluída.");
                        }
                    } else System.out.println("Não encontrada.");
                    break;
                case 0: break;
                default: System.out.println("Inválido.");
            }
        } while (opcao != 0);
    }

    private static void menuRelatorios() {
        int opcao;
        do {
            System.out.println("\n--- RELATÓRIOS ---");
            System.out.println("1 - Vendas por período");
            System.out.println("2 - Produtos e estoque");
            System.out.println("3 - Compras por fornecedor");
            System.out.println("0 - Voltar");
            
            opcao = InputUtil.lerInt("Escolha: ");
            switch (opcao) {
                case 1: relatorioService.relatorioVendasPorPeriodo(); break;
                case 2: relatorioService.relatorioProdutosEstoque(); break;
                case 3: relatorioService.relatorioComprasPorFornecedor(); break;
                case 0: break;
                default: System.out.println("Inválido.");
            }
        } while (opcao != 0);
    }
}
