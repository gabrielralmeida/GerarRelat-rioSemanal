package br.com.fiap.gerarrelatorio.repository;

import jakarta.enterprise.context.ApplicationScoped;

import br.com.fiap.gerarrelatorio.model.Curso;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class CursoRepository implements PanacheRepositoryBase<Curso, Integer> {
}
