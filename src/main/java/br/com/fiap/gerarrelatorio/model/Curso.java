package br.com.fiap.gerarrelatorio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TbCursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CodigoCurso", nullable = false)
    private Integer codigoCurso;

    @Column(name = "NomeCurso", nullable = false, length = 100)
    private String nomeCurso;

    @Column(name = "QtdeHoras", nullable = false)
    private Integer qtdeHoras;

    protected Curso() {
    }

    public Curso(Integer codigoCurso, String nomeCurso, Integer qtdeHoras) {
        this.codigoCurso = codigoCurso;
        this.nomeCurso = nomeCurso;
        this.qtdeHoras = qtdeHoras;
    }

    public Integer getCodigoCurso() {
        return codigoCurso;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public Integer getQtdeHoras() {
        return qtdeHoras;
    }
}
