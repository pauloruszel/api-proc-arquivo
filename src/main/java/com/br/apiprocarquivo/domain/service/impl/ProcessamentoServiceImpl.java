package com.br.apiprocarquivo.domain.service.impl;

import com.br.apiprocarquivo.domain.enums.ProcessamentoErroStatus;
import com.br.apiprocarquivo.domain.model.Processamento;
import com.br.apiprocarquivo.domain.model.ProcessamentoErro;
import com.br.apiprocarquivo.domain.repository.ProcessamentoErroRepository;
import com.br.apiprocarquivo.domain.repository.ProcessamentoRepository;
import com.br.apiprocarquivo.domain.service.ProcessamentoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.br.apiprocarquivo.domain.enums.ProcessamentoStatus.CONCLUIDO;
import static com.br.apiprocarquivo.domain.enums.ProcessamentoStatus.PARCIAL;
import static com.br.apiprocarquivo.infrastructure.helper.ArquivoHelper.isRowComplete;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessamentoServiceImpl implements ProcessamentoService {

    private final ProcessamentoRepository processamentoRepository;
    private final ProcessamentoErroRepository processamentoErroRepository;

    @Override
    public boolean processarLinha(final Row row, final Processamento processamento, final int posicaoLinha) {
        log.info("inicio do processamento da linha {}.", posicaoLinha);

        if (row == null) {
            log.warn("linha nula encontrada na posicao {}.", posicaoLinha);
            salvarErroDeProcessamento(processamento, "Erro de validação ou linha vazia na linha " + posicaoLinha);
            return false;
        }

        if (!isRowComplete(row)) {
            log.error("dados incompletos ou linha vazia detectada na linha {}.", row.getRowNum());
            salvarErroDeProcessamento(processamento, "Erro de validação ou linha vazia na linha " + row.getRowNum());
            return false;
        }
        log.info("fim do processamento da linha {}.", posicaoLinha);
        return true;
    }

    @Override
    public void atualizarStatusProcessamento(final int linhasProcessadasComSucesso, final Sheet sheet, Processamento processamento) {
        log.info("inicio da atualizacao do status processamento para o arquivo {}.", processamento.getNomeArquivo());

        final var resultadoSheet = sheet.getPhysicalNumberOfRows() - 1;
        final var status = linhasProcessadasComSucesso == resultadoSheet ? CONCLUIDO : PARCIAL;
        processamento.setStatus(status);
        processamento.setQuantidadeProcessada(linhasProcessadasComSucesso);
        processamento.setTotalRegistros(resultadoSheet);
        processamentoRepository.save(processamento);

        log.info("fim da atualizacao do status processamento para o arquivo {} data {} e status {}",
                processamento.getNomeArquivo(),
                processamento.getData(),
                processamento.getStatus());
    }

    private void salvarErroDeProcessamento(final Processamento processamento, final String mensagemErro) {
        log.info("inicio do registro erro de processamento para o arquivo {}, erro: {}", processamento.getNomeArquivo(), mensagemErro);

        final var erro = ProcessamentoErro.builder()
                .processamento(processamento)
                .status(ProcessamentoErroStatus.ERRO_DE_VALIDACAO)
                .data(LocalDateTime.now())
                .payload(mensagemErro)
                .build();

        processamentoErroRepository.save(erro);
        log.info("fim do registro erro de processamento para o arquivo {}", processamento.getNomeArquivo());
    }
}
