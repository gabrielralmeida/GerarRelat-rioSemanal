package br.com.fiap.gerarrelatorio.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class RelatorioId implements Serializable {

    @Column(name = "CodigoCurso", nullable = false)
    private Integer codigoCurso;

    @Column(name = "CodigoProfessor", nullable = false)
    private Integer codigoProfessor;

    @Column(name = "DataHoraGeracao", nullable = false)
    private LocalDateTime dataHoraGeracao;

    protected RelatorioId() {
    }

    public RelatorioId(Integer codigoCurso, Integer codigoProfessor, LocalDateTime dataHoraGeracao) {
        this.codigoCurso = codigoCurso;
        this.codigoProfessor = codigoProfessor;
        this.dataHoraGeracao = dataHoraGeracao;
    }

    public Integer getCodigoCurso() {
        return codigoCurso;
    }

    public Integer getCodigoProfessor() {
        return codigoProfessor;
    }

    public LocalDateTime getDataHoraGeracao() {
        return dataHoraGeracao;
    }

    @Override
    public boolean equals(Object outro) {
        if (this == outro) {
            return true;
        }
        if (!(outro instanceof RelatorioId id)) {
            return false;
        }
        return Objects.equals(codigoCurso, id.codigoCurso)
                && Objects.equals(codigoProfessor, id.codigoProfessor)
                && Objects.equals(dataHoraGeracao, id.dataHoraGeracao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoCurso, codigoProfessor, dataHoraGeracao);
    }
}
