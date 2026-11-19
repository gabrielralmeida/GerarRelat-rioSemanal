package br.com.fiap.gerarrelatorio.store;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import br.com.fiap.gerarrelatorio.model.Curso;
import br.com.fiap.gerarrelatorio.model.Feedback;
import br.com.fiap.gerarrelatorio.model.Relatorio;
import br.com.fiap.gerarrelatorio.repository.CursoRepository;
import br.com.fiap.gerarrelatorio.repository.FeedbackRepository;
import br.com.fiap.gerarrelatorio.repository.RelatorioRepository;

@ApplicationScoped
public class RelatorioSemanalPanacheStore implements RelatorioSemanalStore {

    private final FeedbackRepository feedbacks;
    private final CursoRepository cursos;
    private final RelatorioRepository relatorios;

    @Inject
    public RelatorioSemanalPanacheStore(
            FeedbackRepository feedbacks,
            CursoRepository cursos,
            RelatorioRepository relatorios) {
        this.feedbacks = feedbacks;
        this.cursos = cursos;
        this.relatorios = relatorios;
    }

    @Override
    @Transactional
    public List<Feedback> listarFeedbacks(LocalDateTime inicioInclusive, LocalDateTime fimExclusivo) {
        return feedbacks.list("dataHora >= ?1 and dataHora < ?2", inicioInclusive, fimExclusivo);
    }

    @Override
    @Transactional
    public List<Curso> buscarCursos(Collection<Integer> codigos) {
        if (codigos == null || codigos.isEmpty()) {
            return List.of();
        }
        return cursos.list("codigoCurso in ?1", codigos);
    }

    @Override
    @Transactional
    public void gravar(List<Relatorio> relatoriosGerados) {
        if (relatoriosGerados == null || relatoriosGerados.isEmpty()) {
            return;
        }
        for (Relatorio relatorio : relatoriosGerados) {
            relatorios.persist(relatorio);
        }
        relatorios.getEntityManager().flush();
    }
}
