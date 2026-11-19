package br.com.fiap.gerarrelatorio.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "TbRelatorio")
public class Relatorio {

    @EmbeddedId
    private RelatorioId id;

    @Column(name = "NomeCurso", nullable = false, length = 100)
    private String nomeCurso;

    @Column(name = "NomeProfessor", nullable = false, length = 100)
    private String nomeProfessor;

    @Column(name = "QtdeHoras", nullable = false)
    private Integer qtdeHoras;

    @Column(name = "MediaSemanalCurso", nullable = false)
    private Double mediaSemanalCurso;

    protected Relatorio() {
    }

    public Relatorio(
            Integer codigoCurso,
            String nomeCurso,
            Integer codigoProfessor,
            String nomeProfessor,
            Integer qtdeHoras,
            Double mediaSemanalCurso,
            LocalDateTime dataHoraGeracao) {
        this.id = new RelatorioId(codigoCurso, codigoProfessor, dataHoraGeracao);
        this.nomeCurso = nomeCurso;
        this.nomeProfessor = nomeProfessor;
        this.qtdeHoras = qtdeHoras;
        this.mediaSemanalCurso = mediaSemanalCurso;
    }

    public Integer getCodigoCurso() {
        return id.getCodigoCurso();
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public Integer getCodigoProfessor() {
        return id.getCodigoProfessor();
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public Integer getQtdeHoras() {
        return qtdeHoras;
    }

    public Double getMediaSemanalCurso() {
        return mediaSemanalCurso;
    }

    public LocalDateTime getDataHoraGeracao() {
        return id.getDataHoraGeracao();
    }
}
