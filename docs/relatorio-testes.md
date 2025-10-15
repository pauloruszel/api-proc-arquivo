# Relatório de testes unitários

* Comando executado: `mvn test`
* Resultado: Falha ao resolver dependências do parent `spring-boot-starter-parent:3.2.4`.
* Mensagem: HTTP 403 ao acessar `https://repo.maven.apache.org/maven2`.
* Próximos passos sugeridos: configurar mirror/local repository com acesso liberado ou disponibilizar cache offline para dependências Maven.
