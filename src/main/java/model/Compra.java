package model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Classe que representa uma Compra feita pela loja a um fornecedor.
 */
public class Compra {
    private int idCompra;
    private LocalDate dataCompra;
    private BigDecimal valorTotal;
    private int idFornecedor;

    public Compra() {}

    public Compra(LocalDate dataCompra, BigDecimal valorTotal, int idFornecedor) {
        this.dataCompra = dataCompra;
        this.valorTotal = valorTotal;
        this.idFornecedor = idFornecedor;
    }

    public Compra(int idCompra, LocalDate dataCompra, BigDecimal valorTotal, int idFornecedor) {
        this.idCompra = idCompra;
        this.dataCompra = dataCompra;
        this.valorTotal = valorTotal;
        this.idFornecedor = idFornecedor;
    }

    public int getIdCompra() { return idCompra; }
    public void setIdCompra(int idCompra) { this.idCompra = idCompra; }

    public LocalDate getDataCompra() { return dataCompra; }
    public void setDataCompra(LocalDate dataCompra) { this.dataCompra = dataCompra; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public int getIdFornecedor() { return idFornecedor; }
    public void setIdFornecedor(int idFornecedor) { this.idFornecedor = idFornecedor; }

    @Override
    public String toString() {
        return "Compra{" +
                "idCompra=" + idCompra +
                ", dataCompra=" + dataCompra +
                ", valorTotal=" + valorTotal +
                ", idFornecedor=" + idFornecedor +
                '}';
    }
}
