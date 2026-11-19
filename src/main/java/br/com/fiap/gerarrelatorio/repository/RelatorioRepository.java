package br.com.fiap.gerarrelatorio.repository;

import jakarta.enterprise.context.ApplicationScoped;

import br.com.fiap.gerarrelatorio.model.Relatorio;
import br.com.fiap.gerarrelatorio.model.RelatorioId;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class RelatorioRepository implements PanacheRepositoryBase<Relatorio, RelatorioId> {
}
