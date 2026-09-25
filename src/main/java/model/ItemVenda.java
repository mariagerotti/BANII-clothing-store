package model;

import java.math.BigDecimal;

/**
 * Classe que representa um Item de Venda.
 */
public class ItemVenda {
    private int idItemVenda;
    private int idVenda;
    private int idProduto;
    private int qtdVendida;
    private BigDecimal precoUnitario;

    public ItemVenda() {}

    public ItemVenda(int idVenda, int idProduto, int qtdVendida, BigDecimal precoUnitario) {
        this.idVenda = idVenda;
        this.idProduto = idProduto;
        this.qtdVendida = qtdVendida;
        this.precoUnitario = precoUnitario;
    }

    public ItemVenda(int idItemVenda, int idVenda, int idProduto, int qtdVendida, BigDecimal precoUnitario) {
        this.idItemVenda = idItemVenda;
        this.idVenda = idVenda;
        this.idProduto = idProduto;
        this.qtdVendida = qtdVendida;
        this.precoUnitario = precoUnitario;
    }

    public int getIdItemVenda() { return idItemVenda; }
    public void setIdItemVenda(int idItemVenda) { this.idItemVenda = idItemVenda; }

    public int getIdVenda() { return idVenda; }
    public void setIdVenda(int idVenda) { this.idVenda = idVenda; }

    public int getIdProduto() { return idProduto; }
    public void setIdProduto(int idProduto) { this.idProduto = idProduto; }

    public int getQtdVendida() { return qtdVendida; }
    public void setQtdVendida(int qtdVendida) { this.qtdVendida = qtdVendida; }

    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }

    @Override
    public String toString() {
        return "ItemVenda{" +
                "idItemVenda=" + idItemVenda +
                ", idVenda=" + idVenda +
                ", idProduto=" + idProduto +
                ", qtdVendida=" + qtdVendida +
                ", precoUnitario=" + precoUnitario +
                '}';
    }
}
