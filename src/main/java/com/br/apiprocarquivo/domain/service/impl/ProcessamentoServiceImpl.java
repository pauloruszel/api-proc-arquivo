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
    public boolean processarLinha(final Row row, final Processamento processamento) {
        if (!isRowComplete(row)) {
            log.error("Dados incompletos ou linha vazia detectada na linha {}.", row.getRowNum());
            salvarErroDeProcessamento(processamento, "Erro de validação ou linha vazia na linha " + row.getRowNum());
            return false;
        }
        return true;
    }

    @Override
    public void atualizarStatusProcessamento(final int linhasProcessadasComSucesso, final Sheet sheet, Processamento processamento) {
        final var status = linhasProcessadasComSucesso == sheet.getPhysicalNumberOfRows() - 1 ? CONCLUIDO : PARCIAL;
        processamento.setStatus(status);
        processamento.setQuantidadeProcessada(linhasProcessadasComSucesso);
        processamento.setTotalRegistros(sheet.getPhysicalNumberOfRows() - 1);
        processamentoRepository.save(processamento);

        log.info("o processamento foi atualizado com o nome {}, data {} e status {}",
                processamento.getNomeArquivo(),
                processamento.getData(),
                processamento.getStatus());
    }

    private void salvarErroDeProcessamento(final Processamento processamento, final String mensagemErro) {
        final var erro = ProcessamentoErro.builder()
                .processamento(processamento)
                .status(ProcessamentoErroStatus.ERRO_DE_VALIDACAO)
                .data(LocalDateTime.now())
                .payload(mensagemErro)
                .build();

        processamentoErroRepository.save(erro);
    }
}
