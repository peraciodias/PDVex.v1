package br.com.creativex.presentation.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class VendaResumo {

    private final long idVenda;
    private final Timestamp dataVenda;
    private final String operador;
    private final BigDecimal totalLiquido;
    private final String status;

    public VendaResumo(long idVenda, Timestamp dataVenda, String operador, BigDecimal totalLiquido, String status) {
        this.idVenda = idVenda;
        this.dataVenda = dataVenda;
        this.operador = operador;
        this.totalLiquido = totalLiquido;
        this.status = status;
    }

    public long getIdVenda() {
        return idVenda;
    }

    public Timestamp getDataVenda() {
        return dataVenda;
    }

    public String getOperador() {
        return operador;
    }

    public BigDecimal getTotalLiquido() {
        return totalLiquido;
    }

    public String getStatus() {
        return status;
    }
}
