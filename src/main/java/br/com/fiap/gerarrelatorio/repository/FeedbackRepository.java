package br.com.fiap.gerarrelatorio.repository;

import jakarta.enterprise.context.ApplicationScoped;

import br.com.fiap.gerarrelatorio.model.Feedback;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class FeedbackRepository implements PanacheRepositoryBase<Feedback, Integer> {
}
