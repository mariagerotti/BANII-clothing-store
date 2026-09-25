package model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Classe que representa uma Venda na loja.
 */
public class Venda {
    private int idVenda;
    private LocalDate dataVenda;
    private BigDecimal valorTotal;
    private int idCliente;

    public Venda() {}

    public Venda(LocalDate dataVenda, BigDecimal valorTotal, int idCliente) {
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
        this.idCliente = idCliente;
    }

    public Venda(int idVenda, LocalDate dataVenda, BigDecimal valorTotal, int idCliente) {
        this.idVenda = idVenda;
        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
        this.idCliente = idCliente;
    }

    public int getIdVenda() { return idVenda; }
    public void setIdVenda(int idVenda) { this.idVenda = idVenda; }

    public LocalDate getDataVenda() { return dataVenda; }
    public void setDataVenda(LocalDate dataVenda) { this.dataVenda = dataVenda; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    @Override
    public String toString() {
        return "Venda{" +
                "idVenda=" + idVenda +
                ", dataVenda=" + dataVenda +
                ", valorTotal=" + valorTotal +
                ", idCliente=" + idCliente +
                '}';
    }
}
