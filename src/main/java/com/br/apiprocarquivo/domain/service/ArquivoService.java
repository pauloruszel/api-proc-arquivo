package com.br.apiprocarquivo.domain.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ArquivoService {

    void processarArquivo(MultipartFile arquivo) throws IOException;

}