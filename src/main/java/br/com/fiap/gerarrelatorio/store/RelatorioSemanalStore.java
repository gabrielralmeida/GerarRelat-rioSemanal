package br.com.fiap.gerarrelatorio.store;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import br.com.fiap.gerarrelatorio.model.Curso;
import br.com.fiap.gerarrelatorio.model.Feedback;
import br.com.fiap.gerarrelatorio.model.Relatorio;

public interface RelatorioSemanalStore {

    List<Feedback> listarFeedbacks(LocalDateTime inicioInclusive, LocalDateTime fimExclusivo);

    List<Curso> buscarCursos(Collection<Integer> codigos);

    void gravar(List<Relatorio> relatorios);
}
