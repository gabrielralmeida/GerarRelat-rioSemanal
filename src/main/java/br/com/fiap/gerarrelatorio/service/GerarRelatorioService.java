package br.com.fiap.gerarrelatorio.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import br.com.fiap.gerarrelatorio.dto.GerarRelatorioResponse;
import br.com.fiap.gerarrelatorio.model.Curso;
import br.com.fiap.gerarrelatorio.model.Feedback;
import br.com.fiap.gerarrelatorio.model.Relatorio;
import br.com.fiap.gerarrelatorio.store.RelatorioSemanalStore;

@ApplicationScoped
public class GerarRelatorioService {

    static final int CODIGO_PROFESSOR_AUSENTE = 0;
    static final String NOME_PROFESSOR_AUSENTE = "";

    private final RelatorioSemanalStore store;
    private final Clock clock;

    @Inject
    public GerarRelatorioService(RelatorioSemanalStore store, Clock clock) {
        this.store = store;
        this.clock = clock;
    }

    public GerarRelatorioResponse gerar() {
        LocalDateTime agora = LocalDateTime.now(clock);
        LocalDateTime inicio = agora.minusDays(7);
        List<Feedback> feedbacks = store.listarFeedbacks(inicio, agora);
        if (feedbacks.isEmpty()) {
            return GerarRelatorioResponse.ok(List.of());
        }

        Map<Integer, List<Feedback>> porCurso = feedbacks.stream()
                .collect(Collectors.groupingBy(Feedback::getCodigoCurso));
        Map<Integer, Curso> cursos = store.buscarCursos(porCurso.keySet()).stream()
                .collect(Collectors.toMap(Curso::getCodigoCurso, Function.identity()));

        List<Integer> ausentes = porCurso.keySet().stream()
                .filter(codigo -> !cursos.containsKey(codigo))
                .sorted()
                .toList();
        if (!ausentes.isEmpty()) {
            return GerarRelatorioResponse.erro(List.of(mensagemCursoAusente(ausentes)));
        }

        List<Relatorio> relatorios = new ArrayList<>();
        for (Integer codigoCurso : porCurso.keySet().stream().sorted().toList()) {
            Curso curso = cursos.get(codigoCurso);
            relatorios.add(new Relatorio(
                    curso.getCodigoCurso(),
                    curso.getNomeCurso(),
                    CODIGO_PROFESSOR_AUSENTE,
                    NOME_PROFESSOR_AUSENTE,
                    curso.getQtdeHoras(),
                    media(porCurso.get(codigoCurso)),
                    agora));
        }
        store.gravar(relatorios);
        return GerarRelatorioResponse.ok(relatorios);
    }

    private static double media(List<Feedback> feedbacks) {
        int soma = feedbacks.stream().mapToInt(Feedback::getNota).sum();
        return BigDecimal.valueOf(soma)
                .divide(BigDecimal.valueOf(feedbacks.size()), 2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private static String mensagemCursoAusente(List<Integer> ausentes) {
        String codigos = ausentes.stream().map(String::valueOf).collect(Collectors.joining(", "));
        if (ausentes.size() == 1) {
            return "Não foi possível gerar o relatório porque o curso " + codigos + " não existe na TbCursos.";
        }
        return "Não foi possível gerar o relatório porque os cursos " + codigos + " não existem na TbCursos.";
    }
}
