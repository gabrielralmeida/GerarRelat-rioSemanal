package br.com.fiap.gerarrelatorio.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.com.fiap.gerarrelatorio.model.Relatorio;

public record GerarRelatorioResponse(
        boolean sucesso,
        int quantidade,
        List<RelatorioItem> relatorios,
        List<String> mensagens) {

    public GerarRelatorioResponse {
        relatorios = relatorios == null ? List.of() : List.copyOf(relatorios);
        mensagens = mensagens == null ? List.of() : List.copyOf(mensagens);
    }

    public static GerarRelatorioResponse erro(List<String> mensagens) {
        return new GerarRelatorioResponse(false, 0, List.of(), mensagens);
    }

    public static GerarRelatorioResponse ok(List<Relatorio> relatorios) {
        List<RelatorioItem> itens = relatorios.stream()
                .map(relatorio -> new RelatorioItem(
                        relatorio.getCodigoCurso(),
                        relatorio.getNomeCurso(),
                        relatorio.getCodigoProfessor(),
                        relatorio.getNomeProfessor(),
                        relatorio.getQtdeHoras(),
                        relatorio.getMediaSemanalCurso(),
                        relatorio.getDataHoraGeracao()))
                .toList();
        return new GerarRelatorioResponse(true, itens.size(), itens, List.of());
    }

    public record RelatorioItem(
            Integer codigoCurso,
            String nomeCurso,
            Integer codigoProfessor,
            String nomeProfessor,
            Integer qtdeHoras,
            Double mediaSemanalCurso,
            LocalDateTime dataHoraGeracao) {
    }
}
