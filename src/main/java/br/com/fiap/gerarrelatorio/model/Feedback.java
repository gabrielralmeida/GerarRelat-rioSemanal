package br.com.fiap.gerarrelatorio.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TbFeedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Sequencia", nullable = false)
    private Integer sequencia;

    @Column(name = "CodigoCurso", nullable = false)
    private Integer codigoCurso;

    @Column(name = "Observacao", nullable = false, length = 100)
    private String observacao;

    @Column(name = "DataHora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "Nota", nullable = false)
    private Integer nota;

    protected Feedback() {
    }

    public Feedback(Integer codigoCurso, LocalDateTime dataHora, Integer nota) {
        this.codigoCurso = codigoCurso;
        this.observacao = "";
        this.dataHora = dataHora;
        this.nota = nota;
    }

    public Integer getCodigoCurso() {
        return codigoCurso;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Integer getNota() {
        return nota;
    }
}
