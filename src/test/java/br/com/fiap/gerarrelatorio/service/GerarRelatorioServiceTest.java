package br.com.fiap.gerarrelatorio.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.fiap.gerarrelatorio.dto.GerarRelatorioResponse;
import br.com.fiap.gerarrelatorio.model.Curso;
import br.com.fiap.gerarrelatorio.model.Feedback;
import br.com.fiap.gerarrelatorio.model.Relatorio;
import br.com.fiap.gerarrelatorio.store.MemoriaRelatorioSemanalStore;

class GerarRelatorioServiceTest {

    private static final LocalDateTime AGORA = LocalDateTime.of(2026, 11, 15, 23, 0);
    private static final LocalDateTime INICIO = AGORA.minusDays(7);

    private MemoriaRelatorioSemanalStore store;
    private GerarRelatorioService service;

    @BeforeEach
    void setUp() {
        store = new MemoriaRelatorioSemanalStore();
        Clock clock = Clock.fixed(Instant.parse("2026-11-16T02:00:00Z"), ZoneId.of("America/Sao_Paulo"));
        service = new GerarRelatorioService(store, clock);
        store.adicionarCurso(new Curso(1, "Arquitetura Cloud Native", 40));
        store.adicionarCurso(new Curso(2, "Desenvolvimento Java com Quarkus", 80));
    }

    @Test
    void calculaMediaPorCursoNaJanelaDeSeteDias() {
        store.adicionarFeedback(feedback(1, INICIO.minusSeconds(1), 10));
        store.adicionarFeedback(feedback(1, INICIO, 8));
        store.adicionarFeedback(feedback(1, AGORA.minusSeconds(1), 9));
        store.adicionarFeedback(feedback(1, AGORA, 1));
        store.adicionarFeedback(feedback(2, INICIO.plusDays(3), 2));
        store.adicionarFeedback(feedback(2, INICIO.plusDays(3), 2));
        store.adicionarFeedback(feedback(2, INICIO.plusDays(4), 1));
        store.adicionarFeedback(feedback(3, INICIO.minusDays(1), 6));

        GerarRelatorioResponse resposta = service.gerar();

        assertTrue(resposta.sucesso());
        assertEquals(List.of(), resposta.mensagens());
        assertEquals(2, resposta.quantidade());
        assertEquals(INICIO, store.inicioConsultado());
        assertEquals(AGORA, store.fimConsultado());
        assertEquals(
                List.of(
                        item(1, "Arquitetura Cloud Native", 40, 8.50),
                        item(2, "Desenvolvimento Java com Quarkus", 80, new BigDecimal("1.67").doubleValue())),
                resposta.relatorios());
        assertEquals(1, store.gravacoes());
        assertEquals(resposta.relatorios().stream().map(GerarRelatorioResponse.RelatorioItem::codigoCurso).toList(),
                store.gravados().stream().map(Relatorio::getCodigoCurso).toList());
    }

    @Test
    void semanaSemFeedbackNaoGravaRelatorio() {
        GerarRelatorioResponse resposta = service.gerar();

        assertTrue(resposta.sucesso());
        assertEquals(0, resposta.quantidade());
        assertEquals(List.of(), resposta.relatorios());
        assertEquals(List.of(), resposta.mensagens());
        assertEquals(0, store.gravacoes());
    }

    @Test
    void cursoInexistenteInterrompeAGravacao() {
        store.adicionarFeedback(feedback(1, INICIO.plusDays(1), 8));
        store.adicionarFeedback(feedback(9, INICIO.plusDays(1), 7));
        store.adicionarFeedback(feedback(4, INICIO.plusDays(2), 6));

        GerarRelatorioResponse resposta = service.gerar();

        assertFalse(resposta.sucesso());
        assertEquals(0, resposta.quantidade());
        assertEquals(List.of(), resposta.relatorios());
        assertEquals(
                List.of("Não foi possível gerar o relatório porque os cursos 4, 9 não existem na TbCursos."),
                resposta.mensagens());
        assertEquals(0, store.gravacoes());
    }

    private static Feedback feedback(int codigoCurso, LocalDateTime dataHora, int nota) {
        return new Feedback(codigoCurso, dataHora, nota);
    }

    private static GerarRelatorioResponse.RelatorioItem item(
            int codigoCurso,
            String nomeCurso,
            int qtdeHoras,
            double media) {
        return new GerarRelatorioResponse.RelatorioItem(
                codigoCurso,
                nomeCurso,
                GerarRelatorioService.CODIGO_PROFESSOR_AUSENTE,
                GerarRelatorioService.NOME_PROFESSOR_AUSENTE,
                qtdeHoras,
                media,
                AGORA);
    }
}
