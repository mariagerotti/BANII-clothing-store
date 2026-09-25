package model;

import java.math.BigDecimal;

/**
 * Classe que representa um Produto na loja.
 */
public class Produto {
    private int idProduto;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String tamanho;
    private int quantidadeEstoque;
    private String cor;
    private int idMarca;
    private int idCategoria;

    public Produto() {}

    public Produto(String nome, String descricao, BigDecimal preco, String tamanho, int quantidadeEstoque, String cor, int idMarca, int idCategoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.tamanho = tamanho;
        this.quantidadeEstoque = quantidadeEstoque;
        this.cor = cor;
        this.idMarca = idMarca;
        this.idCategoria = idCategoria;
    }

    public Produto(int idProduto, String nome, String descricao, BigDecimal preco, String tamanho, int quantidadeEstoque, String cor, int idMarca, int idCategoria) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.tamanho = tamanho;
        this.quantidadeEstoque = quantidadeEstoque;
        this.cor = cor;
        this.idMarca = idMarca;
        this.idCategoria = idCategoria;
    }

    public int getIdProduto() { return idProduto; }
    public void setIdProduto(int idProduto) { this.idProduto = idProduto; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }

    public String getTamanho() { return tamanho; }
    public void setTamanho(String tamanho) { this.tamanho = tamanho; }

    public int getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(int quantidadeEstoque) { this.quantidadeEstoque = quantidadeEstoque; }

    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    public int getIdMarca() { return idMarca; }
    public void setIdMarca(int idMarca) { this.idMarca = idMarca; }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    @Override
    public String toString() {
        return "Produto{" +
                "idProduto=" + idProduto +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", tamanho='" + tamanho + '\'' +
                ", quantidadeEstoque=" + quantidadeEstoque +
                ", cor='" + cor + '\'' +
                ", idMarca=" + idMarca +
                ", idCategoria=" + idCategoria +
                '}';
    }
}
