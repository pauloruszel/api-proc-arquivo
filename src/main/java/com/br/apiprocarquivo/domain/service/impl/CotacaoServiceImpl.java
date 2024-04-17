package com.br.apiprocarquivo.domain.service.impl;

import com.br.apiprocarquivo.api.model.VeiculoCotacaoModel;
import com.br.apiprocarquivo.domain.model.Cotacao;
import com.br.apiprocarquivo.domain.repository.CotacaoRepository;
import com.br.apiprocarquivo.domain.service.CotacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CotacaoServiceImpl implements CotacaoService {

    private final CotacaoRepository cotacaoRepository;

    @Override
    public Cotacao salvarSeNaoExiste(VeiculoCotacaoModel model) {
        return cotacaoRepository.findByCodigoCotacaoAndAtivo(model.getCotacao(), true)
                .orElseGet(() -> {
                    final var cotacao = Cotacao.builder()
                            .codigoCotacao(model.getCotacao())
                            .fsc(model.getFsc())
                            .ocn(model.getOcn())
                            .pack(model.getPack())
                            .prazo(model.getPrazo())
                            .km(model.getKm())
                            .kmMensal(model.getKmMensal())
                            .mensalidade(model.getMensalidade())
                            .kmSuperior(model.getKmSuperior())
                            .pneus(model.getPneus())
                            .cooparticipacaoAcidente(model.getCooparticipacaoAcidente())
                            .cidadeCirculacao(model.getCidadeCirculacao())
                            .ativo(true)
                            .build();

                    return cotacaoRepository.save(cotacao);
                });
    }
}