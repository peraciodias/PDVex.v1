package br.com.creativex.domain.repository;

import br.com.creativex.domain.entity.venda.VendaResumo;

import java.util.List;

public interface VendaConsultaRepository {
    List<VendaResumo> listarVendas();
}
