package br.com.fiap.gerarrelatorio.config;

import java.time.Clock;
import java.time.ZoneId;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class RelogioProducer {

    @Produces
    @ApplicationScoped
    public Clock clock() {
        return Clock.system(ZoneId.of("America/Sao_Paulo"));
    }
}
