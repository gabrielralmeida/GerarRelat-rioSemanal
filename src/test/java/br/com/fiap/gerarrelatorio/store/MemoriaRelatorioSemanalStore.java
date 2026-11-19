package br.com.fiap.gerarrelatorio.store;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.fiap.gerarrelatorio.model.Curso;
import br.com.fiap.gerarrelatorio.model.Feedback;
import br.com.fiap.gerarrelatorio.model.Relatorio;

public final class MemoriaRelatorioSemanalStore implements RelatorioSemanalStore {

    private final List<Feedback> feedbacks = new ArrayList<>();
    private final Map<Integer, Curso> cursos = new LinkedHashMap<>();
    private final List<Relatorio> gravados = new ArrayList<>();
    private LocalDateTime inicioConsultado;
    private LocalDateTime fimConsultado;
    private int gravacoes;

    public void adicionarFeedback(Feedback feedback) {
        feedbacks.add(feedback);
    }

    public void adicionarCurso(Curso curso) {
        cursos.put(curso.getCodigoCurso(), curso);
    }

    public LocalDateTime inicioConsultado() {
        return inicioConsultado;
    }

    public LocalDateTime fimConsultado() {
        return fimConsultado;
    }

    public int gravacoes() {
        return gravacoes;
    }

    public List<Relatorio> gravados() {
        return List.copyOf(gravados);
    }

    @Override
    public List<Feedback> listarFeedbacks(LocalDateTime inicioInclusive, LocalDateTime fimExclusivo) {
        inicioConsultado = inicioInclusive;
        fimConsultado = fimExclusivo;
        return feedbacks.stream()
                .filter(feedback -> !feedback.getDataHora().isBefore(inicioInclusive))
                .filter(feedback -> feedback.getDataHora().isBefore(fimExclusivo))
                .toList();
    }

    @Override
    public List<Curso> buscarCursos(Collection<Integer> codigos) {
        if (codigos == null || codigos.isEmpty()) {
            return List.of();
        }
        return codigos.stream()
                .filter(cursos::containsKey)
                .map(cursos::get)
                .toList();
    }

    @Override
    public void gravar(List<Relatorio> relatorios) {
        gravacoes++;
        gravados.addAll(relatorios);
    }
}
