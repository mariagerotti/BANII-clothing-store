package model;

import java.math.BigDecimal;

/**
 * Classe que representa um Item de Compra.
 */
public class ItemCompra {
    private int idItemCompra;
    private int idCompra;
    private int idProduto;
    private int qtdComprada;
    private BigDecimal precoUnitario;

    public ItemCompra() {}

    public ItemCompra(int idCompra, int idProduto, int qtdComprada, BigDecimal precoUnitario) {
        this.idCompra = idCompra;
        this.idProduto = idProduto;
        this.qtdComprada = qtdComprada;
        this.precoUnitario = precoUnitario;
    }

    public ItemCompra(int idItemCompra, int idCompra, int idProduto, int qtdComprada, BigDecimal precoUnitario) {
        this.idItemCompra = idItemCompra;
        this.idCompra = idCompra;
        this.idProduto = idProduto;
        this.qtdComprada = qtdComprada;
        this.precoUnitario = precoUnitario;
    }

    public int getIdItemCompra() { return idItemCompra; }
    public void setIdItemCompra(int idItemCompra) { this.idItemCompra = idItemCompra; }

    public int getIdCompra() { return idCompra; }
    public void setIdCompra(int idCompra) { this.idCompra = idCompra; }

    public int getIdProduto() { return idProduto; }
    public void setIdProduto(int idProduto) { this.idProduto = idProduto; }

    public int getQtdComprada() { return qtdComprada; }
    public void setQtdComprada(int qtdComprada) { this.qtdComprada = qtdComprada; }

    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }

    @Override
    public String toString() {
        return "ItemCompra{" +
                "idItemCompra=" + idItemCompra +
                ", idCompra=" + idCompra +
                ", idProduto=" + idProduto +
                ", qtdComprada=" + qtdComprada +
                ", precoUnitario=" + precoUnitario +
                '}';
    }
}
