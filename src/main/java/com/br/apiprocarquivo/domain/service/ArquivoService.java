package com.br.apiprocarquivo.domain.service;

import com.br.apiprocarquivo.domain.model.Processamento;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ArquivoService {

    Processamento iniciarProcessamento(MultipartFile arquivo) throws IOException;

}