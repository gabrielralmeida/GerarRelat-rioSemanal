package br.com.fiap.gerarrelatorio.lambda;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import br.com.fiap.gerarrelatorio.dto.GerarRelatorioResponse;
import br.com.fiap.gerarrelatorio.service.GerarRelatorioService;

@ApplicationScoped
@Named("gerarRelatorioSemanal")
public class GerarRelatorioSemanalLambda implements RequestHandler<Object, GerarRelatorioResponse> {

    private final GerarRelatorioService service;

    @Inject
    public GerarRelatorioSemanalLambda(GerarRelatorioService service) {
        this.service = service;
    }

    @Override
    public GerarRelatorioResponse handleRequest(Object input, Context context) {
        return service.gerar();
    }
}
