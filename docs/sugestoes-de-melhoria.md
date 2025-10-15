# Sugestões de melhoria

## Robustez e observabilidade
- Tratar o `Workbook` de forma segura usando try-with-resources ou fechamento explícito após o processamento. Isso evita vazamentos de memória/descritores porque a instância criada em `WorkbookFactory.create` não é fechada atualmente em `ArquivoServiceImpl.processarArquivo`. 
- Centralizar mensagens de erro retornadas pelo controller em um mecanismo de tradução e códigos de erro padronizados. Hoje, `ProcessamentoController.uploadArquivo` devolve textos literais, o que dificulta internacionalização e automação de clientes.

## Performance e escalabilidade
- Revisar a lógica de limite de registros: o incremento (`++totalRegistros`) antes da comparação faz com que o laço pare um item antes do limite configurado. Uma comparação pós-processamento ou a condição `>` evitaria descarte indevido da última linha.
- Avaliar o uso de `SXSSFWorkbook` (streaming) ou Apache POI em modo streaming para arquivos grandes. A abordagem atual carrega toda a planilha em memória (`WorkbookFactory.create` + `sheet.getLastRowNum()`), o que aumenta o uso de heap.
- Otimizar as idas ao banco no método `salvarDadosSeNecessario` consolidando buscas repetidas (ex.: caches transacionais) e fazendo batch inserts quando possível.

## Experiência do usuário
- Tornar o upload assíncrono (por filas ou eventos) para liberar rapidamente a requisição HTTP. O endpoint `/api/v1/arquivos/upload` hoje fica bloqueado durante todo o processamento, o que pode causar timeouts em arquivos grandes.
- Expor endpoints de acompanhamento do processamento (status, erros por linha) para que o cliente consiga monitorar o andamento sem consultar diretamente o banco.

## Configuração e segurança
- Externalizar credenciais sensíveis (`spring.datasource.password`) para variáveis de ambiente ou secrets management e documentar claramente no README.
- Parametrizar o `processamento.limite.registros` por perfil/ambiente e registrar no banco o limite efetivamente usado, permitindo auditoria.
